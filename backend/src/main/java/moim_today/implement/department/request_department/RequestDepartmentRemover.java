package moim_today.implement.department.request_department;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.department.request_department.RequestDepartmentRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class RequestDepartmentRemover {

    private final RequestDepartmentRepository requestDepartmentRepository;

    public RequestDepartmentRemover(final RequestDepartmentRepository requestDepartmentRepository) {
        this.requestDepartmentRepository = requestDepartmentRepository;
    }

    @Transactional
    public void deleteById(final long requestDepartmentId) {
        requestDepartmentRepository.deleteById(requestDepartmentId);
    }
}
