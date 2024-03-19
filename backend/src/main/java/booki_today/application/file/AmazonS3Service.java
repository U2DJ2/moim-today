package booki_today.application.file;

import booki_today.dto.file.FileAddRequest;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AmazonS3Service implements FileService {


    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public AmazonS3Service(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public List<FileAddRequest> uploadFiles(final String uploadFilePath, final List<MultipartFile> multipartFiles) {
        List<FileAddRequest> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {

            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {

                String keyName = uploadFilePath + "/" + uploadFileName;

                amazonS3.putObject(
                        new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));


                uploadFileUrl = amazonS3.getUrl(bucketName, keyName).toString();

            } catch (IOException e) {
                e.printStackTrace();
                log.error("Filed upload failed", e);
            }

            s3files.add(
                    FileAddRequest.builder()
                            .originalFileName(originalFileName)
                            .uploadFileName(uploadFileName)
                            .uploadFilePath(uploadFilePath)
                            .uploadFileUrl(uploadFileUrl)
                            .build());
        }

        return s3files;
    }

    @Override
    public String deleteFile(final String uploadFilePath, final String uuidFileName) {
        return null;
    }

    public String getUuidFileName(final String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1);
        return UUID.randomUUID() + "." + ext;
    }
}