package booki_today.application.file;

import booki_today.domain.member.MemberSession;
import booki_today.dto.file.FileDeleteRequest;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void uploadFile(final MemberSession memberSession, final MultipartFile multipartFile);

    void deleteFile(final FileDeleteRequest fileDeleteRequest);
}
