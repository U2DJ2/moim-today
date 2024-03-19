package booki_today.dto.file;

import lombok.Builder;

@Builder
public class FileAddRequest {
    private String originalFileName;
    private String uploadFileName;
    private String uploadFilePath;
    private String uploadFileUrl;
}
