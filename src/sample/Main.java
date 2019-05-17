package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.GUI.stage;


public class Main extends Application{
    private static Scene scene;

    public static Scene getScene(){
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/sample.fxml"));
        scene = new Scene(root, 1280, 720);
        primaryStage.setTitle("Paint Application");
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("GUI/styles.css").toExternalForm());
        primaryStage.show();


        stage test = new stage();

        test.setStageWidth(scene.getWidth());
        test.setStageHeight(scene.getHeight());


        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            test.setStageWidth(scene.getWidth());

        });

        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            test.setStageHeight(scene.getHeight());

        });


    }

    public static void main(String[] args) {
        launch(args);
    }
}
