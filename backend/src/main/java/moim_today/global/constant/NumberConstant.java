package moim_today.global.constant;

public enum NumberConstant {

    CERTIFICATION_TOKEN_START_POINT(0),
    CERTIFICATION_TOKEN_LENGTH(16),

    SLASH_CNT_START(0),
    MAX_SLASH_CNT(2),

    DEFAULT_MOIM_CURRENT_COUNT(0),
    DEFAULT_MOIM_VIEWS(0),
    VIEW_COUNT_OF_ONE(1),

    SCHEDULE_MEETING_ID(0),
    SCHEDULE_MOIM_ID(0),
    SCHEDULE_COLOR_START_COUNT(0),
    SCHEDULE_COLOR_NEXT_COUNT(1),
    SCHEDULE_TIME_START_IDX(0),
    MOIM_SCHEDULE_FIRST_IDX(0),
    TIME_TABLE_SCHEDULING_COUNT(10),

    EVERYTIME_ITEM_START_INDEX(0),
    EVERYTIME_NODE_START_INDEX(0),

    CALENDAR_START_ID(0),

    NOT_EXIST_IDX(-1),
    THIRTY_DAYS_IN_SECONDS(2592000),
    ONE_DAYS_IN_SECONDS(3600),
    ONE_DAY(1);

    private final int value;

    NumberConstant(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
