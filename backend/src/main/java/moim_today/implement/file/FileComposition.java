package moim_today.implement.file;

import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.global.annotation.Implement;
import org.springframework.web.multipart.MultipartFile;


@Implement
public class FileComposition {

    private final FileUploader fileUploader;
    private final FileRemover fileRemover;

    public FileComposition(final FileUploader fileUploader, final FileRemover fileRemover) {
        this.fileUploader = fileUploader;
        this.fileRemover = fileRemover;
    }

    public String uploadFile(final String fileType, final MultipartFile multipartFile) {
        return fileUploader.uploadFile(fileType, multipartFile);
    }

    public void deleteFile(final MemberSession memberSession, final FileDeleteRequest fileDeleteRequest){
        fileRemover.deleteFile(memberSession, fileDeleteRequest);
    }
}