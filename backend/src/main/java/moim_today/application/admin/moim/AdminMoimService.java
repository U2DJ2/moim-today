package moim_today.application.admin.moim;

import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN_ERROR;

@Service
public class AdminMoimService {

    private final MoimRepository moimRepository;

    public AdminMoimService(final MoimRepository moimRepository) {
        this.moimRepository = moimRepository;
    }

    @Transactional(readOnly = true)
    public List<MoimSimpleResponse> findAllByUniversityId(final long universityId) {
        return moimRepository.findAllByUniversityId(universityId);
    }

    @Transactional
    public void deleteMoim(final long universityId, final long moimId) {
        MoimJpaEntity moimJpaEntity = moimRepository.getById(moimId);

        if (moimJpaEntity.getUniversityId() != universityId) {
            throw new ForbiddenException(MOIM_FORBIDDEN_ERROR.message());
        }

        moimRepository.deleteById(moimId);
    }
}
