package moim_today.application.admin.user_inquiry;

import moim_today.application.mail.MailService;
import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRespondRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.admin.user_inquiry.UserInquiryFinder;
import moim_today.implement.member.MemberFinder;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.MailConstant.USER_INQUIRY_RESPONSE_SUBJECT_PREFIX;

@Service
public class UserInquiryService {

    private final MailService mailService;
    private final MemberFinder memberFinder;
    private final UserInquiryFinder userInquiryFinder;
    private final UserInquiryRepository userInquiryRepository;

    public UserInquiryService(final MailService mailService,
                              final MemberFinder memberFinder,
                              final UserInquiryFinder userInquiryFinder,
                              final UserInquiryRepository userInquiryRepository) {
        this.mailService = mailService;
        this.memberFinder = memberFinder;
        this.userInquiryFinder = userInquiryFinder;
        this.userInquiryRepository = userInquiryRepository;
    }

    @Transactional(readOnly = true)
    public List<UserInquiryResponse> getAllUserInquiry(final long universityId) {
        return userInquiryFinder.getAllUserInquiry(universityId);
    }

    @Transactional(readOnly = true)
    public List<UserInquiryResponse> getAllNotAnsweredUserInquiry(final long universityId) {
        return userInquiryFinder.getAllNotAnsweredUserInquiry(universityId);
    }

    @Transactional
    public void updateUserInquiryAnswer(final UserInquiryAnswerRequest userInquiryAnswerRequest) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.getById(userInquiryAnswerRequest.userInquiryId());
        findUserInquiry.updateAnswerComplete(userInquiryAnswerRequest.answerComplete());
    }

    public void respondUserInquiry(final UserInquiryRespondRequest userInquiryRespondRequest) {
        String subject = userInquiryFinder.makeResponseSubject(userInquiryRespondRequest.userInquiryId());
        String userEmail = memberFinder.getMemberProfile(userInquiryRespondRequest.memberId()).email();
        MailSendRequest mailSendRequest = userInquiryRespondRequest.toMailSendRequest(subject, userEmail);
        mailService.sendUserInquiryResponseMail(mailSendRequest, userInquiryRespondRequest.responseContent());
    }
}
