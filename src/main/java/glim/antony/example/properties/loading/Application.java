package glim.antony.example.properties.loading;

import glim.antony.example.properties.loading.properties.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import static glim.antony.example.properties.loading.properties.PropertiesConfigurer.addSpringConfigLocationArgumentIfNotPresent;
import static glim.antony.example.properties.loading.properties.PropertiesConfigurer.detectLoggerConfigFileLocation;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class Application {

    public static void main(String[] args) {
        setLoggerConfiguration();
        SpringApplication.run(Application.class, addSpringConfigLocationArgumentIfNotPresent(args));
    }

    /**
     * Not change System property on TOMCAT!
     */
    @Deprecated
    private static void setLoggerConfiguration() {
        System.setProperty("logging.config", detectLoggerConfigFileLocation());
        log.warn("System property 'logging.config' changed {}", System.getProperty("logging.config"));
    }

    /**
     * Print all properties
     *
     * @param properties
     * @return
     */
    @Bean
    ApplicationRunner applicationRunner(AppProperties properties) {
        return args -> log.info("{}", properties);
    }

}
