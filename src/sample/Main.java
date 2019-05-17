package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< refs/remotes/origin/master
=======
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
>>>>>>> Updated default fill and line colours. Started work on resizing canvas.
import javafx.stage.Stage;
import sample.GUI.stage;


public class Main extends Application{
    private static Scene scene;

    public static Scene getScene(){
        return scene;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
<<<<<<< refs/remotes/origin/master
        Parent root = FXMLLoader.load(getClass().getResource("GUI/sample.fxml"));
        /*primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
        Scene scene = new Scene(root, 1280, 720);
=======
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        scene = new Scene(root, 1280, 720);
>>>>>>> Updated default fill and line colours. Started work on resizing canvas.
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
