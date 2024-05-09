package moim_today.global.constant.exception;

public enum JoinedMoimExceptionConstant {

    JOINED_MOIM_MEMBER_IS_EMPTY("모임에 참여한 멤버가 없습니다."),
    JOINED_MOIM_MEMBER_NOT_FOUND("모임에 참여한 멤버가 아닙니다."),
    MEMBER_ALREADY_JOINED("모임에 이미 참여한 멤버입니다.");

    private final String message;

    JoinedMoimExceptionConstant(final String message) {
        this.message = message;
    }

    public String message(){
        return message;
    }
}
