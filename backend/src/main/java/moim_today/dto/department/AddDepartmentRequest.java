package moim_today.dto.department;

import lombok.Builder;
import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;

@Builder
public record AddDepartmentRequest(
        long universityId,
        String departmentName
) {

    public RequestDepartmentJpaEntity toEntity() {
        return RequestDepartmentJpaEntity.builder()
                .universityId(universityId)
                .requestDepartmentName(departmentName)
                .build();
    }
}
