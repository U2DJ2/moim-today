package moim_today.application.moim;

import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;

public interface MoimService {

    void createPublicMoim(final long memberId, final long universityId,
                          final PublicMoimAppendRequest publicMoimAppendRequest);

    void createPrivateMoim(final long memberId, final long universityId,
                           final PrivateMoimAppendRequest privateMoimAppendRequest);
}
