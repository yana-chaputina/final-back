package ru.rosbank.javaschool.finalprojectback.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import ru.rosbank.javaschool.finalprojectback.constants.ErrorStatusConstant;
import ru.rosbank.javaschool.finalprojectback.dto.ErrorResponseDto;
import ru.rosbank.javaschool.finalprojectback.exception.AccessDeniedException;
import ru.rosbank.javaschool.finalprojectback.exception.BadRequestException;
import ru.rosbank.javaschool.finalprojectback.exception.NotFoundException;
import ru.rosbank.javaschool.finalprojectback.exception.UnsupportedFileTypeException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class RestErrorController extends AbstractErrorController {
    private final ErrorAttributes errorAttributes;
    private final String path;
    private final MessageSource messageSource;

    public RestErrorController(ErrorAttributes errorAttributes, @Value("${server.error.path:${error.path:/error}}") String path, MessageSource messageSource) {
        super(errorAttributes);
        this.errorAttributes = errorAttributes;
        this.path = path;
        this.messageSource = messageSource;
    }

    @RequestMapping
    public ResponseEntity<ErrorResponseDto> error(HttpServletRequest request) {
        ServletWebRequest webRequest = new ServletWebRequest(request);
        Throwable error = errorAttributes.getError(webRequest);
        int status = getStatus(request).value();
        String message = "error.unknown";

        if (status == ErrorStatusConstant.STATUS_401) {
            message = "error.unauthorized";
            return ResponseEntity.status(status).body(
                    new ErrorResponseDto(status, message, Collections.emptyMap())
            );
        }
        if (error == null) {
            return ResponseEntity.status(status).body(
                    new ErrorResponseDto(status, message, Collections.emptyMap())
            );
        }
        if (error instanceof BadRequestException) {
            status = ErrorStatusConstant.STATUS_400;
            message = "error.bad_request";
            return getErrorResponseDtoResponseEntity(error, status, message);
        }

        if (error instanceof UnsupportedFileTypeException) {
            status = ErrorStatusConstant.STATUS_400;
            message = "error.bad_filetype";
            return getErrorResponseDtoResponseEntity(error, status, message);
        }

        if (error instanceof NotFoundException) {
            status = ErrorStatusConstant.STATUS_404;
            message = "error.not_found";
            return getErrorResponseDtoResponseEntity(error, status, message);
        }
        if (error instanceof UsernameNotFoundException) {
            status = ErrorStatusConstant.STATUS_404;
            message = "error.not_found";
            return getErrorResponseDtoResponseEntity(error, status, message);
        }
        if (error instanceof AccessDeniedException) {
            status = ErrorStatusConstant.STATUS_403;
            message = "error.forbidden";
            return getErrorResponseDtoResponseEntity(error, status, message);
        }
        if (error instanceof MethodArgumentNotValidException) {
            status = ErrorStatusConstant.STATUS_400;
            message = "error.validation";
            final Map<String, List<String>> errors = ((MethodArgumentNotValidException) error).getBindingResult().getFieldErrors().stream()
                    .collect(
                            Collectors.groupingBy(FieldError::getField,
                                    Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())));

            return getErrorResponseDtoResponseEntity(error, status, message, errors);
        }

        return getErrorResponseDtoResponseEntity(error, status, message);
    }

    private ResponseEntity<ErrorResponseDto> getErrorResponseDtoResponseEntity(Throwable error, int status, String message) {
        return getErrorResponseDtoResponseEntity(error, status, message, Collections.emptyMap());
    }


    private ResponseEntity<ErrorResponseDto> getErrorResponseDtoResponseEntity(Throwable error, int status, String message, Map<String, List<String>> errors) {
        error.printStackTrace();
        return ResponseEntity.status(status).body(
                new ErrorResponseDto(status, message, errors)
        );
    }

    @Override
    public String getErrorPath() {
        return path;
    }
}

