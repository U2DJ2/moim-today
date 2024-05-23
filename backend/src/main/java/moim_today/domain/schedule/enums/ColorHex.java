package moim_today.domain.schedule.enums;


public enum ColorHex {

    RED("#CC6666"),
    GREEN("#66CC66"),
    BLUE("#6666CC"),
    YELLOW("#CCCC66"),
    MAGENTA("#CC66CC"),
    PURPLE("#9966CC"),
    MINT("#66CCCC"),
    CYAN("#66CCCC"),
    BLACK("#666666"),
    ORANGE("#CC9966"),
    PINK("#CC6666"),
    LIME("#66CC66"),
    TEAL("#66CCCC"),
    BROWN("#996666"),
    NAVY("#666699"),
    OLIVE("#999966"),
    MAROON("#993333"),
    AQUA("#66CCCC"),
    GRAY("#999999"),
    CORAL("#CC6666");

    private final String value;

    ColorHex(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ColorHex getHexByCount(final int index) {
        ColorHex[] colors = ColorHex.values();
        int safeIndex = Math.abs(index % colors.length);
        return colors[safeIndex];
    }
}
