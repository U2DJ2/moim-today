package moim_today.application.file;

import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.dto.file.FileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileResponse uploadFile(final String fileType, final MultipartFile multipartFile);

    void deleteFile(final MemberSession memberSession, final FileDeleteRequest fileDeleteRequest);
}
