package moim_today.implement.department.request_department;

import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class RequestDepartmentRemoverTest extends ImplementTest {

    @Autowired
    private RequestDepartmentRemover requestDepartmentRemover;

    @DisplayName("해당 학과 추가 요청을 삭제한다.")
    @Test
    void deleteById() {
        // given
        RequestDepartmentJpaEntity requestDepartmentJpaEntity = RequestDepartmentJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .requestDepartmentName(DEPARTMENT_NAME.value())
                .build();

        requestDepartmentRepository.save(requestDepartmentJpaEntity);

        // when
        requestDepartmentRemover.deleteById(requestDepartmentJpaEntity.getId());

        // then
        assertThat(requestDepartmentRepository.count()).isEqualTo(0);
    }
}