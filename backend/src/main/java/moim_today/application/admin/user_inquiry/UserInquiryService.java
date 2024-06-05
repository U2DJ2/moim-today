package moim_today.application.admin.user_inquiry;

import moim_today.application.mail.MailService;
import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRespondRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.admin.user_inquiry.UserInquiryComposition;
import moim_today.implement.member.MemberFinder;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserInquiryService {

    private final MailService mailService;
    private final MemberFinder memberFinder;
    private final UserInquiryComposition userInquiryComposition;

    public UserInquiryService(final MailService mailService,
                              final MemberFinder memberFinder,
                              final UserInquiryComposition userInquiryComposition) {
        this.mailService = mailService;
        this.memberFinder = memberFinder;
        this.userInquiryComposition = userInquiryComposition;
    }

    @Transactional(readOnly = true)
    public List<UserInquiryResponse> getAllUserInquiry(final long universityId) {
        return userInquiryComposition.getAllUserInquiry(universityId);
    }

    @Transactional(readOnly = true)
    public List<UserInquiryResponse> getAllNotAnsweredUserInquiry(final long universityId) {
        return userInquiryComposition.getAllNotAnsweredUserInquiry(universityId);
    }

    @Transactional
    public void updateUserInquiryAnswer(final UserInquiryAnswerRequest userInquiryAnswerRequest) {
        UserInquiryJpaEntity findUserInquiry = userInquiryComposition.getById(userInquiryAnswerRequest.userInquiryId());
        findUserInquiry.updateAnswerComplete(userInquiryAnswerRequest.answerComplete());
    }

    public void respondUserInquiry(final UserInquiryRespondRequest userInquiryRespondRequest) {
        String subject = userInquiryComposition.makeResponseSubject(userInquiryRespondRequest.userInquiryId());
        String userEmail = memberFinder.getMemberProfile(userInquiryRespondRequest.memberId()).email();
        MailSendRequest mailSendRequest = userInquiryRespondRequest.toMailSendRequest(subject, userEmail);
        mailService.sendUserInquiryResponseMail(mailSendRequest, userInquiryRespondRequest.responseContent());
    }
}
