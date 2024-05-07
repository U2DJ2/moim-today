package moim_today.application.department;

import moim_today.dto.department.DepartmentResponse;

import java.util.List;

public interface DepartmentService {

    void patchAllDepartment();

    List<DepartmentResponse> getAllDepartmentByUniversityName(final String universityName);

    List<DepartmentResponse> getAllDepartmentById(final long universityId);
}
