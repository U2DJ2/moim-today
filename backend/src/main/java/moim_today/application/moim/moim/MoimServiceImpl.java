package moim_today.application.moim.moim;

import jakarta.servlet.http.HttpServletResponse;
import moim_today.domain.moim.MoimSortedFilter;
import moim_today.dto.moim.moim.*;
import moim_today.dto.moim.moim.enums.MoimCategoryDto;
import moim_today.implement.file.FileUploader;
import moim_today.implement.meeting.joined_meeting.JoinedMeetingRemover;
import moim_today.implement.meeting.meeting.MeetingFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimAppender;
import moim_today.implement.moim.joined_moim.JoinedMoimFinder;
import moim_today.implement.moim.joined_moim.JoinedMoimRemover;
import moim_today.implement.moim.moim.*;
import moim_today.implement.schedule.schedule.ScheduleRemover;
import moim_today.implement.todo.TodoRemover;
import moim_today.persistence.entity.moim.joined_moim.JoinedMoimJpaEntity;
import moim_today.persistence.entity.moim.moim.MoimJpaEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

import static moim_today.global.constant.FileTypeConstant.MOIM_IMAGE;

@Service
public class MoimServiceImpl implements MoimService{

    private final MoimAppender moimAppender;
    private final FileUploader fileUploader;
    private final MoimFinder moimFinder;
    private final MoimUpdater moimUpdater;
    private final MoimRemover moimRemover;
    private final JoinedMoimAppender joinedMoimAppender;
    private final JoinedMoimFinder joinedMoimFinder;
    private final MeetingFinder meetingFinder;
    private final JoinedMeetingRemover joinedMeetingRemover;
    private final TodoRemover todoRemover;
    private final JoinedMoimRemover joinedMoimRemover;
    private final ScheduleRemover scheduleRemover;
    private final MoimManager moimManager;

    public MoimServiceImpl(final MoimAppender moimAppender,
                           final FileUploader fileUploader,
                           final MoimFinder moimFinder,
                           final MoimUpdater moimUpdater,
                           final MoimRemover moimRemover,
                           final JoinedMoimAppender joinedMoimAppender,
                           final JoinedMoimFinder joinedMoimFinder,
                           final MeetingFinder meetingFinder,
                           final JoinedMeetingRemover joinedMeetingRemover,
                           final TodoRemover todoRemover,
                           final JoinedMoimRemover joinedMoimRemover,
                           final ScheduleRemover scheduleRemover,
                           final MoimManager moimManager) {
        this.moimAppender = moimAppender;
        this.fileUploader = fileUploader;
        this.moimFinder = moimFinder;
        this.moimUpdater = moimUpdater;
        this.moimRemover = moimRemover;
        this.joinedMoimAppender = joinedMoimAppender;
        this.joinedMoimFinder = joinedMoimFinder;
        this.meetingFinder = meetingFinder;
        this.joinedMeetingRemover = joinedMeetingRemover;
        this.todoRemover = todoRemover;
        this.joinedMoimRemover = joinedMoimRemover;
        this.scheduleRemover = scheduleRemover;
        this.moimManager = moimManager;
    }

    @Override
    public List<MyMoimResponse> findAllMyJoinedMoimResponse(final long memberId) {
        return moimFinder.findAllMyMoimResponse(memberId);
    }

    @Transactional
    @Override
    public MoimIdResponse createMoim(final long memberId, final long universityId,
                           final MoimCreateRequest moimCreateRequest) {
        MoimJpaEntity moim = moimAppender.createMoim(memberId, universityId, moimCreateRequest);
        joinedMoimAppender.createJoinedMoim(memberId, moim.getId());
        return MoimIdResponse.from(moim.getId());
    }

    @Override
    public MoimImageResponse uploadMoimImage(final MultipartFile file) {
        String imageUrl = fileUploader.uploadFile(MOIM_IMAGE.value(), file);
        return MoimImageResponse.from(imageUrl);
    }

    @Override
    public MoimDetailResponse getMoimDetail(final long moimId,
                                            final String viewedMoimsCookieByUrlEncoded,
                                            final HttpServletResponse response) {
        MoimJpaEntity moimJpaEntity =  moimFinder.getById(moimId);
        moimUpdater.updateMoimViews(moimId, viewedMoimsCookieByUrlEncoded, response);
        return MoimDetailResponse.from(moimJpaEntity);
    }

