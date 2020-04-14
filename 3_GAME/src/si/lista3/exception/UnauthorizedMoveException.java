package si.lista3.exception;

public class UnauthorizedMoveException extends RuntimeException {
    public UnauthorizedMoveException(String message) {
        super(message);
    }
}
