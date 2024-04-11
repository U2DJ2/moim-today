package moim_today.persistence.repository.university;

import org.springframework.stereotype.Repository;

@Repository
public class UniversityRepositoryImpl implements UniversityRepository {

    private final UniversityJpaRepository universityJpaRepository;

    public UniversityRepositoryImpl(final UniversityJpaRepository universityJpaRepository) {
        this.universityJpaRepository = universityJpaRepository;
    }
}
