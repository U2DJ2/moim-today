package moim_today.dto.department;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;

import static moim_today.global.constant.exception.ValidationExceptionConstant.DEPARTMENT_NAME_BLANK_ERROR;
import static moim_today.global.constant.exception.ValidationExceptionConstant.UNIVERSITY_ID_MIN_ERROR;

@Builder
public record AddDepartmentRequest(
        @Min(value = 0, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @NotBlank(message = DEPARTMENT_NAME_BLANK_ERROR) String departmentName
) {

    public RequestDepartmentJpaEntity toEntity() {
        return RequestDepartmentJpaEntity.builder()
                .universityId(universityId)
                .requestDepartmentName(departmentName)
                .build();
    }
}
