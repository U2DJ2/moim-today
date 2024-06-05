package moim_today.dto.department;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;

@Builder
public record AddDepartmentRequest(
        @Min(value = 0, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @NotBlank(message = DEPARTMENT_NAME_BLANK_ERROR) String departmentName
) {
    private static final String UNIVERSITY_ID_MIN_ERROR = "잘못된 대학 ID 값이 입력 되었습니다.";
    private static final String DEPARTMENT_NAME_BLANK_ERROR = "학과 이름은 공백일 수 없습니다.";

    public RequestDepartmentJpaEntity toEntity() {
        return RequestDepartmentJpaEntity.builder()
                .universityId(universityId)
                .requestDepartmentName(departmentName)
                .build();
    }
}
