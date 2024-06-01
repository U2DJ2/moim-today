package moim_today.application.admin.user_inquiry;

import moim_today.application.mail.MailService;
import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRespondRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.dto.mail.MailSendRequest;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.MailConstant.USER_INQUIRY_RESPONSE_SUBJECT_PREFIX;

@Service
public class UserInquiryService {

    private final MailService mailService;
    private final MemberRepository memberRepository;
    private final UserInquiryRepository userInquiryRepository;

    public UserInquiryService(final MailService mailService,
                              final MemberRepository memberRepository,
                              final UserInquiryRepository userInquiryRepository) {
        this.mailService = mailService;
        this.memberRepository = memberRepository;
        this.userInquiryRepository = userInquiryRepository;
    }

    @Transactional(readOnly = true)
    public List<UserInquiryResponse> getAllUserInquiryByUniversityId(final long universityId) {
        return userInquiryRepository.getAllUserInquiryByUniversityId(universityId).stream()
                .map(UserInquiryResponse::from)
                .toList();
    }

    @Transactional
    public void updateUserInquiryAnswer(final UserInquiryAnswerRequest userInquiryAnswerRequest) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.findById(userInquiryAnswerRequest.userInquiryId());
        findUserInquiry.updateAnswerComplete(userInquiryAnswerRequest.answerComplete());
    }

    @Transactional
    public void respondUserInquiry(final UserInquiryRespondRequest userInquiryRespondRequest) {
        String subject = makeResponseSubject(userInquiryRespondRequest.userInquiryId());
        String userEmail = memberRepository.getMemberProfile(userInquiryRespondRequest.memberId()).email();
        MailSendRequest mailSendRequest = userInquiryRespondRequest.toMailSendRequest(subject, userEmail);
        mailService.sendUserInquiryResponseMail(mailSendRequest, userInquiryRespondRequest.responseContent());
    }

    @Transactional
    public String makeResponseSubject(final long userInquiryId) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.findById(userInquiryId);
        return USER_INQUIRY_RESPONSE_SUBJECT_PREFIX.value() + findUserInquiry.getInquiryTitle();
    }
}
