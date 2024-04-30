package moim_today.implement.moim;

import moim_today.dto.moim.MoimAppendRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimAppender {

    private final MoimRepository moimRepository;

    public MoimAppender(final MoimRepository moimRepository) {
        this.moimRepository = moimRepository;
    }

    @Transactional
    public MoimJpaEntity createMoim(final long memberId, final long universityId,
                                    final MoimAppendRequest moimAppendRequest) {
        MoimJpaEntity moimJpaEntity = moimAppendRequest.toEntity(memberId, universityId);
        return moimRepository.save(moimJpaEntity);
    }
}
