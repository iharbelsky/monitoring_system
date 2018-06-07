package vrp.dto;

public class StatusOperation {

    private final int code;
    private final String text;

    public StatusOperation(final int  code
                         , final String  text) {
        this.code=code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
