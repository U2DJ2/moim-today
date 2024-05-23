package moim_today.persistence.repository.department.request_department;

public class RequestDepartmentRepositoryImpl implements RequestDepartmentRepository {

    private final RequestDepartmentJpaRepository requestDepartmentJpaRepository;

    public RequestDepartmentRepositoryImpl(final RequestDepartmentJpaRepository requestDepartmentJpaRepository) {
        this.requestDepartmentJpaRepository = requestDepartmentJpaRepository;
    }
}
