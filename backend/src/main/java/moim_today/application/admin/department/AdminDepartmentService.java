package moim_today.application.admin.department;

import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.implement.department.department.DepartmentComposition;
import moim_today.implement.department.request_department.RequestDepartmentComposition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminDepartmentService {

    private final DepartmentComposition departmentComposition;
    private final RequestDepartmentComposition requestDepartmentComposition;

    public AdminDepartmentService(final DepartmentComposition departmentComposition,
                                  final RequestDepartmentComposition requestDepartmentComposition) {
        this.departmentComposition = departmentComposition;
        this.requestDepartmentComposition = requestDepartmentComposition;
    }

    public List<RequestDepartmentResponse> findAllByUniversityId(final long universityId) {
        return requestDepartmentComposition.findAllByUniversityId(universityId);
    }

    @Transactional
    public void approveRequest(final ApproveRequestDepartmentRequest approveRequestDepartmentRequest) {
        departmentComposition.addDepartment(
                approveRequestDepartmentRequest.universityId(),
                approveRequestDepartmentRequest.requestDepartmentName()
        );

        requestDepartmentComposition.deleteById(approveRequestDepartmentRequest.requestDepartmentId());
    }
}
