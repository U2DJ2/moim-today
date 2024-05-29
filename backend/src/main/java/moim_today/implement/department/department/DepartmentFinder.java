package moim_today.implement.department.department;

import moim_today.dto.department.DepartmentResponse;
import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.repository.department.department.DepartmentRepository;
import moim_today.persistence.repository.university.UniversityRepository;

import java.util.List;

import static moim_today.global.constant.StaticSymbolConstant.NO_UNIVERSITY_ID;
import static moim_today.global.constant.exception.DepartmentExceptionConstant.DEPARTMENT_NOT_MATCH_UNIVERSITY;
import static moim_today.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT;

@Implement
public class DepartmentFinder {

    private final DepartmentRepository departmentRepository;
    private final UniversityRepository universityRepository;

    public DepartmentFinder(final DepartmentRepository departmentRepository,
                            final UniversityRepository universityRepository) {
        this.departmentRepository = departmentRepository;
        this.universityRepository = universityRepository;
    }

    public void validateBelongToUniversity(final long universityId, final long departmentId) {
        DepartmentJpaEntity departmentJpaEntity = departmentRepository.getById(departmentId);
        if (departmentJpaEntity.getUniversityId() != universityId) {
            throw new BadRequestException(DEPARTMENT_NOT_MATCH_UNIVERSITY.message());
        }
    }

    public List<DepartmentResponse> getAllDepartmentByUniversityName(final String universityName){
        if(!universityName.isEmpty()){
            long universityId = universityRepository.getByName(universityName).getId();
            return departmentRepository.findAllDepartmentOfUniversity(universityId);
        }
        throw new BadRequestException(UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT.message());
    }

    public List<DepartmentResponse> getAllDepartmentById(final long universityId){
        if(universityId != Long.parseLong(NO_UNIVERSITY_ID)){
            return departmentRepository.findAllDepartmentOfUniversity(universityId);
        }
        throw new BadRequestException(UNIVERSITY_SEARCH_CONDITIONS_INSUFFICIENT.message());
    }
}
