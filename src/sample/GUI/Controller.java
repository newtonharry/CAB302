package sample.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import sample.Exceptions.FileExistsException;
import sample.Exceptions.InvalidPathException;
import sample.Exceptions.ParserException;
import sample.Exceptions.ShapeException;
import sample.Instructions.*;
import sample.Parser.Parser;

import javax.imageio.ImageIO;

public class Controller implements Initializable {

    @FXML
    private Button penToolBtn;
    @FXML
    private Button lineToolBtn;
    @FXML
    private Button rectangleToolBtn;
    @FXML
    private Button ellipseToolBtn;
    @FXML
    private Button polygonToolBtn;
    @FXML
    private ColorPicker lineColorPicker;
    @FXML
    private ColorPicker fillColorPicker;

    @FXML
    private Canvas canvas;
    @FXML
    public AnchorPane canvasPane;
    @FXML
    private AnchorPane canvasAnchorPane;

    @FXML
    private Canvas gridCanvas;
    private boolean showGrid;

    private Color lineColor;
    private Color fillColor;
    private String selectedTool;
    private GraphicsContext brush;
    private double windowSize = 720.0;

    //private Model model;
    private InstructionList instructions;

    private Line line = new Line();
    private Rectangle rectangle = new Rectangle();
    private Ellipse ellipse = new Ellipse();
    private Rectangle ellipseBounds = new Rectangle();
    private double[] polygonPointsX = new double[9999];
    private double[] polygonPointsY = new double[9999];
    private int polygonPointsCount = 0;

    // Temp Drawing Layers
    private Canvas tempDrawingLayer;
    private GraphicsContext tempDrawingLayerGC;

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

        brush.setStroke(lineColor);
        brush.setFill(fillColor);

        brush.setLineWidth(3);

        brush.strokePolygon(polygonPointsX, polygonPointsY, polygonPointsCount);
        brush.fillPolygon(polygonPointsX, polygonPointsY, polygonPointsCount);

