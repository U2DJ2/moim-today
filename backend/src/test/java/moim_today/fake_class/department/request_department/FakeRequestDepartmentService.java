package moim_today.fake_class.department.request_department;

import moim_today.application.department.request_department.RequestDepartmentService;
import moim_today.dto.department.AddDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;

import java.util.List;

public class FakeRequestDepartmentService implements RequestDepartmentService {

    @Override
    public List<RequestDepartmentResponse> findAll() {
        return List.of();
    }

    @Override
    public void addDepartment(final AddDepartmentRequest addDepartmentRequest) {

    }
}
