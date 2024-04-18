package moim_today.application.moim;

import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.implement.moim.MoimAppender;
import org.springframework.stereotype.Service;

@Service
public class MoimService {

    private final MoimAppender moimAppender;

    public MoimService(final MoimAppender moimAppender) {
        this.moimAppender = moimAppender;
    }

    public void createPublicMoim(final long memberId, final long universityId,
                                 final PublicMoimAppendRequest publicMoimAppendRequest) {
        moimAppender.createPublicMoim(memberId, universityId, publicMoimAppendRequest);
    }

    public void createPrivateMoim(final long memberId, final long universityId,
                                  final PrivateMoimAppendRequest privateMoimAppendRequest) {
        moimAppender.createPrivateMoim(memberId, universityId, privateMoimAppendRequest);
    }
}
