package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.meeting.joined_meeting.JoinedMeetingRepository;
import moim_today.persistence.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;

@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final JoinedMeetingRepository joinedMeetingRepository;

    public AdminMemberService(final MemberRepository memberRepository,
                              final JoinedMeetingRepository joinedMeetingRepository
    ) {
        this.memberRepository = memberRepository;
        this.joinedMeetingRepository = joinedMeetingRepository;
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAllMembers(final long universityId, final long departmentId) {
        return memberRepository.findByUniversityIdAndDepartmentId(universityId, departmentId);
    }

    @Transactional
    public void deleteMember(final long universityId, final long memberId) {
        MemberJpaEntity member = memberRepository.getById(memberId);

        if (member.getUniversityId() != universityId) {
            throw new ForbiddenException(ADMIN_FORBIDDEN_ERROR.message());
        }

        joinedMeetingRepository.deleteAllByMemberId(memberId);
        member.changeToUnknownMember();
    }
}
