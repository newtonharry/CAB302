package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;


public class Controller implements Initializable{

    @FXML private ColorPicker colourSelector;
    @FXML private Button penToolBtn;
    @FXML private Button lineToolBtn;
    @FXML private Button rectangleToolBtn;
    @FXML private Button ellipseToolBtn;
    @FXML private Button polygonToolBtn;

    @FXML
    private Canvas canvas;

    boolean toolSelected = false;

    GraphicsContext brush;

    @FXML
    private void onMousePressedListener(MouseEvent e){
        System.out.println("mousepress");


        //brush.setLineWidth(5);
        //brush.setStroke(colourSelector.getValue());
        //brush.clearRect(0, 0, canvasGo.getWidth() , canvasGo.getHeight());
        //brush.strokeLine(10, 10, 100, 100);
    }

    @FXML
    private void penToolClick(){
        changeActiveButton("pen");
    }

    @FXML
    private void lineToolClick(){
        changeActiveButton("line");
    }

    @FXML
    private void rectangleToolClick(){
        changeActiveButton("rectangle");
    }

    @FXML
    private void ellipseToolClick(){
        changeActiveButton("ellipse");
    }

    @FXML
    private void polygonToolClick(){
        changeActiveButton("polygon");
    }

    private void changeActiveButton(String btnType){
        penToolBtn.getStyleClass().remove("headerBtnActive");
        lineToolBtn.getStyleClass().remove("headerBtnActive");
        rectangleToolBtn.getStyleClass().remove("headerBtnActive");
        ellipseToolBtn.getStyleClass().remove("headerBtnActive");
        polygonToolBtn.getStyleClass().remove("headerBtnActive");

        switch(btnType){
            case "pen":
                penToolBtn.getStyleClass().add("headerBtnActive");
                break;
            case "line":
                lineToolBtn.getStyleClass().add("headerBtnActive");
                break;
            case "rectangle":
                rectangleToolBtn.getStyleClass().add("headerBtnActive");
                break;
            case "ellipse":
                ellipseToolBtn.getStyleClass().add("headerBtnActive");
                break;
            case "polygon":
                polygonToolBtn.getStyleClass().add("headerBtnActive");
                break;
        }





    }



    @FXML
    public void newCanvas(ActionEvent e){
        TextField getCanvasWidth = new TextField();
        getCanvasWidth.setPromptText("Width");
        getCanvasWidth.setPrefWidth(150);
        getCanvasWidth.setAlignment(Pos.CENTER);

        TextField getCanvasHeight = new TextField();
        getCanvasHeight.setPromptText("Height");
        getCanvasHeight.setPrefHeight(150);
        getCanvasHeight.setAlignment(Pos.CENTER);

        Button createButton = new Button();
        createButton.setText("Create Canvas");

        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(getCanvasWidth, getCanvasHeight, createButton);

        Stage createStage = new Stage();
        AnchorPane root = new AnchorPane();
        root.setPrefWidth(200);
        root.setPrefHeight(200);
        root.getChildren().add(vBox);

        Scene getDimension = new Scene(root);
        createStage.setTitle("Canvas!!");
        createStage.setScene(getDimension);
        createStage.show();

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                double canvasWidthReceived = Double.parseDouble(getCanvasWidth.getText());
                double canvasHeightReceived = Double.parseDouble(getCanvasHeight.getText());

                Pane pane = new Pane();
                pane.setPrefSize(canvasWidthReceived, canvasHeightReceived);
                pane.setStyle("-fx-background-color: blue;");

                canvas = new Canvas();
                canvas.setWidth(canvasWidthReceived);
                canvas.setHeight(canvasHeightReceived);

                pane.getChildren().add(canvas);

                vBox.getChildren().add(pane);
                createStage.close();
            }
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){

        //brush = canvas.getGraphicsContext2D();

        //canvas.onMouseClickedProperty();
        //canvas.setOnMouseDragged(e -> {
//            double brushSize = Double.parseDouble(brushSizeInput.getText());
//
//            double mouseX = e.getX() - brushSize / 2;
//            double mouseY = e.getY() - brushSize / 2;
//
//            if(toolSelected && !brushSizeInput.getText().isEmpty()){
//                brush.setFill(colourSelector.getValue());
//                brush.fillRoundRect(mouseX, mouseY, brushSize, brushSize, brushSize, brushSize);
//            }
        //});


    }

    @FXML
    public void toolSelected(ActionEvent e){
        toolSelected = true;
    }
}
