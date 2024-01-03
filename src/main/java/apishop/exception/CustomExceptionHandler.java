package apishop.exception;

import apishop.exception.core.ArchitectureException;
import apishop.exception.core.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.metadata.ConstraintDescriptor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Set;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(ArchitectureException.class)
    public final ResponseEntity<ErrorResponse> handle(ArchitectureException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse(exception);
        return ResponseEntity
                .status(exception.getStatus())
                .body(response);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<ErrorResponse> handle(ExpiredJwtException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse("Your token is expired", exception.getClass().getName());
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ErrorResponse> handle(BadCredentialsException exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse("Your username or password is incorrect", exception.getClass().getName());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleException(Exception exception) {
        exception.printStackTrace();
        ErrorResponse response = new ErrorResponse(exception.getMessage(), exception.getClass().getName());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ErrorResponse> exceptionUnique(DataIntegrityViolationException exception) {
        exception.printStackTrace();
        ErrorResponse response;
        if (exception.getMessage().contains("the REFERENCE constraint"))
            response = new ErrorResponse(
                    "The request could not be fulfilled due to a foreign key constraint conflict",
                    exception.getClass().getName());
        else
            response = new ErrorResponse(
                    exception.getMessage(),
                    exception.getClass().getName());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    @ExceptionHandler({ConstraintViolationException.class, TransactionSystemException.class})
    public final ResponseEntity<ErrorResponse> exceptionConstraint(ConstraintViolationException exception) {
        exception.printStackTrace();

        // Lấy tập hợp các ràng buộc toàn vẹn bị vi phạm
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        // Lặp qua tập hợp các ràng buộc toàn vẹn bị vi phạm
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            // Lấy đối tượng annotation
            ConstraintDescriptor constraint = constraintViolation.getConstraintDescriptor();

            // Lấy tên annotation
            String annotationName = constraint.getClass().getSimpleName();

            // Lấy giá trị được sử dụng để xác định ràng buộc
            Object invalidValue = constraintViolation.getInvalidValue();
            // Tạo lỗi tùy chỉnh
            ErrorResponse response = new ErrorResponse(
                    "Field " + constraintViolation.getPropertyPath() + " had wrong database constraints",
                    annotationName + " violation on value " + invalidValue

            );
            // Trả về lỗi
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(response);
        }
        // Nếu không có ràng buộc nào bị vi phạm
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Unknown error", exception.getClass().getName()));
    }
}
