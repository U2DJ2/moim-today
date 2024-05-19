package moim_today.presentation.moim;

import jakarta.servlet.http.HttpServletResponse;
import moim_today.application.moim.moim.MoimService;
import moim_today.application.moim.moim_notice.MoimNoticeService;
import moim_today.domain.member.MemberSession;
import moim_today.domain.moim.MoimSortedFilter;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.*;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.dto.moim.moim_notice.*;
import moim_today.global.annotation.Login;
import moim_today.global.response.CollectionResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static moim_today.global.constant.MoimConstant.VIEWED_MOIM_COOKIE_NAME;

@RequestMapping("/api/moims")
@RestController
public class MoimController {

    private final MoimService moimService;
    private final MoimNoticeService moimNoticeService;

    public MoimController(final MoimService moimService,
                          final MoimNoticeService moimNoticeService) {
        this.moimService = moimService;
        this.moimNoticeService = moimNoticeService;
    }

    @GetMapping
    public CollectionResponse<List<MyMoimResponse>> findAllMyMoimResponse(@Login final MemberSession memberSession) {
        List<MyMoimResponse> myMoimResponses = moimService.findAllMyMoimResponse(memberSession.id());
        return CollectionResponse.from(myMoimResponses);
    }

    @PostMapping
    public MoimIdResponse createMoim(@Login final MemberSession memberSession,
                                     @RequestBody final MoimCreateRequest moimCreateRequest) {
        return moimService.createMoim(memberSession.id(), memberSession.universityId(), moimCreateRequest);
    }

    @PostMapping("/image")
    public MoimImageResponse uploadMoimImage(@RequestPart final MultipartFile file) {
        return moimService.uploadMoimImage(file);
    }

    @GetMapping("/detail/{moimId}")
    public MoimDetailResponse getMoimDetail(@PathVariable final long moimId,
                                            @CookieValue(value = VIEWED_MOIM_COOKIE_NAME, required = false) final String viewedMoimsCookieByUrlEncoded,
                                            final HttpServletResponse response) {
        return moimService.getMoimDetail(moimId, viewedMoimsCookieByUrlEncoded, response);
    }

    @GetMapping("/simple")
    public CollectionResponse<List<MoimSimpleResponse>> findAllMoimResponses(
            @Login final MemberSession memberSession,
            @RequestParam final MoimCategoryDto moimCategoryDto,
            @RequestParam final MoimSortedFilter moimSortedFilter) {
        return CollectionResponse.from(moimService.findAllMoimResponse(memberSession.universityId(), moimCategoryDto, moimSortedFilter));
    }

    @PatchMapping
    public void updateMoim(@Login final MemberSession memberSession,
                           @RequestBody final MoimUpdateRequest moimUpdateRequest) {
        moimService.updateMoim(memberSession.id(), moimUpdateRequest);
    }

    @GetMapping("/members/{moimId}")
    public MoimMemberTabResponse findMoimMembers(@Login final MemberSession memberSession,
                                                 @PathVariable final long moimId) {
        return moimService.findMoimMembers(memberSession.id(), moimId);
    }

    @DeleteMapping
    public void deleteMoim(@Login final MemberSession memberSession,
                           @RequestBody final MoimDeleteRequest moimDeleteRequest) {
        moimService.deleteMoim(memberSession.id(), moimDeleteRequest.moimId());
    }

    @DeleteMapping("/members/kick")
    public void forceDeleteMember(@Login final MemberSession memberSession,
                                  @RequestBody final MoimMemberKickRequest moimMemberKickRequest) {
        moimService.kickMember(memberSession.id(), moimMemberKickRequest);
    }

    @PostMapping("/notices")
    public void createMoimNotice(@Login final MemberSession memberSession,
                                 @RequestBody final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeService.createMoimNotice(memberSession.id(), moimNoticeCreateRequest);
    }

    @DeleteMapping("/members")
    public void deleteMember(@Login final MemberSession memberSession,
                             @RequestBody final MoimMemberDeleteRequest moimMemberDeleteRequest) {
        moimService.deleteMember(memberSession.id(), moimMemberDeleteRequest);
    }

    @PostMapping("/members")
    public void joinMoim(@Login final MemberSession memberSession,
                         @RequestBody final MoimJoinRequest moimJoinRequest) {
        moimService.appendMemberToMoim(memberSession.id(), moimJoinRequest);
    }

    @GetMapping("/notices/simple")
    public CollectionResponse<List<MoimNoticeSimpleResponse>> findAllMoimNotice(@Login final MemberSession memberSession,
                                                                                @RequestParam final long moimId) {
        return CollectionResponse.from(moimNoticeService.findAllMoimNotice(memberSession.id(), moimId));
    }

    @GetMapping("/notices/detail")
    public MoimNoticeDetailResponse getMoimNoticeDetail(@Login final MemberSession memberSession,
                                                        @RequestParam final long moimNoticeId) {
        return moimNoticeService.getMoimNoticeDetail(memberSession.id(), moimNoticeId);
    }

    @PatchMapping("/notices")
    public void updateMoimNotice(@Login final MemberSession memberSession,
                                 @RequestBody final MoimNoticeUpdateRequest moimNoticeUpdateRequest) {
        moimNoticeService.updateMoimNotice(memberSession.id(), moimNoticeUpdateRequest);
    }

    @DeleteMapping("/notices")
    public void deleteMoimNotice(@Login final MemberSession memberSession,
                                 @RequestBody final MoimNoticeDeleteRequest moimNoticeDeleteRequest) {
        moimNoticeService.deleteMoimNotice(memberSession.id(), moimNoticeDeleteRequest);
    }

    @GetMapping("/search")
    public CollectionResponse<List<MoimSimpleResponse>> searchMoim(
            @Login final MemberSession memberSession,
            @RequestParam final String searchParam) {
        return CollectionResponse.from(moimService.searchMoim(memberSession.universityId(), searchParam));
    }

    @GetMapping("/categories")
    public CollectionResponse<MoimCategory[]> getMoimCategories() {
        return CollectionResponse.from(MoimCategory.values());
    }
}
