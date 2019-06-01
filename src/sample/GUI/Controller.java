package sample.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import sample.Exceptions.FileExistsException;
import sample.Exceptions.InvalidPathException;
import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;
import sample.Parser.Parser;

import javax.imageio.ImageIO;


public class Controller implements Initializable {

    @FXML private AnchorPane containerPane;
    @FXML private Button penToolBtn;
    @FXML private Button lineToolBtn;
    @FXML private Button rectangleToolBtn;
    @FXML private Button ellipseToolBtn;
    @FXML private Button polygonToolBtn;
    @FXML private ColorPicker lineColorPicker;
    @FXML private ColorPicker fillColorPicker;

    @FXML public Canvas canvas;
    @FXML public AnchorPane canvasPane;
    @FXML private AnchorPane canvasAnchorPane;
    @FXML private AnchorPane abc;
    private static double windowSize = 720.0;

    @FXML private AnchorPane gridAnchorPane;
    @FXML private Canvas gridCanvas = new Canvas(windowSize, windowSize);
    private boolean showGrid;
    private double gridSize;

    public Color lineColor;
    public Color fillColor;
    private String selectedTool;
    public GraphicsContext brush;

    private Line line = new Line();
    private Rectangle rectangle = new Rectangle();
    private Ellipse ellipse = new Ellipse();
    private Rectangle ellipseBounds = new Rectangle();
    private double[] polygonPointsX = new double[9999];
    private double[] polygonPointsY = new double[9999];
    private int polygonPointsCount = 0;

    private Canvas tempDrawingLayer;
    private GraphicsContext tempDrawingLayerGC;
    private Parser parser;
    private Double canvasScale;

    @FXML
    private void penToolClick() {
        changeActiveButton("pen");
        this.selectedTool = "plot";
        handleMouseEvent();
    }


    @FXML
    private void lineToolClick() {
        changeActiveButton("line");
        this.selectedTool = "line";
        handleMouseEvent();
    }

    @FXML
    private void rectangleToolClick() {
        changeActiveButton("rectangle");
        this.selectedTool = "rectangle";
        handleMouseEvent();
    }

    @FXML
    private void ellipseToolClick() {
        changeActiveButton("ellipse");
        this.selectedTool = "ellipse";
        handleMouseEvent();
    }

    @FXML
    private void polygonToolClick() {
        changeActiveButton("polygon");
        this.selectedTool = "polygon";
        handleMouseEvent();
    }

    @FXML
    private void closePolygon() {
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        List<Double> coordinates = new ArrayList<>();
        System.out.println(polygonPointsX);
        // WHATTTT IS GOING ON HERE ????
        //System.out.println(polygonPointsX.length);

        for(Double num: polygonPointsX){
            if(num != 0.0) {
                coordinates.add((num / canvas.getWidth()) * 1.0);
                System.out.print((num / canvas.getWidth()) * 1.0);
            }
        }

        for(Double num: polygonPointsY){
            if(num != 0.0) {
                coordinates.add((num / canvas.getHeight()) * 1.0);
            }
        }

        try {
            InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new PolygonInstruction(coordinates));
        } catch (ShapeException e) {
            e.printStackTrace();
        }

        /*
        brush.setStroke(lineColor);
        brush.setFill(fillColor);

        brush.setLineWidth(3);

        brush.strokePolygon(polygonPointsX, polygonPointsY, polygonPointsCount);
        brush.fillPolygon(polygonPointsX, polygonPointsY, polygonPointsCount);
         */

