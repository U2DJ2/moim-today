package moim_today.presentation.university;

import moim_today.application.university.UniversityService;
import moim_today.dto.university.UniversityResponse;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/universities")
@RestController
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(final UniversityService universityService) {
        this.universityService = universityService;
    }

    @PostMapping
    public void fetchUniversityInfo(){
        universityService.fetchAllUniversity();
    }

    @GetMapping
    public CollectionResponse<List<UniversityResponse>> getUniversity(){
        return CollectionResponse.of(universityService.getUniversities());
    }
}
