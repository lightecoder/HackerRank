package gpt.chat.app.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static gpt.chat.app.v1.KeytoolExecutor.*;
import static gpt.chat.app.v1.OpenSSLHelper.downloadCertificate;

/*
imageParser: K83462842888957
Cert URL: https://api.openai.com/v1/chat/completions , https://api.ocr.space/parse/image

- keytool -delete -alias gpt-alias -keystore /Users/bodiaky/Library/Java/JavaVirtualMachines/openjdk-20.0.1/Contents/Home/lib/security/cacerts -noprompt
- keytool -import -trustcacerts -file sni.cloudflaressl.com.cer -keystore /Users/bodiaky/Library/Java/JavaVirtualMachines/openjdk-20.0.1/Contents/Home/lib/security/cacerts -alias gpt-alias

- keytool -delete -alias imageParser-alias -keystore /Users/bodiaky/Library/Java/JavaVirtualMachines/openjdk-20.0.1/Contents/Home/lib/security/cacerts -noprompt
- keytool -import -trustcacerts -file api.ocr.space.cer -keystore /Users/bodiaky/Library/Java/JavaVirtualMachines/openjdk-20.0.1/Contents/Home/lib/security/cacerts -alias imageParser-alias

 */
public class ScreenProcessingApp {
    private static final String GPT_URL = "https://api.openai.com/v1/chat/completions";
    private static final String IMAGE_PARSER_URL = "https://api.ocr.space/parse/image";
    private static final String IMAGE_PARSER_ALIAS = "imageParser-alias";
    private static final String GPT_ALIAS = "gpt-alias";
    private static final String GPT_CERT_NAME = "sni.cloudflaressl.com.cer";
    public static final String IMAGE_PARSER_CERT_NAME = "api.ocr.space.cer";
    public static final String OUTPUT_CERTIFICATE_DIRECTORY = "src/main/resources/files-buffer/certificates/";
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("/Users/bodiaky/IdeaProjects/HackerRank/src/main/resources/properties.yaml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadCertificates();
    }

    private static void loadCertificates() {
        downloadCertificate(GPT_URL, GPT_CERT_NAME);
        saveCertificate(GPT_CERT_NAME, GPT_ALIAS);
        downloadCertificate(IMAGE_PARSER_URL, IMAGE_PARSER_CERT_NAME);
        saveCertificate(IMAGE_PARSER_CERT_NAME, IMAGE_PARSER_ALIAS);
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        String gptApiKey = (String) properties.get("gptApiKey");
        String imageParserKey = (String) properties.get("imageParserKey");
        String listenerPath = (String) properties.get("listenerPath");

        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path directory = Paths.get(listenerPath);
        directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path fileName = (Path) event.context();
                    if (fileName.toString().endsWith(".png")) {
                        processImageParserRequest(directory.resolve(fileName), imageParserKey);
                    }
                    if (fileName.toString().endsWith(".txt")) {
                        processGPTRequest(directory.resolve(fileName), gptApiKey);
                    }
                }
            }
            key.reset();
        }
    }

    private static void processImageParserRequest(Path imagePath, String imageParserKey) {
        String localImagePath = imagePath.toAbsolutePath().toString();
        String outputFilePath = "/Users/bodiaky/Downloads/"
                + UUID.randomUUID()
                + "-output.txt";

        try {
            // Read the image from the local file
            File imageFile = new File(localImagePath);

            // Perform OCR to extract text from the image
            String extractedText = extractTextFromImage(imageFile, imageParserKey);

            // Save extracted text to a text file
            if (extractedText != null) saveTextToFile(extractedText, outputFilePath);
            else System.out.printf("Image extractedText is null!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractTextFromImage(File imageFile, String apiKey) throws IOException {
        try {
            URL obj = URI.create(IMAGE_PARSER_URL).toURL();
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // Add request header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Create JSON payload
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("apikey", apiKey);
            postDataParams.put("isTable", "false");
            postDataParams.put("OCREngine", 2);
//            postDataParams.put("isOverlayRequired", "true"); // provide coordinates of words
            postDataParams.put("base64Image", "data:image/png;base64,".concat(encodeImageToBase64(imageFile)));

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(getPostDataString(postDataParams));
            wr.flush();
            wr.close();

            // Read response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return extractParsedText(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static String extractParsedText(String jsonData) {
        // Define the regex pattern
        String regexPattern = "ParsedText\":\"(.*?)\",\"ErrorMessage\"";

        // Compile the pattern
        Pattern pattern = Pattern.compile(regexPattern);

        // Create a Matcher and apply it to the input data
        Matcher matcher = pattern.matcher(jsonData);

        // Find the first match and extract the captured group
        String parsedText = null;
        if (matcher.find()) {
            parsedText = matcher.group(1); // Extract the content inside "ParsedText"
        }
        return parsedText;
    }

    public static String getPostDataString(JSONObject params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();
        while (itr.hasNext()) {
            String key = itr.next();
            Object value = params.get(key);
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }

    private static String encodeImageToBase64(File imageFile) throws IOException {
        byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    private static void saveTextToFile(String text, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            if (text != null) {
                // Split the text by newline characters and write each line separately
                String[] lines = text.split("\\\\n");
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine(); // Add a newline character
                }
            }
        }
    }

    private static final JSONArray CONVERSATION_HISTORY = new JSONArray();

    private static void processGPTRequest(Path filePath, String apiKey) {
        try {
            // Read content from the text file
            String fileContent = new String(Files.readAllBytes(filePath));
            CONVERSATION_HISTORY.put(new JSONObject().put("role", "user").put("content", fileContent));

            // Process content using GPT-4
            String generatedText = processGPT(apiKey, fileContent);

            CONVERSATION_HISTORY.put(new JSONObject().put("role", "assistant").put("content", generatedText));

            generatedText = formatResultString(generatedText, 100);
            System.out.println("\n ############################# \n");
            System.out.println("GPT answer: \n" + generatedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String formatResultString(String text, int maxLineLength) {
        StringBuilder formatted = new StringBuilder();

        String[] lines = text.split("\n");
        for (String line : lines) {
            if (line.length() <= maxLineLength) {
                formatted.append(line).append('\n');
                continue;
            }

            String[] words = line.split(" ");
            StringBuilder currentLine = new StringBuilder();
            for (String word : words) {
                if (currentLine.length() + word.length() > maxLineLength) {
                    formatted.append(currentLine.toString().trim()).append('\n');
                    currentLine.setLength(0);
                }
                currentLine.append(word).append(' ');
            }
            if (!currentLine.isEmpty()) {
                formatted.append(currentLine.toString().trim()).append('\n');
            }
        }

        return formatted.toString();
    }

    private static String processGPT(String apiKey, String inputText) {
        try {
            // Set up the API URL
            URL url = new URL(GPT_URL);

            // Create connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            // Use the conversationHistory for the messages in the request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4");
            requestBody.put("messages", CONVERSATION_HISTORY);

            // Set request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Get response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Create an ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Parse the JSON string
            JsonNode jsonNode = objectMapper.readTree(response.toString());

            // Extract the "content" field
            return jsonNode.at("/choices/0/message/content").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error processing with GPT-4: " + e.getMessage();
        }
    }
}
