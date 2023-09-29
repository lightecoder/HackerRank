package gpt.chat.app;

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

public class ScreenProcessingApp {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("/Users/bodiaky/IdeaProjects/HackerRank/src/main/resources/properties.yaml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            saveTextToFile(extractedText, outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractTextFromImage(File imageFile, String apiKey) throws IOException {
        try {
            // Set up the API URL
            String apiUrl = "https://api.ocr.space/parse/image";

            URL obj = URI.create(apiUrl).toURL();
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

    private static void processGPTRequest(Path filePath, String apiKey) {
        try {
            // Read content from the text file
            String fileContent = new String(Files.readAllBytes(filePath));

            // Process content using GPT-4
            String generatedText = processGPT(apiKey, fileContent);
            System.out.println("\n ############################# \n");
            System.out.println("GPT answer: \n" + generatedText);

            // Send email
//            sendEmail("4456602@gmail.com", "GPT Generated Text", generatedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processGPT(String apiKey, String inputText) {
        try {
            // Set up the API URL
            URL url = new URL("https://api.openai.com/v1/chat/completions");

            // Create connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            // Set request body

            // Create the JSON request body
            JSONObject requestBody = new JSONObject();
            requestBody.put("model", "gpt-4");
            requestBody.put("messages", new JSONArray()
                    .put(new JSONObject()
                            .put("role", "user")
                            .put("content", inputText)
                    )
            );

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
