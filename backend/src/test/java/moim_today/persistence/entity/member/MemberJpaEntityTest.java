package moim_today.persistence.entity.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;


class MemberJpaEntityTest {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @DisplayName("사용자가 입력한 비밀번호를 암호화하여 수정한다.")
    @Test
    void updatePassword() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .password(PASSWORD.value())
                .build();

        // when
        memberJpaEntity.updatePassword(passwordEncoder, NEW_PASSWORD.value());

        // then
        boolean isMatched = passwordEncoder.matches(NEW_PASSWORD.value(), memberJpaEntity.getPassword());
        assertThat(isMatched).isTrue();
    }
}