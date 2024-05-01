package moim_today.fake_class.university;

import moim_today.application.university.UniversityService;
import moim_today.dto.university.UniversityInfoResponse;

import java.util.Arrays;
import java.util.List;

public class FakeUniversityService implements UniversityService {

    @Override
    public void patchAllUniversity() {

    }

    @Override
    public List<UniversityInfoResponse> getUniversities() {

        // given
        List<UniversityInfoResponse> mockUniversities = Arrays.asList(
                new UniversityInfoResponse(1, "가야대학교", "kaya.ac.kr"),
                new UniversityInfoResponse(2, "가천대학교", "gachon.ac.kr"),
                new UniversityInfoResponse(3, "가톨릭관동대학교", "cku.ac.kr")
        );

        return mockUniversities;
    }
}
