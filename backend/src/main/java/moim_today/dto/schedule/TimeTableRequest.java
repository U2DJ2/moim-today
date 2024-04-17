package moim_today.dto.schedule;

import java.time.LocalDate;

public record TimeTableRequest(
        String everytimeId,
        LocalDate startDate,
        LocalDate endDate
) {
}
