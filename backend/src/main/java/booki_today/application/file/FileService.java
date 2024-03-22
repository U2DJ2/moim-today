package booki_today.application.file;

import booki_today.dto.file.FileAddRequest;
import booki_today.dto.file.FileDeleteRequest;
import booki_today.implement.file.FileRemover;
import booki_today.implement.file.FileUploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void uploadFile(FileAddRequest fileAddRequest, MultipartFile multipartFile);

    void deleteFile(FileDeleteRequest fileDeleteRequest);
}
