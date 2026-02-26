package external.dependency;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Connection {
    private String host;
    private String userName;
    private String password;
}
