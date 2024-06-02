package moim_today.persistence.repository.admin.user_inquiry;

import moim_today.persistence.entity.admin.UserInquiryJpaEntity;

import java.util.List;

public interface UserInquiryRepository {

    List<UserInquiryJpaEntity> getAllUserInquiryByUniversityId(final long universityId);

    List<UserInquiryJpaEntity> getAllNotAnsweredUserInquiry(final long universityId);

    UserInquiryJpaEntity save(final UserInquiryJpaEntity userInquiryJpaEntity);

    UserInquiryJpaEntity getById(final long userInquiryId);
}
