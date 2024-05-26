package moim_today.implement.department.department;

import moim_today.dto.department.DepartmentResponse;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static moim_today.global.constant.DepartmentConstant.DEPARTMENT_UPDATE_BATCH_SIZE;
import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.assertThat;

class DepartmentAppenderTest extends ImplementTest {

    @Autowired
    private DepartmentAppender departmentAppender;

    @Autowired
    private DepartmentFinder departmentFinder;

    private final int MAX_SIZE = 1500;

    @DisplayName("Department 정보 컬렉션을 한 번에 업데이트한다")
    @Test
    void batchUpdate() {
        // given
        List<DepartmentJpaEntity> departmentJpaEntities = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            departmentJpaEntities.add(DepartmentJpaEntity.builder()
                            .universityId(0)
                            .departmentName("departmentName"+i)
                    .build());
        }

        // when
        departmentAppender.batchUpdate(departmentJpaEntities);
        List<DepartmentResponse> allDepartment = departmentFinder.getAllDepartmentById(0);

        // then
        assertThat(allDepartment.size()).isEqualTo(10);
    }

    @DisplayName("Department 정보가 Size 값을 넘을 경우 batchUpdate를 실행한다")
    @Test
    void updateDepartmentsIfSizeOver() {
        // given
        Map<String, Set<String>> departmentJpaEntities = new HashMap<>();
        departmentJpaEntities.put(UNIVERSITY_NAME.value(), new HashSet<>());
        UniversityJpaEntity universityJpaEntity = UniversityJpaEntity.builder()
                .universityName(UNIVERSITY_NAME.value())
                .universityEmail(AJOU_EMAIL_DOMAIN.value())
                .build();
        universityRepository.save(universityJpaEntity);

        for(int i = 0; i < MAX_SIZE; i++){
            departmentJpaEntities.get(UNIVERSITY_NAME.value()).add(DEPARTMENT_NAME.value()+i);
        }

        // when
        departmentAppender.updateDepartmentsIfSizeOver(departmentJpaEntities, DEPARTMENT_UPDATE_BATCH_SIZE.intValue());

        // then
        long universityId = universityRepository.getByName(UNIVERSITY_NAME.value()).getId();
        assertThat(departmentRepository.findAllDepartmentOfUniversity(universityId).size())
                .isEqualTo(MAX_SIZE);
    }
}