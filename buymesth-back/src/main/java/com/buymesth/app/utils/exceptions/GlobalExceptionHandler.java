package com.buymesth.app.utils.exceptions;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<Object> handleCustomException(CustomException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false)), ex.getStatus());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public final ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return new ResponseEntity<>(new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public final ResponseEntity<Object> handleUnauthorizedException(RuntimeException ex, WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public final ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public final ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleForbiddenException(RuntimeException ex, WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpClientErrorException.UnprocessableEntity.class)
    public final ResponseEntity<Object> handleUnprocessableEntityException(RuntimeException ex, WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = ex.getConstraintViolations().stream().map(c -> String.format("Param %s %s", c.getPropertyPath().toString().replaceAll("[a-zA-Z]+\\.", ""), c.getMessage())).collect(Collectors.toList());
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), errors.toString(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    //Todo: aclarar mensaje
    @ExceptionHandler(TransactionSystemException.class)
    public final ResponseEntity<Object> handleTransactionSystemException(TransactionSystemException ex, WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), "Transaction could not be commited. ", request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
        List<String> errors = new ArrayList<>(fieldErrors.size() + globalErrors.size());
        String error;
        for (FieldError fieldError : fieldErrors) {
            error = "Param " + fieldError.getField() + " " + fieldError.getDefaultMessage();
            errors.add(error);
        }
        for (ObjectError objectError : globalErrors) {
            error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
            errors.add(error);
        }


        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), errors.toString(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(),
                "The mandatory parameters have not been received in the URL. Missing parameter: " + ex.getVariableName(), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(),
                "The mandatory parameters have not been received in the query: " + ex.getParameterName() +
                        ". Param with name:  " + ex.getParameterName() + "["
                        + ex.getParameterType() + "] needed", request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(),
                "Part " + ex.getRequestPartName() + " is mandatory", request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(),
                "Type mismatch for: " + ex.getPropertyName() + ". Expected: " + ex.getRequiredType().getSimpleName(), request.getDescription(false));

        return new ResponseEntity<>(exceptionResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        if (ex instanceof HttpMessageNotReadableException) {
            CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), "Invalid format data received", request.getDescription(false));
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }

        if (ex instanceof BindException) {
            List<String> errors =
                    ((BindException) ex).getFieldErrors().stream().map(f -> String.format("%s - %s", f.getField(), f.getDefaultMessage())).collect(Collectors.toList());
            CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), errors.toString(), request.getDescription(false));
            return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
        }
        CustomExceptionMessage exceptionResponse = new CustomExceptionMessage(new Date(), "Internal server error",
                request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}