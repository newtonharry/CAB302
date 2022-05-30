package sample.Exceptions;

public class FileExistsException extends Exception {
    /**
     * Exception for when a file already exists.
     * @param errorMessage
     */
    public FileExistsException(String errorMessage) { super(errorMessage); }
}
