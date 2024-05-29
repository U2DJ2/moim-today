package moim_today.application.department.department;

import moim_today.dto.department.DepartmentResponse;
import moim_today.implement.department.department.DepartmentAppender;
import moim_today.implement.department.department.DepartmentFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentAppender departmentAppender;
    private final DepartmentFinder departmentFinder;

    public DepartmentServiceImpl(final DepartmentAppender departmentAppender, final DepartmentFinder departmentFinder) {
        this.departmentAppender = departmentAppender;
        this.departmentFinder = departmentFinder;
    }

    @Override
    public void patchAllDepartment() {
        departmentAppender.putAllDepartment();
    }

    @Override
    public List<DepartmentResponse> getAllDepartmentByUniversityName(final String universityName) {
        return departmentFinder.getAllDepartmentByUniversityName(universityName);
    }

    @Override
    public List<DepartmentResponse> getAllDepartmentById(final long universityId) {
        return departmentFinder.getAllDepartmentById(universityId);
    }
}
