package moim_today.presentation.admin.department;

import jakarta.validation.Valid;
import moim_today.application.admin.department.AdminDepartmentService;
import moim_today.domain.university.AdminSession;
import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.global.annotation.Login;
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

    @GetMapping("/request-departments")
    public CollectionResponse<List<RequestDepartmentResponse>> findAllByUniversityId(
            @Login final AdminSession adminSession) {
        List<RequestDepartmentResponse> requestDepartmentResponses =
                adminDepartmentService.findAllByUniversityId(adminSession.universityId());
        return CollectionResponse.from(requestDepartmentResponses);
    }

    @PostMapping("/request-departments")
    public void approveRequest(@RequestBody @Valid final ApproveRequestDepartmentRequest approveRequestDepartmentRequest) {
        adminDepartmentService.approveRequest(approveRequestDepartmentRequest);
    }
}
