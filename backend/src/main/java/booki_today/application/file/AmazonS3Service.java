package booki_today.application.file;

import booki_today.dto.file.FileAddRequest;
import booki_today.dto.file.FileDeleteRequest;
import booki_today.implement.file.FileRemover;
import booki_today.implement.file.FileUploader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AmazonS3Service implements FileService{

    private final FileUploader fileUploader;

    private final FileRemover fileRemover;

    public AmazonS3Service(final FileUploader fileUploader, final FileRemover fileRemover) {
        this.fileUploader = fileUploader;
        this.fileRemover = fileRemover;
    }

    public void uploadFile(final FileAddRequest fileAddRequest, final MultipartFile multipartFile){
        fileUploader.uploadFile(fileAddRequest, multipartFile);
    }

    public void deleteFile(final FileDeleteRequest fileDeleteRequest){
        fileRemover.deleteFile(fileDeleteRequest);
    }
}
