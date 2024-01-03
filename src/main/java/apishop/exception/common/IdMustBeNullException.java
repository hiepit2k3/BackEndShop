package apishop.exception.common;

import apishop.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static apishop.util.exception.ConstantsException.Exception.Common.ID_MUST_BE_NULL;
import static apishop.util.exception.ConstantsException.Exception.Common.ID_MUST_BE_NULL_CODE;


public class IdMustBeNullException extends ArchitectureException {

    private static final long serialVersionUID = 1L;

    public IdMustBeNullException(String message) {
        super();
        this.code = ID_MUST_BE_NULL_CODE;
        this.message = message + ID_MUST_BE_NULL;
        this.status = HttpStatus.BAD_REQUEST;
    }
}
