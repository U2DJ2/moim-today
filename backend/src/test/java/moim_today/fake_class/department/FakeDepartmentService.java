package moim_today.fake_class.department;

import moim_today.application.department.DepartmentService;
import moim_today.dto.department.DepartmentInfoResponse;

import java.util.List;

public class FakeDepartmentService implements DepartmentService {

    @Override
    public void patchAllDepartment() {

    }

    @Override
    public List<DepartmentInfoResponse> getAllDepartmentByUniversityName(final String universityName) {
        return null;
    }

    @Override
    public List<DepartmentInfoResponse> getAllDepartmentById(final long universityId) {
        return null;
    }
}