    @Override
    public void updateMoim(final long memberId, final MoimUpdateRequest moimUpdateRequest) {
        moimUpdater.updateMoim(memberId, moimUpdateRequest);
    }

    @Transactional
    @Override
    public void deleteMoim(final long memberId, final long moimId) {
        MoimJpaEntity moimJpaEntity =  moimFinder.getById(moimId);
        moimJpaEntity.validateHostMember(memberId);

        joinedMoimRemover.deleteAllByMoimId(moimId);
        todoRemover.deleteAllByMoimId(moimId);
        moimRemover.deleteById(moimId);

        List<Long> meetingIds = meetingFinder.findMeetingIdsByMoimId(moimId);

        joinedMeetingRemover.deleteAllByMeetingIdIn(meetingIds);
        scheduleRemover.deleteAllByMeetingIdIn(meetingIds);
    }

    @Override
    public MoimMemberTabResponse findMoimMembers(final long memberId, final long moimId) {
        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimId);
        long moimHostId = moimJpaEntity.getMemberId();
        boolean isHostRequest = moimFinder.isHost(memberId, moimId);

        List<JoinedMoimJpaEntity> joinedMoimJpaEntities = joinedMoimFinder.findByMoimId(moimId);
        List<Long> moimMemberIds = JoinedMoimJpaEntity.extractMemberIds(joinedMoimJpaEntities);
        List<MoimMemberResponse> moimMemberResponses = moimFinder.findMembersInMoim(moimMemberIds, moimHostId, moimId);

        return MoimMemberTabResponse.of(isHostRequest, moimMemberResponses);
    }

    @Override
    public void kickMember(final long requestMemberId, final MoimMemberKickRequest moimMemberKickRequest) {
        long moimId = moimMemberKickRequest.moimId();
        long deleteMemberId = moimMemberKickRequest.deleteMemberId();

        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimId);
        moimJpaEntity.validateHostMember(requestMemberId);
        moimJpaEntity.validateNotHostMember(moimMemberKickRequest.deleteMemberId());

        moimManager.deleteMemberFromMoim(deleteMemberId, moimId);
    }

    @Override
    public void deleteMember(final long deleteMemberId, final MoimMemberDeleteRequest moimMemberDeleteRequest) {
        long moimId = moimMemberDeleteRequest.moimId();
        MoimJpaEntity moimJpaEntity = moimFinder.getById(moimId);

        moimJpaEntity.validateNotHostMember(deleteMemberId);
        moimManager.deleteMemberFromMoim(deleteMemberId, moimId);
    }

    @Override
    public void appendMemberToMoim(final long requestMemberId, final MoimJoinRequest moimJoinRequest) {
        long enterMoimId = moimJoinRequest.moimId();
        moimManager.appendMemberToMoim(requestMemberId, enterMoimId);
    }

    @Override
    public List<MoimSimpleResponse> findAllMoimResponses(
            final long universityId,
            final MoimCategoryDto moimCategoryDto,
            final MoimSortedFilter moimSortedFilter) {

        return moimFinder.findAllMoimResponses(universityId, moimCategoryDto, moimSortedFilter);
    }

    @Override
    public List<MoimSimpleResponse> searchMoim(final long universityId, final String searchParam) {
        return moimFinder.searchMoim(universityId, searchParam);
    }

    @Override
    public List<MoimSimpleResponse> findAllMyMoimSimpleResponses(final long hostMemberId,
                                                                 final long lastMoimId,
                                                                 final Boolean ended) {
        return moimManager.findAllMyMoimSimpleResponses(hostMemberId, lastMoimId, LocalDate.now(), ended);
    }

    @Override
    public List<MoimSimpleResponse> findAllMyJoinedMoimSimpleResponses(final long memberId,
                                                                       final long lastMoimId,
                                                                       final boolean ended) {
        return moimManager.findAllMyJoinedMoimSimpleResponses(memberId, lastMoimId, LocalDate.now(), ended);
    }
}
