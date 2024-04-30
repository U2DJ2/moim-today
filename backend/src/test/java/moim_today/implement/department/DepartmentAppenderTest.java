package moim_today.implement.department;

import moim_today.dto.department.DepartmentInfoResponse;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DepartmentAppenderTest extends ImplementTest {

    @Autowired
    private DepartmentAppender departmentAppender;

    @Autowired
    private DepartmentFinder departmentFinder;

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
        List<DepartmentInfoResponse> allDepartment = departmentFinder.getAllDepartment(0, null);

        // then
        assertThat(allDepartment.size()).isEqualTo(10);
    }
}