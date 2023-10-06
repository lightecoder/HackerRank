package gpt.chat.app;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import static gpt.chat.app.ScreenProcessingApp.OUTPUT_CERTIFICATE_DIRECTORY;

public class KeytoolExecutor {
    public static final String CACERT_DIR = "/Users/bodiaky/Library/Java/JavaVirtualMachines/openjdk-20.0.1/Contents/Home/lib/security/cacerts";

    public static void saveCertificate(String certName, String alias) {
        try {
            // Delete the alias
            executeCommand("keytool", "-delete", "-alias", alias, "-keystore", CACERT_DIR, "-noprompt");

            // Import the certificate
            executeCommand("keytool", "-import", "-trustcacerts", "-file", OUTPUT_CERTIFICATE_DIRECTORY.concat(certName),
                    "-keystore", CACERT_DIR,
                    "-alias", alias);

            System.out.println("Certificate " + certName + " saved successfully to Java cacert file.");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String... command) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        Process process = processBuilder.start();

        // If the command is keytool import, send 'yes' to confirm
        if (Arrays.asList(command).contains("-import")) {
            try (OutputStream os = process.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os))) {
                writer.write("yes\n");
                writer.flush();
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            System.out.println("Command failed with exit code: " + exitCode);
        }
    }
}
