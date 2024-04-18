package moim_today.dto.moim;

import moim_today.domain.moim.enums.MoimCategory;

import java.time.LocalDate;

public record PublicMoimAppendRequest(
        String title,
        String contents,
        int capacity,
        MoimCategory moimCategory,
        LocalDate startDate,
        LocalDate endDate
) {
}
