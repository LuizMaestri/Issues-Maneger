package exception;

/**
 * @Author luiz on 02/12/15.
 */
public class InvalidParamsException extends Exception{

    private String message;

    public InvalidParamsException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
