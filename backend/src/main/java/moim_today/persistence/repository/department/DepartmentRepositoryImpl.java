package moim_today.persistence.repository.department;

import moim_today.dto.department.DepartmentInfoResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.global.constant.exception.DepartmentExceptionConstant.DEPARTMENT_NOT_FOUND_ERROR;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentJpaRepository departmentJpaRepository;

    public DepartmentRepositoryImpl(final DepartmentJpaRepository departmentJpaRepository) {
        this.departmentJpaRepository = departmentJpaRepository;
    }

    @Override
    public void save(final DepartmentJpaEntity departmentJpaEntity) {
        departmentJpaRepository.save(departmentJpaEntity);
    }

    @Override
    public List<DepartmentInfoResponse> findAllDepartmentOfUniversity(final long universityId) {
        return departmentJpaRepository.findAllByUniversityId(universityId).stream()
                .map(DepartmentInfoResponse::from)
                .toList();
    }

    @Override
    public DepartmentJpaEntity getById(final long departmentId) {
        return departmentJpaRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR.message()));
    }
}
