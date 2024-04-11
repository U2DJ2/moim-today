package moim_today.persistence.repository.university;

import moim_today.persistence.entity.university.UniversityJpaEntity;

import java.util.List;

public interface UniversityRepository {

    void save(final UniversityJpaEntity universityJpaEntity);

    void put(final UniversityJpaEntity universityJpaEntity);

    UniversityJpaEntity getByName(final String name);

    UniversityJpaEntity findByName(final String name);

    List<UniversityJpaEntity> findAll();

    void save(final UniversityJpaEntity universityJpaEntity);
}
