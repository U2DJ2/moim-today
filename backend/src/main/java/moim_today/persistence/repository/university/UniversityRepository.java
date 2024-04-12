package moim_today.persistence.repository.university;

import moim_today.persistence.entity.university.UniversityJpaEntity;

public interface UniversityRepository {

    void save(final UniversityJpaEntity universityJpaEntity);
}
