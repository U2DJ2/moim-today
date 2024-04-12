package moim_today.application.department;

import java.util.List;

public interface DepartmentService {

    void putAllDepartment();

    List<String> getAllDepartment(long universityId, String universityName);
}
