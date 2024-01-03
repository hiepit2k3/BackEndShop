package apishop.exception.core;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
public class ErrorResponse extends Response {
    public ErrorResponse(ArchitectureException exception) {
        this.result = false;
        this.status = exception.status.value();
        this.error_code = exception.getCode();
        this.message = exception.getMessage();
    }

    public ErrorResponse(String message, String classException) {
        this.result = false;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.error_code = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        this.message = message;
        this.classException = classException;
    }
}
