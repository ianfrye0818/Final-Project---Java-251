package exceptions;

public class InvalidDataException extends RuntimeException {
    private final String element;

    public InvalidDataException(String message, String element) {
        super(message);
        this.element = element;
    }

    public InvalidDataException(String message, Throwable cause, String element) {
        super(message, cause);
        this.element = element;

    }

    public InvalidDataException(Throwable cause, String element) {
        super(cause);
        this.element = element;
    }

    public InvalidDataException(String element) {
        super("Invalid data: " + element);
        this.element = element;
    }

    public String getElement() {
        return element;
    }

}
