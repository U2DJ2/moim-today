package moim_today.global.constant;

public enum DepartmentConstant {
    DEPARTMENT_UPDATE_BATCH_SIZE("1000");

    private final String value;

    DepartmentConstant(final String value) {
        this.value = value;
    }

    public String value(){
        return value;
    }

    public int intValue(){
        return Integer.parseInt(value);
    }
}
