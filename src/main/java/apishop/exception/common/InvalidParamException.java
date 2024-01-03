package apishop.exception.common;

import apishop.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static apishop.util.exception.ConstantsException.Exception.Common.INVALID_PARAM;
import static apishop.util.exception.ConstantsException.Exception.Common.INVALID_PARAM_CODE;
public class InvalidParamException extends ArchitectureException {

    //region
    private static final long serialVersionUID = 1L;
    //endregion

    public InvalidParamException() {
        super();
        this.code = INVALID_PARAM_CODE;
        this.message = INVALID_PARAM;
        this.status = HttpStatus.BAD_REQUEST;
    }

}
