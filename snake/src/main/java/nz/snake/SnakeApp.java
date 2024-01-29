package nz.snake;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nz.snake.SceneManager.AppUi;

public class SnakeApp extends Application {
    private static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage stage) throws IOException {

        SceneManager.addUi(AppUi.STARTER_PAGE, loadFxml("chess"));

        scene = new Scene(SceneManager.getUi(AppUi.STARTER_PAGE), 800, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene = new Scene(loadFxml(fxml), 800, 800);
    }

    private static Parent loadFxml(final String fxml) throws IOException {
        return new FXMLLoader(SnakeApp.class.getResource("/fxml/" + fxml + ".fxml")).load();
    }
}