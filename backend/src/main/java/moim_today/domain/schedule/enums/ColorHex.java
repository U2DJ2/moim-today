package moim_today.domain.schedule.enums;


public enum ColorHex {

    RED("#FF0000"),
    GREEN("#008000"),
    BLUE("#0000FF"),
    YELLOW("#FFFF00"),
    MAGENTA("#FF00FF"),
    PURPLE("#800080"),
    MINT("#AAF0D1"),
    CYAN("#00FFFF"),
    BLACK("#000000"),
    ORANGE("#FFA500"),
    PINK("#FFC0CB"),
    LIME("#00FF00"),
    TEAL("#008080"),
    BROWN("#A52A2A"),
    NAVY("#000080"),
    OLIVE("#808000"),
    MAROON("#800000"),
    AQUA("#00FFFF"),
    GRAY("#808080"),
    CORAL("#FF7F50");

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
