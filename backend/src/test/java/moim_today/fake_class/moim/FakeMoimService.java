package moim_today.fake_class.moim;

import moim_today.application.moim.moim.MoimService;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.*;
import moim_today.global.error.ForbiddenException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.MoimExceptionConstant.MOIM_FORBIDDEN;
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
                .moimId(moimId)
                .title(MOIM_TITLE.value())
                .contents(MOIM_CONTENTS.value())
                .capacity(Integer.parseInt((CAPACITY.value())))
                .currentCount(Integer.parseInt(CURRENT_COUNT.value()))
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .views(Integer.parseInt(VIEWS.value()))
                .startDate(LocalDate.of(2024, 3, 1))
                .endDate(LocalDate.of(2024, 6, 30))
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
        MoimMemberResponse m1 = MoimMemberResponse.builder()
                .isHost(false)
                .memberId(MEMBER_ID.longValue())
                .memberName("kim")
                .joinedDate(LocalDateTime.of(2024, 3, 5, 15, 30, 45))
                .build();
        MoimMemberResponse m2 = MoimMemberResponse.builder()
                .isHost(false)
                .memberId(MEMBER_ID.longValue() + 1L)
                .memberName("yang")
                .joinedDate(LocalDateTime.of(2024, 4, 5, 15, 30, 45))
                .build();
        MoimMemberResponse m3 = MoimMemberResponse.builder()
                .isHost(true)
                .memberId(MEMBER_ID.longValue() + 2L)
                .memberName("jung")
                .joinedDate(LocalDateTime.of(2024, 5, 5, 15, 30, 45))
                .build();

        List<MoimMemberResponse> moimMemberResponses = List.of(m1,m2,m3);

        return MoimMemberTabResponse.builder()
                .isHostRequest(true)
                .moimMembers(moimMemberResponses)
                .build();
    }

    @Override
    public void deleteMember(long id, MoimMemberDeleteRequest moimMemberDeleteRequest) {
        long moimHostId = MEMBER_ID.longValue();
        // 실제 로직과 다름
        if (moimMemberDeleteRequest.memberId() != moimHostId) {
            throw new ForbiddenException(MOIM_FORBIDDEN.message());
        }
    }
}
