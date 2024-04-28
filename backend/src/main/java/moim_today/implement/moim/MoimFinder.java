package moim_today.implement.moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimFinder {

    private final MoimRepository moimRepository;

    public MoimFinder(final MoimRepository moimRepository) {
        this.moimRepository = moimRepository;
    }

    @Transactional(readOnly = true)
    public MoimJpaEntity getById(final long moimId) {
        return moimRepository.getById(moimId);
    }
}