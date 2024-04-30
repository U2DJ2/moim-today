package moim_today.application.department;

import moim_today.dto.department.DepartmentInfoResponse;

import java.util.List;

public interface DepartmentService {

    void patchAllDepartment();

    List<DepartmentInfoResponse> getAllDepartment(final long universityId, String universityName);
}
