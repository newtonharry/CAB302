package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        /*primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Paint Application");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        primaryStage.show();

        sample.stage.setStageWidth(scene.getWidth());
        sample.stage.setStageHeight(scene.getHeight());

        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            sample.stage.setStageWidth(scene.getWidth());

        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            sample.stage.setStageHeight(scene.getHeight());
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
