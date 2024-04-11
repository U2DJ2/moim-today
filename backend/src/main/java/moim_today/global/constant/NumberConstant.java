package moim_today.global.constant;

public enum NumberConstant {

    CERTIFICATION_TOKEN_START_POINT(0),
    CERTIFICATION_TOKEN_LENGTH(16);

    private final int value;

    NumberConstant(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
