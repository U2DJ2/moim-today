package moim_today.global.constant;

public enum MoimConstant {
    //TODO: 기본 모임 이미지 추가 예정
    DEFAULT_MOIM_IMAGE_URL("defaultMoimImageUrl"),
    DEFAULT_MOIM_PASSWORD("NONE");

    private final String value;

    MoimConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
