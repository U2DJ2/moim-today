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
    private static final String NO_OBJECT = null;

    public MemberRegisterInfo {
    }

    private void validatePassword(String password){

    }

    private void validateUsername(String username){

    }

    private boolean isNull(Object o){
        return o == NO_OBJECT;
    }
}
