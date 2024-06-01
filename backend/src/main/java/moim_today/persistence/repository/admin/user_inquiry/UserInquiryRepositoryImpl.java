package moim_today.persistence.repository.admin.user_inquiry;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.global.constant.exception.UserInquiryExceptionConstant.USER_INQUIRY_NOT_FOUND_ERROR;

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
    public UserInquiryJpaEntity findById(final long userInquiryId) {
        return userInquiryJpaRepository.findById(userInquiryId)
                .orElseThrow(() -> new NotFoundException(USER_INQUIRY_NOT_FOUND_ERROR.message()));
    }

    @Override
    public List<UserInquiryJpaEntity> getAllUserInquiryByUniversityId(final long universityId){
        return userInquiryJpaRepository.findAllByUniversityIdOrderByCreatedAtDesc(universityId);
    }
}
