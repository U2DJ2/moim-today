package moim_today.application.university;

import moim_today.dto.university.UniversityInfoResponse;

import java.util.List;

public interface UniversityService {

    void fetchAllUniversity();

    List<UniversityInfoResponse> getUniversities();
}
