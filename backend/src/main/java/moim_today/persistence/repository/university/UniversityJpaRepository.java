package moim_today.persistence.repository.university;

import moim_today.persistence.entity.university.UniversityJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UniversityJpaRepository extends JpaRepository<UniversityJpaEntity, Long> {

    Optional<UniversityJpaEntity> findByUniversityName(final String universityName);

    Optional<UniversityJpaEntity> findByUniversityEmail(final String universityEmail);

    boolean existsByUniversityEmail(final String universityEmail);

    Optional<UniversityJpaEntity> findById(final long id);

    List<UniversityJpaEntity> findAll();
}
