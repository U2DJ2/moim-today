package moim_today.fake_class.university;

import moim_today.application.university.UniversityService;
import moim_today.dto.university.UniversityInfoResponse;

import java.util.List;

public class FakeUniversityService implements UniversityService {

    @Override
    public void patchAllUniversity() {

    }

    @Override
    public List<UniversityInfoResponse> getUniversities() {
        return null;
    }
}
