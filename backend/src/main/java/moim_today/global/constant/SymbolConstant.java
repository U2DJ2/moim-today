package moim_today.global.constant;

public enum SymbolConstant {

    HYPHEN("-"),
    BLANK(""),
    WWW("www."),
    SLASH("/"),
    EMAIL_EXTENSION("ac.kr");

    private final String value;

    SymbolConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}