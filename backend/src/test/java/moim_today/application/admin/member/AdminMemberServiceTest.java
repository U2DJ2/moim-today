package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;
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

import static org.assertj.core.api.Assertions.assertThat;

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
    
    @DisplayName("대학교Id, 학과Id로 모든 멤버를 조회한다.")
    @Test
    void findAllMembers(){
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
}