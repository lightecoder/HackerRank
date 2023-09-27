package gpt.chat.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Properties;

public class ScreenProcessingAppTesseract {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("/Users/bodiaky/IdeaProjects/HackerRank/src/main/resources/properties.yaml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.setProperty("jna.library.path", "/Users/bodiaky/tools/lib");
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        String gptApiKey = (String) properties.get("gptApiKey");
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
                        processImageParserRequest(directory.resolve(fileName));
                    }
                    if (fileName.toString().endsWith(".txt")) {
                        processGPTRequest(directory.resolve(fileName), gptApiKey);
                    }
                }
            }
            key.reset();
        }
    }

    private static void processImageParserRequest(Path imagePath) {
        String localImagePath = imagePath.toAbsolutePath().toString();
        String outputFilePath = "/Users/bodiaky/Downloads/output.txt";

        try {
            // Read the image from the local file
            File imageFile = new File(localImagePath);

            // Perform OCR to extract text from the image
            String extractedText = extractTextFromImage(imageFile);

            // Save extracted text to a text file
            saveTextToFile(extractedText, outputFilePath);

            java.lang.System.out.println("Text extracted and saved to " + outputFilePath);
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
        }
    }

    private static String extractTextFromImage(File imageFile) throws TesseractException {
        Tesseract tesseract = new Tesseract();
        // Set the Tesseract data path (where the language files are located)
        tesseract.setDatapath("/usr/local/share/tessdata");

        // Perform OCR on the image
        return tesseract.doOCR(imageFile);
    }

    private static void saveTextToFile(String text, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(new File(filePath))) {
            fos.write(text.getBytes());
        }
    }

    private static void processGPTRequest(Path filePath, String apiKey) {
        try {
            // Read content from the text file
            String fileContent = new String(Files.readAllBytes(filePath));

            // Process content using GPT-3
            String generatedText = processGPT(apiKey, fileContent);
            java.lang.System.out.println("GPT answer: " + generatedText);

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
            requestBody.put("model", "gpt-3.5-turbo");
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
            return "Error processing with GPT-3.5 Turbo: " + e.getMessage();
        }
    }


    private static void sendEmail(String recipient, String subject, String content) {
        // Load Gmail SMTP settings from properties.yaml

        String smtpHost = (String) properties.get("smtpHost");
        String smtpPort = (String) properties.get("smtpPort");
        String senderEmail = (String) properties.get("senderEmail");
        String senderPassword = (String) properties.get("senderPassword");

        // Set up email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);

        // Create a Session object
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);

            // Send the message
            Transport.send(message);

            java.lang.System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}