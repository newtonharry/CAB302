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

    public void queNewInstruction(VecInstruction instruction) {
        quedInsturctions.add(instruction);
    }

    public void undoInstruction(){
        if(quedInsturctions.size() >= 1) {
            quedInsturctions.remove(quedInsturctions.size() - 1);
        }
    }

    public void revertTo(int time){
        quedInsturctions = (InstructionList) quedInsturctions.subList(0,time);
    }

    public void drawShapes(){
        brush.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (VecInstruction instr : quedInsturctions) {
            if (instr instanceof Shape)
                ((Shape) instr).draw();
        }
    }

    public void clearInstructions(){
        quedInsturctions.clear();
    }

    public List<VecInstruction> getInstructions(){
       return quedInsturctions;
    }

    public void listen(){
        this.quedInsturctions.addListener((
                ListChangeListener.Change<? extends VecInstruction> instructions) -> {
            while (instructions.next())
                if(instructions.wasAdded() || instructions.wasRemoved())
                    drawShapes();
        });
    }
}
