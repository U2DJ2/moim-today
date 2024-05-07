package moim_today.global.constant;

public enum MoimConstant {

    DEFAULT_MOIM_IMAGE_URL("https://anak2.s3.ap-northeast-2.amazonaws.com/moim/default-moim.jpg"),
    DEFAULT_MOIM_PASSWORD("NONE");

    private final String value;

    MoimConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
