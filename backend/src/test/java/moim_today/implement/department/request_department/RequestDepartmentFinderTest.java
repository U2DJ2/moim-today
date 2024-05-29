package moim_today.implement.department.request_department;

import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;
import moim_today.util.ImplementTest;
import moim_today.util.TestConstant;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class RequestDepartmentFinderTest extends ImplementTest {

    @Autowired
    private RequestDepartmentFinder requestDepartmentFinder;

    @DisplayName("해당 학교의 모든 학과 추가 요청 정보를 조회한다.")
    @Test
    void findAllByUniversityId() {
        // given
        RequestDepartmentJpaEntity requestDepartmentJpaEntity1 = RequestDepartmentJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .build();

        RequestDepartmentJpaEntity requestDepartmentJpaEntity2 = RequestDepartmentJpaEntity.builder()
                .universityId(UNIV_ID.longValue())
                .build();

        RequestDepartmentJpaEntity requestDepartmentJpaEntity3 = RequestDepartmentJpaEntity.builder()
                .universityId(UNIV_ID.longValue() + 1)
                .build();

        requestDepartmentRepository.save(requestDepartmentJpaEntity1);
        requestDepartmentRepository.save(requestDepartmentJpaEntity2);
        requestDepartmentRepository.save(requestDepartmentJpaEntity3);

        // when
        List<RequestDepartmentResponse> requestDepartmentResponses =
                requestDepartmentFinder.findAllByUniversityId(UNIV_ID.longValue());

        // then
        assertThat(requestDepartmentResponses.size()).isEqualTo(2);
    }
}