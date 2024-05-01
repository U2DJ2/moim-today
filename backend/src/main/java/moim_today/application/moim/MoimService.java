package moim_today.application.moim;

import moim_today.dto.moim.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MoimService {

    void createPublicMoim(final long memberId, final long universityId,
                          final PublicMoimAppendRequest publicMoimAppendRequest);

    void createPrivateMoim(final long memberId, final long universityId,
                           final PrivateMoimAppendRequest privateMoimAppendRequest);

    UploadMoimImageResponse uploadMoimImage(final MultipartFile file);

    MoimDetailResponse getMoimDetail(final long moimId);

    void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest);

    List<MoimMemberTabResponse> findMoimMembers(final long memberId, final long moimId);
}
