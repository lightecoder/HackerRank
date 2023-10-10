package gpt.chat.app.v2;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.*;
import java.util.Base64;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScreenProcessingApp {

    public static void main(String[] args) throws IOException, InterruptedException {

        String imageParserKey = "imageParserKey";
        String listenerPath = "provide here browser temporary location for screen shots";

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
                }
            }
            key.reset();
        }
    }

    private static void processImageParserRequest(Path imagePath, String imageParserKey) {
        String localImagePath = imagePath.toAbsolutePath().toString();

        try {
            // Read the image from the local file
            File imageFile = new File(localImagePath);

            // Perform OCR to extract text from the image
            String extractedText = extractTextFromImage(imageFile, imageParserKey);
            // TODO
            // - use here logic to send extractedText to #prompt-textarea and send to GPT chat
            // - next step delete screenshot image that we previously created.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String extractTextFromImage(File imageFile, String apiKey) throws IOException {
        try {
            URL obj = URI.create("https://api.ocr.space/parse/image").toURL();
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
}
