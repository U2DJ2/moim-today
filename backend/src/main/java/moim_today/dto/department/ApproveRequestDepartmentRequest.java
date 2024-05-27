package moim_today.dto.department;

public record ApproveRequestDepartmentRequest(
        long requestDepartmentId,
        long universityId,
        String requestDepartmentName
) {
}
