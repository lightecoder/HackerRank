package dmv;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyHttpServer extends Application {
    public static void main(String[] args) {
        new MyHttpServer().call();
    }

    private void call() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            try {
                URL url = new URL("https://nqapflhsmv.nemoqappointment.com/wp-admin/admin-ajax.php?location=17&service=5&worker=3&action=ea_month_status&month=6&year=2023&check=ed65addf52");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                String date = "2023-06-22";
                String status = response.substring(
                        response.indexOf(date),
                        response.indexOf("\"", response.indexOf(date) + 15)
                ).replaceAll("\"", "");
                System.out.println("Response : " + response);
                System.out.println("Time: " + LocalDateTime.now().format(formatter) + ", Response code: " + responseCode + ", status = " + status);

                if (response.toString().contains("free")) {
                    for (int i = 0; i < 100; i++) {
                        launch();
                    }
                } else {
                    System.out.println("Still not available .. \n");
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 5, TimeUnit.SECONDS);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = this.getClass().getResource("/mus.mp3").getPath();
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
        mediaPlayer.play();
    }
}
