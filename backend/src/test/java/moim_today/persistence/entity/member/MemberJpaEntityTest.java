package moim_today.persistence.entity.member;

import moim_today.dto.member.ProfileUpdateRequest;
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

    @DisplayName("사용자가 입력한 프로필 정보로 프로필을 수정한다.")
    @Test
    void updateProfile() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        long updateDepartmentId = Long.parseLong(DEPARTMENT_ID.value());
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(updateDepartmentId, PROFILE_IMAGE_URL.value());

        // when
        memberJpaEntity.updateProfile(profileUpdateRequest);

        // then
        assertThat(memberJpaEntity.getDepartmentId()).isEqualTo(updateDepartmentId);
        assertThat(memberJpaEntity.getMemberProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL.value());
    }
}