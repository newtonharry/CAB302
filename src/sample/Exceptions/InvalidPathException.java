package sample.Exceptions;

public class InvalidPathException extends Exception {
    /**
     * Exception for when an invalid path is specified.
     * @param errorMessage
     */
    public InvalidPathException(String errorMessage) { super(errorMessage); }
}
