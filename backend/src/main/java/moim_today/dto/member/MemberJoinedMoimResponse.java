package moim_today.dto.member;

import lombok.Builder;

@Builder
public record MemberJoinedMoimResponse(
        boolean isJoined
) {

    public static MemberJoinedMoimResponse from(final boolean isJoined) {
        return MemberJoinedMoimResponse.builder()
                .isJoined(isJoined)
                .build();
    }
}
