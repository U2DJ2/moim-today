package booki_today.application.mail;

import java.util.List;
import java.util.Map;

public interface SMTPService {

    void send(String subject, Map<String, Object> variables, List<String> to);
}
