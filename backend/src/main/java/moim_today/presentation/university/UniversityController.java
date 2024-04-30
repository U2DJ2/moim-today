package moim_today.presentation.university;

import moim_today.application.department.DepartmentService;
import moim_today.application.university.UniversityService;
import moim_today.dto.department.DepartmentInfoResponse;
import moim_today.dto.university.UniversityInfoResponse;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static moim_today.global.constant.StaticSymbolConstant.BLANK;
import static moim_today.global.constant.StaticSymbolConstant.NO_UNIVERSITY_ID;

@RequestMapping("/api/universities")
@RestController
public class UniversityController {

    private final UniversityService universityService;
    private final DepartmentService departmentService;

    public UniversityController(final UniversityService universityService, final DepartmentService departmentService) {
        this.universityService = universityService;
        this.departmentService = departmentService;
    }

    @PostMapping
    public void updateUniversityInfo(){
        universityService.patchAllUniversity();
    }

    @PostMapping("/departments")
    public void updateDepartmentInfo(){
        departmentService.patchAllDepartment();
    }

    @GetMapping
    public CollectionResponse<List<UniversityInfoResponse>> getUniversity(){
        return CollectionResponse.of(universityService.getUniversities());
    }

    @GetMapping("/departments")
    public CollectionResponse<List<DepartmentInfoResponse>> getDepartments(
            @RequestParam(defaultValue = NO_UNIVERSITY_ID, required = false) final long universityId,
            @RequestParam(defaultValue = BLANK, required = false) final String universityName) {
        return CollectionResponse.of(departmentService.getAllDepartment(universityId, universityName));
    }
}
