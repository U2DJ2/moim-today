package moim_today.application.university;

import moim_today.dto.university.UniversityResponse;
import moim_today.implement.university.UniversityComposition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService{

    private final UniversityComposition universityComposition;

    public UniversityServiceImpl(final UniversityComposition universityComposition) {
        this.universityComposition = universityComposition;
    }

    @Override
    public void fetchAllUniversity() {
        universityComposition.fetchAllUniversity();
    }

    @Override
    public List<UniversityResponse> getUniversities() {
        return universityComposition.getAllUniversity();
    }
}
