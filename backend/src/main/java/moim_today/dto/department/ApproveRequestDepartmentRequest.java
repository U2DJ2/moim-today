package moim_today.dto.department;

import lombok.Builder;

@Builder
public record ApproveRequestDepartmentRequest(
        long requestDepartmentId,
        long universityId,
        String requestDepartmentName
) {
}
