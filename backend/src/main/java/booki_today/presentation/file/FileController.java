package booki_today.presentation.file;

import booki_today.application.file.FileService;
import booki_today.dto.file.FileAddRequest;
import booki_today.dto.file.FileDeleteRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/files")
    public void uploadFile(@RequestPart(value = "fileAddRequest") FileAddRequest fileAddRequest,
                            @RequestPart(value = "file") MultipartFile multipartFile) {

        fileService.uploadFile(fileAddRequest, multipartFile);
    }

    @DeleteMapping("/files")
    public void deleteFile(@RequestBody FileDeleteRequest fileDeleteRequest){
        fileService.deleteFile(fileDeleteRequest);
    }
}
