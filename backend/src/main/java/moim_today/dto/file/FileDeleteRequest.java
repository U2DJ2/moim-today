package moim_today.dto.file;

import jakarta.validation.constraints.NotBlank;

public record FileDeleteRequest(
        @NotBlank(message = UPLOAD_FILE_PATH_BLANK_ERROR) String uploadFilePath,
        @NotBlank(message = FILENAME_BLANK_ERROR) String fileName
) {
    private static final String UPLOAD_FILE_PATH_BLANK_ERROR = "업로드 파일 경로는 공백일 수 없습니다.";
    private static final String FILENAME_BLANK_ERROR = "파일 이름은 공백일 수 없습니다.";
}
