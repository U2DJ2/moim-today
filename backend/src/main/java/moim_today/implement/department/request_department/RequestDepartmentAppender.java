package moim_today.implement.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;
import moim_today.persistence.repository.department.request_department.RequestDepartmentRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class RequestDepartmentAppender {

    private final RequestDepartmentRepository requestDepartmentRepository;

    public RequestDepartmentAppender(final RequestDepartmentRepository requestDepartmentRepository) {
        this.requestDepartmentRepository = requestDepartmentRepository;
    }

    @Transactional
    public void addDepartmentName(final AddDepartmentRequest addDepartmentRequest) {
        RequestDepartmentJpaEntity requestDepartmentJpaEntity = addDepartmentRequest.toEntity();
        requestDepartmentRepository.save(requestDepartmentJpaEntity);
    }
}
