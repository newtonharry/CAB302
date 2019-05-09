package sample;

public class stage {
    private static double stageWidth;
    private static double stageHeight;

    public static double getStageWidth(){
        return stageWidth;
    }

    public static double getStageHeight(){
        return stageHeight;
    }

    public static void setStageHeight(double height){
        stageHeight =  height;
    }

    public static void setStageWidth(double width){
        stageWidth = width;
    }
}
