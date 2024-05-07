package moim_today.dto.auth;

import moim_today.domain.member.enums.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class MemberSignUpRequestTest {

    private static final long VALID_ID = 1L;
    private static final String VALID_EMAIL = "validEmail@example.com";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String VALID_USERNAME = "validUsername";
    private static final String VALID_STUDENT_ID = "validStudentId";
    private static final String INVALID_GENDER = "INVALID_GENDER";
    private static final String VALID_GENDER = "FEMALE";

    @DisplayName("회원가입 요청에 대한 올바른 GENDER 값이 들어오지 않았을 때 에러메세지 출력")
    @Test
    public void testRegisterRequest_GENDER_Exception() {

        assertThatThrownBy(
                () -> {
                    MemberSignUpRequest.builder()
                            .universityId(VALID_ID)
                            .departmentId(VALID_ID)
                            .email(VALID_EMAIL)
                            .password(VALID_PASSWORD)
                            .username(VALID_USERNAME)
                            .studentId(VALID_STUDENT_ID)
                            .birthDate(LocalDate.now())
                            .gender(Gender.valueOf(INVALID_GENDER))
                            .build();
                }
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원가입 요청에 대한 올바른 GENDER 값이 들어오면 이상이 없다")
    @Test
    public void testRegisterRequest_GENDER_success() {

        assertThatCode(
                () -> {
                    MemberSignUpRequest.builder()
                            .universityId(VALID_ID)
                            .departmentId(VALID_ID)
                            .email(VALID_EMAIL)
                            .password(VALID_PASSWORD)
                            .username(VALID_USERNAME)
                            .studentId(VALID_STUDENT_ID)
                            .birthDate(LocalDate.now())
                            .gender(Gender.valueOf(VALID_GENDER))
                            .build();
                }
        ).doesNotThrowAnyException();
    }
}
