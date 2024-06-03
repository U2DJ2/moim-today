package moim_today.dto.admin.user_inquiry;

import jakarta.validation.constraints.NotBlank;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;

public record UserInquiryRequest (
        @NotBlank(message = INQUIRY_TITLE_BLANK_ERROR) String inquiryTitle,
        @NotBlank(message = INQUIRY_CONTENT_BLANK_ERROR) String inquiryContent
){
    private static final String INQUIRY_TITLE_BLANK_ERROR = "제목은 공백일 수 없습니다.";
    private static final String INQUIRY_CONTENT_BLANK_ERROR = "답변은 공백일 수 없습니다.";

    public UserInquiryJpaEntity toEntity(final long memberId, final long universityId, final long departmentId){
        return UserInquiryJpaEntity.builder()
                .memberId(memberId)
                .universityId(universityId)
                .departmentId(departmentId)
                .inquiryTitle(inquiryTitle)
                .inquiryContent(inquiryContent)
                .answerComplete(false)
                .build();
    }
}
