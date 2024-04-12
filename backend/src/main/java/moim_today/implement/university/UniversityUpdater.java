package moim_today.implement.university;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import moim_today.persistence.repository.university.UniversityRepository;

@Implement
public class UniversityUpdater {

    private final UniversityRepository universityRepository;

    public UniversityUpdater(final UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public void putUniversity(final UniversityJpaEntity universityJpaEntity) {
        UniversityJpaEntity findUniversity = universityRepository.findByName(universityJpaEntity.getUniversityName());

        if (findUniversity == null) {
            universityRepository.save(universityJpaEntity);
            return;
        }
        if (!universityJpaEntity.getUniversityEmail().isEmpty()) {
            findUniversity.updateEmail(universityJpaEntity.getUniversityEmail());
            universityRepository.save(findUniversity);
        }
    }
}
