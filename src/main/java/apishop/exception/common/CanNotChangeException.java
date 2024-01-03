package apishop.exception.common;

import apishop.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static apishop.util.exception.ConstantsException.Exception.Common.CAN_NOT_CHANGE;
import static apishop.util.exception.ConstantsException.Exception.Common.CAN_NOT_CHANGE_CODE;


public class CanNotChangeException extends ArchitectureException {

    private static final long serialVersionUID = 1L;
    //endregion

    public CanNotChangeException(String field) {
        super();
        this.code = CAN_NOT_CHANGE_CODE;
        this.message = field + CAN_NOT_CHANGE;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
