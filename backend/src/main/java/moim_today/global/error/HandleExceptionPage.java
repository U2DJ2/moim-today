package moim_today.global.error;

import lombok.Getter;


@Getter
public class HandleExceptionPage extends RuntimeException {

    private final String page;

    public HandleExceptionPage(final String page) {
        this.page = page;
    }
}
