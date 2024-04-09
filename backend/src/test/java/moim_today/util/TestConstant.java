package moim_today.util;

public enum TestConstant {

    EMAIL("email"),
    WRONG_EMAIL("wrongEmail"),

    PASSWORD("password"),
    NEW_PASSWORD("newPassword"),
    WRONG_PASSWORD("wrongPassword"),

    CERTIFICATION_TOKEN("certificationToken"),
    NEW_CERTIFICATION_TOKEN("newCertificationToken"),
    WRONG_CERTIFICATION_TOKEN("wrongCertificationToken");

    private final String value;

    TestConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
