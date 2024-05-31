package moim_today.dto.admin.user_inquiry;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record UserInquiryResponse(
        long userInquiryId,
        long memberId,
        long universityId,
        String inquiryTitle,
        String inquiryContent,
        boolean answerComplete,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime createdAt
) {

        public static UserInquiryResponse from(final UserInquiryJpaEntity userInquiryJpaEntity){
                return UserInquiryResponse.builder()
                        .userInquiryId(userInquiryJpaEntity.getId())
                        .memberId(userInquiryJpaEntity.getMemberId())
                        .universityId(userInquiryJpaEntity.getUniversityId())
                        .inquiryTitle(userInquiryJpaEntity.getInquiryTitle())
                        .inquiryContent(userInquiryJpaEntity.getInquiryContent())
                        .answerComplete(userInquiryJpaEntity.isAnswerComplete())
                        .build();
        }
}
