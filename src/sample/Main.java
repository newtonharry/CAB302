package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.GUI.Controller;
import sample.Instructions.InstructionBufferProcessor;
import sample.Instructions.InstructionList;
import sample.Instructions.VecInstruction;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class Main extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private static Controller controller;
    public static Canvas previewCanvas;
    public static GraphicsContext previewGC;
    private static int undoHistoryCurrent;

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

        controller = loader.getController();

        InstructionBufferProcessor.BUFFER_PROCESSOR.canvas = controller.canvas;
        InstructionBufferProcessor.BUFFER_PROCESSOR.brush = controller.brush;
        InstructionBufferProcessor.BUFFER_PROCESSOR.listen();

        //controller.resizeCanvas(scene.getWidth(), scene.getHeight());
        this.primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            //test.setStageWidth(scene.getWidth());
            controller.resizeCanvas(scene.getWidth(), scene.getHeight());
        });

        this.primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            //test.setStageHeight(scene.getHeight());
            controller.resizeCanvas(scene.getWidth(), scene.getHeight());
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
        rn = () -> sample.GUI.KeyboardShortcuts.gridCommand(); //testPopup();
        scene.getAccelerators().put(kc, rn);
    }

    static public Stage getPrimaryStage() {
        return sample.Main.primaryStage;
    }

    static public Controller getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showUndoHistoryGUI(List<VecInstruction> instructions) {
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
            undoHistoryConfirm(undoHistoryCurrent);
            dialog.close();
        });


        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color: #3B4046; -fx-text-fill: white; -fx-min-width: 75; -fx-min-height: 35; -fx-border-insets: 5px; -fx-padding: 5px; -fx-background-insets: 5px;");

        cancelButton.setOnAction((event) -> {
            dialog.close();
        });

        final Pane spacerLeft = new Pane();
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
        historyView.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


        VBox instructionContainer = new VBox();

        int index = 0;
        InstructionList tempInstuctions = new InstructionList();
        System.out.println(tempInstuctions);
        for (VecInstruction instruction : instructions) {
            tempInstuctions.add(instruction);
            Button button = new Button(instruction.getType().toString());
            button.setStyle("-fx-background-color: #3B4046; -fx-text-fill: white; -fx-min-width: 75; -fx-min-height: 35; -fx-border-insets: 5px; -fx-padding: 5px; -fx-background-insets: 5px;");
            instructionContainer.getChildren().add(button);

            int tempIndex = index;
            button.setOnAction((event) -> {
                drawToCanvas(tempIndex);
            });

            index++;
        }

        historyView.setContent(instructionContainer);

        VBox historyViewContainer = new VBox();
        historyViewContainer.getChildren().addAll(historyViewLabel, historyView);


        HBox mainContainer = new HBox();
        mainContainer.getChildren().addAll(spacerRight, historyViewContainer, spacerRight2);

        final Pane spacerTop = new Pane();
        spacerTop.setMinSize(1, 1);

        dialogVbox.getChildren().add(spacerTop);
        dialogVbox.getChildren().add(mainContainer);
        dialogVbox.getChildren().add(buttonContainer);

        Scene dialogScene = new Scene(dialogVbox, 170, 410);
        dialog.setTitle("Undo History");
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private static void drawToCanvas(int index) {
        System.out.println(index);//revertto
        //InstructionBufferProcessor.BUFFER_PROCESSOR.brush.strokeLine(10, 10, 200, 10);
        undoHistoryCurrent = index+1;
        InstructionBufferProcessor.BUFFER_PROCESSOR.drawShapes(index+1);
    }

    private static void undoHistoryConfirm(int index){
        //System.out.println(index);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        /*alert.setTitle("Confirmation");
        alert.setContentText("Are you sure you want to revert to this version. This cannot be undone.");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){*/
            InstructionBufferProcessor.BUFFER_PROCESSOR.revertTo(index);
        /*}*/
    }
}
