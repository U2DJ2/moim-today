package moim_today.fake_class.department;

import moim_today.application.department.DepartmentService;
import moim_today.dto.department.DepartmentDetailResponse;

import java.util.List;

public class FakeDepartmentService implements DepartmentService {

    @Override
    public void patchAllDepartment() {

    }

    @Override
    public List<DepartmentDetailResponse> getAllDepartmentByUniversityName(final String universityName) {

        // given
        List<DepartmentDetailResponse> mockData = List.of(
                new DepartmentDetailResponse(1L, "소프트웨어학과"),
                new DepartmentDetailResponse(2L, "의예과"),
                new DepartmentDetailResponse(3L, "철학과")
        );

        return mockData;
    }

    @Override
    public List<DepartmentDetailResponse> getAllDepartmentById(final long universityId) {
        // given
        List<DepartmentDetailResponse> mockData = List.of(
                new DepartmentDetailResponse(1L, "소프트웨어학과"),
                new DepartmentDetailResponse(2L, "의예과"),
                new DepartmentDetailResponse(3L, "철학과")
        );

        return mockData;
    }
}
