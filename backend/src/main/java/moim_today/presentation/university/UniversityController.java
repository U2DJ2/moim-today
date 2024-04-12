package moim_today.presentation.university;

import moim_today.application.university.UniversityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/universities")
@RestController
public class UniversityController {

    private final UniversityService universityService;

    public UniversityController(final UniversityService universityService) {
        this.universityService = universityService;
    }

    @PostMapping("/university")
    public void updateCollegeInfo(){
        universityService.putAllUniversity();
    }
}
