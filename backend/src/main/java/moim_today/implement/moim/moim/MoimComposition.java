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

    public List<MyMoimResponse> findAllMyMoimResponse(final long memberId) {
        return moimFinder.findAllMyMoimResponse(memberId);
    }

    public MoimJpaEntity getById(final long moimId) {
        return moimFinder.getById(moimId);
    }

    public MoimJpaEntity getByIdWithPessimisticLock(final long moimId) {
        return moimFinder.getByIdWithPessimisticLock(moimId);
    }

    public String getTitleById(final long moimId) {
        return moimFinder.getTitleById(moimId);
    }

    public MoimDateResponse findMoimDate(final long moimId) {
        return moimFinder.findMoimDate(moimId);
    }

    public List<MoimSimpleResponse> findAllMoimResponses(final long universityId, final MoimCategoryDto moimCategoryDto, final MoimSortedFilter moimSortedFilter) {
        return moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);
    }

    public boolean isHost(final long memberId, final long moimId) {
        return moimFinder.isHost(memberId, moimId);
    }

    public List<MoimSimpleResponse> searchMoim(final long universityId, final String searchParam) {
        return moimFinder.searchMoim(universityId, searchParam);
    }

    public List<MoimSimpleResponse> findEndedMoimSimpleResponsesByMoimIds(final List<Long> moimIds, final LocalDate now) {
        return moimFinder.findEndedMoimSimpleResponsesByMoimIds(moimIds, now);
    }

    public List<MoimSimpleResponse> findInProgressMoimSimpleResponsesByMoimIds(final List<Long> moimIds, final LocalDate now) {
        return moimFinder.findInProgressMoimSimpleResponsesByMoimIds(moimIds, now);
    }

    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {
        moimUpdater.updateMoim(memberId, moimUpdateRequest);
    }

    public void updateMoimViews(final long moimId, final String viewedMoimsCookieByUrlEncoded, final HttpServletResponse response) {
        moimUpdater.updateMoimViews(moimId, viewedMoimsCookieByUrlEncoded, response);
    }

    public void updateMoimCurrentCount(final long moimId, final int value) {
        moimUpdater.updateMoimCurrentCount(moimId, value);
    }

    public void deleteById(final long moimId) {
        moimRemover.deleteById(moimId);
    }
}
