package moim_today.domain.schedule.enums;


public enum ColorHex {

    RED("#FFCCCC"),
    GREEN("#99FF99"),
    BLUE("#CCCCFF"),
    YELLOW("#fafaaf"),
    MAGENTA("#FFCCFF"),
    PURPLE("#E6CCFF"),
    MINT("#c2f2e7"),
    CYAN("#CCFFFF"),
    BLACK("#CCCCCC"),
    ORANGE("#FFD9B3"),
    PINK("#FFE6EB"),
    LIME("#CCFFCC"),
    TEAL("#B3FFFF"),
    BROWN("#E6B8B7"),
    NAVY("#CCCCE0"),
    OLIVE("#E6E6CC"),
    MAROON("#E6CCCC"),
    AQUA("#CCFFFF"),
    GRAY("#E0E0E0"),
    CORAL("#FFCCB3");

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
