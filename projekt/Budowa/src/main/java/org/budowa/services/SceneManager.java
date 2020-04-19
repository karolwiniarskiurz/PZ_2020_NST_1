package org.budowa.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.budowa.App;

import java.io.IOException;

/**
 * Scene manager MUST be a singleton!
 */
public class SceneManager {
    private static SceneManager sceneManager;

    public static SceneManager inject() {
        if (SceneManager.sceneManager == null) {
            SceneManager.sceneManager = new SceneManager();
        }
        return SceneManager.sceneManager;
    }

    /**
     * Stage
     */
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Scene
     */
    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    /**
     * Public methods
     */
    public void closeWindow() {
        stage.close();
    }

    public void createScene(String fxmlName, String title) throws IOException {
        this.scene = new Scene(loadFXML(fxmlName));
        this.stage.setScene(scene);
        this.stage.setTitle(title);
        this.stage.show();
    }

    public void createDefaultScene() {
        var defaultScene = "ManagerDashboardScene";
        var defaultTitle = "Zarządzanie budowami";
        try {
            this.createScene(defaultScene, defaultTitle);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}
