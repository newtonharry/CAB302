package sample.GUI;

public class KeyboardShortcuts {
    static public void newCommand() {
        sample.Main.getController().newCanvasMenuBtnClick();
    }

    static public void openCommand() {
        sample.Main.getController().openMenuBtnClick();
    }

    static public void saveCommand() {
        sample.Main.getController().saveMenuBtnClick();
    }

    static public void exportCommand() {
        sample.Main.getController().exportMenuBtnClick();
    }

    static public void undoCommand() {
        sample.Main.getController().undoMenuBtnClick();
    }

    static public void gridCommand() {
        sample.Main.getController().showGridMenuBtnClick();
    }

}
