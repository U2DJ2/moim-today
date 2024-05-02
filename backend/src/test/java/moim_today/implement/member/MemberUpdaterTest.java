package moim_today.implement.member;

import moim_today.dto.member.ProfileUpdateRequest;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.HandleExceptionPage;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.certification.password.PasswordCertificationJpaEntity;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static moim_today.global.constant.exception.DepartmentExceptionConstant.DEPARTMENT_NOT_MATCH_UNIVERSITY;
import static moim_today.global.constant.exception.MailExceptionConstant.MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberUpdaterTest extends ImplementTest {

    @Autowired
    private MemberUpdater memberUpdater;

    @DisplayName("사용자가 입력한 비밀번호를 암호화하여 수정한다.")
    @Test
    void updatePassword() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .password(PASSWORD.value())
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        // when
        memberUpdater.updatePassword(memberId, NEW_PASSWORD.value());

        // then
        MemberJpaEntity findEntity = memberRepository.getById(memberId);
        boolean isMatched = passwordEncoder.matches("newPassword", findEntity.getPassword());
        assertThat(isMatched).isTrue();
    }

    @DisplayName("토큰으로 회원을 찾을 수 있으면 비밀번호를 변경할 수 있습니다.")
    @Test
    void recoverPassword() {
        // given 1
        String passwordToken = CERTIFICATION_TOKEN.value();
        String newPassword = NEW_PASSWORD.value();
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        // given 2
        PasswordCertificationJpaEntity passwordCertificationJpaEntity = PasswordCertificationJpaEntity.builder()
                .email(EMAIL.value())
                .certificationToken(passwordToken)
                .expiredDateTime(expiredTime)
                .build();

        passwordCertificationRepository.save(passwordCertificationJpaEntity);

        // given 3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // when
        memberUpdater.recoverPassword(passwordToken, newPassword, expiredTime.minusMinutes(10));

        // then
        MemberJpaEntity findEntity = memberRepository.getByEmail(EMAIL.value());
        assertThat(passwordEncoder.matches(newPassword, findEntity.getPassword())).isTrue();
    }

    @DisplayName("토큰으로 회원을 찾을 수 없으면 비밀번호를 변경할 수 없습니다.")
    @Test
    void recoverPasswordFailNotMatchToken() {
        // given 1
        String passwordToken = CERTIFICATION_TOKEN.value();
        String newPassword = NEW_PASSWORD.value();
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        // given 2
        PasswordCertificationJpaEntity passwordCertificationJpaEntity = PasswordCertificationJpaEntity.builder()
                .email(EMAIL.value())
                .certificationToken(passwordToken)
                .expiredDateTime(expiredTime)
                .build();

        passwordCertificationRepository.save(passwordCertificationJpaEntity);

        // given 3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // expected
        assertThatThrownBy(
                () -> memberUpdater.recoverPassword(WRONG_CERTIFICATION_TOKEN.value(), newPassword, expiredTime.minusMinutes(10))
        )
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MAIL_CERTIFICATION_TOKEN_NOT_FOUND_ERROR.message());
    }

    @DisplayName("토큰이 만료되면 비밀번호를 변경할 수 없습니다.")
    @Test
    void recoverPasswordFailExpiredToken() {
        // given 1
        String passwordToken = CERTIFICATION_TOKEN.value();
        String newPassword = NEW_PASSWORD.value();
        LocalDateTime expiredTime = LocalDateTime.of(2024, 1, 1, 10, 00, 00);

        // given 2
        PasswordCertificationJpaEntity passwordCertificationJpaEntity = PasswordCertificationJpaEntity.builder()
                .email(EMAIL.value())
                .certificationToken(passwordToken)
                .expiredDateTime(expiredTime)
                .build();

        passwordCertificationRepository.save(passwordCertificationJpaEntity);

        // given 3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // expected
        assertThatThrownBy(
                () -> memberUpdater.recoverPassword(passwordToken, newPassword, expiredTime.plusMinutes(11))
        )
                .isInstanceOf(HandleExceptionPage.class);
    }

    @DisplayName("사용자가 입력한 프로필 정보로 사용자 정보를 수정한다.")
    @Test
    void updateProfile() {
        // given
        long universityId = Long.parseLong(UNIV_ID.value());

        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .universityId(universityId)
                .build();

        departmentRepository.save(departmentJpaEntity);
        long updateDepartmentId = departmentJpaEntity.getId();

        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(
                updateDepartmentId, PROFILE_IMAGE_URL.value());

        // when
        memberUpdater.updateProfile(memberId, universityId, profileUpdateRequest);

        // then
        MemberJpaEntity findEntity = memberRepository.getById(memberId);
        assertThat(findEntity.getDepartmentId()).isEqualTo(updateDepartmentId);
        assertThat(findEntity.getMemberProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL.value());
    }

    @DisplayName("수정을 요청한 전공이 해당 대학교에 있는 전공이 아닌 경우 예외가 발생한다.")
    @Test
    void updateProfileNotMatchUniversity() {
        // given
        long right_universityId = Long.parseLong(UNIV_ID.value());
        long wrong_universityId = right_universityId + 1L;

        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .universityId(wrong_universityId)
                .build();

        departmentRepository.save(departmentJpaEntity);
        long wrong_departmentId = departmentJpaEntity.getId();

        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .universityId(right_universityId)
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(
                wrong_departmentId, PROFILE_IMAGE_URL.value());

        // when & then
        assertThatThrownBy(() -> memberUpdater.updateProfile(memberId, right_universityId, profileUpdateRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(DEPARTMENT_NOT_MATCH_UNIVERSITY.message());
    }
}
