package si.lista3.exception;

public class FieldNotEmptyException extends RuntimeException {
    public FieldNotEmptyException(String message) {
        super(message);
    }
}
