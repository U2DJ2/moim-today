package moim_today.persistence.repository.admin.user_inquiry;

import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserInquiryRepositoryImpl implements UserInquiryRepository{

    private final UserInquiryJpaRepository userInquiryJpaRepository;

    public UserInquiryRepositoryImpl(final UserInquiryJpaRepository userInquiryJpaRepository) {
        this.userInquiryJpaRepository = userInquiryJpaRepository;
    }

    @Override
    public UserInquiryJpaEntity save(final UserInquiryJpaEntity userInquiryJpaEntity) {
        return userInquiryJpaRepository.save(userInquiryJpaEntity);
    }

    @Override
    public List<UserInquiryJpaEntity> getAllUserInquiryByUniversityId(final long universityId){
        return userInquiryJpaRepository.findAllByUniversityId(universityId);
    }
}
