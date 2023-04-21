package dmv;

import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class MyHttpServer extends Application {
    public static void main(String[] args) {
        launch(args);
//        new MyHttpServer().call();
    }

    private void call() {
//        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
//        Runnable task = () -> {
//            try {
//                URL url = new URL("https://nqapflhsmv.nemoqappointment.com/wp-admin/admin-ajax.php?location=17&service=5&worker=3&action=ea_month_status&month=6&year=2023&check=ed65addf52");
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("GET");
//                int responseCode = connection.getResponseCode();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String line;
//                StringBuilder response = new StringBuilder();
//                while ((line = reader.readLine()) != null) {
//                    response.append(line);
//                }
//                reader.close();
//                String status = response.substring(response.indexOf("2023-06-19"), (response.indexOf("2023-06-19") + 21)).replaceAll("\"", "");
//                System.out.println("Response code: " + responseCode + ", status = " + status);

//                if (!status.split(":")[1].equals("no-slot")) {
                Media media = new Media(new File("src/main/resources/mus.mp3").toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
//                }
//                connection.disconnect();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        };

//        scheduler.scheduleAtFixedRate(task, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //"src/main/resources/mus.mp3"
        String path = getClass().getResource("mus.mp3").getPath();
        System.out.println(path);
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
//        primaryStage.setTitle("Music Player");
//        primaryStage.show();
    }
}
