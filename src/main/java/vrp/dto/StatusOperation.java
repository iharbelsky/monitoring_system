package vrp.dto;

public class StatusOperation {

    private final int status;
    private final String message;

    public StatusOperation(final int  status, final String  message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
