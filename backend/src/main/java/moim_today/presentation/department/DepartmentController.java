package moim_today.presentation.department;

import moim_today.application.department.DepartmentService;
import moim_today.dto.department.DepartmentResponse;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static moim_today.global.constant.StaticSymbolConstant.BLANK;
import static moim_today.global.constant.StaticSymbolConstant.NO_UNIVERSITY_ID;

@RequestMapping("/api/departments")
@RestController
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(final DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public void updateDepartmentInfo() {
        departmentService.patchAllDepartment();
    }

    @GetMapping("/university-name")
    public CollectionResponse<List<DepartmentResponse>> getDepartmentsByUniversityName(
            @RequestParam(defaultValue = BLANK) final String universityName) {
        return CollectionResponse.from(departmentService.getAllDepartmentByUniversityName(universityName));
    }

    @GetMapping("/university-id")
    public CollectionResponse<List<DepartmentResponse>> getDepartmentsByUniversityId(
            @RequestParam(defaultValue = NO_UNIVERSITY_ID) final long universityId) {
        return CollectionResponse.from(departmentService.getAllDepartmentById(universityId));
    }
}