package moim_today.application.admin.user_inquiry;

import moim_today.dto.admin.user_inquiry.UserInquiryAnswerRequest;
import moim_today.dto.admin.user_inquiry.UserInquiryResponse;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.persistence.repository.admin.user_inquiry.UserInquiryRepository;
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
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void setUpDatabase() {
        databaseCleaner.cleanUp();
    }

    @DisplayName("한 대학교의 모든 사용자 문의를 가져온다")
    @Test
    void getAllUserInquiryByUniversityId() {
        // given
        UserInquiryJpaEntity ui1 = UserInquiryJpaEntity.builder()
                .universityId(1L)
                .build();
        UserInquiryJpaEntity ui2 = UserInquiryJpaEntity.builder()
                .universityId(1L)
                .build();
        UserInquiryJpaEntity ui3 = UserInquiryJpaEntity.builder()
                .universityId(2L)
                .build();

        userInquiryRepository.save(ui1);
        userInquiryRepository.save(ui2);
        userInquiryRepository.save(ui3);

        // when
        List<UserInquiryResponse> allUserInquiryByUniversityId = userInquiryService.getAllUserInquiry(1L);

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