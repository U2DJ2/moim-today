package moim_today.domain.moim;

import java.time.LocalDate;

public record MoimMember(
        boolean isHost,
        long memberId,
        String memberName,
        LocalDate joinedDate
) {
}
