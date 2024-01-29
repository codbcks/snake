package nz.snake;

import java.util.HashMap;
import javafx.scene.Parent;

public class SceneManager {

    /** Enum for the different scenes in the game. */
    public enum AppUi {
        STARTER_PAGE,
        CHESS
    }

    private static HashMap<AppUi, Parent> sceneMap = new HashMap<>();

    // Adds a scene to the scene map
    public static void addUi(AppUi ui, Parent root) {
        sceneMap.put(ui, root);
    }

    // Gets a scene from the scene map
    public static Parent getUi(AppUi ui) {
        return sceneMap.get(ui);
    }
}
