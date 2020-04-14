package si.lista3.exception;

public class ColumnFullException extends RuntimeException {
    public ColumnFullException(String message) {
        super(message);
    }
}
