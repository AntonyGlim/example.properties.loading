package glim.antony.example.properties.loading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

/*
 *
 * @author a.yatsenko
 * Created at 05.08.2021
 */
@Slf4j
public class PropertiesConfigurer {

    private PropertiesConfigurer() {
    }

    private static final String LOGGER_CONFIG_FILE_NAME = "logback.xml";
    private static final String APPLICATION_PROPERTIES_FILE_NAME = "application.properties";
    private static final String[] FOLDERS_FROM_ROOT = new String[]{"conf", "example", "properties"};
    private static final String SPRING_CONFIG_LOCATION_ARGUMENT_NAME = "spring.config.location"; //this properties will be rewrite
    private static final String SPRING_CONFIG_ADDITIONAL_LOCATION_ARGUMENT_NAME = "spring.config.additional-location"; // this is general properties

    /**
     * @return example: C:\example.properties.loading\conf\example\properties\logback.xml
     */
    public static String detectLoggerConfigFileLocation() {
        return findPropertiesFileLocation(LOGGER_CONFIG_FILE_NAME, FOLDERS_FROM_ROOT);
    }

    /**
     * If command line arguments no contains ${SPRING_CONFIG_LOCATION_ARGUMENT_NAME} argument
     * and ${SPRING_CONFIG_ADDITIONAL_LOCATION_ARGUMENT_NAME} argument,
     * method find custom ${FILE_NAME} file location
     * and add argument to command line arguments
     *
     * @param args - command line arguments
     * @return copy of @param args with extra ${SPRING_CONFIG_LOCATION_ARGUMENT_NAME}
     * and ${SPRING_CONFIG_ADDITIONAL_LOCATION_ARGUMENT_NAME} arguments
     */
    public static String[] addSpringConfigLocationArgumentIfNotPresent(String[] args) {
        if (args == null) args = new String[]{};
        if (!contains(args, SPRING_CONFIG_LOCATION_ARGUMENT_NAME)) {
            args = copyArrayAndAddExtraArgument(args, "--" + SPRING_CONFIG_LOCATION_ARGUMENT_NAME
                    + "=classpath:" + APPLICATION_PROPERTIES_FILE_NAME);
        }
        if (!contains(args, SPRING_CONFIG_ADDITIONAL_LOCATION_ARGUMENT_NAME)) {
            args = copyArrayAndAddExtraArgument(args, "--" + SPRING_CONFIG_ADDITIONAL_LOCATION_ARGUMENT_NAME
                    + "=file:" + findPropertiesFileLocation(APPLICATION_PROPERTIES_FILE_NAME, FOLDERS_FROM_ROOT));
        }
        return args;
    }

    private static String[] copyArrayAndAddExtraArgument(String[] args, String argument) {
        args = Arrays.copyOf(args, args.length + 1);
        args[args.length - 1] = argument;
        log.info("Add command line argument {}", argument);
        return args;
    }

    /**
     * 1. try load from 'user.dir'
     * 2. try load from 'catalina.base'
     * 3. load from 'classpath'
     *
     * @param fileName        - example: "application.properties"
     * @param foldersFromRoot - example: "conf", "example", "properties"
     * @return example: C:\example.properties.loading\conf\example\properties\application.properties
     */
    private static String findPropertiesFileLocation(String fileName, String... foldersFromRoot) {
        String fileLocation;
        String userDir = buildPathAsString(fileName, System.getProperty("user.dir"), foldersFromRoot);
        String catalinaBase = buildPathAsString(fileName, System.getProperty("catalina.base"), foldersFromRoot);

        File userDirFile = new File(userDir);
        File catalinaBaseFile = new File(catalinaBase);
        if (userDirFile.exists()) {
            fileLocation = userDir;
        } else if (System.getProperty("catalina.base") != null && catalinaBaseFile.exists()) {
            fileLocation = catalinaBase;
        } else {
            fileLocation = "classpath:" + fileName;
        }
        log.info("Properties '{}' from '{}'", fileName, fileLocation);
        return fileLocation;
    }

    /**
     * @param fileName        - example: "application.properties"
     * @param rootDirectory   - example: System.getProperty("user.dir")
     * @param foldersFromRoot - example: "conf", "example", "properties"
     * @return example: C:\example.properties.loading\conf\example\properties\application.properties
     */
    private static String buildPathAsString(String fileName, String rootDirectory, String... foldersFromRoot) {
        if (foldersFromRoot == null || foldersFromRoot.length == 0) {
            return String.join(File.separator, rootDirectory, fileName);
        }
        return String.join(File.separator, rootDirectory, String.join(File.separator, foldersFromRoot), fileName);
    }

    /**
     * If array contains element
     *
     * @return - true if contains
     */
    private static boolean contains(String[] array, String argument) {
        if (array == null || array.length == 0) return false;
        String location = Arrays.stream(array)
                .filter(Objects::nonNull)
                .filter(s -> s.contains(argument))
                .findAny().orElse(null);
        return !StringUtils.isEmpty(location);
    }

}
