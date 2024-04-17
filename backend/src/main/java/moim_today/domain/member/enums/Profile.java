package moim_today.domain.member.enums;


public enum Profile {
    DEFAULT_PROFILE_URL("https://anak2.s3.ap-northeast-2.amazonaws.com/profile/default-profile.png");

    private final String value;

    Profile(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }
}
