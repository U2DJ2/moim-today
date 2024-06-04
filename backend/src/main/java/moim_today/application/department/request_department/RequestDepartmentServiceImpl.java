package moim_today.application.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.implement.department.request_department.RequestDepartmentAppender;
import org.springframework.stereotype.Service;


@Service
public class RequestDepartmentServiceImpl implements RequestDepartmentService {

    private final RequestDepartmentAppender requestDepartmentAppender;

    public RequestDepartmentServiceImpl(final RequestDepartmentAppender requestDepartmentAppender) {
        this.requestDepartmentAppender = requestDepartmentAppender;
    }

    @Override
    public void addDepartment(final AddDepartmentRequest addDepartmentRequest) {
        requestDepartmentAppender.addDepartment(addDepartmentRequest);
    }
}
