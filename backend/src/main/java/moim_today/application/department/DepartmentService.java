package moim_today.application.department;

import moim_today.dto.department.DepartmentInfoResponse;

import java.util.List;

public interface DepartmentService {

    void patchAllDepartment();

    List<DepartmentInfoResponse> getAllDepartmentByUniversityName(final String universityName);

    List<DepartmentInfoResponse> getAllDepartmentById(final long universityId);
}
