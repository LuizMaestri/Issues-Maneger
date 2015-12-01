package exception;

/**
 * Created by luiz on 28/11/15.
 */
public class InvolvedException extends Exception{

    private String message;

    public InvolvedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
