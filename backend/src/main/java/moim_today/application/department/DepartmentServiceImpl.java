package moim_today.application.department;

import moim_today.dto.department.DepartmentInfoResponse;
import moim_today.implement.department.DepartmentAppender;
import moim_today.implement.department.DepartmentFinder;
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
    public List<DepartmentInfoResponse> getAllDepartmentByUniversityName(final String universityName) {
        return departmentFinder.getAllDepartmentByUniversityName(universityName);
    }

    @Override
    public List<DepartmentInfoResponse> getAllDepartmentById(final long universityId) {
        return departmentFinder.getAllDepartmentById(universityId);
    }
}
