package moim_today.global.spring_event.event_listener;

import moim_today.global.spring_event.event.AdminMemberDeleteEvent;
import moim_today.implement.moim.joined_moim.JoinedMoimComposition;
import moim_today.implement.moim.moim.MoimComposition;
import moim_today.implement.schedule.schedule.ScheduleComposition;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@Component
public class AdminMemberEventListener {

    private final MoimComposition moimComposition;
    private final JoinedMoimComposition joinedMoimComposition;
    private final ScheduleComposition scheduleComposition;

    public AdminMemberEventListener(final MoimComposition moimComposition,
                                    final JoinedMoimComposition joinedMoimComposition,
                                    final ScheduleComposition scheduleComposition) {
        this.moimComposition = moimComposition;
        this.joinedMoimComposition = joinedMoimComposition;
        this.scheduleComposition = scheduleComposition;
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void memberDeleteFromMoim(final AdminMemberDeleteEvent adminMemberDeleteEvent){
        long memberId = adminMemberDeleteEvent.memberId();
        List<Long> joinedMoimIds = joinedMoimComposition.findMoimIdsByMemberId(memberId);
        joinedMoimIds.forEach(moimId -> moimComposition.deleteMemberFromMoim(memberId, moimId));
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void deleteMemberSchedules(final AdminMemberDeleteEvent adminMemberDeleteEvent){
        long memberId = adminMemberDeleteEvent.memberId();
        scheduleComposition.deleteAllByMemberId(memberId);
    }
}
