package moim_today.application.file;

import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.dto.file.FileResponse;
import moim_today.implement.file.FileRemover;
import moim_today.implement.file.FileUploader;
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

    public FileResponse uploadFile(final String fileType, final MultipartFile multipartFile){
        String imageUrl = fileUploader.uploadFile(fileType, multipartFile);
        return new FileResponse(imageUrl);
    }

    public void deleteFile(final MemberSession memberSession, final FileDeleteRequest fileDeleteRequest){
        fileRemover.deleteFile(memberSession, fileDeleteRequest);
    }
}
