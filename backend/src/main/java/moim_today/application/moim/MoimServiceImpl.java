package moim_today.application.moim;

import moim_today.dto.moim.MoimAppendRequest;
import moim_today.dto.moim.MoimDetailResponse;
import moim_today.dto.moim.MoimUpdateRequest;
import moim_today.dto.moim.MoimImageResponse;
import moim_today.implement.file.FileUploader;
import moim_today.implement.moim.joined_moim.JoinedMoimAppender;
import moim_today.implement.moim.moim.MoimAppender;
import moim_today.implement.moim.moim.MoimFinder;
import moim_today.implement.moim.moim.MoimRemover;
import moim_today.implement.moim.moim.MoimUpdater;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static moim_today.global.constant.FileTypeConstant.MOIM_IMAGE;

@Service
public class MoimServiceImpl implements MoimService{

    private final MoimAppender moimAppender;
    private final FileUploader fileUploader;
    private final MoimFinder moimFinder;
    private final MoimUpdater moimUpdater;
    private final MoimRemover moimRemover;
    private final JoinedMoimAppender joinedMoimAppender;

    public MoimServiceImpl(final MoimAppender moimAppender,
                           final FileUploader fileUploader,
                           final MoimFinder moimFinder,
                           final MoimUpdater moimUpdater,
                           final MoimRemover moimRemover,
                           final JoinedMoimAppender joinedMoimAppender) {
        this.moimAppender = moimAppender;
        this.fileUploader = fileUploader;
        this.moimFinder = moimFinder;
        this.moimUpdater = moimUpdater;
        this.moimRemover = moimRemover;
        this.joinedMoimAppender = joinedMoimAppender;
    }

    @Override
    public void createMoim(final long memberId, final long universityId,
                           final MoimAppendRequest moimAppendRequest) {
        MoimJpaEntity moim = moimAppender.createMoim(memberId, universityId, moimAppendRequest);
        joinedMoimAppender.createJoinedMoim(memberId, moim.getId());
    }

    @Override
    public MoimImageResponse uploadMoimImage(final MultipartFile file) {
        String imageUrl = fileUploader.uploadFile(MOIM_IMAGE.value(), file);
        return MoimImageResponse.from(imageUrl);
    }

    @Override
    public MoimDetailResponse getMoimDetail(final long moimId) {
        MoimJpaEntity moimJpaEntity =  moimFinder.getById(moimId);
        return MoimDetailResponse.from(moimJpaEntity);
    }

    @Override
    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {
        moimUpdater.updateMoim(memberId, moimUpdateRequest);
    }

    @Override
    public void deleteMoim(final long memberId, final long moimId) {
        moimRemover.deleteMoim(memberId, moimId);
    }
}
