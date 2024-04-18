package moim_today.implement.moim;

import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
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
    public void createPublicMoim(final long memberId, final long universityId,
                                 final PublicMoimAppendRequest publicMoimAppendRequest) {

        MoimJpaEntity moimJpaEntity = publicMoimAppendRequest.toEntity(memberId, universityId);
        moimRepository.save(moimJpaEntity);
    }

    @Transactional
    public void createPrivateMoim(final long memberId, final long universityId,
                                  final PrivateMoimAppendRequest privateMoimAppendRequest) {
        MoimJpaEntity moimJpaEntity = privateMoimAppendRequest.toEntity(memberId, universityId);
        moimRepository.save(moimJpaEntity);
    }
}
