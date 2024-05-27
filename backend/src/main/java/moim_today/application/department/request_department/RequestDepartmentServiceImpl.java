package moim_today.application.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.implement.department.department.DepartmentAppender;
import moim_today.implement.department.request_department.RequestDepartmentAppender;
import moim_today.implement.department.request_department.RequestDepartmentFinder;
import moim_today.implement.department.request_department.RequestDepartmentRemover;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestDepartmentServiceImpl implements RequestDepartmentService {

    private final RequestDepartmentAppender requestDepartmentAppender;
    private final RequestDepartmentFinder requestDepartmentFinder;
    private final RequestDepartmentRemover requestDepartmentRemover;
    private final DepartmentAppender departmentAppender;

    public RequestDepartmentServiceImpl(final RequestDepartmentAppender requestDepartmentAppender,
                                        final RequestDepartmentFinder requestDepartmentFinder,
                                        final RequestDepartmentRemover requestDepartmentRemover,
                                        final DepartmentAppender departmentAppender) {
        this.requestDepartmentAppender = requestDepartmentAppender;
        this.requestDepartmentFinder = requestDepartmentFinder;
        this.requestDepartmentRemover = requestDepartmentRemover;
        this.departmentAppender = departmentAppender;
    }

    @Override
    public List<RequestDepartmentResponse> findAll() {
        return requestDepartmentFinder.findAll();
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

    @Override
    public void addDepartment(final AddDepartmentRequest addDepartmentRequest) {
        requestDepartmentAppender.addDepartment(addDepartmentRequest);
    }
}
