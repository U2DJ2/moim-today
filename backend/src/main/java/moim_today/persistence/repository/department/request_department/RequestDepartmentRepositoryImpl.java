package moim_today.persistence.repository.department.request_department;

import com.querydsl.jpa.impl.JPAQueryFactory;
import moim_today.dto.department.QRequestDepartmentResponse;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.persistence.entity.department.request_deparment.QRequestDepartmentJpaEntity;
import moim_today.persistence.entity.department.request_deparment.RequestDepartmentJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

import static moim_today.persistence.entity.department.request_deparment.QRequestDepartmentJpaEntity.*;

@Repository
public class RequestDepartmentRepositoryImpl implements RequestDepartmentRepository {

    private final RequestDepartmentJpaRepository requestDepartmentJpaRepository;
    private final JPAQueryFactory queryFactory;

    public RequestDepartmentRepositoryImpl(final RequestDepartmentJpaRepository requestDepartmentJpaRepository,
                                           final JPAQueryFactory queryFactory) {
        this.requestDepartmentJpaRepository = requestDepartmentJpaRepository;
        this.queryFactory = queryFactory;
    }

    @Override
    public List<RequestDepartmentResponse> findAll() {
        return queryFactory.select(
                        new QRequestDepartmentResponse(
                                requestDepartmentJpaEntity.id,
                                requestDepartmentJpaEntity.universityId,
                                requestDepartmentJpaEntity.requestDepartmentName
                        )
                )
                .from(requestDepartmentJpaEntity)
                .fetch();
    }

    @Override
    public void save(final RequestDepartmentJpaEntity requestDepartmentJpaEntity) {
        requestDepartmentJpaRepository.save(requestDepartmentJpaEntity);
    }

    @Override
    public long count() {
        return requestDepartmentJpaRepository.count();
    }
}
