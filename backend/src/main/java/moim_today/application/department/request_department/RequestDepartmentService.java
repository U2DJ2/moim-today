package moim_today.application.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;

import java.util.List;

public interface RequestDepartmentService {

    List<RequestDepartmentResponse> findAll();

    void approveRequest(final ApproveRequestDepartmentRequest approveRequestDepartmentRequest);

    void addDepartment(final AddDepartmentRequest addDepartmentRequest);
}
