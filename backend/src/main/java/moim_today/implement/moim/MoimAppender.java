package moim_today.implement.moim;

import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.moim.moim.MoimJpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimAppender {

    private final MoimJpaRepository moimJpaRepository;

    public MoimAppender(final MoimJpaRepository moimJpaRepository) {
        this.moimJpaRepository = moimJpaRepository;
    }

    @Transactional
    public void createPublicMoim(final long memberId, final long universityId,
                                 final PublicMoimAppendRequest publicMoimAppendRequest) {

    }

    @Transactional
    public void createPrivateMoim(final long memberId, final long universityId,
                                  final PrivateMoimAppendRequest privateMoimAppendRequest) {

    }
}
