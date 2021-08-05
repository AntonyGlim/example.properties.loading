package glim.antony.example.properties.loading;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;


import java.io.File;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
class ApplicationTests {

	@Test
	void copyArray() {
		String[] array = new String[]{"a", "b", "c"};
		String[] copyArray = Arrays.copyOf(array, array.length + 1);
		System.out.println("array     : " + Arrays.toString(array));
		System.out.println("copyArray : " + Arrays.toString(copyArray));
	}

	public static void main(String[] args) {
		System.out.println("args : " + Arrays.toString(args));
		args = addSpringConfigLocationArgumentIfNotPresent(args);
		System.out.println("args : " + Arrays.toString(args));
	}

	private static String[] addSpringConfigLocationArgumentIfNotPresent(String[] args) {
		if (args == null) args = new String[]{};
		if (args.length == 0 || !containsArgument(args, "spring.config.location")){
			args = Arrays.copyOf(args, args.length + 1);
			args[args.length - 1] = "spring.config.location=" + findPropertiesFileLocation("application.properties");
		}
		return args;
	}

	private static boolean containsArgument(String[] args, String argument) {
		String location = Arrays.stream(args)
				.filter(Objects::nonNull)
				.filter(s -> s.contains(argument))
				.findAny().orElse(null);
		return !StringUtils.isEmpty(location);
	}

	/**
	 * 1. try load from 'user.dir'
	 * 2. try load from 'catalina.base'
	 * 3. load from 'classpath'
	 */
	private static String findPropertiesFileLocation(String fileName) {
		String fileLocation;
		String userDir = String.join(File.separator,
				System.getProperty("user.dir"), "conf", "example", "properties", fileName);
		String catalinaBase = String.join(File.separator,
				System.getProperty("catalina.base"), "conf", "example", "properties", fileName);

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

}
