package moim_today.fake_class.moim;

import moim_today.application.moim.MoimService;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static moim_today.util.TestConstant.*;

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
        return MoimDetailResponse.builder()
                .title(TITLE.value())
                .contents(CONTENTS.value())
                .capacity(Integer.parseInt((CAPACITY.value())))
                .currentCount(Integer.parseInt(CURRENT_COUNT.value()))
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .views(Integer.parseInt(VIEWS.value()))
                .startDate(LocalDate.of(2024,3,1))
                .endDate(LocalDate.of(2024,6,30))
                .build();
    }

    @Override
    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {

    }
}
