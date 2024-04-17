package moim_today.global.constant;

public enum EveryTimeConstant {

    REQUEST_TIME_TABLE_URL("https://api.everytime.kr/find/timetable/table/friend"),

    HTTP_HEADER_ORIGIN("Origin"),
    HTTP_HEADER_REFERER("Referer"),
    HTTP_HEADER_USER_AGENT("User-Agent"),

    EVERYTIME_ORIGIN("https://everytime.kr"),
    EVERYTIME_REFERER("https://everytime.kr/"),
    EVERYTIME_USER_AGENT("Mozilla/5.0 (X11; Ubuntu; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36"),

    EVERYTIME_ID("identifier="),
    EVERYTIME_FRIEND_INFO("&friendInfo=true"),

    EVERYTIME_SUBJECT("subject");

    private final String value;

    EveryTimeConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}
