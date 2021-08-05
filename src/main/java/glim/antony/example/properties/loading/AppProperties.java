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
@ConfigurationProperties(prefix = "flight.scenario.saver")
public class AppProperties {

    private String RABBIT_MQ_USER;

    private String RABBIT_MQ_PASS;

    private List<String> DOC_SERVERS_DESCRIPTION;

}
