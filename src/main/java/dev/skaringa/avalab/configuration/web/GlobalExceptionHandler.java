package dev.skaringa.avalab.configuration.web;

import dev.skaringa.avalab.api.Error;
import dev.skaringa.avalab.api.ErrorCode;
import dev.skaringa.avalab.exception.BaseException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Set;
import java.util.stream.Collectors;

@Log4j2
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception e, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("An error occurred", e);
        return ResponseEntity.status(status).body(toErrors(e));
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException e) {
        log.error("An error occurred", e);
        Set<Error> errors = toErrors(e);
        return ResponseEntity.status(resolveStatus(errors)).body(errors);
    }

    @ExceptionHandler({HttpMessageConversionException.class})
    protected ResponseEntity<Object> handleHttpMessageConversionException(HttpMessageConversionException e) {
        log.error("An error occurred", e);
        Set<Error> errors = toErrors(e);
        return ResponseEntity.status(resolveStatus(errors)).body(errors);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("An error occurred", e);
        Set<Error> errors = toErrors(e);
        return ResponseEntity.status(resolveStatus(errors)).body(errors);
    }

    @ExceptionHandler({BaseException.class})
    protected ResponseEntity<Object> handleBase(BaseException e) {
        log.error("An error occurred", e);
        Set<Error> errors = toErrors(e);
        return ResponseEntity.status(resolveStatus(errors)).body(errors);
    }

    private Set<Error> toErrors(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return Set.of(toIllegalArgumentExceptionError(e));
        } else if (e instanceof HttpMessageConversionException) {
            return Set.of(toHttpMessageConversionExceptionError(e));
        } else if (e instanceof MethodArgumentNotValidException) {
            return toArgumentNotValidErrors((MethodArgumentNotValidException) e);
        } else if (e instanceof BaseException) {
            return Set.of(toError((BaseException) e));
        }

        return Set.of(toUnexpectedError(e));
    }

    private Error toIllegalArgumentExceptionError(Exception e) {
        return Error.system(ErrorCode.ILLEGAL_ARGUMENT, e.getMessage());
    }

    private Error toHttpMessageConversionExceptionError(Exception e) {
        return Error.system(ErrorCode.INVALID_JSON, e.getMessage());
    }

    private Set<Error> toArgumentNotValidErrors(MethodArgumentNotValidException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(this::toIllegalArgumentExceptionError)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Error toIllegalArgumentExceptionError(FieldError e) {
        return Error.system(ErrorCode.INVALID_ARGUMENT, "'" + e.getField() + "' " + e.getDefaultMessage());
    }

    private Error toError(BaseException source) {
        return new Error(source.getType(), source.getCode(), source.getMessage());
    }

    private Error toUnexpectedError(Exception t) {
        return Error.unexpected(t.getMessage());
    }

    private HttpStatus resolveStatus(Set<Error> errors) {
        if (errors.stream().anyMatch(error -> error.getCode() == ErrorCode.ILLEGAL_ARGUMENT)) {
            return HttpStatus.BAD_REQUEST;
        } else if (errors.stream().anyMatch(error -> error.getCode() == ErrorCode.INVALID_ARGUMENT)) {
            return HttpStatus.BAD_REQUEST;
        } else if (errors.stream().anyMatch(error -> error.getCode() == ErrorCode.NOT_FOUND)) {
            return HttpStatus.NOT_FOUND;
        } else

            return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
