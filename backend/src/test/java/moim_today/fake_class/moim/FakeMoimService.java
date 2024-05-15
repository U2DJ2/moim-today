package moim_today.fake_class.moim;

import jakarta.servlet.http.HttpServletResponse;
import moim_today.application.moim.moim.MoimService;
import moim_today.domain.moim.DisplayStatus;
import moim_today.domain.moim.MoimSortedFilter;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.*;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.global.error.BadRequestException;
import moim_today.global.error.ForbiddenException;
import moim_today.global.error.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static moim_today.global.constant.exception.JoinedMoimExceptionConstant.MEMBER_ALREADY_JOINED;
import static moim_today.global.constant.exception.MoimExceptionConstant.*;
import static moim_today.util.TestConstant.*;

public class FakeMoimService implements MoimService {

    @Override
    public MoimIdResponse createMoim(final long memberId, final long universityId, final MoimCreateRequest moimCreateRequest) {
        return MoimIdResponse.from(MOIM_ID.longValue());
    }

    @Override
    public MoimImageResponse uploadMoimImage(final MultipartFile file) {
        return MoimImageResponse.from(MOIM_IMAGE_URL.value());
    }

    @Override
    public MoimDetailResponse getMoimDetail(final long moimId,
                                            final String viewedMoimsCookieByUrlEncoded,
                                            final HttpServletResponse response) {
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
                .profileImageUrl(PROFILE_IMAGE_URL.value())
                .build();
        MoimMemberResponse m2 = MoimMemberResponse.builder()
                .isHost(false)
                .memberId(MEMBER_ID.longValue() + 1L)
                .memberName("yang")
                .joinedDate(LocalDateTime.of(2024, 4, 5, 15, 30, 45))
                .profileImageUrl(PROFILE_IMAGE_URL.value())
                .build();
        MoimMemberResponse m3 = MoimMemberResponse.builder()
                .isHost(true)
                .memberId(MEMBER_ID.longValue() + 2L)
                .memberName("jung")
                .joinedDate(LocalDateTime.of(2024, 5, 5, 15, 30, 45))
                .profileImageUrl(PROFILE_IMAGE_URL.value())
                .build();

        List<MoimMemberResponse> moimMemberResponses = List.of(m1,m2,m3);

        return MoimMemberTabResponse.builder()
                .isHostRequest(true)
                .moimMembers(moimMemberResponses)
                .build();
    }

    @Override
    public void kickMember(long id, MoimMemberKickRequest moimMemberKickRequest) {
        long moimHostId = MEMBER_ID.longValue();
        // 실제 로직과 다름
        if (moimMemberKickRequest.moimId() != moimHostId) {
            throw new ForbiddenException(MOIM_MEMBER_DELETE_AUTHORIZED_ERROR.message());
        }
        else if(moimMemberKickRequest.deleteMemberId() == moimHostId){
            throw new ForbiddenException(MOIM_HOST_ERROR.message());
        }
    }

    @Override
    public void deleteMember(final long requestMemberId, final MoimMemberDeleteRequest moimMemberDeleteRequest) {
        // 실제 로직과 다름
        if (requestMemberId == moimMemberDeleteRequest.moimId()) {
            throw new ForbiddenException(MOIM_HOST_ERROR.message());
        }
    }

    @Override
    public void appendMemberToMoim(final long requestMemberId, final MoimJoinRequest moimJoinRequest) {
        // MOIM_ID , 모임에 멤버 참여, 성공
        // MOIM_ID + 1 , 모임에 이미 참여한 멤버, 실패
        // MOIM_ID + 2 , 없는 모임, 실패
        // MOIM_ID + 3 , 모임 정원이 가득 참, 실패
        if(moimJoinRequest.moimId() == MOIM_ID.longValue() + 1L){
            throw new BadRequestException(MEMBER_ALREADY_JOINED.message());
        }
        else if(moimJoinRequest.moimId() == MOIM_ID.longValue() + 2L){
            throw new NotFoundException(MOIM_NOT_FOUND_ERROR.message());
        }
        else if(moimJoinRequest.moimId() == MOIM_ID.longValue() + 3L){
            throw new BadRequestException(MOIM_CAPACITY_ERROR.message());
        }
    }

    @Override
    public List<MoimSimpleResponse> findAllMoimResponse(final MoimCategoryDto moimCategoryDto, final MoimSortedFilter moimSortedFilter) {
        MoimSimpleResponse moimSimpleResponse1 = MoimSimpleResponse.builder()
                .moimId(1L)
                .title(MOIM_TITLE.value())
                .capacity(CAPACITY.intValue())
                .currentCount(CURRENT_COUNT.intValue())
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .build();

        MoimSimpleResponse moimSimpleResponse2 = MoimSimpleResponse.builder()
                .moimId(2L)
                .title(MOIM_TITLE.value())
                .capacity(CAPACITY.intValue())
                .currentCount(CURRENT_COUNT.intValue())
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .build();

        return List.of(moimSimpleResponse1, moimSimpleResponse2);
    }

    @Override
    public List<MoimSimpleResponse> searchMoim(final String searchParam) {
        MoimSimpleResponse moimSimpleResponse1 = MoimSimpleResponse.builder()
                .moimId(1L)
                .title(MOIM_TITLE.value())
                .capacity(CAPACITY.intValue())
                .currentCount(CURRENT_COUNT.intValue())
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .build();

        MoimSimpleResponse moimSimpleResponse2 = MoimSimpleResponse.builder()
                .moimId(2L)
                .title(MOIM_TITLE.value())
                .capacity(CAPACITY.intValue())
                .currentCount(CURRENT_COUNT.intValue())
                .imageUrl(MOIM_IMAGE_URL.value())
                .moimCategory(MoimCategory.STUDY)
                .displayStatus(DisplayStatus.PUBLIC)
                .build();

        return List.of(moimSimpleResponse1, moimSimpleResponse2);
    }
}
