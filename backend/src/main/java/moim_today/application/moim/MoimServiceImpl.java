package moim_today.application.moim;

import moim_today.dto.moim.*;
import moim_today.implement.file.FileUploader;
import moim_today.implement.moim.MoimAppender;
import moim_today.implement.moim.MoimFinder;
import moim_today.implement.moim.MoimUpdater;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static moim_today.global.constant.FileTypeConstant.MOIM_IMAGE;

@Service
public class MoimServiceImpl implements MoimService{

    private final MoimAppender moimAppender;
    private final FileUploader fileUploader;
    private final MoimFinder moimFinder;
    private final MoimUpdater moimUpdater;

    public MoimServiceImpl(MoimAppender moimAppender, FileUploader fileUploader, MoimFinder moimFinder, MoimUpdater moimUpdater) {
        this.moimAppender = moimAppender;
        this.fileUploader = fileUploader;
        this.moimFinder = moimFinder;
        this.moimUpdater = moimUpdater;
    }

    @Override
    public void createPublicMoim(final long memberId, final long universityId,
                                 final PublicMoimAppendRequest publicMoimAppendRequest) {
        moimAppender.createPublicMoim(memberId, universityId, publicMoimAppendRequest);
    }

    @Override
    public void createPrivateMoim(final long memberId, final long universityId,
                                  final PrivateMoimAppendRequest privateMoimAppendRequest) {
        moimAppender.createPrivateMoim(memberId, universityId, privateMoimAppendRequest);
    }

    @Override
    public UploadMoimImageResponse uploadMoimImage(final MultipartFile file) {
        String imageUrl = fileUploader.uploadFile(MOIM_IMAGE.value(), file);
        return UploadMoimImageResponse.from(imageUrl);
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
    public List<MoimMemberTabResponse> findMoimMembers(final long memberId, final long moimId) {
        List<MemberJpaEntity> moimMember = moimFinder.findMoimMembers(moimId);
        return null;
    }
}
