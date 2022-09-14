package exception;

public class SpaceContainingStringException extends RuntimeException {
    public SpaceContainingStringException(String message) {
        super(message);
    }
}
