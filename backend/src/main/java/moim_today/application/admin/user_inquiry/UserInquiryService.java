package moim_today.application.admin.user_inquiry;

import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInquiryService {

    private final UserInquiryRepository userInquiryRepository;

    public UserInquiryService(UserInquiryRepository userInquiryRepository) {
        this.userInquiryRepository = userInquiryRepository;
    }

    public List<UserInquiryResponse> getAllUserInquiryByUniversityId(final long universityId){
        return userInquiryRepository.getAllUserInquiryByUniversityId(universityId).stream()
                .map(UserInquiryResponse::from)
                .toList();
    }

    public void createUserInquiry(final long memberId, final long universityId, final long departmentId, final UserInquiryRequest userInquiryRequest) {
        UserInquiryJpaEntity userInquiryJpaEntity = userInquiryRequest.toEntity(memberId, universityId, departmentId);
        userInquiryRepository.save(userInquiryJpaEntity);
    }

    public void updateUserInquiryAnswer(final UserInquiryAnswerRequest userInquiryAnswerRequest) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.findById(userInquiryAnswerRequest.userInquiryId());
        findUserInquiry.updateAnswerComplete(userInquiryAnswerRequest.answerComplete());
    }
}
