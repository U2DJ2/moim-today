package moim_today.util;

import moim_today.global.enum_descriptor.EnumDescriptor;

import java.util.Arrays;

public abstract class EnumDocsUtils {

    public static String getEnumNames(Class<? extends EnumDescriptor> enums){

        return Arrays.stream(enums.getEnumConstants())
                .map(EnumDescriptor::getName)
                .toList()
                .toString();
    }
}
