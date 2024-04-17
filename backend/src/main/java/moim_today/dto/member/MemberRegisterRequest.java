package moim_today.dto.member;

import lombok.Builder;
import moim_today.domain.member.MemberRegisterInfo;
import moim_today.domain.member.enums.Gender;
import moim_today.global.error.BadRequestException;

import java.time.LocalDate;

import static moim_today.global.constant.exception.MemberExceptionConstant.NO_PASSWORD;
import static moim_today.global.constant.exception.MemberExceptionConstant.NO_USERNAME;

@Builder
public record MemberRegisterRequest (
        long universityId,
        long departmentId,
        String email,
        String password,
        String username,
        String studentId,
        LocalDate birthDate,
        Gender gender
){
    private static final String NO_OBJECT = null;

    public MemberRegisterInfo toDomain(final String encodedPassword){
        validatePassword(password);
        validateUsername(username);

        return MemberRegisterInfo.builder()
                .universityId(universityId)
                .departmentId(departmentId)
                .email(email)
                .password(encodedPassword)
                .username(username)
                .studentId(studentId)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }

    private void validatePassword(String password){
        if(isNull(password) && password.isEmpty()){
            throw new BadRequestException(NO_PASSWORD.message());
        }
    }

    private void validateUsername(String username){
        if(isNull(username) && username.isEmpty()){
            throw new BadRequestException(NO_USERNAME.message());
        }
    }

    private boolean isNull(Object o){
        return o == NO_OBJECT;
    }
}
