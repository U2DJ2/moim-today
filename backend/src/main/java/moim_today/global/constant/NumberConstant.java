package moim_today.global.constant;

public enum NumberConstant {

    CERTIFICATION_TOKEN_START_POINT(0),
    CERTIFICATION_TOKEN_LENGTH(16),

    SLASH_CNT_START(0),
    MAX_SLASH_CNT(2),

    SCHEDULE_MEETING_ID(0),
    TIME_TABLE_SCHEDULING_COUNT(10),

    EVERYTIME_ITEM_START_INDEX(0),
    EVERYTIME_NODE_START_INDEX(0),

    NOT_EXIST_IDX(-1);

    private final int value;

    NumberConstant(final int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
