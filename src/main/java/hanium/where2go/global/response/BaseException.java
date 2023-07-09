package hanium.where2go.global.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BaseException extends RuntimeException {

    private final int status;
    private final String message;

    public BaseException(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
