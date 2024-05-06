package moim_today.fake_class.department;

import moim_today.application.department.DepartmentService;
import moim_today.dto.department.DepartmentResponse;

import java.util.List;

public class FakeDepartmentService implements DepartmentService {

    @Override
    public void patchAllDepartment() {

    }

    @Override
    public List<DepartmentResponse> getAllDepartmentByUniversityName(final String universityName) {

        // given
        List<DepartmentResponse> mockData = List.of(
                new DepartmentResponse(1L, "소프트웨어학과"),
                new DepartmentResponse(2L, "의예과"),
                new DepartmentResponse(3L, "철학과")
        );

        return mockData;
    }

    @Override
    public List<DepartmentResponse> getAllDepartmentById(final long universityId) {
        // given
        List<DepartmentResponse> mockData = List.of(
                new DepartmentResponse(1L, "소프트웨어학과"),
                new DepartmentResponse(2L, "의예과"),
                new DepartmentResponse(3L, "철학과")
        );

        return mockData;
    }
}
