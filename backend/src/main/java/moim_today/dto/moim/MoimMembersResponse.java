package moim_today.dto.moim;

import java.time.LocalDate;

public record MoimMembersResponse(
        long memberId,
        String memberName,
        LocalDate joinedDate,
        boolean isAuthorizedForForceExit
) {

}
