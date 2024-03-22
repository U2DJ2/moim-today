package booki_today.persistence.repository.category.university_category;

import org.springframework.stereotype.Repository;

@Repository
public class UniversityCategoryRepositoryImpl implements UniversityCategoryRepository {

    private final UniversityCategoryJpaRepository universityCategoryJpaRepository;

    public UniversityCategoryRepositoryImpl(final UniversityCategoryJpaRepository universityCategoryJpaRepository) {
        this.universityCategoryJpaRepository = universityCategoryJpaRepository;
    }
}
