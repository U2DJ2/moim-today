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
import java.util.Optional;

import static moim_today.global.constant.exception.MemberExceptionConstant.EMAIL_NOT_FOUND_ERROR;
import static moim_today.global.constant.exception.MemberExceptionConstant.MEMBER_NOT_FOUND_ERROR;
import static moim_today.util.TestConstant.EMAIL;
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
                .universityName("universityName")
                .build();

        universityRepository.save(universityJpaEntity);
        long universityId = universityJpaEntity.getId();

        // given2
        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .departmentName("departmentName")
                .universityId(universityId)
                .build();

        departmentRepository.save(departmentJpaEntity);
        long departmentId = departmentJpaEntity.getId();

        // given3
        MemberJpaEntity memberJpaEntity = MemberJpaEntity.builder()
                .universityId(universityId)
                .departmentId(departmentId)
                .email(EMAIL.value())
                .username("username")
                .studentId("studentId")
                .birthDate(LocalDate.of(2000, 12, 18))
                .gender(Gender.MALE)
                .memberProfileImageUrl("testUrl")
                .build();

        memberRepository.save(memberJpaEntity);
        long memberId = memberJpaEntity.getId();

        // when
        MemberProfileResponse memberProfileResponse = memberFinder.getMemberProfile(memberId);

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
