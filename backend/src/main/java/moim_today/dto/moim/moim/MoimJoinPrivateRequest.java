package moim_today.dto.moim.moim;

import lombok.Builder;

@Builder
public record MoimJoinPrivateRequest(
        long moimId,
        String password
) {
}
