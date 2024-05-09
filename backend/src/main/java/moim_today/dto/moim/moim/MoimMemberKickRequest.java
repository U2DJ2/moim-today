package moim_today.dto.moim.moim;

import lombok.Builder;

@Builder
public record MoimMemberKickRequest(
        long moimId,
        long deleteMemberId
){
}
