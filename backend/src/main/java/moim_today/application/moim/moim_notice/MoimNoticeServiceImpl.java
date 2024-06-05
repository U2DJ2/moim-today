package moim_today.application.moim.moim_notice;

import moim_today.dto.moim.moim_notice.*;
import moim_today.implement.moim.joined_moim.JoinedMoimComposition;
import moim_today.implement.moim.moim_notice.*;
import moim_today.persistence.entity.moim.moim_notice.MoimNoticeJpaEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoimNoticeServiceImpl implements MoimNoticeService{

    private final MoimNoticeComposition moimNoticeComposition;
    private final JoinedMoimComposition joinedMoimComposition;

    public MoimNoticeServiceImpl(final MoimNoticeComposition moimNoticeComposition,
                                 final JoinedMoimComposition joinedMoimComposition) {
        this.moimNoticeComposition = moimNoticeComposition;
        this.joinedMoimComposition = joinedMoimComposition;
    }

    @Override
    public void createMoimNotice(final long memberId, final MoimNoticeCreateRequest moimNoticeCreateRequest) {
        moimNoticeComposition.createMoimNotice(memberId, moimNoticeCreateRequest.moimId(), moimNoticeCreateRequest);
    }

    @Override
    public List<MoimNoticeSimpleResponse> findAllMoimNotice(final long memberId, final long moimId) {
        joinedMoimComposition.validateMemberInMoim(memberId, moimId);
        return moimNoticeComposition.findAllMoimNotice(moimId);
    }

    @Override
    public MoimNoticeDetailResponse getMoimNoticeDetail(final long memberId, final long moimNoticeId) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeComposition.getById(moimNoticeId);
        joinedMoimComposition.validateMemberInMoim(memberId, moimNoticeJpaEntity.getMoimId());
        return MoimNoticeDetailResponse.from(moimNoticeJpaEntity);
    }

    @Override
    public void updateMoimNotice(final long memberId, final MoimNoticeUpdateRequest moimNoticeUpdateRequest) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeComposition.getById(moimNoticeUpdateRequest.moimNoticeId());
        moimNoticeComposition.updateMoimNotice(memberId, moimNoticeJpaEntity.getMoimId(), moimNoticeUpdateRequest);
    }

    @Override
    public void deleteMoimNotice(final long memberId, final MoimNoticeDeleteRequest moimNoticeDeleteRequest) {
        MoimNoticeJpaEntity moimNoticeJpaEntity = moimNoticeComposition.getById(moimNoticeDeleteRequest.moimNoticeId());
        moimNoticeComposition.deleteMoimNotice(memberId, moimNoticeJpaEntity.getMoimId(), moimNoticeDeleteRequest.moimNoticeId());
    }
}
