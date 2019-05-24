package sample.GUI;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class KeyboardShortcuts {
    static public void newCommand(){
        System.out.println("New");
    }

    static public void openCommand(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open VEC File");
        fileChooser.showOpenDialog(stage);
    }

    static public void saveCommand(){
        System.out.println("Save");
    }

    static public void exportCommand(){
        System.out.println("Export");
    }

    static public void undoCommand(){
        System.out.println("Undo");
    }

    static public void gridCommand(){
        System.out.println("Grid");
    }

}
