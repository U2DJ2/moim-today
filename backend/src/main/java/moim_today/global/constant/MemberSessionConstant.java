package moim_today.global.constant;

public enum MemberSessionConstant {

    MEMBER_SESSION("MemberSession");

    private final String value;

    MemberSessionConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
