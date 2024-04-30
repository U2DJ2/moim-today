package moim_today.persistence.repository.university;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.nCopies;
import static moim_today.global.constant.UniversityConstant.UNIVERSITY_EMAIL;
import static moim_today.global.constant.UniversityConstant.UNIVERSITY_NAME;
import static moim_today.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_EMAIL_NOT_FOUND;
import static moim_today.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_NOT_FOUND;

@Repository
public class UniversityRepositoryImpl implements UniversityRepository {

    private final UniversityJpaRepository universityJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    public UniversityRepositoryImpl(final UniversityJpaRepository universityJpaRepository, JdbcTemplate jdbcTemplate) {
        this.universityJpaRepository = universityJpaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public UniversityJpaEntity save(final UniversityJpaEntity universityJpaEntity) {
        return universityJpaRepository.save(universityJpaEntity);
    }

    @Override
    public UniversityJpaEntity getByName(final String universityName) {
        return universityJpaRepository.findByUniversityName(universityName)
                .orElseThrow(() -> new NotFoundException(universityName+UNIVERSITY_NOT_FOUND.message()));
    }

    @Override
    public UniversityJpaEntity getByEmail(final String email) {
        return universityJpaRepository.findByUniversityEmail(email)
                .orElseThrow(() -> new NotFoundException(UNIVERSITY_EMAIL_NOT_FOUND.message()));
    }

    @Override
    public boolean validateUniversityEmail(final String universityEmail) {
        return universityJpaRepository.existsByUniversityEmail(universityEmail);
    }

    @Override
    public Optional<UniversityJpaEntity> findByName(final String universityName) {
        return universityJpaRepository.findByUniversityName(universityName);
    }

    @Override
    public List<UniversityJpaEntity> findAll() {
        return universityJpaRepository.findAll();
    }

    @Override
    public List<UniversityJpaEntity> findExistingUniversities(final List<String> universityNames) {
        if (universityNames == null || universityNames.isEmpty()) {
            return new ArrayList<>();
        }

        String inSql = String.join(",", nCopies(universityNames.size(), "?"));
        String sql = "SELECT * FROM university WHERE "+UNIVERSITY_NAME.value()+" IN (" + inSql + ")";

        return jdbcTemplate.query(sql, universityNames.toArray(new Object[0]), universityJpaEntityRowMapper());
    }

    @Override
    public Optional<UniversityJpaEntity> findById(final long id) {
        return universityJpaRepository.findById(id);
    }

    private RowMapper<UniversityJpaEntity> universityJpaEntityRowMapper() {
        return (rs, rowNum) -> {
            return UniversityJpaEntity.builder()
                    .universityName(rs.getString(UNIVERSITY_NAME.value()))
                    .universityEmail(rs.getString(UNIVERSITY_EMAIL.value()))
                    .build();
        };
    }
}
