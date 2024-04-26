package moim_today.implement.university;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.university.UniversityRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Implement
public class UniversityUpdater {

    private final UniversityRepository universityRepository;

    public UniversityUpdater(final UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @Transactional
    public void putUniversity(final UniversityJpaEntity universityJpaEntity) {
        Optional<UniversityJpaEntity> findUniversity = universityRepository.findByName(universityJpaEntity.getUniversityName());

        if (findUniversity.isEmpty()) {
            universityRepository.save(universityJpaEntity);
            return;
        }
        if (!universityJpaEntity.getUniversityEmail().isEmpty()) {
            UniversityJpaEntity getUniversity = findUniversity.get();
            getUniversity.updateEmail(universityJpaEntity.getUniversityEmail());
            universityRepository.save(getUniversity);
        }
    }
}
