package booki_today.presentation.file;

import booki_today.application.file.FileService;
import booki_today.dto.file.FileAddRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FileController {

    private final FileService amazonS3Service;

    public FileController(FileService amazonS3Service) {
        this.amazonS3Service = amazonS3Service;
    }

    @PostMapping(value = "/files",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FileAddRequest> uploadFiles(
            @RequestParam(value = "uploadFilePath") String uploadFilePath,
            @RequestPart(value = "files") List<MultipartFile> multipartFiles) {
        return amazonS3Service.uploadFiles(uploadFilePath, multipartFiles);
    }

    @DeleteMapping("/file")
    public ResponseEntity<Object> deleteFile(
            @RequestParam(value = "uploadFilePath") String uploadFilePath,
            @RequestParam(value = "uuidFileName") String uuidFileName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(amazonS3Service.deleteFile(uploadFilePath, uuidFileName));
    }
}
