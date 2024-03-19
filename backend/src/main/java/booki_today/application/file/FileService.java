package booki_today.application.file;

import booki_today.dto.file.FileAddRequest;
import booki_today.dto.file.FileDeleteRequest;
import booki_today.implement.file.FileDeleter;
import booki_today.implement.file.FileUploader;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private final FileUploader fileUploader;
    private final FileDeleter fileDeleter;

    public FileService(final FileUploader fileUploader, final FileDeleter fileDeleter) {
        this.fileUploader = fileUploader;
        this.fileDeleter = fileDeleter;
    }


    public void uploadFile(FileAddRequest fileAddRequest){
        fileUploader.uploadFile(fileAddRequest);
    }
    public void deleteFile(FileDeleteRequest fileDeleteRequest){
        fileDeleter.deleteFile(fileDeleteRequest);
    }
}
