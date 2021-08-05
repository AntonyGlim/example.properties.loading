package glim.antony.example.properties.loading;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/*
 *
 * @author a.yatsenko
 * Created at 31.03.2021
 */
@Data
@ConfigurationProperties(prefix = "example.properties")
public class AppProperties {

    private String user;

    private String password;

    private String salt;

    private List<String> description;

}
