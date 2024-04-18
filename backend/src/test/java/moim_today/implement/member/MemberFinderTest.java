package moim_today.implement.member;

import moim_today.domain.member.enums.Gender;
import moim_today.dto.member.MemberProfileResponse;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static moim_today.global.constant.exception.MemberExceptionConstant.*;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class MemberFinderTest extends ImplementTest {

    @Autowired
    private MemberFinder memberFinder;

    @DisplayName("해당 이메일을 가진 회원이 존재하지 않으면 예외가 발생한다.")
    @Test
    void validateEmailNotExists() {
        // expected
        assertThatThrownBy(() -> memberFinder.validateEmailExists(EMAIL.value()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(EMAIL_NOT_FOUND_ERROR.message());
    }

    @DisplayName("해당 이메일을 가진 회원이 존재하면 검증에 성공한다.")
    @Test
    void validateEmailExists() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();

        memberRepository.save(memberJpaEntity);

        // when && then
        memberFinder.validateEmailExists(EMAIL.value());
    }

    @DisplayName("memberId로 프로필 조회 성공시 프로필 정보를 반환한다.")
    @Test
    void getProfileByMemberId() {
        // given1
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIVERSITY_NAME.value())
                .build();

        universityRepository.save(universityJpaEntity);
        long universityId = universityJpaEntity.getId();

        // given2
        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .departmentName(DEPARTMENT_NAME.value())
                .universityId(universityId)
                .build();

        departmentRepository.save(departmentJpaEntity);
        long departmentId = departmentJpaEntity.getId();

        // given3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .universityId(universityId)
                .departmentId(departmentId)
                .email(EMAIL.value())
                .username(USERNAME.value())
                .studentId(STUDENT_ID.value())
                .birthDate(LocalDate.of(2000, 12, 18))
                .gender(Gender.MALE)
                .memberProfileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        // when
        MemberProfileResponse memberProfileResponse = memberFinder.getMemberProfile(memberId);

        // then
        assertThat(memberProfileResponse.universityName()).isEqualTo(UNIVERSITY_NAME.value());
        assertThat(memberProfileResponse.departmentName()).isEqualTo(DEPARTMENT_NAME.value());
        assertThat(memberProfileResponse.email()).isEqualTo(EMAIL.value());
        assertThat(memberProfileResponse.username()).isEqualTo(USERNAME.value());
        assertThat(memberProfileResponse.studentId()).isEqualTo(STUDENT_ID.value());
        assertThat(memberProfileResponse.birthDate()).isEqualTo(LocalDate.of(2000,12,18));
        assertThat(memberProfileResponse.gender()).isEqualTo(Gender.MALE);
        assertThat(memberProfileResponse.memberProfileImageUrl()).isEqualTo(PROFILE_IMAGE_URL.value());
    }

    @DisplayName("memberId로 프로필 조회 실패시 프로필 예외가 발생한다.")
    @Test
    void getProfileByWrongMemberId() {
        // expected
        assertThatThrownBy(() -> memberFinder.getMemberProfile(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MEMBER_NOT_FOUND_ERROR.message());
    }

    @DisplayName("이미 가입한 이메일일 경우 예외가 발생한다.")
    @Test
    void 가입한_이메일이_있으니_예외발생() {
        // given
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .email(EMAIL.value())
                .build();
        memberRepository.save(memberJpaEntity);

        // then
        assertThatThrownBy(() -> memberFinder.validateEmailNotExists(EMAIL.value()))
                .isInstanceOf(BadRequestException.class)
                .hasMessage(EMAIL_ALREADY_USED_ERROR.message());
    }

    @DisplayName("가입한 이메일이 없을 경우 예외가 발생하지 않는다.")
    @Test
    void 가입한_이메일이_없으니_예외발생_없음(){
        // expected
        assertThatCode(() -> {
            memberFinder.validateEmailNotExists(EMAIL.value());
        }).doesNotThrowAnyException();
    }
}
