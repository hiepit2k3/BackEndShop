package apishop.exception.entity;

import apishop.exception.core.ArchitectureException;
import org.springframework.http.HttpStatus;

import static apishop.util.exception.ConstantsException.Entity.ENTITY_NOT_FOUND;
import static apishop.util.exception.ConstantsException.Entity.ENTITY_NOT_FOUND_CODE;

public class EntityNotFoundException extends ArchitectureException {

    //region
    private static final long serialVersionUID = 1L;
    //endregion

    public EntityNotFoundException() {
        super();
        this.code = ENTITY_NOT_FOUND_CODE;
        this.message = ENTITY_NOT_FOUND;
        this.status = HttpStatus.NOT_FOUND;
    }
}