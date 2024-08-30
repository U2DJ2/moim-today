package moim_today.dto.department;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import static moim_today.global.constant.exception.ValidationExceptionConstant.*;

@Builder
public record ApproveRequestDepartmentRequest(
        @Min(value = 0, message = DEPARTMENT_ID_MIN_ERROR) long requestDepartmentId,
        @Min(value = 0, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @NotBlank(message = DEPARTMENT_NAME_BLANK_ERROR) String requestDepartmentName
) {

}