        polygonPointsX = new double[9999];
        polygonPointsY = new double[9999];
        polygonPointsCount = 0;
    }


    @FXML
    private void newCanvasMenuBtnClick() {
        sample.GUI.KeyboardShortcuts.newCommand();
    }

    @FXML
    private void saveMenuBtnClick() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save vector file");
        fileChooser.setInitialDirectory(new File("resources"));
        fileChooser.setInitialFileName("untitled.vec");
        File importVec = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        try {
            Parser parser = new Parser(importVec.toString(), instructions);
            parser.writeInstructions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void undoMenuBtnClick() {
        sample.GUI.KeyboardShortcuts.undoCommand();
    }

    @FXML
    private void showGridMenuBtnClick() {
        System.out.println("Toggle Grid Called");
        sample.GUI.KeyboardShortcuts.gridCommand();
        toggleGrid();

    }

    public void toggleGrid() {
        showGrid = !showGrid;
        BooleanProperty showGridBP = new SimpleBooleanProperty(!showGrid);

        gridCanvas.visibleProperty().bind(showGridBP);
    }

    private void drawGrid() {
        GraphicsContext grid = gridCanvas.getGraphicsContext2D();

        Color gridColor = Color.web("#787878");
        double gridSize = 0.1;
        double calculatedGridSize = gridSize * windowSize;
        double gridlinesRequired = gridSize * 100;

        grid.setStroke(gridColor);
        grid.setLineWidth(0.5);


        for (int i = 0; i < gridlinesRequired; i++) {
            grid.strokeLine(0, calculatedGridSize * i, windowSize, calculatedGridSize * i);
        }

        for (int i = 0; i < gridlinesRequired; i++) {
            grid.strokeLine(calculatedGridSize * i, 0, calculatedGridSize * i, windowSize);
        }

        //grid.strokeLine(10, 10, 50, 10);
        //line = new Line();
    }

    @FXML
    private void exportMenuBtnClick() {
        try {
            exportBMP("", "CanvasImage.bmp", 4096);
        } catch (IOException e) {

        } catch (FileExistsException e) {

        } catch (InvalidPathException e) {

        }
    }

    @FXML
    public void openMenuBtnClick() {
        // sample.GUI.KeyboardShortcuts.openCommand(stage);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open vector file");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Vector Files", "*.vec")
        );
        fileChooser.setInitialDirectory(new File("resources"));
        fileChooser.setInitialFileName("myDesign.vec");
        File importVec = fileChooser.showOpenDialog(canvas.getScene().getWindow());

        brush.clearRect(0, 0, 700, 700);
        instructions.clear();
        try {
            Parser parser = new Parser(importVec.toString(), instructions);
            parser.readInstructions();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        } catch (ShapeException e) {
            e.printStackTrace();
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
        brush.setStroke(lineColor);
        brush.setLineWidth(1);

        brush.strokeLine(x, y, x, y);
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
            canvasAnchorPane.getChildren().remove(tempDrawingLayer);

            coordinates.add((x / canvas.getWidth()) * 1.0);
            coordinates.add((y / canvas.getHeight()) * 1.0);
            coordinates.add((event.getX() / canvas.getWidth()) * 1.0);
            coordinates.add((event.getY() / canvas.getHeight()) * 1.0);

            LineInstruction LineInst = null;
            try {
                LineInst = new LineInstruction(lineColor.toString(), coordinates);
            } catch (ShapeException e) {
                e.printStackTrace();
            }

            instructions.add(LineInst);
        });
    }


    private void setupRectangle(Double x, Double y) {
        tempDrawingLayer = new Canvas(windowSize, windowSize);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        List<Double> coordinates = new ArrayList<>();

        rectangle.setX(x);
        rectangle.setY(y);

        canvas.setOnMouseReleased(event -> {
            canvasAnchorPane.getChildren().remove(tempDrawingLayer);
            coordinates.add((x / canvas.getWidth()) * 1.0);
            coordinates.add((y / canvas.getHeight()) * 1.0);
            coordinates.add((event.getX() / canvas.getWidth()) * 1.0);
            coordinates.add((event.getY() / canvas.getHeight()) * 1.0);

            RectangleInstruction RectangleInst = null;
            try {
                RectangleInst = new RectangleInstruction(lineColor.toString(), fillColor.toString(), coordinates);
            } catch (ShapeException e) {
                e.printStackTrace();
            }

            instructions.add(RectangleInst);
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
            canvasAnchorPane.getChildren().remove(tempDrawingLayer);

            coordinates.add((x / canvas.getWidth()) * 1.0);
            coordinates.add((y / canvas.getHeight()) * 1.0);
            coordinates.add((event.getX() / canvas.getWidth()) * 1.0);
            coordinates.add((event.getY() / canvas.getHeight()) * 1.0);


            EllipseInstruction ellipseInst = null;
            try {
                ellipseInst = new EllipseInstruction(lineColor.toString(), fillColor.toString(), coordinates);
            } catch (ShapeException e) {
                e.printStackTrace();
            }
            instructions.add(ellipseInst); // NEED to translate coordinates to normal number
        });
    }


    /*
    private void endEllipse(Double x, Double y) {
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        ellipseBounds.setWidth(Math.abs(x - ellipseBounds.getX()));
        ellipseBounds.setHeight(Math.abs(y - ellipseBounds.getY()));

        brush.setStroke(lineColor);
        brush.setFill(fillColor);
        brush.setLineWidth(3);

        brush.strokeOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());
        brush.fillOval(Math.min(ellipseBounds.getX(), x), Math.min(ellipseBounds.getY(), y), ellipseBounds.getWidth(), ellipseBounds.getHeight());

    }
     */

    private void polygonClick() {
        canvasAnchorPane.setOnMouseClicked(event -> {
            if (selectedTool.equals("polygon")) {
                renderPolygonPreview(event.getX(), event.getY(), event.getButton());
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

    private void handleMouseEvent() {
        canvas.setOnMouseClicked(event -> {

            //canvasPane.setPrefWidth(Main.getScene().getWidth());

            refreshColors();
            if (selectedTool.equals("plot")) {
                plotPoint(event.getX(), event.getY());

            }

            if (selectedTool.equals("polygon")) {
                if (polygonPointsCount == 0) {
                    setupPolygon(event.getX(), event.getY());
                } else {
                    renderPolygonPreview(event.getX(), event.getY(), event.getButton());
                }
            }
        });

        canvas.setOnMousePressed(event -> {
            refreshColors();

            if (selectedTool.equals("line")) {
                setupLine(event.getX(), event.getY());
            }

            if (selectedTool.equals("rectangle")) {
                setupRectangle(event.getX(), event.getY());
            }

            if (selectedTool.equals("ellipse")) {
                setupEllipse(event.getX(), event.getY());
            }
        });

        canvas.setOnMouseDragged(event -> {
            refreshColors();

            if (selectedTool.equals("line")) {
                renderLinePreview(event.getX(), event.getY());
            }

            if (selectedTool.equals("rectangle")) {
                renderRectanglePreview(event.getX(), event.getY());
            }

            if (selectedTool.equals("ellipse")) {
                renderEllipsePreview(event.getX(), event.getY());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        drawGrid();
        showGrid = false;

        Color transparent = Color.web("0xffffff00", 0);

        lineColorPicker.setValue(Color.BLACK);
        fillColorPicker.setValue(transparent);

        lineColorPicker.getStyleClass().add("button");
        fillColorPicker.getStyleClass().add("button");

        brush = canvas.getGraphicsContext2D();
        brush.setLineWidth(1);
    }

    /**
     * Exports the file which is currently being worked on as a BMP image, so that image files in a raster format
     * can be created from within the application.
     *
     * @param path       The path to the folder in which BMP is to be saved (e.g. "CAB-302_Assignment/")
     * @param fileName   The file name for the BMP to be saved (e.g. "CanvasImage.bmp")
     * @param resolution The number of pixels across one edge of the BMP image
     * @throws IOException          Thrown if the BMP data cannot be interpreted when saving
     * @throws FileExistsException  Thrown if the BMP file to be saved already exists
     * @throws InvalidPathException Thrown if the path for the BMP file is invalid
     */

    // TODO: complete resolution scaling so that image is correctly scaled to output resolution
    private void exportBMP(String path, String fileName, int resolution)
            throws IOException, FileExistsException, InvalidPathException {

        File exportFile = new File(path + fileName);

        if (!Files.exists(new File(path).toPath()))
            throw new InvalidPathException("Could not export BMP file, directory for exporting was invalid");

        if (!exportFile.exists())
            throw new FileExistsException("Could not export BMP file, file given by pathname already exists");

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
        canvas.snapshot(null, writableImage);

        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
        BufferedImage convertedImage = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB
        );

        convertedImage.createGraphics()
                .drawImage(bufferedImage, 0, 0, java.awt.Color.WHITE, null);

        ImageIO.write(convertedImage, "bmp", exportFile);
    }

    /**
     * Initialises the listeners to adjust shapes on the canvas based on the
     * instructions listed in the model
     *
     * @param instructions the model containing the shape instructions
     */
    public void initialiseModel(InstructionList instructions) {
        if (this.instructions != null)
            throw new IllegalStateException("Model is only initialisable once");
        //this.model = model;
        this.instructions = instructions;

        this.instructions.addListener((
                ListChangeListener.Change<? extends VecInstruction> instruction) -> {
            while (instruction.next())
                if (instruction.wasAdded())
                    for (VecInstruction instr : instruction.getList())
                        if (instr instanceof Shape)
                            ((Shape) instr).draw(canvas, brush);
        });
    }
}
