package exception;

public class AuthenticationUserException extends Exception {

    private String message;

    public AuthenticationUserException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
