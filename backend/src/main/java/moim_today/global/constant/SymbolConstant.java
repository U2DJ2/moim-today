package moim_today.global.constant;

public enum SymbolConstant {

    HYPHEN("-"),
    BLANK(""),
    WWW("www."),
    CAPITAL_WWW("WWW."),
    SLASH("/"),
    AT("@"),
    EMAIL_EXTENSION("ac.kr"),
    PERCENT("%");

    private final String value;

    SymbolConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}