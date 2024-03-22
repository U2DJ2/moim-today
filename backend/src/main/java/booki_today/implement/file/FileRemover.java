package booki_today.implement.file;

import booki_today.dto.file.FileDeleteRequest;
import booki_today.global.annotation.Implement;
import booki_today.global.error.InternalServerException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import static booki_today.global.constant.FileExceptionConstant.FILE_DELETE_ERROR;

@Implement
@Slf4j
public class FileRemover {

    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public FileRemover(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void deleteFile(final FileDeleteRequest fileDeleteRequest){
        try{
            String uploadFilePath = fileDeleteRequest.uploadFilePath();
            String fileName = fileDeleteRequest.fileName();
            String key = uploadFilePath+"/"+fileName;

            amazonS3.deleteObject(bucketName, key);
        }catch (SdkClientException e){
            log.error("Exception [Err_Location]: {}", e.getStackTrace()[0]);
            throw new InternalServerException(FILE_DELETE_ERROR.message());
        }
    }
}
