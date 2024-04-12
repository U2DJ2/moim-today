package moim_today.application.university;

import moim_today.dto.university.UniversityInfoResponse;
import moim_today.implement.university.UniversityAppender;
import moim_today.implement.university.UniversityFinder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService{

    private final UniversityAppender universityAppender;
    private final UniversityFinder universityFinder;

    public UniversityServiceImpl(final UniversityAppender universityAppender, final UniversityFinder universityFinder) {
        this.universityAppender = universityAppender;
        this.universityFinder = universityFinder;
    }

    @Override
    public void putAllUniversity() {
        universityAppender.fetchAllUniversity();
    }

    @Override
    public List<UniversityInfoResponse> getAllUniversity() {
        return universityFinder.getAllUniversity();
    }
}
