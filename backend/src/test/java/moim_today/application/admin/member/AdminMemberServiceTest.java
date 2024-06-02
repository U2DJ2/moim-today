package moim_today.application.admin.member;

import moim_today.domain.member.enums.Gender;
import moim_today.dto.member.MemberResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.persistence.repository.university.UniversityRepository;
import moim_today.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static moim_today.global.constant.MemberConstant.*;
import static moim_today.global.constant.NumberConstant.*;
import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AdminMemberServiceTest {

    @Autowired
    private AdminMemberService adminMemberService;

    @Autowired
    private MemberRepository memberRepository;

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

    @DisplayName("어드민 - 대학교Id, 학과Id로 모든 멤버를 조회한다.")
    @Test
    void findAllMembers() {
        //given
        UniversityJpaEntity university = UniversityJpaEntity.builder()
                .build();

        UniversityJpaEntity otherUniversity = UniversityJpaEntity.builder()
                .build();

        universityRepository.save(university);
        universityRepository.save(otherUniversity);

        DepartmentJpaEntity department = DepartmentJpaEntity.builder()
                .build();

        DepartmentJpaEntity otherDepartment = DepartmentJpaEntity.builder()
                .build();

        departmentRepository.save(department);
        departmentRepository.save(otherDepartment);

        MemberJpaEntity member1 = MemberJpaEntity.builder()
                .universityId(university.getId())
                .departmentId(department.getId())
                .build();

        MemberJpaEntity member2 = MemberJpaEntity.builder()
                .universityId(university.getId())
                .departmentId(department.getId())
                .build();

        MemberJpaEntity member3 = MemberJpaEntity.builder()
                .universityId(university.getId())
                .departmentId(otherDepartment.getId())
                .build();

        MemberJpaEntity member4 = MemberJpaEntity.builder()
                .universityId(otherUniversity.getId())
                .departmentId(otherDepartment.getId())
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        //when
        List<MemberResponse> allMembers1 = adminMemberService.findAllMembers(university.getId(), department.getId());
        List<MemberResponse> allMembers2 = adminMemberService.findAllMembers(0, 0);

        //then
        assertThat(allMembers1.size()).isEqualTo(2);
        assertThat(allMembers2.size()).isEqualTo(4);
    }

    @DisplayName("어드민 - 회원을 삭제한다.")
    @Test
    void deleteMember() {
        //given
        UniversityJpaEntity university = UniversityJpaEntity.builder()
                .build();

        universityRepository.save(university);

        MemberJpaEntity member = MemberJpaEntity.builder()
                .universityId(university.getId())
                .build();

        memberRepository.save(member);

        //when
        adminMemberService.deleteMember(university.getId(), member.getId());

        //then
        MemberJpaEntity deletedMember = memberRepository.getById(member.getId());
        assertThat(deletedMember.getPassword().length()).isEqualTo(DELETED_MEMBER_PASSWORD_LENGTH.value());
        assertThat(deletedMember.getUsername()).isEqualTo(DELETED_MEMBER_USERNAME.value());
        assertThat(deletedMember.getStudentId()).isEqualTo(DELETED_MEMBER_STUDENT_ID.value());
        assertThat(deletedMember.getStudentId()).isEqualTo(DELETED_MEMBER_STUDENT_ID.value());
        assertThat(deletedMember.getGender()).isEqualTo(Gender.UNKNOWN);
        assertThat(deletedMember.getMemberProfileImageUrl()).isEqualTo(DEFAULT_PROFILE_URL.value());
        assertThat(deletedMember.getBirthDate().getYear()).isEqualTo(DELETED_MEMBER_BIRTH_YEAR.value());
        assertThat(deletedMember.getBirthDate().getMonthValue()).isEqualTo(DELETED_MEMBER_BIRTH_MONTH.value());
        assertThat(deletedMember.getBirthDate().getDayOfMonth()).isEqualTo(DELETED_MEMBER_BIRTH_DAY.value());
    }

    @DisplayName("어드민 - 다른 학교의 회원은 삭제할 수 없다.")
    @Test
    void deleteOtherUniversityMember() {
        //given
        UniversityJpaEntity university = UniversityJpaEntity.builder()
                .build();

        UniversityJpaEntity otherUniversity = UniversityJpaEntity.builder()
                .build();

        universityRepository.save(university);

        MemberJpaEntity member = MemberJpaEntity.builder()
                .universityId(otherUniversity.getId())
                .build();

        memberRepository.save(member);

        //expected
        assertThatThrownBy(() -> adminMemberService.deleteMember(university.getId(), member.getId()))
                .isInstanceOf(ForbiddenException.class)
                .hasMessage(ADMIN_FORBIDDEN_ERROR.message());
    }
}