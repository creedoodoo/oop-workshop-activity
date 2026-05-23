package com.library.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneManager {

    private static final String STYLESHEET = "/com/library/styles.css";

    public static void switchScene(Stage stage, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            SceneManager.class.getResource("/com/library/" + fxmlPath)
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneManager.class.getResource(STYLESHEET).toExternalForm());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public static <T> T switchSceneAndGetController(Stage stage, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(
            SceneManager.class.getResource("/com/library/" + fxmlPath)
        );
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(SceneManager.class.getResource(STYLESHEET).toExternalForm());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        return loader.getController();
    }
}
