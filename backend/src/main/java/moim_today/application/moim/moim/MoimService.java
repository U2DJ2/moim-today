package moim_today.application.moim.moim;

import moim_today.dto.moim.moim.*;
import moim_today.dto.moim.moim.filter.MoimFilterRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MoimService {

    void createMoim(final long memberId, final long universityId,
                    final MoimCreateRequest moimCreateRequest);

    MoimImageResponse uploadMoimImage(final MultipartFile file);

    MoimDetailResponse getMoimDetail(final long moimId);

    void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest);

    void deleteMoim(final long memberId, final long moimId);

    MoimMemberTabResponse findMoimMembers(final long memberId, final long moimId);

    void kickMember(final long requestMemberId, final MoimMemberKickRequest moimMemberKickRequest);

    void deleteMember(final long memberId, final MoimMemberDeleteRequest moimMemberDeleteRequest);

    void appendMemberToMoim(final long requestMemberId, final MoimJoinRequest moimJoinRequest);

    List<MoimSimpleResponse> findAllMoimResponse(final MoimFilterRequest moimFilterRequest);
}
