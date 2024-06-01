package moim_today.global.constant;

public enum SessionConstant {

    MEMBER_SESSION("MemberSession"),
    ADMIN_SESSION("AdminSession");

    private final String value;

    SessionConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
