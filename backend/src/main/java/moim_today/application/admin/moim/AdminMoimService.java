package moim_today.application.admin.moim;

import moim_today.dto.moim.moim.MoimSimpleResponse;
import moim_today.persistence.repository.moim.moim.MoimRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
