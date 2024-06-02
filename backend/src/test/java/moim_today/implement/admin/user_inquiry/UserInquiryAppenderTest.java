package moim_today.implement.admin.user_inquiry;

import moim_today.dto.admin.user_inquiry.UserInquiryRequest;
import moim_today.persistence.entity.admin.UserInquiryJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserInquiryAppenderTest extends ImplementTest {

    @Autowired
    private UserInquiryAppender userInquiryAppender;

    @DisplayName("사용자가 문의를 하면 저장된다")
    @Test
    void createUserInquiry() {
        UserInquiryJpaEntity ui = UserInquiryJpaEntity.builder()
                .universityId(1L)
                .departmentId(1L)
                .inquiryTitle("문의 제목")
                .inquiryContent("문의 내용")
                .answerComplete(false)
                .build();

        userInquiryAppender.createUserInquiry(ui);

        List<UserInquiryJpaEntity> allUserInquiryByUniversityId = userInquiryRepository.getAllUserInquiryByUniversityId(1L);

        // then
        assertThat(allUserInquiryByUniversityId.size()).isEqualTo(1);
    }
}