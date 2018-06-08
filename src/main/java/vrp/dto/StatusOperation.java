package vrp.dto;

public class StatusOperation {

    private final String message;

    public StatusOperation(final String  message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
