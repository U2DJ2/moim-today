package moim_today.dto.moim;

import moim_today.domain.moim.MoimMember;

import java.util.List;

public record MoimMemberTabResponse(
        boolean isHostRequest,
        List<MoimMember> moimMembers
) {

}
