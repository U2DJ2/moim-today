package moim_today.util;

import java.util.Arrays;

public abstract class EnumDocsUtils {

    public static String getEnumNames(Class<? extends Enum> enums){

        return Arrays.stream(enums.getEnumConstants())
                .map(Enum::name)
                .toList()
                .toString();
    }
}
