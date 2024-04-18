package moim_today.global.constant;

public enum TimeConstant {

    TEN_MINUTES(10),
    ONE_WEEK(1),
    DAYS_PER_WEEK(7),
    WEEK_START_POINT(1),

    FIVE_MINUTES_IN_SECONDS(300);

    private final int time;

    TimeConstant(final int time) {
        this.time = time;
    }

    public int time() {
        return time;
    }
}
