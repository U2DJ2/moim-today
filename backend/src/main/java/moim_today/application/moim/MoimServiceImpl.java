package moim_today.application.moim;

import moim_today.dto.moim.PrivateMoimAppendRequest;
import moim_today.dto.moim.PublicMoimAppendRequest;
import moim_today.dto.moim.UploadMoimImageResponse;
import moim_today.implement.file.FileUploader;
import moim_today.implement.moim.MoimAppender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static moim_today.global.constant.FileTypeConstant.MOIM_IMAGE;

@Service
public class MoimServiceImpl implements MoimService{

    private final MoimAppender moimAppender;
    private final FileUploader fileUploader;

    public MoimServiceImpl(final MoimAppender moimAppender,
                           final FileUploader fileUploader) {
        this.moimAppender = moimAppender;
        this.fileUploader = fileUploader;
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
}
