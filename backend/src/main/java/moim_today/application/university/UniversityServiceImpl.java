package moim_today.application.university;

import moim_today.dto.university.UniversityInfoResponse;
import moim_today.implement.university.UniversityAppender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversityServiceImpl implements UniversityService{

    private final UniversityAppender universityAppender;

    public UniversityServiceImpl(final UniversityAppender universityAppender) {
        this.universityAppender = universityAppender;
    }

    @Override
    public void putAllUniversity() {
        universityAppender.fetchAllUniversity();
    }

    @Override
    public void putAllDepartment() {

    }

    @Override
    public List<UniversityInfoResponse> getAllUniversity() {
        return null;
    }

    @Override
    public List<String> getAllDepartment(final long universityId, final String universityName) {
        return null;
    }
}
