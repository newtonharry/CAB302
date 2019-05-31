package sample.Instructions;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class InstructionList extends SimpleListProperty<VecInstruction> {

    public InstructionList(){
        super(FXCollections.observableArrayList());
    }
}
