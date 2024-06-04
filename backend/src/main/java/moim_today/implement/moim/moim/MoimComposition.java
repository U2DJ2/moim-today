package moim_today.implement.moim.moim;

import jakarta.servlet.http.HttpServletResponse;
import moim_today.domain.moim.MoimSortedFilter;
import moim_today.dto.moim.moim.*;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.global.annotation.Implement;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;

import java.time.LocalDate;
import java.util.List;


@Implement
public class MoimComposition {

    private final MoimAppender moimAppender;
    private final MoimFinder moimFinder;
    private final MoimUpdater moimUpdater;
    private final MoimRemover moimRemover;

    public MoimComposition(final MoimAppender moimAppender,
                           final MoimFinder moimFinder,
                           final MoimUpdater moimUpdater,
                           final MoimRemover moimRemover) {
        this.moimAppender = moimAppender;
        this.moimFinder = moimFinder;
        this.moimUpdater = moimUpdater;
        this.moimRemover = moimRemover;
    }

    public MoimJpaEntity createMoim(final long memberId, final long universityId,
                                    final MoimCreateRequest moimCreateRequest) {
        return moimAppender.createMoim(memberId, universityId, moimCreateRequest);
    }

    public void appendMemberToMoim(final long requestMemberId, final long moimId, final LocalDate currentDate) {
        moimAppender.appendMemberToMoim(requestMemberId, moimId, currentDate);
    }

    public List<MyMoimResponse> findAllMyMoimResponse(final long memberId) {
        return moimFinder.findAllMyMoimResponse(memberId);
    }

    public MoimJpaEntity getById(final long moimId) {
        return moimFinder.getById(moimId);
    }

    public String getTitleById(final long moimId) {
        return moimFinder.getTitleById(moimId);
    }

    public List<MoimSimpleResponse> findAllMoimResponses(final long universityId, final MoimCategoryDto moimCategoryDto,
                                                         final MoimSortedFilter moimSortedFilter) {
        return moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);
    }

    public boolean isHost(final long memberId, final long moimId) {
        return moimFinder.isHost(memberId, moimId);
    }

    public List<MoimSimpleResponse> searchMoim(final long universityId, final String searchParam) {
        return moimFinder.searchMoim(universityId, searchParam);
    }

    public List<MoimSimpleResponse> findAllJoinedMoimSimpleResponseByEndStatus(final long memberId, final LocalDate now,
                                                                               final boolean ended) {
        return moimFinder.findAllJoinedMoimSimpleResponseByEndStatus(memberId, now, ended);
    }

    public List<MoimSimpleResponse> findAllHostMoimSimpleResponsesByEndStatus(final long hostMemberId, final LocalDate now,
                                                                              final boolean ended) {
        return moimFinder.findAllHostMoimSimpleResponsesByEndStatus(hostMemberId, now, ended);
    }

    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {
        moimUpdater.updateMoim(memberId, moimUpdateRequest);
    }

    public void updateMoimViews(final long moimId, final String viewedMoimsCookieByUrlEncoded,
                                final HttpServletResponse response) {
        moimUpdater.updateMoimViews(moimId, viewedMoimsCookieByUrlEncoded, response);
    }

    public void deleteById(final long moimId) {
        moimRemover.deleteById(moimId);
    }

    public void deleteMemberFromMoim(final long memberId, final long moimId) {
        moimRemover.deleteMemberFromMoim(memberId, moimId);
    }

    public void deleteMoim(final long memberId, final long moimId) {
        moimRemover.deleteMoim(memberId, moimId);
    }
}
