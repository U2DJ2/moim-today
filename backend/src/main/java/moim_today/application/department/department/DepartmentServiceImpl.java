package moim_today.application.department.department;

import moim_today.dto.department.DepartmentResponse;
import moim_today.implement.department.department.DepartmentComposition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentComposition departmentComposition;

    public DepartmentServiceImpl(final DepartmentComposition departmentComposition) {
        this.departmentComposition = departmentComposition;
    }

    @Override
    public void patchAllDepartment() {
        departmentComposition.putAllDepartment();
    }

    @Override
    public List<DepartmentResponse> getAllDepartmentByUniversityName(final String universityName) {
        return departmentComposition.getAllDepartmentByUniversityName(universityName);
    }

    @Override
    public List<DepartmentResponse> getAllDepartmentById(final long universityId) {
        return departmentComposition.getAllDepartmentById(universityId);
    }
}
