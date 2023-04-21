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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyHttpServer extends Application {
    public static void main(String[] args) {
        new MyHttpServer().call(args);
    }

    private void call(String[] args) {
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

                String date = "2023-06-21";
                String status = response.substring(response.indexOf(date), (response.indexOf(date) + 21)).replaceAll("\"", "");
                System.out.println("Response : " + response);
                System.out.println("Response code: " + responseCode + ", status = " + status + "\n");

                if (!status.split(":")[1].equals("no-slots")) {
                    for (int i = 0; i < 100; i++) {
                        launch(args);
                    }
                } else {
                    System.out.println("Still not available .. ");
                }
                connection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
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
