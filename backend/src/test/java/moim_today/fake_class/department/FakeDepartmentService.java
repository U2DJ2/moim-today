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

        // given
        List<DepartmentInfoResponse> mockData = List.of(
                new DepartmentInfoResponse(1L, "소프트웨어학과"),
                new DepartmentInfoResponse(2L, "의예과"),
                new DepartmentInfoResponse(3L, "철학과")
        );

        return mockData;
    }

    @Override
    public List<DepartmentInfoResponse> getAllDepartmentById(final long universityId) {
        // given
        List<DepartmentInfoResponse> mockData = List.of(
                new DepartmentInfoResponse(1L, "소프트웨어학과"),
                new DepartmentInfoResponse(2L, "의예과"),
                new DepartmentInfoResponse(3L, "철학과")
        );

        return mockData;
    }
}
