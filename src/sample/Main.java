package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.GUI.Controller;
import sample.GUI.Model;
import sample.GUI.stage;
import sample.Instructions.InstructionList;

import java.io.IOException;


public class Main extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private Model model;
    private InstructionList instructions;

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GUI/sample.fxml"));
        try {
            root.setTop(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        scene = new Scene(root, 730, 821);
        this.primaryStage = primaryStage;
        this.primaryStage.setMinHeight(641.0);
        this.primaryStage.setMinWidth(550.0);
        this.primaryStage.setTitle("Paint Application");
        this.primaryStage.initStyle(StageStyle.UTILITY);

        this.primaryStage.setScene(scene);

        this.primaryStage.show();

        scene.getStylesheets()
                .add(getClass().getResource("GUI/styles.css").toExternalForm());


        //Model model = new Model();
        instructions = new InstructionList();
        Controller controller = loader.getController();
        //controller.initialiseModel(model);
        controller.initialiseModel(instructions);


        /*stage test = new stage();

        test.setStageWidth(scene.getWidth());
        test.setStageHeight(scene.getHeight());

        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            test.setStageWidth(scene.getWidth());
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            test.setStageHeight(scene.getHeight());
        });*/

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
        rn = () -> testPopup();//sample.GUI.KeyboardShortcuts.gridCommand();
        scene.getAccelerators().put(kc, rn);
    }

    static public Stage getPrimaryStage() {
        return sample.Main.primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void testPopup(){
        final Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setResizable(false);

        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.setBackground(new Background(new BackgroundFill(Color.web("#727B87"), CornerRadii.EMPTY, Insets.EMPTY)));



        Button okayButton = new Button("Undo");
        okayButton.setStyle("-fx-background-color: #3B4046; -fx-text-fill: white; -fx-min-width: 75; -fx-min-height: 35; -fx-border-insets: 5px; -fx-padding: 5px; -fx-background-insets: 5px;");


        okayButton.setOnAction((event) -> {

            dialog.close();
        });


        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #3B4046; -fx-text-fill: white; -fx-min-width: 75; -fx-min-height: 35; -fx-border-insets: 5px; -fx-padding: 5px; -fx-background-insets: 5px;");

        cancelButton.setOnAction((event) -> {
            dialog.close();
        });

        final Pane spacerLeft = new Pane();
        HBox.setHgrow(spacerLeft, Priority.ALWAYS);
        spacerLeft.setMinSize(10, 1);

        final Pane spacerRight = new Pane();
        spacerRight.setMinSize(10, 1);
        final Pane spacerRight2 = new Pane();
        spacerRight2.setMinSize(10, 1);

        HBox buttonContainer = new HBox();
        buttonContainer.getChildren().addAll(spacerLeft, okayButton, cancelButton, spacerRight);


        Label historyViewLabel = new Label("History");
        ScrollPane historyView = new ScrollPane();
        historyView.setStyle("-fx-min-height: 300; -fx-min-width: 150; -fx-background: #3B4046; -fx-background-color: #3B4046;");

        VBox historyViewContainer = new VBox();
        historyViewContainer.getChildren().addAll(historyViewLabel, historyView);

        Label canvasPreviewLabel = new Label("Preview");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle(" -fx-background: #FFFFFF; -fx-background-color: #FFFFFF; -fx-min-width: 300; -fx-min-height: 300; ");

        Canvas previewCanvas = new Canvas(300, 300);
        anchorPane.getChildren().add(previewCanvas);

        VBox canvasPreview = new VBox();
        canvasPreview.getChildren().addAll(canvasPreviewLabel, anchorPane);

        final Pane spacerMiddle = new Pane();
        spacerMiddle.setMinSize(30, 1);

        HBox mainContainer = new HBox();
        mainContainer.getChildren().addAll(spacerRight, historyViewContainer, spacerMiddle, canvasPreview, spacerRight2);

        final Pane spacerTop = new Pane();
        spacerTop.setMinSize(1, 1);

        dialogVbox.getChildren().add(spacerTop);
        dialogVbox.getChildren().add(mainContainer);
        dialogVbox.getChildren().add(buttonContainer);

        Scene dialogScene = new Scene(dialogVbox, 500, 410);
        dialog.setTitle("Undo History");
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }
}
