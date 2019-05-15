package sample.GUI;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

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

    Color lineColor;
    Color fillColor;

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
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        brush.setStroke(lineColor);
        brush.setFill(fillColor);

        brush.setLineWidth(3);

        brush.strokePolygon(polygonPointsX, polygonPointsY, polygonPointsCount);
        brush.fillPolygon(polygonPointsX, polygonPointsY, polygonPointsCount);

        polygonPointsX = new double[9999];
        polygonPointsY = new double[9999];
        polygonPointsCount = 0;
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

        line.setEndX(x);
        line.setEndY(y);

        tempDrawingLayerGC.setStroke(lineColor);
        tempDrawingLayerGC.setLineWidth(1);

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }

    private void plotPoint(Double x, Double y){
        brush.setStroke(lineColor);
        brush.setLineWidth(1);

        brush.strokeLine(x, y, x, y);
    }

    private void endLine(){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        brush.setStroke(lineColor);
        brush.setLineWidth(1);

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

        tempDrawingLayerGC.setStroke(lineColor);
        tempDrawingLayerGC.setFill(fillColor);
        tempDrawingLayerGC.setLineWidth(3);

        tempDrawingLayerGC.strokePolygon(xPoints, yPoints, 4);
        tempDrawingLayerGC.fillPolygon(xPoints, yPoints, 4);

        rectangle.setX(startX);
        rectangle.setY(startY);
    }

    private void endRectangle(Double x, Double y){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        double startX = rectangle.getX();
        double startY = rectangle.getY();
        double endX = x;
        double endY = y;

        double[] xPoints = {startX, endX, endX, startX};
        double[] yPoints = {startY, startY, endY, endY};

        brush.setStroke(lineColor);
        brush.setFill(fillColor);
        brush.setLineWidth(3);

        brush.strokePolygon(xPoints, yPoints, 4);
        brush.fillPolygon(xPoints, yPoints, 4);

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

        tempDrawingLayerGC.setStroke(lineColor);
        tempDrawingLayerGC.setFill(fillColor);
        tempDrawingLayerGC.setLineWidth(3);

        tempDrawingLayerGC.strokeOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());
        tempDrawingLayerGC.fillOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());
    }

    private void endEllipse(Double x, Double y){
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        ellipseBounds.setWidth(Math.abs(x - ellipseBounds.getX()));
        ellipseBounds.setHeight(Math.abs(y - ellipseBounds.getY()));

        brush.setStroke(lineColor);
        brush.setFill(fillColor);
        brush.setLineWidth(3);

        brush.strokeOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());
        brush.fillOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());

    }

    private void polygonClick(){
        canvasAnchorPane.setOnMouseClicked(event -> {
            if(selectedTool == "polygon") {
                renderPolygonPreview(event.getX(), event.getY(), event.getButton());
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

    private void renderPolygonPreview(Double x, Double y, MouseButton button){
        if(button == MouseButton.SECONDARY){
            closePolygon();
            return;
        }

        polygonPointsX[polygonPointsCount] = x;
        polygonPointsY[polygonPointsCount] = y;

        tempDrawingLayerGC.setStroke(lineColor);
        tempDrawingLayerGC.setLineWidth(1);

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokeLine(polygonPointsX[polygonPointsCount-1], polygonPointsY[polygonPointsCount-1], polygonPointsX[polygonPointsCount], polygonPointsY[polygonPointsCount]);
        polygonPointsCount++;
    }

    private void refreshColors(){
        lineColor = lineColorPicker.getValue();
        fillColor = fillColorPicker.getValue();
    }

    private void handleMouseEvent(){
        canvas.setOnMouseClicked(event -> {
            refreshColors();
            if(selectedTool == "plot") {
                plotPoint(event.getX(), event.getY());
            }

            if(selectedTool == "polygon") {
                if(polygonPointsCount == 0){
                    setupPolygon(event.getX(), event.getY());
                } else {
                    renderPolygonPreview(event.getX(), event.getY(), event.getButton());
                }
            }
        });

        canvas.setOnMousePressed(event -> {
            refreshColors();

            if(selectedTool == "line") {    setupLine(event.getX(), event.getY());  }

            if(selectedTool == "rectangle"){
                setupRectangle(event.getX(), event.getY());
            }

            if(selectedTool == "ellipse"){
                setupEllipse(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseDragged(event -> {
            refreshColors();

            if(selectedTool == "line") {    renderLinePreview(event.getX(), event.getY());  }

            if(selectedTool == "rectangle"){
                renderRectanglePreview(event.getX(), event.getY());
            }

            if(selectedTool == "ellipse"){
                renderEllipsePreview(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseReleased(event->{
            refreshColors();

            if(selectedTool == "line") {    endLine();  }

            if(selectedTool == "rectangle"){
                endRectangle(event.getX(), event.getY());
            }

            if(selectedTool == "ellipse"){
                endEllipse(event.getX(), event.getY());
            }
        });
    }

    private static void hex2Rgb(String colorStr) {
        //String hex[] = colorStr.split("0x");
        //System.out.println(hex[1]);
        //Color test = Color.decode("#FFCCEE");
        /*for(int i=0; i<hex.length; i++){
            System.out.println(hex[i]);
        }*/
        //System.out.println(Integer.valueOf( colorStr.substring( 1, 3 ), 16 ));
        //System.out.println(Integer.valueOf( colorStr.substring( 3, 5 ), 16 ));
        //System.out.println(Integer.valueOf( colorStr.substring( 5, 7 ), 16 ));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        Color transparent = Color.web("0xffffff00", 0);

        lineColorPicker.setValue(Color.BLACK);
        fillColorPicker.setValue(transparent);

        lineColorPicker.getStyleClass().add("button");
        fillColorPicker.getStyleClass().add("button");

        brush = canvas.getGraphicsContext2D();
        brush.setLineWidth(1);
    }



    @FXML
    public void toolSelected(ActionEvent e){
        toolSelected = true;
    }
}
