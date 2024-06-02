package moim_today.application.admin.user_inquiry;

import moim_today.application.mail.MailService;
import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryRespondRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.dto.mail.MailSendRequest;
import moim_today.implement.member.MemberFinder;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.MailConstant.USER_INQUIRY_RESPONSE_SUBJECT_PREFIX;

@Service
public class UserInquiryService {

    private final MailService mailService;
    private final MemberFinder memberFinder;
    private final DepartmentRepository departmentRepository;
    private final UserInquiryRepository userInquiryRepository;

    public UserInquiryService(final MailService mailService,
                              final MemberFinder memberFinder,
                              final DepartmentRepository departmentRepository,
                              final UserInquiryRepository userInquiryRepository) {
        this.mailService = mailService;
        this.memberFinder = memberFinder;
        this.departmentRepository = departmentRepository;
        this.userInquiryRepository = userInquiryRepository;
    }

    @Transactional(readOnly = true)
    public List<UserInquiryResponse> getAllUserInquiry(final long universityId) {
        return userInquiryRepository.getAllUserInquiryByUniversityId(universityId).stream()
                .map(userInquiryJpaEntity -> {
                    String departmentName = departmentRepository.getById(userInquiryJpaEntity.getDepartmentId()).getDepartmentName();
                    return UserInquiryResponse.of(userInquiryJpaEntity, departmentName);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserInquiryResponse> getAllNotAnsweredUserInquiry(final long universityId) {
        return userInquiryRepository.getAllNotAnsweredUserInquiry(universityId).stream()
                .map(userInquiryJpaEntity -> {
                    String departmentName = departmentRepository.getById(userInquiryJpaEntity.getDepartmentId()).getDepartmentName();
                    return UserInquiryResponse.of(userInquiryJpaEntity, departmentName);
                })
                .toList();
    }

    @Transactional
    public void updateUserInquiryAnswer(final UserInquiryAnswerRequest userInquiryAnswerRequest) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.getById(userInquiryAnswerRequest.userInquiryId());
        findUserInquiry.updateAnswerComplete(userInquiryAnswerRequest.answerComplete());
    }

    public void respondUserInquiry(final UserInquiryRespondRequest userInquiryRespondRequest) {
        String subject = makeResponseSubject(userInquiryRespondRequest.userInquiryId());
        String userEmail = getUserEmail(userInquiryRespondRequest.memberId());
        MailSendRequest mailSendRequest = userInquiryRespondRequest.toMailSendRequest(subject, userEmail);
        mailService.sendUserInquiryResponseMail(mailSendRequest, userInquiryRespondRequest.responseContent());
    }

    public String getUserEmail(final long memberId){
        return memberFinder.getMemberProfile(memberId).email();
    }

    @Transactional(readOnly = true)
    public String makeResponseSubject(final long userInquiryId) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.getById(userInquiryId);
        return USER_INQUIRY_RESPONSE_SUBJECT_PREFIX.value() + findUserInquiry.getInquiryTitle();
    }
}
