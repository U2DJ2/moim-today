package moim_today.presentation.department.request_department;

import moim_today.application.department.request_department.RequestDepartmentService;
import moim_today.dto.department.AddDepartmentRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class RequestDepartmentController {

    private final RequestDepartmentService requestDepartmentService;

    public RequestDepartmentController(final RequestDepartmentService requestDepartmentService) {
        this.requestDepartmentService = requestDepartmentService;
    }

    @PostMapping("/request-departments")
    public void requestAddDepartment(@RequestBody final AddDepartmentRequest addDepartmentRequest) {
        requestDepartmentService.addDepartmentName(addDepartmentRequest);
    }
}
