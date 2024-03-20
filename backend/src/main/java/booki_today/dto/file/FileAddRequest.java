package booki_today.dto.file;

import lombok.Builder;

@Builder
public record FileAddRequest(
        String uploadFilePath
) {
}
