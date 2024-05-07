package moim_today.fake_class.file;

import moim_today.application.file.FileService;
import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.dto.file.FileResponse;
import org.springframework.web.multipart.MultipartFile;

public class FakeFileService implements FileService {

    @Override
    public FileResponse uploadFile(final String fileType, final MultipartFile multipartFile) {
        return new FileResponse("fileUrl");
    }

    @Override
    public void deleteFile(final MemberSession memberSession, final FileDeleteRequest fileDeleteRequest) {
    }
}
