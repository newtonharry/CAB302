package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.GUI.Controller;
import sample.GUI.Model;
import sample.GUI.stage;

import java.io.IOException;


public class Main extends Application {

    private static Scene scene;
    private Stage primaryStage;
    private Model model;

    @Override
    public void start(Stage primaryStage) {

        BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/sample.fxml"));
        try {
            root.setTop(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(root, 1280, 720);
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Paint Application");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        scene.getStylesheets()
                .add(getClass().getResource("GUI/styles.css").toExternalForm());

        Model model = new Model();
        Controller controller = loader.getController();
        stage test = new stage();
        controller.initialiseModel(model);

        test.setStageWidth(scene.getWidth());
        test.setStageHeight(scene.getHeight());

        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            test.setStageWidth(scene.getWidth());
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            test.setStageHeight(scene.getHeight());
        });

        initKeyboardShortcuts();
    }

    private void initKeyboardShortcuts() {
        KeyCombination kc = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        Runnable rn = () -> sample.GUI.KeyboardShortcuts.newCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        rn = () -> sample.GUI.KeyboardShortcuts.openCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        rn = () -> sample.GUI.KeyboardShortcuts.saveCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN);
        rn = () -> sample.GUI.KeyboardShortcuts.exportCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN);
        rn = () -> sample.GUI.KeyboardShortcuts.undoCommand();
        scene.getAccelerators().put(kc, rn);

        kc = new KeyCodeCombination(KeyCode.G, KeyCombination.CONTROL_DOWN);
        rn = () -> sample.GUI.KeyboardShortcuts.gridCommand();
        scene.getAccelerators().put(kc, rn);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
