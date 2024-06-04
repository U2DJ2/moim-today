package moim_today.implement.moim.moim;

import moim_today.global.annotation.Implement;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.transaction.annotation.Transactional;

@Implement
public class MoimRemover {

    private final MoimRepository moimRepository;

    public MoimRemover(final MoimRepository moimRepository) {
        this.moimRepository = moimRepository;
    }

    @Transactional
    public void deleteById(final long moimId) {
        moimRepository.deleteById(moimId);
    }
}
