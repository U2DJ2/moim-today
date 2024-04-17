package moim_today.domain.member;

import lombok.Builder;
import moim_today.domain.member.enums.Gender;

import java.time.LocalDate;

@Builder
public record MemberRegisterInfo (
        long universityId,
        long departmentId,
        String email,
        String password,
        String username,
        String studentId,
        LocalDate birthDate,
        Gender gender
) {
}
