package moim_today.dto.moim.moim;

import lombok.Builder;

@Builder
public record MoimMemberForceDeleteRequest(
        long moimId,
        long deleteMemberId
){
}
