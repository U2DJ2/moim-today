package moim_today.application.admin.department;

import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;

import java.util.List;

public interface AdminDepartmentService {

    List<RequestDepartmentResponse> findAllByUniversityId(final long universityId);

    void approveRequest(final ApproveRequestDepartmentRequest approveRequestDepartmentRequest);
}
