package moim_today.global.constant;


public enum MemberConstant {
    DEFAULT_PROFILE_URL("https://anak2.s3.ap-northeast-2.amazonaws.com/profile/default-profile.png");

    private final String value;

    MemberConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
