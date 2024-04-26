package moim_today.implement.file;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import lombok.extern.slf4j.Slf4j;
import moim_today.domain.member.MemberSession;
import moim_today.dto.file.FileDeleteRequest;
import moim_today.global.annotation.Implement;
import moim_today.global.error.InternalServerException;
import org.springframework.beans.factory.annotation.Value;

import static moim_today.global.constant.exception.FileExceptionConstant.FILE_DELETE_ERROR;

@Slf4j
@Implement
public class FileRemover {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public FileRemover(final AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public void deleteFile(final MemberSession memberSession, final FileDeleteRequest fileDeleteRequest){
        try{
            // TODO: memberSession 에서 대학 ID와 학번 ID 를 이용해 데이터베이스에서 업로드한 파일명 가져오기
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
