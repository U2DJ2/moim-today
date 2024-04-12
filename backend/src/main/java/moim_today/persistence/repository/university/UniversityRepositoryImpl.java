package moim_today.persistence.repository.university;

import moim_today.global.error.NotFoundException;
import moim_today.persistence.entity.university.UniversityJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.global.constant.exception.UniversityExceptionConstant.UNIVERSITY_NOT_FOUND;

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

    @Override
    public void put(final UniversityJpaEntity universityJpaEntity) {
        UniversityJpaEntity findUniversity = findByName(universityJpaEntity.getUniversityName());

        if(findUniversity == null){
            universityJpaRepository.save(universityJpaEntity);
            return;
        }
        if(!universityJpaEntity.getUniversityEmail().isEmpty()){
            findUniversity.updateEmail(universityJpaEntity.getUniversityEmail());
            universityJpaRepository.save(findUniversity);
        }
    }

    @Override
    public UniversityJpaEntity getByName(final String universityName) {
        return universityJpaRepository.findByUniversityName(universityName)
                .orElseThrow(() -> new NotFoundException(universityName+UNIVERSITY_NOT_FOUND.message()));
    }

    @Override
    public UniversityJpaEntity findByName(final String universityName) {
        return universityJpaRepository.findByUniversityName(universityName).orElse(null);
    }

    @Override
    public List<UniversityJpaEntity> findAll() {
        return universityJpaRepository.findAll();
    }
}
