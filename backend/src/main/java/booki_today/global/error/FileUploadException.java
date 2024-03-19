package booki_today.global.error;

import lombok.Getter;

import static booki_today.global.constant.StatusCodeConstant.SERVICE_UNAVAILABLE;

@Getter
public class FileUploadException extends RuntimeException{

    private final String statusCode = SERVICE_UNAVAILABLE.statusCode();
    private final String message;

    public FileUploadException(final String message) {
        this.message = message;
    }
}
