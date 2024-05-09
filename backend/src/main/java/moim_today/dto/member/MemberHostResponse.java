package moim_today.dto.member;

import lombok.Builder;

@Builder
public record MemberHostResponse(
        boolean isHost
) {
}
