package aureo.anaerosync;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AnaerosyncApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AnaerosyncApplication.class.getResource("index.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        stage.setTitle("Anaerosync");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}