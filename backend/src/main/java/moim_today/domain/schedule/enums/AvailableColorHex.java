package moim_today.domain.schedule.enums;

public enum AvailableColorHex {

    WHITE("#FFFFFF"),
    VERY_LIGHT_RED("#FFC0CB"),
    LIGHT_RED("#FF9999"),
    PALE_RED("#FF6666"),
    SALMON("#FF4C4C"),
    CORAL("#FF3232"),
    BRIGHT_RED("#FF0000"),
    CRIMSON("#E60000"),
    DARK_RED("#CC0000"),
    DEEP_RED("#B30000"),
    VERY_DARK_RED("#990000");

    private final String colorHex;

    AvailableColorHex(final String colorHex) {
        this.colorHex = colorHex;
    }

    public String colorHex() {
        return colorHex;
    }

    public static String getHexByCount(final int index) {
        AvailableColorHex[] colors = AvailableColorHex.values();
        int safeIndex = Math.min(index, colors.length - 1);
        return colors[safeIndex].colorHex;
    }
}
