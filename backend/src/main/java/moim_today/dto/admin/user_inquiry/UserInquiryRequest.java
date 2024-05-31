package moim_today.dto.admin.user_inquiry;

import moim_today.persistence.entity.admin.UserInquiryJpaEntity;

public record UserInquiryRequest (
        String inquiryTitle,
        String inquiryContent
){

    public UserInquiryJpaEntity toEntity(final long memberId, final long universityId, final long departmentId){
        return UserInquiryJpaEntity.builder()
                .memberId(memberId)
                .universityId(universityId)
                .departmentId(departmentId)
                .inquiryTitle(inquiryTitle)
                .inquiryContent(inquiryContent)
                .build();
    }
}
