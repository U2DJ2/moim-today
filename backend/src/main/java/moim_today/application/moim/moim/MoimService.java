package moim_today.application.moim.moim;

import jakarta.servlet.http.HttpServletResponse;
import moim_today.domain.moim.MoimSortedFilter;
import moim_today.domain.moim.enums.MoimCategory;
import moim_today.dto.moim.moim.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MoimService {

    MoimIdResponse createMoim(final long memberId, final long universityId,
                    final MoimCreateRequest moimCreateRequest);

    MoimImageResponse uploadMoimImage(final MultipartFile file);

    MoimDetailResponse getMoimDetail(final long moimId, final String viewedMoimsCookieByUrlEncoded, final HttpServletResponse response);

    void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest);

    void deleteMoim(final long memberId, final long moimId);

    MoimMemberTabResponse findMoimMembers(final long memberId, final long moimId);

    void kickMember(final long requestMemberId, final MoimMemberKickRequest moimMemberKickRequest);

    void deleteMember(final long memberId, final MoimMemberDeleteRequest moimMemberDeleteRequest);

    void appendMemberToMoim(final long requestMemberId, final MoimJoinRequest moimJoinRequest);

    List<MoimSimpleResponse> findAllMoimResponse(final MoimCategory moimCategory, final MoimSortedFilter moimSortedFilter);

    List<MoimSimpleResponse> searchMoim(final String searchParam);
}
