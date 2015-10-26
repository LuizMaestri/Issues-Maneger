package exception;

public class ConnectionException extends Exception{

    private String message;

    public ConnectionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
