package sample.Exceptions;

public class ParserException extends Exception {
    /**
     * Exception relating to any errors that happen when parsing
     * VEC instructions.
     * @param errorMessage
     */
    public ParserException(String errorMessage){
        super(errorMessage);
    }
}
