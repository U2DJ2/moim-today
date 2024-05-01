package moim_today.dto.moim;

import java.util.List;

public record MoimMemberTabResponse(
        boolean isHostRequest,
        List<MoimMemberResponse> moimMembers
) {

}
