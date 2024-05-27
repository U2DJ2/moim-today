package moim_today.dto.department;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;

@Builder
public record RequestDepartmentResponse(
        long requestDepartmentId,
        long universityId,
        String requestDepartmentName
) {

    @QueryProjection
    public RequestDepartmentResponse {
    }
}
