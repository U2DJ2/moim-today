package moim_today.dto.department;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ApproveRequestDepartmentRequest(
        @Min(value = 1, message = REQUEST_DEPARTMENT_ID_MIN_ERROR) long requestDepartmentId,
        @Min(value = 1, message = UNIVERSITY_ID_MIN_ERROR) long universityId,
        @NotBlank(message = DEPARTMENT_NAME_BLANK_ERROR) String requestDepartmentName
) {
    private static final String REQUEST_DEPARTMENT_ID_MIN_ERROR = "잘못된 학과 요청 ID 값이 들어왔습니다.";
    private static final String UNIVERSITY_ID_MIN_ERROR = "잘못된 대학 ID 값이 들어왔습니다.";
    private static final String DEPARTMENT_NAME_BLANK_ERROR = "요청 학과 이름은 공백일 수 없습니다.";
}
