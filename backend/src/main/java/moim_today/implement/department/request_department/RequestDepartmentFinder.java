package moim_today.implement.department.request_department;

import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.department.request_department.RequestDepartmentRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Implement
public class RequestDepartmentFinder {

    private final RequestDepartmentRepository requestDepartmentRepository;

    public RequestDepartmentFinder(final RequestDepartmentRepository requestDepartmentRepository) {
        this.requestDepartmentRepository = requestDepartmentRepository;
    }

    @Transactional(readOnly = true)
    public List<RequestDepartmentResponse> findAll() {
        return requestDepartmentRepository.findAll();
    }
}
