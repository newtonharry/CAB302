package sample.Instructions;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

public class InstructionList extends SimpleListProperty<VecInstruction> {
    /**
     * A new type of list which inherits the properties of the SimpleListProperty
     * as well as the JavaFX observableArrayList methods. Accepts only VecInstruction
     * objects.
     */
    public InstructionList(){
        super(FXCollections.observableArrayList());
    }
}
