package moim_today.global.constant;

public enum FileTypeConstant {

    PROFILE_IMAGE("profile"),
    MOIM_IMAGE("moim");

    private final String value;

    FileTypeConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
