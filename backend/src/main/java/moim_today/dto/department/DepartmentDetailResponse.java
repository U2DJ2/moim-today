package moim_today.dto.department;

import lombok.Builder;
import moim_today.persistence.entity.department.DepartmentJpaEntity;

@Builder
public record DepartmentDetailResponse(
    long departmentId,
    String departmentName
){
    public static DepartmentDetailResponse from(final DepartmentJpaEntity departmentJpaEntity){
        return DepartmentDetailResponse.builder()
                .departmentId(departmentJpaEntity.getId())
                .departmentName(departmentJpaEntity.getDepartmentName())
                .build();
    }
}
