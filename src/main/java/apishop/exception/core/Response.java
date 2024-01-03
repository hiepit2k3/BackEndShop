package apishop.exception.core;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Response {
    protected boolean result;
    protected Integer status;
    protected String error_code;
    protected String message;
    protected String classException;
}
