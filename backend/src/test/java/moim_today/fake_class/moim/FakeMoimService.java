package moim_today.fake_class.moim;

import moim_today.application.moim.MoimService;
import moim_today.dto.moim.MoimDetailResponse;
import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.dto.moim.UploadMoimImageResponse;
import org.springframework.web.multipart.MultipartFile;

import static moim_today.util.TestConstant.MOIM_IMAGE_URL;

public class FakeMoimService implements MoimService {

    @Override
    public void createPublicMoim(final long memberId, final long universityId, final PublicMoimAppendRequest publicMoimAppendRequest) {

    }

    @Override
    public void createPrivateMoim(final long memberId, final long universityId, final PrivateMoimAppendRequest privateMoimAppendRequest) {

    }

    @Override
    public UploadMoimImageResponse uploadMoimImage(final MultipartFile file) {
        return UploadMoimImageResponse.from(MOIM_IMAGE_URL.value());
    }

    @Override
    public MoimDetailResponse getMoimDetail(final long moimId) {
        return null;
    }
}
