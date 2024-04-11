package moim_today.global.constant;

public enum TimeConstant {

    TEN_MINUTES(10);

    private final int time;

    TimeConstant(final int time) {
        this.time = time;
    }

    public int time() {
        return time;
    }
}
