package moim_today.domain.member.enums;

public enum Gender {

    MALE, FEMALE, UNKNOWN;

    public static boolean isValidGender(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
