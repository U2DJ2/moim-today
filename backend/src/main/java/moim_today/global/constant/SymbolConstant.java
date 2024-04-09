package moim_today.global.constant;

public enum SymbolConstant {

    HYPHEN("-"), BLANK("");

    private final String value;

    SymbolConstant(final String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}