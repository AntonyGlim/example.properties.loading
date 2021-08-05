package glim.antony.example.properties.loading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/*
 *
 * @author a.yatsenko
 * Created at 05.08.2021
 */
@Slf4j
@Configuration
public class AppConfig {

    @Value("${flight.scenario.saver.RABBIT_MQ_USER}")
    String user;

    @PostConstruct
    public void init() {
        log.info("{}", user);
    }

}
