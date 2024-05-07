package moim_today.application.university;

import moim_today.dto.university.UniversityResponse;

import java.util.List;

public interface UniversityService {

    void fetchAllUniversity();

    List<UniversityResponse> getUniversities();
}
