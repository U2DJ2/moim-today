package moim_today.persistence.repository.university;

import moim_today.persistence.entity.university.UniversityJpaEntity;

import java.util.List;
import java.util.Optional;

public interface UniversityRepository {

    UniversityJpaEntity save(final UniversityJpaEntity universityJpaEntity);

    UniversityJpaEntity getByName(final String name);

    UniversityJpaEntity getByEmail(final String email);

    boolean validateUniversityEmail(final String universityEmail);

    Optional<UniversityJpaEntity> findByName(final String name);

    Optional<UniversityJpaEntity> findById(final long id);

    List<UniversityJpaEntity> findAll();

    List<UniversityJpaEntity> findExistingUniversities(List<String> universityNames);

    String getAdminPasswordById(final long universityId);
}
