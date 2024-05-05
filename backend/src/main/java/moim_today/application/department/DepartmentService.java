package moim_today.application.department;

import moim_today.dto.department.DepartmentDetailResponse;

import java.util.List;

public interface DepartmentService {

    void patchAllDepartment();

    List<DepartmentDetailResponse> getAllDepartmentByUniversityName(final String universityName);

    List<DepartmentDetailResponse> getAllDepartmentById(final long universityId);
}
