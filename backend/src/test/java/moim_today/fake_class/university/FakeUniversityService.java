package moim_today.fake_class.university;

import moim_today.application.university.UniversityService;
import moim_today.dto.university.UniversityDetailResponse;

import java.util.Arrays;
import java.util.List;

public class FakeUniversityService implements UniversityService {

    @Override
    public void fetchAllUniversity() {

    }

    @Override
    public List<UniversityDetailResponse> getUniversities() {

        // given
        List<UniversityDetailResponse> mockUniversities = Arrays.asList(
                new UniversityDetailResponse(1, "가야대학교", "kaya.ac.kr"),
                new UniversityDetailResponse(2, "가천대학교", "gachon.ac.kr"),
                new UniversityDetailResponse(3, "가톨릭관동대학교", "cku.ac.kr")
        );

        return mockUniversities;
    }
}
