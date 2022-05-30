package coll.UserAccounts;

class FundsException extends Exception {
    public FundsException() {
        super();
    }

    public FundsException(String message) {
        super(message);
    }

    public FundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public FundsException(Throwable cause) {
        super(cause);
    }
}
