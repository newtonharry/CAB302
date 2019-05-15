package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.GUI.stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/sample.fxml"));
        /*primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Paint Application");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("GUI/styles.css").toExternalForm());
        primaryStage.show();

        stage.setStageWidth(scene.getWidth());
        stage.setStageHeight(scene.getHeight());

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            stage.setStageWidth(scene.getWidth());

        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            stage.setStageHeight(scene.getHeight());
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
