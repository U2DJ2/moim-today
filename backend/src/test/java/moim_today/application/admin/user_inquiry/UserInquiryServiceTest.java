package moim_today.application.admin.user_inquiry;

import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import moim_today.persistence.repository.university.UniversityJpaRepository;
import moim_today.persistence.repository.university.UniversityRepository;
import moim_today.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserInquiryServiceTest {

    @Autowired
    private UserInquiryService userInquiryService;

    @Autowired
    private UserInquiryRepository userInquiryRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @DisplayName("한 대학교의 모든 사용자 문의를 가져온다")
    @Test
    void getAllUserInquiryByUniversityId() {
        // given1
        UniversityJpaEntity univ1 = UniversityJpaEntity.builder()
                .build();
        UniversityJpaEntity univ2 = UniversityJpaEntity.builder()
                .build();

        universityRepository.save(univ1);
        universityRepository.save(univ2);

        // given2
        DepartmentJpaEntity dep1 = DepartmentJpaEntity.builder()
                .universityId(univ1.getId())
                .build();
        DepartmentJpaEntity dep2 = DepartmentJpaEntity.builder()
                .universityId(univ2.getId())
                .build();

        departmentRepository.save(dep1);
        departmentRepository.save(dep2);

        // given3
        UserInquiryJpaEntity ui1 = UserInquiryJpaEntity.builder()
                .universityId(univ1.getId())
                .departmentId(dep1.getId())
                .build();
        UserInquiryJpaEntity ui2 = UserInquiryJpaEntity.builder()
                .universityId(univ1.getId())
                .departmentId(dep1.getId())
                .build();
        UserInquiryJpaEntity ui3 = UserInquiryJpaEntity.builder()
                .universityId(univ2.getId())
                .departmentId(dep1.getId())
                .build();

        userInquiryRepository.save(ui1);
        userInquiryRepository.save(ui2);
        userInquiryRepository.save(ui3);

        // when
        List<UserInquiryResponse> allUserInquiryByUniversityId = userInquiryService.getAllUserInquiry(univ1.getId());

        // then
        assertThat(allUserInquiryByUniversityId.size()).isEqualTo(2);
    }

    @DisplayName("사용자 문의 완료 여부를 업데이트한다")
    @Test
    void updateUserInquiryAnswer() {
        // given
        UserInquiryJpaEntity ui1 = UserInquiryJpaEntity.builder()
                .universityId(1L)
                .answerComplete(false)
                .build();

        userInquiryRepository.save(ui1);

        UserInquiryAnswerRequest userInquiryAnswerRequest = UserInquiryAnswerRequest.builder()
                .userInquiryId(ui1.getId())
                .answerComplete(true)
                .build();

        // when
        userInquiryService.updateUserInquiryAnswer(userInquiryAnswerRequest);
        UserInquiryJpaEntity findUi1 = userInquiryRepository.findById(ui1.getId());

        // then
        assertThat(findUi1.isAnswerComplete()).isTrue();
    }
}