package moim_today.implement.member;

import moim_today.domain.member.enums.Gender;
import moim_today.dto.member.MemberProfileResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.MemberExceptionConstant.MEMBER_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName("universityName")
                .build();

        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .departmentName("departmentName")
                .universityId(1L)
                .build();

        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .universityId(1L)
                .departmentId(1L)
                .email(EMAIL.value())
                .username("username")
                .studentId("studentId")
                .birthDate(LocalDate.of(2000, 12, 18))
                .gender(Gender.MALE)
                .memberProfileImageUrl("testUrl")
                .build();

        universityRepository.save(universityJpaEntity);
        departmentRepository.save(departmentJpaEntity);
        memberRepository.save(memberJpaEntity);

        // when
        MemberProfileResponse memberProfileResponse = memberFinder.getMemberProfile(memberJpaEntity.getId());

        // then
        assertThat(memberProfileResponse.universityName()).isEqualTo("universityName");
        assertThat(memberProfileResponse.departmentName()).isEqualTo("departmentName");
        assertThat(memberProfileResponse.email()).isEqualTo(EMAIL.value());
        assertThat(memberProfileResponse.username()).isEqualTo("username");
        assertThat(memberProfileResponse.studentId()).isEqualTo("studentId");
        assertThat(memberProfileResponse.birthDate()).isEqualTo("2000-12-18");
        assertThat(memberProfileResponse.gender()).isEqualTo(Gender.MALE);
        assertThat(memberProfileResponse.memberProfileImageUrl()).isEqualTo("testUrl");
    }

    @DisplayName("memberId로 프로필 조회 실패시 프로필 예외가 발생한다.")
    @Test
    void getProfileByWrongMemberId() {
        // expected
        assertThatThrownBy(() -> memberFinder.getMemberProfile(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(MEMBER_NOT_FOUND_ERROR.message());
    }
}
