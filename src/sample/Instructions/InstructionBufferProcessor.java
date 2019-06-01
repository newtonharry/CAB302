package sample.Instructions;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class InstructionBufferProcessor {
    public static final InstructionBufferProcessor BUFFER_PROCESSOR = new InstructionBufferProcessor();


    private ObservableList<VecInstruction> quedInsturctions = new InstructionList();

    public Canvas canvas;
    public GraphicsContext brush;
    public Color lineColor;
    public Color fillColor;

    /**
     * Adds a new VecInstruction to the quedInstructions list
     *
     * @param instruction the x-coordinate to be converted as a double
     */
    public void queNewInstruction(VecInstruction instruction) {
        quedInsturctions.add(instruction);
    }

    /**
     * Removes a VecInstruction of the quedInstructions list
     * if the size is greater than or equal to 1
     *
     * @return void
     */
    public void undoInstruction() {
        if (quedInsturctions.size() >= 1) {
            quedInsturctions.remove(quedInsturctions.size() - 1);
        }
    }

    /**
     * Given an index time greater than or equal to 0, a calculation is made which decides
     * how many times the undoInstruction method is called.
     *
     * @param time
     */
    public void revertTo(int time) {
        if (time >= 0) {
            int timesToUndo = quedInsturctions.size() - time;
            for (int i = 0; i < timesToUndo; i++) {
                undoInstruction();
            }
        }
    }

    /**
     * Clears the quedInstructions list, removing all VecInstructions
     *
     */
    public void clearInstructions() {
        quedInsturctions.clear();
    }

    /**
     * Returns the current quedInstructions list
     *
     * @return List<VecInstruction>
     */
    public List<VecInstruction> getInstructions() {
        return quedInsturctions;
    }

    /**
     * Clears the current canvas of all drawings and then loops through
     * the quedInstructions list drawing out all of the shapes within the list
     *
     */
    public void drawShapes(int upto) {
        if(upto == -1){upto = quedInsturctions.size();}
        resetCanvas();
        for (VecInstruction instr : quedInsturctions.subList(0, upto)) {
            instr.draw();
        }
    }


    /**
     *  Clears the canvas of all drawings. Re-assigns lineColor
     *  and fillColor to their default values.
     */
    public void resetCanvas(){
        brush.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        Color transparent = Color.web("0xffffff00", 0);
        lineColor = Color.BLACK;
        fillColor = transparent;
    }

    /**
     * Attaches a listener to the quedInstructions list checking if
     * a VecInstruction was add or removed. If so, then it draws out all
     * of the shapes.
     */
    public void listen() {
        this.quedInsturctions.addListener((
                ListChangeListener.Change<? extends VecInstruction> instructions) -> {
            while (instructions.next())
                if (instructions.wasAdded() || instructions.wasRemoved())
                    drawShapes(quedInsturctions.size());
        });
    }
}
