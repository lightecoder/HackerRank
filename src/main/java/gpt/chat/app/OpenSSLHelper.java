package gpt.chat.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static gpt.chat.app.ScreenProcessingApp.OUTPUT_CERTIFICATE_DIRECTORY;

public class OpenSSLHelper {

    public static void downloadCertificate(String website, String certName) {
        try {
            Process process = new ProcessBuilder(
                    "openssl", "s_client", "-showcerts", "-connect", extractHostFromUrl(website) + ":443").start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                StringBuilder certificate = new StringBuilder();
                boolean isCertificateBlock = false;

                while ((line = reader.readLine()) != null) {
                    if (line.contains("-----BEGIN CERTIFICATE-----")) {
                        isCertificateBlock = true;
                    }
                    if (isCertificateBlock) {
                        certificate.append(line).append("\n");
                    }
                    if (line.contains("-----END CERTIFICATE-----")) {
                        break;
                    }
                }
                String path = OUTPUT_CERTIFICATE_DIRECTORY.concat(certName);
                java.nio.file.Files.write(java.nio.file.Paths.get(path), certificate.toString().getBytes());

                System.out.println("Certificate saved to " + path);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String extractHostFromUrl(String url) {
        return url.split("//")[1].split("/")[0];
    }
}
