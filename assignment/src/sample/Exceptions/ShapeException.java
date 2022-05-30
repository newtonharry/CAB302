package sample.Exceptions;

public class ShapeException extends Exception {
    /**
     * Exception which is used for when there are errors in
     * the creation of shape objects.
     * @param errorMessage
     */
    public ShapeException(String errorMessage){
        super(errorMessage);
    }
}
