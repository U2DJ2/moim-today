package moim_today.global.constant;


public enum MemberConstant {
    DEFAULT_PROFILE_URL("https://anak2.s3.ap-northeast-2.amazonaws.com/profile/default-profile.png"),
    UNKNOWN_MEMBER("0"),
    DELETED_MEMBER_USERNAME("(알수없음)"),
    DELETED_MEMBER_STUDENT_ID("999999999");

    private final String value;

    MemberConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }

    public long longValue(){
        return Long.parseLong(value);
    }
}
