package moim_today.implement.university;

import moim_today.domain.university.ExtractUniversity;
import moim_today.dto.university.UniversityResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.university.UniversityJpaEntity;

import java.util.List;
import java.util.Optional;


@Implement
public class UniversityComposition {

    private final UniversityFinder universityFinder;
    private final UniversityUpdater universityUpdater;

    public UniversityComposition(final UniversityFinder universityFinder,
                                 final UniversityUpdater universityUpdater) {
        this.universityFinder = universityFinder;
        this.universityUpdater = universityUpdater;
    }

    public List<UniversityResponse> getAllUniversity() {
        return universityFinder.getAllUniversity();
    }

    public Optional<UniversityJpaEntity> findByName(final String schoolName){
        return universityFinder.findByName(schoolName);
    }

    public UniversityJpaEntity getByUniversityEmail(final String emailDomain) {
        return universityFinder.getByUniversityEmail(emailDomain);
    }

    public void validateExists(final String emailDomain) {
        universityFinder.validateExists(emailDomain);
    }

    public boolean checkUniversityIdIsPresent(final long universityId){
        return universityFinder.checkUniversityIdIsPresent(universityId);
    }

    public List<UniversityJpaEntity> findUniversitiesByName(final List<String> universityNames) {
        return universityFinder.findUniversitiesByName(universityNames);
    }

    public void fetchAllUniversity() {
        universityUpdater.fetchAllUniversity();
    }

    public void putUniversity(final ExtractUniversity extractUniversity) {
        universityUpdater.putUniversity(extractUniversity);
    }
}
