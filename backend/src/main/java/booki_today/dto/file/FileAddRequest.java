package booki_today.dto.file;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record FileAddRequest(
        String uploadFilePath,
        MultipartFile multipartFile
) {
}
