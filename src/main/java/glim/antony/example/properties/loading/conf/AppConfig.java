package glim.antony.example.properties.loading.conf;

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

    @Value("${example.properties.user}")
    String user;

    @Value("${example.properties.password}")
    String password;

    @Value("${example.properties.salt:DEFAULT}")
    String salt;


    @PostConstruct
    public void init() {
        log.info("AppConfig.class: user: {}, password: {}, salt: {}", user, password, salt);
    }

}
