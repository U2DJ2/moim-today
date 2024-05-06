package moim_today.dto.moim.moim;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDate;

public record MoimDateResponse(
        LocalDate startDate,
        LocalDate endDate
) {

    @QueryProjection
    public MoimDateResponse {
    }
}
