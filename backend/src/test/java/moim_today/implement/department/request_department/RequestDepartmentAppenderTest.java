package moim_today.implement.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.util.ImplementTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static moim_today.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;


class RequestDepartmentAppenderTest extends ImplementTest {

    @Autowired
    private RequestDepartmentAppender requestDepartmentAppender;

    @DisplayName("학과 추가 요청 정보를 저장한다.")
    @Test
    void addDepartment() {
        // given
        AddDepartmentRequest addDepartmentRequest = AddDepartmentRequest.builder()
                .universityId(UNIV_ID.longValue())
                .departmentName(DEPARTMENT_NAME.value())
                .build();

        // when
        requestDepartmentAppender.addDepartment(addDepartmentRequest);

        // then
        assertThat(requestDepartmentRepository.count()).isEqualTo(1);
    }
}