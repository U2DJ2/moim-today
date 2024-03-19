package booki_today.implement.file;

import booki_today.dto.file.FileDeleteRequest;
import booki_today.global.annotation.Implement;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Value;

@Implement
public class FileDeleter {

    private final AmazonS3 amazonS3;
    private final String bucketName;
    public FileDeleter(final AmazonS3 amazonS3, @Value("${cloud.aws.s3.bucket}") final String bucketName) {
        this.amazonS3 = amazonS3;
        this.bucketName = bucketName;
    }

    public void deleteFile(final FileDeleteRequest fileDeleteRequest) {
        String uploadFilePath = fileDeleteRequest.uploadFilePath();
        String uuidFileName = fileDeleteRequest.uuidFileName();
    }
}
