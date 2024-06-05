package moim_today.application.file;

import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.dto.file.FileResponse;
import moim_today.implement.file.FileComposition;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AmazonS3Service implements FileService {

    private final FileComposition fileComposition;

    public AmazonS3Service(final FileComposition fileComposition) {
        this.fileComposition = fileComposition;
    }

    public FileResponse uploadFile(final String fileType, final MultipartFile multipartFile){
        String imageUrl = fileComposition.uploadFile(fileType, multipartFile);
        return new FileResponse(imageUrl);
    }

    public void deleteFile(final MemberSession memberSession, final FileDeleteRequest fileDeleteRequest){
        fileComposition.deleteFile(memberSession, fileDeleteRequest);
    }
}
