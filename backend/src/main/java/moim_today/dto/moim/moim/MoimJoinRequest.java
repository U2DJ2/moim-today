package moim_today.dto.moim.moim;

import lombok.Builder;

@Builder
public record MoimJoinRequest(
    long moimId
) {
}
