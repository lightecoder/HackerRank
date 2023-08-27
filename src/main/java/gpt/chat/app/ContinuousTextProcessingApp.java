package gpt.chat.app;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Properties;

public class ContinuousTextProcessingApp {
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
        String apiKey = (String) properties.get("apiKey");
        String listenerPath = (String) properties.get("listenerPath");

        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path directory = Paths.get(listenerPath);
        directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

        while (true) {
            WatchKey key;
            key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                    Path fileName = (Path) event.context();
                    if (fileName.toString().endsWith(".txt")) {
                        processAndSendEmail(directory.resolve(fileName), apiKey);
                    }
                }
            }
            key.reset();
        }
    }

    private static void processAndSendEmail(Path filePath, String apiKey) {
        try {
            // Read content from the text file
            String fileContent = new String(Files.readAllBytes(filePath));

            // Process content using GPT-3
//            String generatedText = processWithGPT3(apiKey, fileContent);
            String generatedText = processGPT(apiKey, fileContent);
            System.out.println("GPT answer: " + generatedText);

            // Send email
            sendEmail("4456602@gmail.com", "GPT Generated Text", generatedText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String processWithGPT3(String apiKey, String inputText) {
        try {
            // Set up API URI
            URI uri = new URI("https://api.openai.com/v1/engines/davinci/completions");

            // Create connection
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setDoOutput(true);

            // Set request body
            String requestBody = "{\"prompt\": \"" + inputText + "\", \"max_tokens\": 50}";

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                wr.write(input, 0, input.length);
            }

            // Get response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Parse response JSON and extract generated text
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing with GPT-3: " + e.getMessage();
        }
    }

    private static String processGPT(String apiKey, String inputText) throws IOException {
        URL url = new URL("https://api.openai.com/v1/engines/davinci/completions?prompt=" +
                inputText +
                "&temperature=0.7&max_tokens=100&n=1");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer "+ apiKey);
        connection.setRequestProperty("Content-Type", "application/json");

        String body = "{\"prompt\":\"What is the meaning of life?\",\"temperature\":0.7,\"max_tokens\":100,\"n\":1}";
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            os.write(body.getBytes());
        }

        int responseCode = connection.getResponseCode();
        String response = null;
        if (responseCode == 200) {
            InputStream is = connection.getInputStream();
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            response = new String(bytes);
            System.out.println(response);
        } else {
            System.out.println("Error: " + responseCode);
        }
        return response;
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

            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}