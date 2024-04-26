package moim_today.global.error;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import moim_today.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static moim_today.global.constant.StatusCodeConstant.BAD_REQUEST;
import static moim_today.global.constant.exception.SpringExceptionConstant.*;

@Slf4j
@RestControllerAdvice
public class ApiRestControllerAdvice {

    /**
     * 커스텀 예외 처리
     */

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorResponse handleException(final BadRequestException e) {
        log.info("BadRequestException={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }

    // 401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ErrorResponse handleException(final UnauthorizedException e) {
        log.info("UnauthorizedException Exception={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }

    // 403
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorResponse handleException(final ForbiddenException e) {
        log.info("ForbiddenException Exception={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handleException(final NotFoundException e) {
        log.info("NotFoundException Exception={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }

    // 500
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerException.class)
    public ErrorResponse handleInternalServerError(final InternalServerException e) {
        log.error("InternalServerException={}", e.getMessage());
        return ErrorResponse.of(e.getStatusCode(), e.getMessage());
    }

    // 400
    // description : Valid Annotation Custom Error Message
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleConstraintViolationException(final ConstraintViolationException e) {
        log.error("ConstraintViolationException={}", e.getMessage());
        return ErrorResponse.of(BAD_REQUEST.statusCode(), e.getMessage());
    }

    // 400
    // description : Enum class JSON Parse Error
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleIncorrectData(final HttpMessageNotReadableException e){
        return ErrorResponse.of(BAD_REQUEST.statusCode(), e.getMessage());
    }

    /**
     * 스프링 예외 처리
     */

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse handleException(final MethodArgumentTypeMismatchException e) {
        log.error("MethodArgumentTypeMismatchException={}", e.getMessage());
        BadRequestException exception = new BadRequestException(METHOD_ARGUMENT_TYPE_MISMATCH.message());
        return ErrorResponse.of(exception.getStatusCode(), exception.getMessage());
    }

    // 400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse handleException(final MissingServletRequestParameterException e) {
        log.error("MissingServletRequestParameterException={}", e.getMessage());
        BadRequestException exception = new BadRequestException(MISSING_SERVLET_REQUEST_PARAMETER.message());
        return ErrorResponse.of(exception.getStatusCode(), exception.getMessage());
    }

    // 404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResponse handleException(final NoHandlerFoundException e) {
        log.error("NoHandlerFoundException={}", e.getMessage());
        NotFoundException exception = new NotFoundException(NO_HANDLER_FOUND.message());
        return ErrorResponse.of(exception.getStatusCode(), exception.getMessage());
    }

    // 405
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse handleException(final HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException={}", e.getMessage());
        MethodNotAllowedException exception = new MethodNotAllowedException(METHOD_NOT_ALLOWED.message());
        return ErrorResponse.of(exception.getStatusCode(), exception.getMessage());
    }
}
