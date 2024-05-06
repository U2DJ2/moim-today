package moim_today.application.moim.moim;

import moim_today.dto.moim.moim.*;
import org.springframework.web.multipart.MultipartFile;

public interface MoimService {

    void createMoim(final long memberId, final long universityId,
                    final MoimAppendRequest moimAppendRequest);

    MoimImageResponse uploadMoimImage(final MultipartFile file);

    MoimDetailResponse getMoimDetail(final long moimId);

    void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest);

    void deleteMoim(final long memberId, final long moimId);

    MoimMemberTabResponse findMoimMembers(final long memberId, final long moimId);

    void forceDeleteMember(final long requestMemberId, final MoimMemberForceDeleteRequest moimMemberForceDeleteRequest);

    void deleteMember(final long requestMemberId, final long moimId);

    void appendMemberToMoim(final long requestMemberId, final MoimJoinRequest moimJoinRequest);
}
