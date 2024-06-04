package moim_today.application.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.implement.department.request_department.RequestDepartmentComposition;
import org.springframework.stereotype.Service;


@Service
public class RequestDepartmentServiceImpl implements RequestDepartmentService {

    private final RequestDepartmentComposition requestDepartmentComposition;

    public RequestDepartmentServiceImpl(final RequestDepartmentComposition requestDepartmentComposition) {
        this.requestDepartmentComposition = requestDepartmentComposition;
    }

    @Override
    public void addDepartment(final AddDepartmentRequest addDepartmentRequest) {
        requestDepartmentComposition.addDepartment(addDepartmentRequest);
    }
}
