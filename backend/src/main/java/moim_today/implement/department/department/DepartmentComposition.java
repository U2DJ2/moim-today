package moim_today.implement.department.department;

import moim_today.dto.department.DepartmentResponse;
import moim_today.global.annotation.Implement;

import java.util.*;


@Implement
public class DepartmentComposition {

    private final DepartmentAppender departmentAppender;
    private final DepartmentFinder departmentFinder;

    public DepartmentComposition(final DepartmentAppender departmentAppender,
                                 final DepartmentFinder departmentFinder) {
        this.departmentAppender = departmentAppender;
        this.departmentFinder = departmentFinder;
    }

    public void putAllDepartment() {
        departmentAppender.putAllDepartment();
    }

    public void addDepartment(final long universityId, final String requestDepartmentName) {
        departmentAppender.addDepartment(universityId, requestDepartmentName);
    }

    public void validateBelongToUniversity(final long universityId, final long departmentId) {
        departmentFinder.validateBelongToUniversity(universityId, departmentId);
    }

    public List<DepartmentResponse> getAllDepartmentByUniversityName(final String universityName){
        return departmentFinder.getAllDepartmentByUniversityName(universityName);
    }

    public List<DepartmentResponse> getAllDepartmentById(final long universityId){
        return departmentFinder.getAllDepartmentById(universityId);
    }
}
