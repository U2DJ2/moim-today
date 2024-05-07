package moim_today.implement.file;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.InternalServerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static moim_today.global.constant.exception.FileExceptionConstant.FILE_EXTENSION_ERROR;
import static moim_today.global.constant.exception.FileExceptionConstant.FILE_UPLOAD_ERROR;

@Slf4j
@Implement
public class FileUploader {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public FileUploader(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(final String fileType, final MultipartFile multipartFile) {

        String uploadFolder = fileType;

        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = createFileName(originalFileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            String keyName = uploadFolder + "/" + uploadFileName;
            amazonS3.putObject(
                    new PutObjectRequest(bucketName, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));
//            TODO: 데이터베이스에 대학 ID와 학번 ID를 key 값으로, uploadFileName 을 Value 값으로 저장하기
            return amazonS3.getUrl(bucketName, keyName).toString();
        } catch (IOException e) {
            log.error("Exception [Err_Location]: {}", e.getStackTrace()[0]);
            throw new InternalServerException(FILE_UPLOAD_ERROR.message());
        }
    }

    // UUID 를 이용해 파일명 중복 회피
    private String createFileName(final String originalFileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    private String getFileExtension(final String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        }catch(StringIndexOutOfBoundsException e) {
            throw new BadRequestException(FILE_EXTENSION_ERROR.message());
        }
    }
}
