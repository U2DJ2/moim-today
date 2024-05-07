package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimCreateRequest;
import moim_today.global.annotation.Implement;
import moim_today.implement.moim.joined_moim.JoinedMoimAppender;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimAppender {

    private final MoimRepository moimRepository;

    private final JoinedMoimAppender joinedMoimAppender;

    public MoimAppender(final MoimRepository moimRepository,
                        final JoinedMoimAppender joinedMoimAppender) {
        this.moimRepository = moimRepository;
        this.joinedMoimAppender = joinedMoimAppender;
    }

    @Transactional
    public MoimJpaEntity createMoim(final long memberId, final long universityId,
                                    final MoimCreateRequest moimCreateRequest) {
        MoimJpaEntity moimJpaEntity = moimCreateRequest.toEntity(memberId, universityId);
        return moimRepository.save(moimJpaEntity);
    }
}
