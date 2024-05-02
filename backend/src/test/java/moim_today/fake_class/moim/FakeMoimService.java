package moim_today.fake_class.moim;

import moim_today.application.moim.moim.MoimService;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.MoimAppendRequest;
import moim_today.dto.moim.moim.MoimDetailResponse;
import moim_today.dto.moim.moim.MoimUpdateRequest;
import moim_today.dto.moim.moim.MoimImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static moim_today.util.TestConstant.*;

public class FakeMoimService implements MoimService {

    @Override
    public void createMoim(final long memberId, final long universityId, final MoimAppendRequest moimAppendRequest) {

    }

    @Override
    public MoimImageResponse uploadMoimImage(final MultipartFile file) {
        return MoimImageResponse.from(MOIM_IMAGE_URL.value());
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

    @Override
    public void deleteMoim(final long memberId, final long moimId) {

    }

    @Override
    public MoimMemberTabResponse findMoimMembers(final long memberId, final long moimId) {
        return null;
    }
}
