package moim_today.application.university;

import moim_today.dto.university.UniversityInfoResponse;

import java.util.List;

public interface UniversityService {

    void putAllUniversity();

    void putAllDepartment();

    List<UniversityInfoResponse> getAllUniversity();

    List<String> getAllDepartment(long universityId, String universityName);
}
