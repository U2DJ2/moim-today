package moim_today.application.admin.user_inquiry;

import moim_today.application.mail.MailService;
import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRespondRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.dto.mail.MailSendRequest;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import static moim_today.global.constant.MailConstant.USER_INQUIRY_RESPONSE_SUBJECT_PREFIX;

@Service
public class UserInquiryService {

    private final MailService mailService;
    private final UserInquiryRepository userInquiryRepository;

    public UserInquiryService(final MailService mailService,
                              final UserInquiryRepository userInquiryRepository) {
        this.mailService = mailService;
        this.userInquiryRepository = userInquiryRepository;
    }

    public List<UserInquiryResponse> getAllUserInquiryByUniversityId(final long universityId) {
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

    public void respondUserInquiry(final UserInquiryRespondRequest userInquiryRespondRequest) {
        String subject = makeResponseSubject(userInquiryRespondRequest.userInquiryId());
        MailSendRequest mailSendRequest = userInquiryRespondRequest.toMailSendRequest(subject);
        mailService.sendUserInquiryResponseMail(mailSendRequest, userInquiryRespondRequest.responseContent());
    }

    private String makeResponseSubject(final long userInquiryId) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.findById(userInquiryId);
        return USER_INQUIRY_RESPONSE_SUBJECT_PREFIX.value() + findUserInquiry.getInquiryTitle();
    }
}
