package moim_today;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MoimTodayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoimTodayApplication.class, args);
    }

}
