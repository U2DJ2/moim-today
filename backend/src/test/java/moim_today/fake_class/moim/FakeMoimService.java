package moim_today.fake_class.moim;

import moim_today.application.moim.MoimService;
import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;

public class FakeMoimService implements MoimService {

    @Override
    public void createPublicMoim(final long memberId, final long universityId, final PublicMoimAppendRequest publicMoimAppendRequest) {

    }

    @Override
    public void createPrivateMoim(final long memberId, final long universityId, final PrivateMoimAppendRequest privateMoimAppendRequest) {

    }
}
