package moim_today.dto.department;

import lombok.Builder;
import moim_today.persistence.entity.department.DepartmentJpaEntity;

@Builder
public record DepartmentResponse(
    long departmentId,
    String departmentName
){
    public static DepartmentResponse from(final DepartmentJpaEntity departmentJpaEntity){
        return DepartmentResponse.builder()
                .departmentId(departmentJpaEntity.getId())
                .departmentName(departmentJpaEntity.getDepartmentName())
                .build();
    }
}
