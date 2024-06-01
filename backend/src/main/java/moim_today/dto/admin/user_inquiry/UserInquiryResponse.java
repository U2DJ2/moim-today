package moim_today.dto.admin.user_inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;

import java.time.LocalDateTime;

@Builder
public record UserInquiryResponse(
        long userInquiryId,
        long memberId,
        String departmentName,
        String inquiryTitle,
        String inquiryContent,
        boolean answerComplete,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
       ) {

        public static UserInquiryResponse of(final UserInquiryJpaEntity userInquiryJpaEntity, final String departmentName){
                return UserInquiryResponse.builder()
                        .userInquiryId(userInquiryJpaEntity.getId())
                        .memberId(userInquiryJpaEntity.getMemberId())
                        .departmentName(departmentName)
                        .inquiryTitle(userInquiryJpaEntity.getInquiryTitle())
                        .inquiryContent(userInquiryJpaEntity.getInquiryContent())
                        .answerComplete(userInquiryJpaEntity.isAnswerComplete())
                        .createdAt(userInquiryJpaEntity.getCreatedAt())
                        .build();
        }
}
