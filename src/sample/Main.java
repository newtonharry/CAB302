package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.GUI.stage;

import java.lang.reflect.Method;


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



        KeyCombination kc = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        Runnable rn = ()-> sample.GUI.KeyboardShortcuts.newCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        rn = ()-> sample.GUI.KeyboardShortcuts.openCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        rn = ()-> sample.GUI.KeyboardShortcuts.saveCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        rn = ()-> sample.GUI.KeyboardShortcuts.exportCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        rn = ()-> sample.GUI.KeyboardShortcuts.undoCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
        rn = ()-> sample.GUI.KeyboardShortcuts.gridCommand();
        scene.getAccelerators().put(kc, rn);

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
