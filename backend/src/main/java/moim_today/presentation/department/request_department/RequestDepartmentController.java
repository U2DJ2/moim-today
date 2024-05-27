package moim_today.presentation.department.request_department;

import moim_today.application.department.request_department.RequestDepartmentService;
import moim_today.dto.department.AddDepartmentRequest;
import moim_today.dto.department.ApproveRequestDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class RequestDepartmentController {

    private final RequestDepartmentService requestDepartmentService;

    public RequestDepartmentController(final RequestDepartmentService requestDepartmentService) {
        this.requestDepartmentService = requestDepartmentService;
    }

    @GetMapping("/admin/request-departments")
    public CollectionResponse<List<RequestDepartmentResponse>> findAll() {
        List<RequestDepartmentResponse> requestDepartmentResponses = requestDepartmentService.findAll();
        return CollectionResponse.from(requestDepartmentResponses);
    }

    @PostMapping("/admin/request-departments")
    public void approveRequest(@RequestBody final ApproveRequestDepartmentRequest approveRequestDepartmentRequest) {
        requestDepartmentService.approveRequest(approveRequestDepartmentRequest);
    }

    @PostMapping("/request-departments")
    public void requestAddDepartment(@RequestBody final AddDepartmentRequest addDepartmentRequest) {
        requestDepartmentService.addDepartment(addDepartmentRequest);
    }
}
