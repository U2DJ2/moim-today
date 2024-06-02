package moim_today.implement.admin.user_inquiry;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class UserInquiryAppender {

    private final UserInquiryRepository userInquiryRepository;

    public UserInquiryAppender(final UserInquiryRepository userInquiryRepository) {
        this.userInquiryRepository = userInquiryRepository;
    }

    @Transactional
    public void createUserInquiry(final UserInquiryJpaEntity userInquiryJpaEntity) {
        userInquiryRepository.save(userInquiryJpaEntity);
    }
}
