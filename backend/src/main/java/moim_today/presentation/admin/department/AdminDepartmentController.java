package moim_today.presentation.admin.department;

import moim_today.application.admin.department.AdminDepartmentService;
import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
public class AdminDepartmentController {

    private final AdminDepartmentService adminDepartmentService;

    public AdminDepartmentController(final AdminDepartmentService adminDepartmentService) {
        this.adminDepartmentService = adminDepartmentService;
    }

    @GetMapping("/request-departments/{universityId}")
    public CollectionResponse<List<RequestDepartmentResponse>> findAllByUniversityId(
            @PathVariable final long universityId) {
        List<RequestDepartmentResponse> requestDepartmentResponses =
                adminDepartmentService.findAllByUniversityId(universityId);
        return CollectionResponse.from(requestDepartmentResponses);
    }

    @PostMapping("/request-departments")
    public void approveRequest(@RequestBody final ApproveRequestDepartmentRequest approveRequestDepartmentRequest) {
        adminDepartmentService.approveRequest(approveRequestDepartmentRequest);
    }
}
