package moim_today.dto.member;

import lombok.Builder;

@Builder
public record MemberHostResponse(
        boolean isHost
) {

    public static MemberHostResponse from(final boolean isHost) {
        return MemberHostResponse.builder()
                .isHost(isHost)
                .build();
    }
}
