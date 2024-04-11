package moim_today.application.file;

import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.dto.file.FileInfoResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileInfoResponse uploadFile(final MemberSession memberSession, final MultipartFile multipartFile);

    void deleteFile(final MemberSession memberSession, final FileDeleteRequest fileDeleteRequest);
}
