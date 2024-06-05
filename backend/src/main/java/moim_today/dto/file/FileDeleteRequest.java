package moim_today.dto.file;

import jakarta.validation.constraints.NotBlank;

import static moim_today.global.constant.exception.ValidationExceptionConstant.FILENAME_BLANK_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.UPLOAD_FILE_PATH_BLANK_ERROR;

public record FileDeleteRequest(
        @NotBlank(message = UPLOAD_FILE_PATH_BLANK_ERROR) String uploadFilePath,
        @NotBlank(message = FILENAME_BLANK_ERROR) String fileName
) {

}
