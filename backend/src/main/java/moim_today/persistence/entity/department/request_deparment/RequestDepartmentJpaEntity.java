package moim_today.persistence.entity.department.request_deparment;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import moim_today.global.annotation.Association;
import moim_today.global.base_entity.BaseTimeEntity;

@Getter
@Table(name = "request_department")
@Entity
public class RequestDepartmentJpaEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_department_id")
    private long id;

    @Association
    private long universityId;

    private String requestDepartmentName;

    protected RequestDepartmentJpaEntity() {
    }

    @Builder
    private RequestDepartmentJpaEntity(final long universityId, final String requestDepartmentName) {
        this.universityId = universityId;
        this.requestDepartmentName = requestDepartmentName;
    }
}
