package moim_today.fake_class.university;

import moim_today.application.university.UniversityService;
import moim_today.dto.university.UniversityResponse;

import java.util.Arrays;
import java.util.List;

public class FakeUniversityService implements UniversityService {

    @Override
    public void fetchAllUniversity() {

    }

    @Override
    public List<UniversityResponse> getUniversities() {

        // given
        List<UniversityResponse> mockUniversities = Arrays.asList(
                new UniversityResponse(1, "가야대학교", "kaya.ac.kr"),
                new UniversityResponse(2, "가천대학교", "gachon.ac.kr"),
                new UniversityResponse(3, "가톨릭관동대학교", "cku.ac.kr")
        );

        return mockUniversities;
    }
}
