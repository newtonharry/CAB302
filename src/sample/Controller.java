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
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import javax.tools.Tool;


public class Controller implements Initializable{

    @FXML private ColorPicker colourSelector;
    @FXML private Button penToolBtn;
    @FXML private Button lineToolBtn;
    @FXML private Button rectangleToolBtn;
    @FXML private Button ellipseToolBtn;
    @FXML private Button polygonToolBtn;
    @FXML private ColorPicker lineColorPicker;
    @FXML private ColorPicker fillColorPicker;

    @FXML private Canvas canvas;
    @FXML private AnchorPane canvasPane;
    @FXML private AnchorPane canvasAnchorPane;

    boolean toolSelected = false;
    String selectedTool;

    GraphicsContext brush;

    Line line = new Line();
    Rectangle rectangle = new Rectangle();
    Ellipse ellipse = new Ellipse();
    Rectangle ellipseBounds = new Rectangle();
    Polygon polygon = new Polygon();
    double[] polygonXPoints = new double[9999]; //Set to max 9999 points
    double[] polygonYPoints = new double[9999]; //Set to max 9999 points
    int polygonPoints = 0;
    double[] polygonPointsX = new double[9999];
    double[] polygonPointsY = new double[9999];
    int polygonPointsCount = 0;

    // Temp Drawing Layers
    Canvas tempDrawingLayer;
    GraphicsContext tempDrawingLayerGC;


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
        this.selectedTool = "plot";
        handleMouseEvent();


    }



    @FXML
    private void lineToolClick(){
        changeActiveButton("line");
        this.selectedTool = "line";
        handleMouseEvent();

    }

    @FXML
    private void rectangleToolClick(){
        changeActiveButton("rectangle");
        this.selectedTool = "rectangle";
        handleMouseEvent();
    }

    @FXML
    private void ellipseToolClick(){
        changeActiveButton("ellipse");
        this.selectedTool = "ellipse";
        handleMouseEvent();
    }

    @FXML
    private void polygonToolClick(){
        changeActiveButton("polygon");
        this.selectedTool = "polygon";
        handleMouseEvent();
    }

    @FXML
    private void closePolygon(){
        brush.strokePolygon(polygonXPoints, polygonYPoints, polygonPoints);
    }


    @FXML private void newCanvasMenuBtnClick(){
        System.out.println("New Canvas Menu Button Click");

    }

    @FXML private void openMenuBtnClick(){ System.out.println("Open Menu Button Click"); }
    @FXML private void saveMenuBtnClick(){ System.out.println("Save Menu Button Click"); }
    @FXML private void exportMenuBtnClick(){ System.out.println("Export Menu Button Click"); }
    @FXML private void undoMenuBtnClick(){ System.out.println("Undo Menu Button Click"); }
    @FXML private void showGridMenuBtnClick(){ System.out.println("Show Grid Menu Button Click"); }


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


    private void setupLine(Double x, Double y){
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);

        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        brush.beginPath();
        line.setStartX(x);
        line.setStartY(y);
    }

    private void renderLinePreview(Double x, Double y){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();
        //tempDrawingLayerGC.setStroke(colourSelector.getValue());

        line.setEndX(x);
        line.setEndY(y);

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }

    private void endLine(){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        brush.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        line = new Line();
    }

    private void setupRectangle(Double x, Double y){
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);

        rectangle.setX(x);
        rectangle.setY(y);
    }

    private void renderRectanglePreview(Double x, Double y){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        double startX = rectangle.getX();
        double startY = rectangle.getY();
        double endX = x;
        double endY = y;

        double[] xPoints = {startX, endX, endX, startX};
        double[] yPoints = {startY, startY, endY, endY};

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokePolygon(xPoints, yPoints, 4);

        rectangle.setX(startX);
        rectangle.setY(startY);
    }

    private void endRectangle(Double x, Double y){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        rectangle.setWidth(x - rectangle.getX());
        rectangle.setHeight(y - rectangle.getY());

        if(rectangle.getWidth() < 0){
            rectangle.setWidth(rectangle.getWidth()*-1.0);
            rectangle.setX(rectangle.getX() - rectangle.getWidth());
        }

        if(rectangle.getHeight() < 0){
            rectangle.setHeight(rectangle.getHeight()*-1.0);
            rectangle.setY(rectangle.getY() - rectangle.getHeight());
        }

        brush.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
        rectangle = new Rectangle();
    }

    private void setupEllipse(Double x, Double y){
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);

        ellipseBounds.setX(x);
        ellipseBounds.setY(y);
        ellipse.setCenterX(ellipseBounds.getX());
        ellipse.setCenterY(ellipseBounds.getY());
        ellipse.setRadiusX(0);
        ellipse.setRadiusY(0);
    }

    private void renderEllipsePreview(Double x, Double y){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        ellipse.setCenterX((x + ellipseBounds.getX()) / 2);
        ellipse.setCenterY((y + ellipseBounds.getY()) / 2);
        ellipse.setRadiusX(Math.abs((x - ellipseBounds.getX()) / 2));
        ellipse.setRadiusY(Math.abs((y - ellipseBounds.getY()) / 2));

        ellipseBounds.setWidth(Math.abs(x - ellipseBounds.getX()));
        ellipseBounds.setHeight(Math.abs(y - ellipseBounds.getY()));

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokeOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());
    }

    private void endEllipse(Double x, Double y){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        ellipseBounds.setWidth(Math.abs(x - ellipseBounds.getX()));
        ellipseBounds.setHeight(Math.abs(y - ellipseBounds.getY()));

        brush.strokeOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());
    }

    private void polygonClick(){
        canvasAnchorPane.setOnMouseClicked(event -> {
            if(selectedTool == "polygon") {
                renderPolygonPreview(event.getX(), event.getY());
            }
        });
    }

    private void setupPolygon(Double x, Double y){
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        polygonPointsX[polygonPointsCount] = x;
        polygonPointsY[polygonPointsCount] = y;
        polygonPointsCount++;

        polygonClick();
    }

    private void renderPolygonPreview(Double x, Double y){
        polygonPointsX[polygonPointsCount] = x;
        polygonPointsY[polygonPointsCount] = y;

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokeLine(polygonPointsX[polygonPointsCount-1], polygonPointsY[polygonPointsCount-1], polygonPointsX[polygonPointsCount], polygonPointsY[polygonPointsCount]);
        polygonPointsCount++;
    }



    @FXML private void endPolygon(){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        brush.strokePolygon(polygonPointsX, polygonPointsY, polygonPointsCount);
        polygonPointsX = new double[9999];
        polygonPointsY = new double[9999];
        polygonPointsCount = 0;
    }

    private void handleMouseEvent(){
        canvas.setOnMouseClicked(event -> {
            if(selectedTool == "polygon") {
                if(polygonPointsCount == 0){
                    setupPolygon(event.getX(), event.getY());
                } else {
                    renderPolygonPreview(event.getX(), event.getY());
                }
            }
        });

        canvas.setOnMousePressed(event -> {
            brush.setStroke(lineColorPicker.getValue());
            brush.setFill(Color.RED);

            if(selectedTool == "line") {    setupLine(event.getX(), event.getY());  }

            if(selectedTool == "rectangle"){
                setupRectangle(event.getX(), event.getY());
            }

            if(selectedTool == "ellipse"){
                setupEllipse(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseDragged(event -> {
            if(selectedTool == "line") {    renderLinePreview(event.getX(), event.getY());  }

            if(selectedTool == "rectangle"){
                renderRectanglePreview(event.getX(), event.getY());
            }

            if(selectedTool == "ellipse"){
                renderEllipsePreview(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased(event->{
            if(selectedTool == "line") {    endLine();  }

            if(selectedTool == "rectangle"){
                endRectangle(event.getX(), event.getY());
            }

            if(selectedTool == "ellipse"){
                endEllipse(event.getX(), event.getY());
            }
        });



                /*canvas.setOnMouseClicked(event -> {
                    brush.setStroke(lineColorPicker.getValue());
                    if(selectedTool == "plot"){
                        brush.beginPath();
                        brush.lineTo(event.getX(), event.getY());
                        brush.stroke();
                    }


                });*/
                /*canvas.setOnMousePressed(event -> {
                    brush.setStroke(lineColorPicker.getValue());

                    if(selectedTool == "line") {
                        brush.beginPath();
                        line.setStartX(event.getX());
                        line.setStartY(event.getY());
                    }

                    if(selectedTool == "rectangle"){
                        rectangle.setX(event.getX());
                        rectangle.setY(event.getY());
                    }

                    if(selectedTool == "ellipse"){
                        ellipseBounds.setX(event.getX());
                        ellipseBounds.setY(event.getY());
                    }

                    if(selectedTool == "polygon"){
                        double[] xPoints = {300.0, 450.0, 300.0, 150.0};
                        double[] yPoints = {50.0, 150.0, 250.0, 150.0};
                        polygonXPoints[polygonPoints] = event.getX();
                        polygonYPoints[polygonPoints] = event.getY();
                        polygonPoints++;
                    }

                });*/

                /*canvas.setOnMouseReleased(event->{
                    if(selectedTool == "line") {
                        line.setEndX(event.getX());
                        line.setEndY(event.getY());
                        brush.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
                        brush.stroke();
                    }

                    if(selectedTool == "rectangle"){
                        rectangle.setWidth(event.getX() - rectangle.getX());
                        rectangle.setHeight(event.getY() - rectangle.getY());

                        if(rectangle.getWidth() < 0){
                            rectangle.setWidth(rectangle.getWidth()*-1.0);
                            rectangle.setX(rectangle.getX() - rectangle.getWidth());
                        }

                        if(rectangle.getHeight() < 0){
                            rectangle.setHeight(rectangle.getHeight()*-1.0);
                            rectangle.setY(rectangle.getY() - rectangle.getHeight());
                        }

                        brush.strokeRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
                    }

                    if(selectedTool == "ellipse"){
                        ellipseBounds.setWidth(event.getX() - ellipseBounds.getX());
                        ellipseBounds.setHeight(event.getY() - ellipseBounds.getY());

                        if(ellipseBounds.getWidth() < 0){
                            ellipseBounds.setWidth(ellipseBounds.getWidth()*-1.0);
                            ellipseBounds.setX(ellipseBounds.getX() - ellipseBounds.getWidth());
                        }

                        if(ellipseBounds.getHeight() < 0){
                            ellipseBounds.setHeight(ellipseBounds.getHeight()*-1.0);
                            ellipseBounds.setY(ellipseBounds.getY() - ellipseBounds.getHeight());
                        }

                        brush.strokeOval(ellipseBounds.getX(), ellipseBounds.getY(), ellipseBounds.getWidth(), ellipseBounds.getHeight());
                    }
                });*/




    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        lineColorPicker.getStyleClass().add("button");
        fillColorPicker.getStyleClass().add("button");

        brush = canvas.getGraphicsContext2D();
        brush.setLineWidth(1);


        if(selectedTool != null){

        } else {
            System.out.println("Please Select a Tool");
        }


        /*brush = canvas.getGraphicsContext2D();
        brush.setLineWidth(1);

        canvas.setOnMouseClicked(event -> {
            //double x = event.getX(), y = event.getY();
            System.out.println("Canvas Clicked");
            double coordX = event.getX();
            double coordY = event.getY();

            System.out.println(coordX);
            System.out.println(coordY);

            brush.beginPath();
            brush.lineTo(coordX, coordY);
            brush.stroke();

        });*/
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
