package moim_today.implement.department;

import moim_today.dto.department.DepartmentInfoResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.repository.department.DepartmentRepository;
import moim_today.persistence.repository.university.UniversityRepository;

import java.util.List;

import static moim_today.global.constant.exception.DepartmentExceptionConstant.DEPARTMENT_NOT_MATCH_UNIVERSITY;
import static moim_today.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT;

@Implement
public class DepartmentFinder {

    private final DepartmentRepository departmentRepository;
    private final UniversityRepository universityRepository;

    public DepartmentFinder(final DepartmentRepository departmentRepository, final UniversityRepository universityRepository) {
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    public void validateDepartmentId(final long universityId, final long departmentId) {
        DepartmentJpaEntity departmentJpaEntity = departmentRepository.getById(departmentId);
        if (departmentJpaEntity.getUniversityId() != universityId) {
            throw new BadRequestException(DEPARTMENT_NOT_MATCH_UNIVERSITY.message());
        }
    }

    public List<DepartmentInfoResponse> getAllDepartment(final long universityId, final String universityName){
        if(universityId != -1){
            return getAllDepartmentByUniversityId(universityId);
        }
        if(!universityName.isEmpty()){
            return getAllDepartmentByUniversityName(universityName);
        }
        throw new BadRequestException(UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT.message());
    }

    private List<DepartmentInfoResponse> getAllDepartmentByUniversityId(final long universityId) {
        return departmentRepository.findAllDepartmentOfUniversity(universityId);
    }

    private List<DepartmentInfoResponse> getAllDepartmentByUniversityName(final String universityName) {
        long universityId = universityRepository.getByName(universityName).getId();
        return departmentRepository.findAllDepartmentOfUniversity(universityId);
    }
}
