package moim_today.application.moim.moim;

import moim_today.dto.moim.moim.MoimAppendRequest;
import moim_today.dto.moim.moim.MoimDetailResponse;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.dto.moim.moim.MoimImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MoimService {

    void createMoim(final long memberId, final long universityId,
                    final MoimAppendRequest moimAppendRequest);

    MoimImageResponse uploadMoimImage(final MultipartFile file);

    MoimDetailResponse getMoimDetail(final long moimId);

    void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest);

    void deleteMoim(final long memberId, final long moimId);
}