        polygonPointsX = new double[9999];
        polygonPointsY = new double[9999];
        polygonPointsCount = 0;
    }


    @FXML public void newCanvasMenuBtnClick() {
        sample.GUI.KeyboardShortcuts.newCommand();
        canvasAnchorPane.getChildren().remove(canvas);
        canvas = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(canvas);

        //Need to clear the observer
    }

    @FXML public void showUndoHistoryMenuBtnClick(){
        List<VecInstruction> instructions = InstructionBufferProcessor.BUFFER_PROCESSOR.getInstructions();

        /*for (VecInstruction instruction : instructions) {
            System.out.println(instruction);
        }*/


        sample.Main.showUndoHistoryGUI(instructions);
    }


    @FXML public void saveMenuBtnClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save vector file");

        if(parser != null){
            fileChooser.setInitialFileName(parser.getFileName());
        }else{
            fileChooser.setInitialFileName("untitled.vec");
        }
        File importVec = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        if (importVec != null) {
            try {
                Parser parser = new Parser(importVec.toString());
                parser.writeInstructions();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParserException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML public void lineColorPickerChange(){
//        lineColor = lineColorPicker.getValue();
        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new PenInstruction(lineColorPicker.getValue().toString()));

    }

    @FXML public void fillColorPickerChange(){
 //       fillColor = fillColorPicker.getValue();
        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(new FillInstruction(fillColorPicker.getValue().toString()));
    }


    @FXML public void undoMenuBtnClick() {
        InstructionBufferProcessor.BUFFER_PROCESSOR.undoInstruction();
    }

    @FXML public void showGridMenuBtnClick() {
        showGrid = !showGrid;
        if (showGrid) {
            String popupValue = showPopup("Grid Size", "0.05", "Please enter a grid size between 0.01 and 0.1", "Grid Size:");
            if (popupValue == null || popupValue == "") {
                showGrid = !showGrid; // Swap Back
                System.out.println("No Value Entered");
            } else {
                try {
                    Double gridValue = Double.parseDouble(popupValue);
                    if (gridValue <= 0.1 && gridValue >= 0.01) {
                        gridCanvas = new Canvas(windowSize, windowSize);
                        gridAnchorPane.getChildren().add(gridCanvas);
                        gridSize = gridValue;
                        drawGrid(gridValue);
                    } else{
                        showGrid = !showGrid; //Swap Back
                        alert(null, "Value out of bounds. Please try again and enter a value between 0.01 and 0.1");
                        showGridMenuBtnClick();
                    }
                } catch (Exception e) {
                    showGrid = !showGrid; //Swap Back
                    alert(null, "Value not valid. Please try again");
                    showGridMenuBtnClick();
                }
            }
        } else{
            gridAnchorPane.getChildren().remove(gridCanvas);
        }
    }

    private void alert(String title, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.setHeaderText(null);
        alert.setGraphic(null);

        alert.showAndWait();
    }

    private String showPopup(String title, String defaultValue, String headerText, String contentText){
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setContentText(contentText);
        dialog.setHeaderText(headerText);
        dialog.setGraphic(null);
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    private void drawGrid(Double gridSize) {
        GraphicsContext grid = gridCanvas.getGraphicsContext2D();

        Color gridColor = Color.web("#C3C3C3");
        //double gridSize = 0.01;
        double calculatedGridSize = gridSize * windowSize;
        double gridlinesRequired = 1 / gridSize;

        grid.setStroke(gridColor);
        grid.setLineWidth(0.5);


        for(int i=0; i<gridlinesRequired; i++){
            grid.strokeLine(0, calculatedGridSize*i, windowSize, calculatedGridSize*i);
            grid.strokeLine(calculatedGridSize*i, 0, calculatedGridSize*i, windowSize);
        }
    }

    private int getResolution(){
        String resolutionString = showPopup("Export Resolution", "720", null, "Resolution:");

        if(resolutionString == null || resolutionString == ""){ return -1;  }

        int resolution = 720;

        try {
            resolution = Integer.parseInt(resolutionString);

            if(resolution < 0){
                System.out.println("Less Than 0");
                getResolution();
            }
        } catch(Exception e){
            System.out.println("Illegal Characters");
            getResolution();
        }

        return resolution;
    }

    @FXML public void exportMenuBtnClick() {
        int resolution = getResolution();
        if(resolution == -1){return;}

        String fileLocation = showSaveFileExplorer("BMP", "bmp").toString();

        try {
            exportBMP(fileLocation, resolution);
            //exportBMP("", "CanvasImage.bmp", 4096);
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (InvalidPathException e) {
            e.printStackTrace();
        } catch (FileExistsException e) {
            e.printStackTrace();
        }*/
    }

    private File showSaveFileExplorer(String fileDescription, String fileExtension){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(fileDescription, "*." + fileExtension)
        );

        File fileLocation = fileChooser.showSaveDialog(sample.Main.getPrimaryStage());

        return fileLocation;
    }

    @FXML
    public void openMenuBtnClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open vector file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Vector Files", "*.vec")
        );
        fileChooser.setInitialFileName("myDesign.vec");
        //fileChooser.setInitialDirectory(new File("../resources"));
        File importVec = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        brush.clearRect(0, 0, 700, 700);
        InstructionBufferProcessor.BUFFER_PROCESSOR.clearInstructions();
        try {
            parser = new Parser(importVec.toString());
            parser.readInstructions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            alert("Parsing Error",e.getMessage());
        } catch (ShapeException e) {
            alert("Shape Error",e.getMessage());
        }
    }

    private void changeActiveButton(String btnType) {
        penToolBtn.getStyleClass().remove("headerBtnActive");
        lineToolBtn.getStyleClass().remove("headerBtnActive");
        rectangleToolBtn.getStyleClass().remove("headerBtnActive");
        ellipseToolBtn.getStyleClass().remove("headerBtnActive");
        polygonToolBtn.getStyleClass().remove("headerBtnActive");

        switch (btnType) {
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

    private void renderLinePreview(Double x, Double y) {
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        tempDrawingLayer = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        line.setEndX(x);
        line.setEndY(y);

        tempDrawingLayerGC.setStroke(lineColor);
        tempDrawingLayerGC.setLineWidth(1);

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
    }

    private void renderRectanglePreview(Double x, Double y) {
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        tempDrawingLayer = new Canvas(windowSize, windowSize);
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

    private void renderPolygonPreview(Double x, Double y, MouseButton button) {
        if (button == MouseButton.SECONDARY) {
            closePolygon();
            return;
        }

        polygonPointsX[polygonPointsCount] = x;
        polygonPointsY[polygonPointsCount] = y;

        tempDrawingLayerGC.setStroke(lineColor);
        tempDrawingLayerGC.setLineWidth(1);

        tempDrawingLayerGC.setStroke(lineColorPicker.getValue());
        tempDrawingLayerGC.strokeLine(polygonPointsX[polygonPointsCount - 1], polygonPointsY[polygonPointsCount - 1], polygonPointsX[polygonPointsCount], polygonPointsY[polygonPointsCount]);
        polygonPointsCount++;
    }


    private void renderEllipsePreview(Double x, Double y) {
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        tempDrawingLayer = new Canvas(windowSize, windowSize);
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


    private void plotPoint(Double x, Double y) {
        List<Double> coordinates = new ArrayList<>();

        Double xPoint = calculateSnapToGrid(x);
        Double yPoint = calculateSnapToGrid(y);

        coordinates.add((xPoint / canvas.getWidth()) * 1.0);
        coordinates.add((yPoint / canvas.getHeight()) * 1.0);

        PlotInstruction PlotInst = null;
        try {
            PlotInst = new PlotInstruction(coordinates);
        } catch (ShapeException e) {
            e.printStackTrace();
        }

        InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(PlotInst);
    }

    private void setupLine(Double x, Double y) {
        tempDrawingLayer = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        List<Double> coordinates = new ArrayList<>();

        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        brush.beginPath();
        line.setStartX(x);
        line.setStartY(y);


        canvas.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                canvasAnchorPane.getChildren().remove(tempDrawingLayer);

                Double xPoint = calculateSnapToGrid(event.getX());
                Double yPoint = calculateSnapToGrid(event.getY());

                coordinates.add((x / canvas.getWidth()) * 1.0);
                coordinates.add((y / canvas.getHeight()) * 1.0);
                coordinates.add((xPoint / canvas.getWidth()) * 1.0);
                coordinates.add((yPoint / canvas.getHeight()) * 1.0);

                LineInstruction LineInst = null;
                try {
                    LineInst = new LineInstruction(coordinates);
                } catch (ShapeException e) {
                    e.printStackTrace();
                }

                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(LineInst);
            }
        });
    }


    private void setupRectangle(Double x, Double y) {
        tempDrawingLayer = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        List<Double> coordinates = new ArrayList<>();

        rectangle.setX(x);
        rectangle.setY(y);

        canvas.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                Double xPoint = calculateSnapToGrid(event.getX());
                Double yPoint = calculateSnapToGrid(event.getY());

                canvasAnchorPane.getChildren().remove(tempDrawingLayer);
                coordinates.add((x / canvas.getWidth()) * 1.0);
                coordinates.add((y / canvas.getHeight()) * 1.0);
                coordinates.add((xPoint / canvas.getWidth()) * 1.0);
                coordinates.add((yPoint / canvas.getHeight()) * 1.0);

                RectangleInstruction RectangleInst = null;
                try {
                    RectangleInst = new RectangleInstruction(coordinates);
                } catch (ShapeException e) {
                    e.printStackTrace();
                }

                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(RectangleInst);
            }
        });
    }




    private void setupEllipse(Double x, Double y) {
        tempDrawingLayer = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        List<Double> coordinates = new ArrayList<>();

        ellipseBounds.setX(x);
        ellipseBounds.setY(y);
        ellipse.setCenterX(ellipseBounds.getX());
        ellipse.setCenterY(ellipseBounds.getY());
        ellipse.setRadiusX(0);
        ellipse.setRadiusY(0);


        canvas.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                canvasAnchorPane.getChildren().remove(tempDrawingLayer);

                Double xPoint = calculateSnapToGrid(event.getX());
                Double yPoint = calculateSnapToGrid(event.getY());
                Double startX = x;
                Double startY = y;

                if (x > xPoint) {
                    Double temp = startX;
                    startX = xPoint;
                    xPoint = temp;
                }

                if (y > yPoint) {
                    Double temp = startY;
                    startY = yPoint;
                    yPoint = temp;
                }

                coordinates.add((startX / canvas.getWidth()) * 1.0);
                coordinates.add((startY / canvas.getHeight()) * 1.0);
                coordinates.add((xPoint / canvas.getWidth()) * 1.0);
                coordinates.add((yPoint / canvas.getHeight()) * 1.0);


                EllipseInstruction ellipseInst = null;
                try {
                    ellipseInst = new EllipseInstruction(coordinates);
                } catch (ShapeException e) {
                    e.printStackTrace();
                }
                InstructionBufferProcessor.BUFFER_PROCESSOR.queNewInstruction(ellipseInst); // NEED to translate coordinates to normal number
            }
        });
    }



    private void polygonClick() {
        canvasAnchorPane.setOnMouseClicked(event -> {

            Double xPoint = calculateSnapToGrid(event.getX());
            Double yPoint = calculateSnapToGrid(event.getY());

            if (selectedTool.equals("polygon")) {
                renderPolygonPreview(xPoint, yPoint, event.getButton());
            }
        });
    }

    private void setupPolygon(Double x, Double y) {
        tempDrawingLayer = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        polygonPointsX[polygonPointsCount] = x;
        polygonPointsY[polygonPointsCount] = y;
        polygonPointsCount++;

        polygonClick();
    }


    private void refreshColors() {
        lineColor = lineColorPicker.getValue();
        fillColor = fillColorPicker.getValue();
    }

    private Double calculateSnapToGrid(Double mouseLocation){
        if(showGrid){
        Double gridDimension = (windowSize / (1 / gridSize));
        Double xGridPoint = mouseLocation / gridDimension;
        int xGridPointRounded = (int)Math.round(xGridPoint);
        return xGridPointRounded * gridDimension;
        }

        return mouseLocation;
    }



    private void handleMouseEvent() {
        canvas.setOnMouseClicked(event -> {
            //refreshColors();
            Double xPoint = calculateSnapToGrid(event.getX());
            Double yPoint = calculateSnapToGrid(event.getY());

            if (selectedTool.equals("plot") && event.getButton() == MouseButton.PRIMARY) {
                plotPoint(xPoint, yPoint);
            }

            if (selectedTool.equals("polygon")) {
                if (polygonPointsCount == 0) {
                    setupPolygon(xPoint, yPoint);
                } else {
                    renderPolygonPreview(xPoint, yPoint, event.getButton());
                }
            }
        });

        canvas.setOnMousePressed(event -> {
            //refreshColors();

            if(event.getButton() == MouseButton.PRIMARY){
                Double xPoint = calculateSnapToGrid(event.getX());
                Double yPoint = calculateSnapToGrid(event.getY());

                if (selectedTool.equals("line")) {
                    setupLine(xPoint, yPoint);
                }

                if (selectedTool.equals("rectangle")) {
                    setupRectangle(xPoint, yPoint);
                }

                if (selectedTool.equals("ellipse")) {
                    setupEllipse(xPoint, yPoint);
                }
            }
        });

        canvas.setOnMouseDragged(event -> {
            //refreshColors();

            if(event.getButton() == MouseButton.PRIMARY) {
                Double xPoint = calculateSnapToGrid(event.getX());
                Double yPoint = calculateSnapToGrid(event.getY());

                if (selectedTool.equals("line")) {
                    renderLinePreview(xPoint, yPoint);
                }

                if (selectedTool.equals("rectangle")) {
                    renderRectanglePreview(xPoint, yPoint);
                }

                if (selectedTool.equals("ellipse")) {
                    renderEllipsePreview(xPoint, yPoint);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showGrid = false;

        Color transparent = Color.web("0xffffff00", 0);

        lineColorPicker.setValue(Color.BLACK);
        fillColorPicker.setValue(transparent);

        lineColorPicker.getStyleClass().add("button");
        fillColorPicker.getStyleClass().add("button");

        brush = canvas.getGraphicsContext2D();
        brush.setLineWidth(1);
        refreshColors();

    }

    public void resizeCanvas(Double stageWidth, Double stageHeight){
        //canvasPane.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        Double currentCanvasHeight = stageHeight - 66 - 25 - 10;
        Double currentCanvasWidth = stageWidth - 10;
        Double smallestCanvasDimension;

        if(currentCanvasWidth <= currentCanvasHeight){
            smallestCanvasDimension = currentCanvasWidth;
        } else {
            smallestCanvasDimension = currentCanvasHeight;
        }

        windowSize = smallestCanvasDimension;

        containerPane.setPrefWidth(smallestCanvasDimension + 10);
        containerPane.setPrefHeight(smallestCanvasDimension + 101);

        canvasPane.setPrefWidth(smallestCanvasDimension + 10);
        canvasPane.setPrefHeight(smallestCanvasDimension + 10);

        abc.setPrefWidth(smallestCanvasDimension);
        abc.setPrefHeight(smallestCanvasDimension);

        gridAnchorPane.setPrefWidth(smallestCanvasDimension);
        gridAnchorPane.setPrefHeight(smallestCanvasDimension);

        gridCanvas.setWidth(smallestCanvasDimension);
        gridCanvas.setHeight(smallestCanvasDimension);

        canvasAnchorPane.setPrefWidth(smallestCanvasDimension);
        canvasAnchorPane.setPrefHeight(smallestCanvasDimension);

        canvas.setWidth(smallestCanvasDimension);
        canvas.setHeight(smallestCanvasDimension);

        if(showGrid){
            gridAnchorPane.getChildren().remove(gridCanvas);
            gridCanvas = new Canvas(windowSize, windowSize);
            gridAnchorPane.getChildren().add(gridCanvas);
            drawGrid(gridSize);
        }

        Double originalDim = 720.0;
        Double newDim = smallestCanvasDimension;
        //System.out.println(originalDim + " " + smallestCanvasDimension);
        //System.out.println((smallestCanvasDimension/originalDim));
        canvasScale = newDim/originalDim;
        scaleShapes();
    }

    /**
     * Exports the file which is currently being worked on as a BMP image, so that image files in a raster format
     * can be created from within the application.
     *
     * @param fileName   The file name for the BMP to be saved (e.g. "CanvasImage.bmp")
     * @param resolution The number of pixels across one edge of the BMP image
     * @throws IOException          Thrown if the BMP data cannot be interpreted when saving
     * @throws FileExistsException  Thrown if the BMP file to be saved already exists
     * @throws InvalidPathException Thrown if the path for the BMP file is invalid
     */

    // TODO: complete resolution scaling so that image is correctly scaled to output resolution
    private void exportBMP(String fileName, int resolution) throws IOException{
            //throws IOException, FileExistsException, InvalidPathException {
        File exportFile = new File(fileName);

        /*if (!Files.exists(new File(path).toPath()))
            throw new InvalidPathException("Could not export BMP file, directory for exporting was invalid");

        if (!exportFile.exists())
            throw new FileExistsException("Could not export BMP file, file given by pathname already exists");
*/
        /*
        final Bounds bounds = canvas.getLayoutBounds();

        double scale = resolution / bounds.getWidth();

        final WritableImage scaledImage = new WritableImage(
                (int) (bounds.getWidth() * scale),
                (int) (bounds.getHeight() * scale)
        );

        final SnapshotParameters snapParams = new SnapshotParameters();
        snapParams.setTransform(javafx.scene.transform.Transform.scale(scale, scale));

        final ImageView imView = new ImageView(canvas.snapshot(snapParams, scaledImage));
        imView.setFitWidth(bounds.getWidth());
        imView.setFitHeight(bounds.getHeight());
        */


        WritableImage writableImage = new WritableImage(resolution, resolution);

        canvas.setWidth(resolution);
        canvas.setHeight(resolution);

        InstructionBufferProcessor.BUFFER_PROCESSOR.drawShapes(-1);

        canvas.snapshot(null, writableImage);

        canvas.setWidth(windowSize);
        canvas.setHeight(windowSize);

        InstructionBufferProcessor.BUFFER_PROCESSOR.drawShapes(-1);

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        BufferedImage convertedImage = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB
        );

        convertedImage.createGraphics()
                .drawImage(bufferedImage, 0, 0, java.awt.Color.WHITE, null);

        ImageIO.write(convertedImage, "bmp", exportFile);
    }



    /*Double testSX = 10.0;
    Double testSY = 10.0;
    Double testEX = 100.0;
    Double testEY = 100.0;

    public void testLine(){
        brush.strokeLine(testSX, testSY, testEX, testEY);
    }*/

    public void scaleShapes(){
        //InstructionBufferProcessor.BUFFER_PROCESSOR.drawShapes();
        /*canvasAnchorPane.getChildren().remove(canvas);
        canvas = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(canvas);

        brush = canvas.getGraphicsContext2D();*/

        /*testSX = 10.0 * canvasScale;
        testSY = 10.0 * canvasScale;
        testEX = 100.0 * canvasScale;
        testEY = 100.0 * canvasScale;

        testLine();*/
    }
}
