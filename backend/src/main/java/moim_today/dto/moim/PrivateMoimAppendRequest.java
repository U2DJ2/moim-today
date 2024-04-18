package moim_today.dto.moim;

import moim_today.domain.moim.enums.MoimCategory;

import java.time.LocalDate;

public record PrivateMoimAppendRequest(
        String title,
        String contents,
        int capacity,
        String password,
        MoimCategory moimCategory,
        LocalDate startDate,
        LocalDate endDate
) {
}
