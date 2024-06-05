package moim_today.implement.university;

import moim_today.dto.university.UniversityResponse;
import moim_today.global.annotation.Implement;

import java.util.List;


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

    public boolean checkUniversityIdIsPresent(final long universityId){
        return universityFinder.checkUniversityIdIsPresent(universityId);
    }

    public void fetchAllUniversity() {
        universityUpdater.fetchAllUniversity();
    }
}
