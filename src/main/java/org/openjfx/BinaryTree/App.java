package org.openjfx.BinaryTree;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        try {

            Stage stage = new Stage();

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/BinaryTree.fxml"));

            Scene scene = new Scene(root);

            scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());

            stage.setFullScreen(false);
            stage.setResizable(false);
            stage.setTitle("Binary Tree Visualizer");
            stage.getIcons().add(new Image(App.class.getResourceAsStream("/img/switch.png")));
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
