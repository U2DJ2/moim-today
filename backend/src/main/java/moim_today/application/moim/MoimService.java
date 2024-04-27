package moim_today.application.moim;

import moim_today.dto.moim.MoimDetailResponse;
import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.dto.moim.UploadMoimImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MoimService {

    void createPublicMoim(final long memberId, final long universityId,
                          final PublicMoimAppendRequest publicMoimAppendRequest);

    void createPrivateMoim(final long memberId, final long universityId,
                           final PrivateMoimAppendRequest privateMoimAppendRequest);

    UploadMoimImageResponse uploadMoimImage(final MultipartFile file);

    MoimDetailResponse getMoimDetail(long moimId);
}
