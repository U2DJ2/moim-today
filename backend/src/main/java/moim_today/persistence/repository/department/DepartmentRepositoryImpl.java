package moim_today.persistence.repository.department;

import moim_today.dto.department.DepartmentResponse;
import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.department.DepartmentJpaEntity;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static moim_today.global.constant.exception.DepartmentExceptionConstant.DEPARTMENT_NOT_FOUND_ERROR;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private final DepartmentJpaRepository departmentJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    public DepartmentRepositoryImpl(final DepartmentJpaRepository departmentJpaRepository, JdbcTemplate jdbcTemplate) {
        this.departmentJpaRepository = departmentJpaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public DepartmentJpaEntity save(final DepartmentJpaEntity departmentJpaEntity) {
        return departmentJpaRepository.save(departmentJpaEntity);
    }

    @Override
    public List<DepartmentResponse> findAllDepartmentOfUniversity(final long universityId) {
        return departmentJpaRepository.findAllByUniversityId(universityId).stream()
                .map(DepartmentResponse::from)
                .toList();
    }

    @Transactional
    @Override
    public void batchUpdate(final List<DepartmentJpaEntity> departmentJpaEntities) {
        String sql = "INSERT INTO department (university_id, department_name) VALUES (?, ?)";

        this.jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DepartmentJpaEntity department = departmentJpaEntities.get(i);
                ps.setLong(1, department.getUniversityId());
                ps.setString(2, department.getDepartmentName());
            }

            @Override
            public int getBatchSize() {
                return departmentJpaEntities.size();
            }
        });
    }

    @Override
    public DepartmentJpaEntity getById(final long departmentId) {
        return departmentJpaRepository.findById(departmentId)
                .orElseThrow(() -> new NotFoundException(DEPARTMENT_NOT_FOUND_ERROR.message()));
    }
}
