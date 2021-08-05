package glim.antony.example.properties.loading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static glim.antony.example.properties.loading.PropertiesConfigurer.addSpringConfigLocationArgumentIfNotPresent;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, addSpringConfigLocationArgumentIfNotPresent(args));
    }

    @Bean
    ApplicationRunner applicationRunner(AppProperties properties) {
        return args -> log.info("{}", properties);
    }

}
