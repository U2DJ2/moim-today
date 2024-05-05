package moim_today.application.university;

import moim_today.dto.university.UniversityDetailResponse;

import java.util.List;

public interface UniversityService {

    void fetchAllUniversity();

    List<UniversityDetailResponse> getUniversities();
}
