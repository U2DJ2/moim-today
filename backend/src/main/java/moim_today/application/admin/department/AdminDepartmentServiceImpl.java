package moim_today.application.admin.department;

import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.implement.department.department.DepartmentAppender;
import moim_today.implement.department.request_department.RequestDepartmentFinder;
import moim_today.implement.department.request_department.RequestDepartmentRemover;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminDepartmentServiceImpl implements AdminDepartmentService {

    private final RequestDepartmentFinder requestDepartmentFinder;
    private final RequestDepartmentRemover requestDepartmentRemover;
    private final DepartmentAppender departmentAppender;

    public AdminDepartmentServiceImpl(final RequestDepartmentFinder requestDepartmentFinder,
                                      final RequestDepartmentRemover requestDepartmentRemover,
                                      final DepartmentAppender departmentAppender) {
        this.requestDepartmentFinder = requestDepartmentFinder;
        this.requestDepartmentRemover = requestDepartmentRemover;
        this.departmentAppender = departmentAppender;
    }

    @Override
    public List<RequestDepartmentResponse> findAllByUniversityId(final long universityId) {
        return requestDepartmentFinder.findAllByUniversityId(universityId);
    }

    @Transactional
    @Override
    public void approveRequest(final ApproveRequestDepartmentRequest approveRequestDepartmentRequest) {
        departmentAppender.addDepartment(
                approveRequestDepartmentRequest.universityId(),
                approveRequestDepartmentRequest.requestDepartmentName()
        );

        requestDepartmentRemover.deleteById(approveRequestDepartmentRequest.requestDepartmentId());
    }
}
