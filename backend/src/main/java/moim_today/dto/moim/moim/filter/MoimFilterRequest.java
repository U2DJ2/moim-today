package moim_today.dto.moim.moim.filter;

import lombok.Builder;
import moim_today.domain.moim.enums.MoimCategory;

@Builder
public record MoimFilterRequest(
        MoimCategory moimCategory,
        MoimSortedFilter moimSortedFilter
) {
}
