package moim_today.global.constant.exception;

public enum JoinedMoimExceptionConstant {

    JOINED_MOIM_MEMBER_NOT_FOUND("모임에 참여한 멤버가 없습니다.");

    private final String message;

    JoinedMoimExceptionConstant(final String message) {
        this.message = message;
    }

    public String message(){
        return message;
    }
}
