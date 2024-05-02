package moim_today.dto.moim;

import lombok.Builder;

import java.util.List;

@Builder
public record MoimMemberTabResponse(
        boolean isHostRequest,
        List<MoimMemberResponse> moimMembers
) {

    public static MoimMemberTabResponse of(final boolean isHostRequest, final List<MoimMemberResponse> moimMembers) {
        return MoimMemberTabResponse.builder()
                .isHostRequest(isHostRequest)
                .moimMembers(moimMembers)
                .build();
    }
}
