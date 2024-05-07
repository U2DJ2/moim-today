package moim_today.implement.moim.moim;

import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimUpdater {

    private final MoimRepository moimRepository;

    public MoimUpdater(final MoimRepository moimRepository) {
        this.moimRepository = moimRepository;
    }

    @Transactional
    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {
        MoimJpaEntity moimJpaEntity = moimRepository.getById(moimUpdateRequest.moimId());
        moimJpaEntity.validateMember(memberId);
        moimJpaEntity.updateMoim(moimUpdateRequest);
    }
}
