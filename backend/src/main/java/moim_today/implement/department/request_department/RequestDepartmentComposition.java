package moim_today.implement.department.request_department;

import moim_today.dto.department.AddDepartmentRequest;
import moim_today.dto.department.RequestDepartmentResponse;
import moim_today.global.annotation.Implement;

import java.util.List;

@Implement
public class RequestDepartmentComposition {

    private final RequestDepartmentAppender requestDepartmentAppender;
    private final RequestDepartmentFinder requestDepartmentFinder;
    private final RequestDepartmentRemover requestDepartmentRemover;

    public RequestDepartmentComposition(final RequestDepartmentAppender requestDepartmentAppender,
                                        final RequestDepartmentFinder requestDepartmentFinder,
                                        final RequestDepartmentRemover requestDepartmentRemover) {
        this.requestDepartmentAppender = requestDepartmentAppender;
        this.requestDepartmentFinder = requestDepartmentFinder;
        this.requestDepartmentRemover = requestDepartmentRemover;
    }

    public void addDepartment(final AddDepartmentRequest addDepartmentRequest) {
        requestDepartmentAppender.addDepartment(addDepartmentRequest);
    }

    public List<RequestDepartmentResponse> findAllByUniversityId(final long universityId) {
        return requestDepartmentFinder.findAllByUniversityId(universityId);
    }

    public void deleteById(final long requestDepartmentId) {
        requestDepartmentRemover.deleteById(requestDepartmentId);
    }
}
