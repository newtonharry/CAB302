package sample.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

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

    @FXML private Button penToolBtn;
    @FXML private Button lineToolBtn;
    @FXML private Button rectangleToolBtn;
    @FXML private Button ellipseToolBtn;
    @FXML private Button polygonToolBtn;
    @FXML private ColorPicker lineColorPicker;
    @FXML private ColorPicker fillColorPicker;

    @FXML private Canvas canvas;
    @FXML public AnchorPane canvasPane;
    @FXML private AnchorPane canvasAnchorPane;

    private Color lineColor;
    private Color fillColor;
    private String selectedTool;
    private GraphicsContext brush;

    private Model model;

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
        sample.GUI.KeyboardShortcuts.saveCommand();
    }

    @FXML
    private void undoMenuBtnClick() {
        sample.GUI.KeyboardShortcuts.undoCommand();
    }

    @FXML
    private void showGridMenuBtnClick() {
        sample.GUI.KeyboardShortcuts.gridCommand();
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

        try {
            Parser parser = new Parser(importVec.toString(), model);
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


    private void setupLine(Double x, Double y) {
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);

        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        brush.beginPath();
        line.setStartX(x);
        line.setStartY(y);
    }


    private void renderLinePreview(Double x, Double y) {
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

    private void plotPoint(Double x, Double y) {
        brush.setStroke(lineColor);
        brush.setLineWidth(1);

        brush.strokeLine(x, y, x, y);
    }

    private void endLine() {
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);
        canvasAnchorPane.getChildren().remove(tempDrawingLayer);

        brush.setStroke(lineColor);
        brush.setLineWidth(1);

        brush.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
        line = new Line();
    }

    private void setupRectangle(Double x, Double y) {
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);

        rectangle.setX(x);
        rectangle.setY(y);
    }

    private void renderRectanglePreview(Double x, Double y) {
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

    private void endRectangle(Double x, Double y) {
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

    private void setupEllipse(Double x, Double y) {
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);

        ellipseBounds.setX(x);
        ellipseBounds.setY(y);
        ellipse.setCenterX(ellipseBounds.getX());
        ellipse.setCenterY(ellipseBounds.getY());
        ellipse.setRadiusX(0);
        ellipse.setRadiusY(0);
    }

    private void renderEllipsePreview(Double x, Double y) {
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

    private void polygonClick() {
        canvasAnchorPane.setOnMouseClicked(event -> {
            if (selectedTool.equals("polygon")) {
                renderPolygonPreview(event.getX(), event.getY(), event.getButton());
            }
        });
    }

    private void setupPolygon(Double x, Double y) {
        tempDrawingLayer = new Canvas(700, 500);
        canvasAnchorPane.getChildren().add(tempDrawingLayer);
        tempDrawingLayerGC = tempDrawingLayer.getGraphicsContext2D();

        polygonPointsX[polygonPointsCount] = x;
        polygonPointsY[polygonPointsCount] = y;
        polygonPointsCount++;

        polygonClick();
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

        canvas.setOnMouseReleased(event -> {
            refreshColors();

             if (selectedTool.equals("line")) {
                endLine();
            }

            if (selectedTool.equals("rectangle")) {
                endRectangle(event.getX(), event.getY());
            }

            if (selectedTool.equals("ellipse")) {
                endEllipse(event.getX(), event.getY());
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


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
     * @param path                   The path to the folder in which BMP is to be saved (e.g. "CAB-302_Assignment/")
     * @param fileName               The file name for the BMP to be saved (e.g. "CanvasImage.bmp")
     * @param resolution             The number of pixels across one edge of the BMP image
     * @throws IOException           Thrown if the BMP data cannot be interpreted when saving
     * @throws FileExistsException   Thrown if the BMP file to be saved already exists
     * @throws InvalidPathException  Thrown if the path for the BMP file is invalid
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
     * @param model     the model containing the shape instructions
     */
    public void initialiseModel(Model model) {
        if (this.model != null)
            throw new IllegalStateException("Model is only initialisable once");
        this.model = model;

        this.model.getEllipseList().addListener((
            ListChangeListener.Change<? extends EllipseInstruction> ellipses) -> {
                while (ellipses.next())
                    if (ellipses.wasAdded())
                        for (EllipseInstruction ellipse : ellipses.getAddedSubList())
                            drawEllipse(
                                    ellipse.getFill(), ellipse.getPen(),
                                    ellipse.getCoordinates()
                            );
        });

        this.model.getLineList().addListener((
            ListChangeListener.Change<? extends LineInstruction> lines) -> {
                while (lines.next())
                    if (lines.wasAdded())
                        for (LineInstruction line : lines.getAddedSubList())
                            drawLine(
                                    line.getPen(),
                                    line.getCoordinates()
                            );
        });

        this.model.getPlotList().addListener((
            ListChangeListener.Change<? extends PlotInstruction> plots) -> {
                while (plots.next())
                    if (plots.wasAdded())
                        for (PlotInstruction plot : plots.getAddedSubList())
                            drawPlot(
                                    plot.getPen(),
                                    plot.getCoordinates()
                            );
        });

        this.model.getPolygonList().addListener((
            ListChangeListener.Change<? extends PolygonInstruction> polygons) -> {
                while (polygons.next())
                    if (polygons.wasAdded())
                        for (PolygonInstruction polygon : polygons.getAddedSubList())
                            drawPolygon(
                                    polygon.getFill(), polygon.getPen(),
                                    polygon.getCoordinates()
                            );
        });

        this.model.getRectangleList().addListener((
            ListChangeListener.Change<? extends RectangleInstruction> rectangles) -> {
                while (rectangles.next())
                    if (rectangles.wasAdded())
                        for (RectangleInstruction rectangle : rectangles.getAddedSubList())
                            drawRectangle(
                                    rectangle.getFill(), rectangle.getPen(),
                                    rectangle.getCoordinates()
                            );
        });
    }

    /**
     * Draws an ellipse when editing, parsing .vec files, or exporting
     *
     * @param fill      the hexadecimal representation of the rectangle's
     *                  fill colour
     * @param pen       the hexadecimal representation of the rectangle's
     *                  pen colour
     * @param coords    a list of doubles representing the coordinates for
     *                  the bounds of the ellipse
     */
    private void drawEllipse(String fill, String pen, List<Double> coords) {

        int x = convertXCoord(coords.get(0)),
            y = convertYCoord(coords.get(1)),
            width  = convertWidth(coords.get(0), coords.get(2)),
            height = convertHeight(coords.get(1), coords.get(3));

        brush = canvas.getGraphicsContext2D();

        if (fill.equals("OFF"))
            brush.setFill(Color.web("000000", 0.0));
        else
            brush.setFill(Color.web(fill, 1.0));

        brush.setStroke(Color.web(pen, 1.0));
        brush.setLineWidth(3);
        brush.strokeOval(x, y, width, height);
        brush.fillOval(x, y, width, height);
    }

    /**
     * Draws a line when editing, parsing .vec files, or exporting
     *
     * @param pen       the hexadecimal representation of the rectangle's
     *                  pen colour
     * @param coords    a list of doubles representing the coordinates of the
     *                  two ends of the line
     */
    private void drawLine(String pen, List<Double> coords) {

        int x1 = convertXCoord(coords.get(0)),
            y1 = convertYCoord(coords.get(1)),
            x2 = convertXCoord(coords.get(2)),
            y2 = convertYCoord(coords.get(3));

        brush = canvas.getGraphicsContext2D();
        brush.setStroke(Color.web(pen, 1.0));

        System.out.println(pen);
        brush.setLineWidth(1);
        brush.strokeLine(x1, y1, x2, y2);
    }

    /**
     * Draws a plot when editing, parsing .vec files, or exporting
     *
     *
     * @param pen       the hexadecimal representation of the rectangle's
     *                  pen colour
     * @param coords    a list of doubles representing the coordinates of
     *                  the plot
     */
    private void drawPlot(String pen, List<Double> coords) {

        int x = convertXCoord(coords.get(0)),
            y = convertYCoord(coords.get(1));

        brush = canvas.getGraphicsContext2D();
        brush.setStroke(Color.web(pen, 1.0));

        System.out.println(pen);
        brush.setLineWidth(1);
        brush.strokeLine(x, y, x, y);
    }

    /**
     * Draws a polygon when editing, parsing .vec files, or exporting
     *
     * @param fill      the hexadecimal representation of the rectangle's
     *                  fill colour
     * @param pen       the hexadecimal representation of the rectangle's
     *                  pen colour
     *
     * @param coords    a list of doubles representing the coordinates along
     *                  the polygon's perimeter
     */
    private void drawPolygon(String fill, String pen, List<Double> coords) {

        double[] xCoords = new double[coords.size() / 2],
                 yCoords = new double[coords.size() / 2];

        brush = canvas.getGraphicsContext2D();
        if (fill.equals("OFF"))
            brush.setFill(Color.web("000000", 0.0));
        else
            brush.setFill(Color.web(fill, 1.0));
        brush.setStroke(Color.web(pen, 1.0));

        for (int i = 0; i < coords.size(); i++)
            if (i % 2 == 0)
                xCoords[i / 2]       = convertXCoord(coords.get(i));
            else
                yCoords[(i - 1) / 2] = convertYCoord(coords.get(i));

        System.out.println(pen);
        System.out.println(fill);
        brush.setLineWidth(1);
        brush.strokePolygon(xCoords, yCoords, coords.size() / 2);
        brush.fillPolygon(xCoords, yCoords, coords.size() / 2);
    }

    /**
     * Draws a rectangle when editing, parsing .vec files, or exporting
     *
     * @param fill      the hexadecimal representation of the rectangle's
     *                  fill colour
     * @param pen       the hexadecimal representation of the rectangle's
     *                  pen colour
     * @param coords    a list of doubles representing the coordinates of the
     *                  bounds of the rectangle
     */
    private void drawRectangle(String fill, String pen, List<Double> coords) {

        double[] xCoords = {
                    convertXCoord(coords.get(0)),
                    convertXCoord(coords.get(2)),
                    convertXCoord(coords.get(2)),
                    convertXCoord(coords.get(0))
        },
                 yCoords = {
                    convertYCoord(coords.get(1)),
                    convertYCoord(coords.get(1)),
                    convertYCoord(coords.get(3)),
                    convertYCoord(coords.get(3))
        };

        brush = canvas.getGraphicsContext2D();

        if (fill.equals("OFF"))
            brush.setFill(Color.web("000000", 0.0));
        else
            brush.setFill(Color.web(fill, 1.0));

        brush.setStroke(Color.web(pen, 1.0));

        System.out.println(pen);
        System.out.println(fill);
        brush.strokePolygon(xCoords, yCoords, 4);
        brush.fillPolygon(xCoords, yCoords, 4);
    }

    /**
     * Converts a fractional x-coordinate into an integer based on the
     * current width of the canvas
     *
     * @param coord     the x-coordinate to be converted as a double
     * @return          the integer version of the coordinate
     */
    private int convertXCoord(double coord) {
        return (int) Math.round(coord * canvas.getWidth());
    }

    /**
     * Converts a fractional y-coordinate into an integer based on the
     * current height of the canvas
     *
     * @param coord     the y-coordinate to be converted as a double
     * @return          the integer version of the coordinate
     */
    private int convertYCoord(double coord) {
        return (int) Math.round(coord * canvas.getHeight());
    }

    /**
     * Converts a pair of fractional x-coordinates into an integer width
     * based on the current width of the canvas
     *
     * @param coord1    the first x-coordinate as a double
     * @param coord2    the second x-coordinate as a double
     * @return          the width between them as an integer
     */
    private int convertWidth(double coord1, double coord2) {
        return (int) Math.round(Math.abs(coord1 - coord2) * canvas.getWidth());
    }

    /**
     * Converts a pair of fractional y-coordinates into an integer height
     * based on the current height of the canvas
     *
     * @param coord1    the first y-coordinate as a double
     * @param coord2    the second y-coordinate as a double
     * @return          the height between them as an integer
     */
    private int convertHeight(double coord1, double coord2) {
        return (int) Math.round(Math.abs(coord1 - coord2) * canvas.getHeight());
    }
}
