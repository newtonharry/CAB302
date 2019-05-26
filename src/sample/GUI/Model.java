package sample.GUI;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Instructions.*;

public class Model {

    private final ObservableList<EllipseInstruction> ellipses       = FXCollections.observableArrayList();
    private final ObservableList<LineInstruction> lines             = FXCollections.observableArrayList();
    private final ObservableList<PlotInstruction> plots             = FXCollections.observableArrayList();
    private final ObservableList<PolygonInstruction> polygons       = FXCollections.observableArrayList();
    private final ObservableList<RectangleInstruction> rectangles   = FXCollections.observableArrayList();

    private final ObjectProperty<EllipseInstruction> currentEllipse     = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LineInstruction> currentLine           = new SimpleObjectProperty<>(null);
    private final ObjectProperty<PlotInstruction> currentPlot           = new SimpleObjectProperty<>(null);
    private final ObjectProperty<PolygonInstruction> currentPolygon     = new SimpleObjectProperty<>(null);
    private final ObjectProperty<RectangleInstruction> currentRectangle = new SimpleObjectProperty<>(null);
    private ObjectProperty<PenInstruction> currentPen             = new SimpleObjectProperty<>(null);
    private ObjectProperty<FillInstruction> currentFill           = new SimpleObjectProperty<>(null);

    ObservableList<EllipseInstruction> getEllipseList()      { return ellipses;      }
    ObservableList<LineInstruction> getLineList()            { return lines;         }
    ObservableList<PlotInstruction> getPlotList()            { return plots;         }
    ObservableList<PolygonInstruction> getPolygonList()      { return polygons;      }
    ObservableList<RectangleInstruction> getRectangleList()  { return rectangles;    }

    public final EllipseInstruction getCurrentEllipse()     { return currentEllipse.get();      }
    public final LineInstruction getCurrentLine()           { return currentLine.get();         }
    public final PlotInstruction getCurrentPlot()           { return currentPlot.get();         }
    public final PolygonInstruction getCurrentPolygon()     { return currentPolygon.get();      }
    public final RectangleInstruction getCurrentRectangle() { return currentRectangle.get();    }
    public final PenInstruction getCurrentPen()             { return currentPen.get();          }
    public final FillInstruction getCurrentFill()           { return currentFill.get();         }

    public void addEllipseInstruction(EllipseInstruction ellipse) {
        ellipses.add(ellipse);
        currentEllipse.setValue(ellipse);
    }

    public void addLineInstruction(LineInstruction line) {
        lines.add(line);
        currentLine.setValue(line);
    }

    public void addPlotInstruction(PlotInstruction plot) {
        plots.add(plot);
        currentPlot.setValue(plot);
    }

    public void addPolygonInstruction(PolygonInstruction polygon) {
        polygons.add(polygon);
        currentPolygon.setValue(polygon);
    }

    public void addRectangleInstruction(RectangleInstruction rectangle) {
        rectangles.add(rectangle);
    }

    public void addPenInstruction(PenInstruction pen) {
        currentPen.setValue(pen);
    }

    public void addFillInstruction(FillInstruction fill) {
        currentFill.setValue(fill);
    }
}
