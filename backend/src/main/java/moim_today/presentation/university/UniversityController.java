package moim_today.presentation.university;

import moim_today.application.department.DepartmentService;
import moim_today.application.university.UniversityService;
import moim_today.dto.university.UniversityInfoResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/universities")
@RestController
public class UniversityController {

    private final UniversityService universityService;
    private final DepartmentService departmentService;

    public UniversityController(final UniversityService universityService, final DepartmentService departmentService) {
        this.universityService = universityService;
        this.departmentService = departmentService;
    }

    @PutMapping("/university")
    public void updateCollegeInfo(){
        universityService.putAllUniversity();
    }

    @PutMapping("/department")
    public void updateDepartmentInfo(){
        departmentService.putAllDepartment();
    }

    @GetMapping("/university")
    public List<UniversityInfoResponse> getUniversityInfo(){
        return universityService.getAllUniversity();
    }
}
