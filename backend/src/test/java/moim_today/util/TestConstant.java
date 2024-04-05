package moim_today.util;

public enum TestConstant {
    EMAIL("email"),
    PASSWORD("password"),
    WRONG_EMAIL("wrongEmail"),
    WRONG_PASSWORD("wrongPassword");

    private final String value;

    TestConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
