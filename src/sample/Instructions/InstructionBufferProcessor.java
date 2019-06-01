package sample.Instructions;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class InstructionBufferProcessor {
    public static final InstructionBufferProcessor BUFFER_PROCESSOR = new InstructionBufferProcessor();


    private InstructionList quedInsturctions = new InstructionList();

    public Canvas canvas;
    public GraphicsContext brush;

    /**
     * Adds a new VecInstruction to the quedInstructions list
     *
     *
     * @param instruction the x-coordinate to be converted as a double
     * @return void
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
     * Given an index time, the quedInstruction list is assigned
     * to a subList of itself from index 0 up to and including the index
     * time
     *
     * @param time
     * @return void
     */
    public void revertTo(int time) {
        quedInsturctions = (InstructionList) FXCollections.observableArrayList(quedInsturctions.subList(0, time));
    }

    /**
     * Clears the quedInstructions list, removing all VecInstructions
     *
     * @return void
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
     * @return void
     */
    public void drawShapes(int upto) {
        brush.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (VecInstruction instr : quedInsturctions.subList(0,upto)) {
            if (instr instanceof Shape)
                ((Shape) instr).draw();
        }
    }

    /**
     *  Attatches a listener to the quedInstructions list checking if
     *  a VecInstruction was add or removed. If so, then it draws out all
     *  of the shapes.
     *
     * @return void
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
