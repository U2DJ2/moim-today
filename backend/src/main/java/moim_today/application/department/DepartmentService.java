package moim_today.application.department;

import moim_today.dto.department.DepartmentInfoResponse;

import java.util.List;

public interface DepartmentService {

    void putAllDepartment();

    List<DepartmentInfoResponse> getAllDepartment(long universityId, String universityName);
}
