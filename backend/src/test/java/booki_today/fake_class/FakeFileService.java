package booki_today.fake_class;

import booki_today.application.file.FileService;
import booki_today.dto.file.FileAddRequest;
import booki_today.dto.file.FileDeleteRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public class FakeFileService implements FileService {

    @Override
    public void uploadFile(final FileAddRequest fileAddRequest, final MultipartFile multipartFile) {
    }

    @Override
    public void deleteFile(final FileDeleteRequest fileDeleteRequest) {
    }
}
