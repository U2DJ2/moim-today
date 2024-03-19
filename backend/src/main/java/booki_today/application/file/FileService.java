package booki_today.application.file;

import booki_today.dto.file.FileAddRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<FileAddRequest> uploadFiles(String uploadFilePath, List<MultipartFile> multipartFiles);
    String deleteFile(String uploadFilePath, String uuidFileName);
}
