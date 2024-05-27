package moim_today.persistence.entity.department;

import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Table(name = "department")
@Entity
public class DepartmentJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private long id;

    @Association
    private long universityId;

    private String departmentName;

    protected DepartmentJpaEntity() {
    }

    @Builder
    private DepartmentJpaEntity(final long universityId, final String departmentName) {
        this.universityId = universityId;
        this.departmentName = departmentName;
    }

    public static DepartmentJpaEntity toEntity(final long universityId, final String departmentName) {
        return DepartmentJpaEntity.builder()
                .universityId(universityId)
                .departmentName(departmentName)
                .build();
    }
}
