package apishop.exception.common;

import apishop.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static apishop.util.exception.ConstantsException.Exception.Common.*;

public class CanNotDeleteException extends ArchitectureException {

    private static final long serialVersionUID = 1L;

    public CanNotDeleteException(String field) {
        super();
        this.code = CAN_NOT_DELETE_CODE;
        this.message = "Can not delete" + field + CAN_NOT_DELETE;
        this.status = HttpStatus.CONFLICT;
    }

}
