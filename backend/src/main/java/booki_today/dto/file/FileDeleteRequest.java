package booki_today.dto.file;

public record FileDeleteRequest(
        String uploadFilePath,
        String fileName
) {
}
