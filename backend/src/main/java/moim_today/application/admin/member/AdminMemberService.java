package moim_today.application.admin.member;

import moim_today.dto.member.MemberResponse;
import moim_today.global.error.ForbiddenException;
import moim_today.persistence.entity.member.MemberJpaEntity;
import moim_today.persistence.repository.member.MemberRepository;
import moim_today.global.spring_event.event.AdminMemberDeleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static moim_today.global.constant.exception.AdminExceptionConstant.ADMIN_FORBIDDEN_ERROR;

@Service
public class AdminMemberService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public AdminMemberService(final MemberRepository memberRepository,
                              final ApplicationEventPublisher applicationEventPublisher
    ) {
        this.memberRepository = memberRepository;
        this.applicationEventPublisher = applicationEventPublisher;
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

        AdminMemberDeleteEvent adminMemberDeleteEvent = new AdminMemberDeleteEvent(memberId);
        applicationEventPublisher.publishEvent(adminMemberDeleteEvent);
        member.changeToUnknownMember();
    }
}
