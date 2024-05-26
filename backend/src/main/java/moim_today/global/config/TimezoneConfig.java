package moim_today.global.config;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

@Configuration
public class TimezoneConfig {

    private static final String ASIA_SEOUL_TIME_ZONE = "Asia/Seoul";

    @PostConstruct
    public void setTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone(ASIA_SEOUL_TIME_ZONE));
    }
}
