package moim_today.dto.department;

import lombok.Builder;
import moim_today.persistence.entity.department.DepartmentJpaEntity;

@Builder
public record DepartmentInfoResponse (
    long departmentId,
    String departmentName
){
    public static DepartmentInfoResponse from(final DepartmentJpaEntity departmentJpaEntity){
        return DepartmentInfoResponse.builder()
                .departmentId(departmentJpaEntity.getId())
                .departmentName(departmentJpaEntity.getDepartmentName())
                .build();
    }
}
