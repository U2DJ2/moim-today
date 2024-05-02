package moim_today.domain.schedule;


public enum ColorCount {
    RED("#FF0000"),
    GREEN("#008000"),
    BLUE("#0000FF"),
    YELLOW("#FFFF00"),
    PURPLE("#800080"),
    CYAN("#00FFFF"),
    BLACK("#000000"),
    WHITE("#FFFFFF"),
    ORANGE("#FFA500"),
    GRAY("#808080");

    private final String value;

    ColorCount(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }

    public static ColorCount getHexByCount(final int index) {
        ColorCount[] colors = ColorCount.values();
        int safeIndex = Math.abs(index % colors.length);
        return colors[safeIndex];
    }
}
