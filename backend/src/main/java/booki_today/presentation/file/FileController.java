package booki_today.presentation.file;

import booki_today.application.file.FileService;
import booki_today.dto.file.FileAddRequest;
import booki_today.dto.file.FileDeleteRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/files",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFiles(@RequestBody FileAddRequest fileAddRequest) {
        fileService.uploadFile(fileAddRequest);
    }

    @DeleteMapping("/files")
    public void deleteFile(@RequestBody  FileDeleteRequest fileDeleteRequest){
        fileService.deleteFile(fileDeleteRequest);
    }
}
