package moim_today.fake_class;

import moim_today.application.file.FileService;
import moim_today.dto.file.FileAddRequest;
import moim_today.dto.file.FileDeleteRequest;
import org.springframework.web.multipart.MultipartFile;

public class FakeFileService implements FileService {

    @Override
    public void uploadFile(final FileAddRequest fileAddRequest, final MultipartFile multipartFile) {
    }

    @Override
    public void deleteFile(final FileDeleteRequest fileDeleteRequest) {
    }
}
