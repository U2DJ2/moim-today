package moim_today.persistence.repository.university;

import moim_today.persistence.entity.university.UniversityJpaEntity;
import org.springframework.stereotype.Repository;

@Repository
public class UniversityRepositoryImpl implements UniversityRepository {

    private final UniversityJpaRepository universityJpaRepository;

    public UniversityRepositoryImpl(final UniversityJpaRepository universityJpaRepository) {
        this.universityJpaRepository = universityJpaRepository;
    }

    @Override
    public void save(final UniversityJpaEntity universityJpaEntity) {
        universityJpaRepository.save(universityJpaEntity);
    }
}
