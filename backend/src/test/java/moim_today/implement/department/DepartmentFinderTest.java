package moim_today.implement.department;

import moim_today.dto.department.DepartmentInfoResponse;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DepartmentFinderTest extends ImplementTest {

    private final String UNIV_NAME = "testUniv";
    private final String UNIV_MAIL = "testMail";
    private final String DEPARTMENT_NAME = "testDepart";

    @Autowired
    private DepartmentFinder departmentFinder;

    @DisplayName("입력받은 Department 와 University 가 연관관계가 있는지 검사하는 테스트 ")
    @Test
    void isDepartmentAssociatedWithUniversity() {
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIV_NAME)
                .universityEmail(UNIV_MAIL)
                .build();
        UniversityJpaEntity saveUniv = universityRepository.save(universityJpaEntity);

        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .universityId(saveUniv.getId())
                .departmentName(DEPARTMENT_NAME)
                .build();
        DepartmentJpaEntity saveDepart = departmentRepository.save(departmentJpaEntity);

        // expected
        assertThatCode(() -> {
            departmentFinder.isDepartmentAssociatedWithUniversity(saveUniv.getId(), saveDepart.getId());
        }).doesNotThrowAnyException();

    }

    @DisplayName("한 대학교의 모든 학과 대학교 이름으로 조회")
    @Test
    void getAllDepartmentByUniversityName() {
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIV_NAME)
                .universityEmail(UNIV_MAIL)
                .build();
        UniversityJpaEntity saveUniv = universityRepository.save(universityJpaEntity);

        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .universityId(saveUniv.getId())
                .departmentName(DEPARTMENT_NAME)
                .build();
        DepartmentJpaEntity saveDepart = departmentRepository.save(departmentJpaEntity);

        // when
        List<DepartmentInfoResponse> allDepartmentByUniversityName = departmentFinder.getAllDepartmentByUniversityName(UNIV_NAME);

        // then
        assertThat(allDepartmentByUniversityName.size()).isEqualTo(1);
    }

    @DisplayName("한 대학교의 모든 학과 대학교 ID로 조회")
    @Test
    void getAllDepartmentByUniversityId() {
        // given
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIV_NAME)
                .universityEmail(UNIV_MAIL)
                .build();
        UniversityJpaEntity saveUniv = universityRepository.save(universityJpaEntity);

        DepartmentJpaEntity departmentJpaEntity = DepartmentJpaEntity.builder()
                .universityId(saveUniv.getId())
                .departmentName(DEPARTMENT_NAME)
                .build();
        DepartmentJpaEntity saveDepart = departmentRepository.save(departmentJpaEntity);

        // when
        List<DepartmentInfoResponse> allDepartmentByUniversityId = departmentFinder.getAllDepartmentById(saveUniv.getId());

        // then
        assertThat(allDepartmentByUniversityId.size()).isEqualTo(1);
    }
}