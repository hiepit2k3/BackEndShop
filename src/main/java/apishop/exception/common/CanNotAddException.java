package apishop.exception.common;

import apishop.exception.core.ArchitectureException;

import org.springframework.http.HttpStatus;

import static apishop.util.exception.ConstantsException.Exception.Common.*;


public class CanNotAddException extends ArchitectureException {

    private static final long serialVersionUID = 1L;
    //endregion

    public CanNotAddException(String field) {
        super();
        this.code = CAN_NOT_ADD_CODE;
        this.message = field + CAN_NOT_ADD;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
