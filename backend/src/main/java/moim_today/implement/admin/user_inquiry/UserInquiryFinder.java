package moim_today.implement.admin.user_inquiry;

import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.MailConstant.USER_INQUIRY_RESPONSE_SUBJECT_PREFIX;

@Implement
public class UserInquiryFinder {

    private final UserInquiryRepository userInquiryRepository;
    private final DepartmentRepository departmentRepository;

    public UserInquiryFinder(final UserInquiryRepository userInquiryRepository,
                             final DepartmentRepository departmentRepository) {
        this.userInquiryRepository = userInquiryRepository;
        this.departmentRepository = departmentRepository;
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

    @Transactional(readOnly = true)
    public String makeResponseSubject(final long userInquiryId) {
        UserInquiryJpaEntity findUserInquiry = userInquiryRepository.getById(userInquiryId);
        return USER_INQUIRY_RESPONSE_SUBJECT_PREFIX.value() + findUserInquiry.getInquiryTitle();
    }
}
