package moim_today.application.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.implement.department.request_department.RequestDepartmentAppender;
import moim_today.implement.department.request_department.RequestDepartmentFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestDepartmentServiceImpl implements RequestDepartmentService {

    private final RequestDepartmentAppender requestDepartmentAppender;
    private final RequestDepartmentFinder requestDepartmentFinder;

    public RequestDepartmentServiceImpl(final RequestDepartmentAppender requestDepartmentAppender,
                                        final RequestDepartmentFinder requestDepartmentFinder) {
        this.requestDepartmentAppender = requestDepartmentAppender;
        this.requestDepartmentFinder = requestDepartmentFinder;
    }

    @Override
    public List<RequestDepartmentResponse> findAll() {
        return requestDepartmentFinder.findAll();
    }

    @Override
    public void addDepartment(final AddDepartmentRequest addDepartmentRequest) {
        requestDepartmentAppender.addDepartment(addDepartmentRequest);
    }
}
