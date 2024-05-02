package moim_today.domain.member.enums;

import moim_today.global.enum_descriptor.EnumDescriptor;

public enum Gender implements EnumDescriptor {

    MALE("MALE"),
    FEMALE("FEMALE"),
    UNKNOWN("UNKNOWN");

    private final String value;

    Gender(final String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public String getValue() {
        return value;
    }
}
