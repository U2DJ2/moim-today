package moim_today.dto.admin.user_inquiry;

import jakarta.validation.constraints.NotBlank;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;

import static moim_today.global.constant.exception.ValidationExceptionConstant.INQUIRY_CONTENT_BLANK_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.INQUIRY_TITLE_BLANK_ERROR;

public record UserInquiryRequest (
        @NotBlank(message = INQUIRY_TITLE_BLANK_ERROR) String inquiryTitle,
        @NotBlank(message = INQUIRY_CONTENT_BLANK_ERROR) String inquiryContent
){

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
