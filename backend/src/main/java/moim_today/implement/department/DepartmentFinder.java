package moim_today.implement.department;

import moim_today.global.annotation.Implement;
import moim_today.global.error.BadRequestException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import moim_today.persistence.repository.department.DepartmentRepository;

import static moim_today.global.constant.exception.DepartmentExceptionConstant.DEPARTMENT_NOT_MATCH_UNIVERSITY;

@Implement
public class DepartmentFinder {

    private final DepartmentRepository departmentRepository;

    public DepartmentFinder(final DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void validateDepartmentId(final long universityId, final long departmentId) {
        DepartmentJpaEntity departmentJpaEntity = departmentRepository.getById(departmentId);
        if (departmentJpaEntity.getUniversityId() != universityId) {
            throw new BadRequestException(DEPARTMENT_NOT_MATCH_UNIVERSITY.message());
        }
    }
}
