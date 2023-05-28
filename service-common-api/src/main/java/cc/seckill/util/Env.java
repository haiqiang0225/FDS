package cc.seckill.util;

import java.nio.file.Paths;
import java.util.Objects;

/**
 * description: Env 加载一些需要的环境变量 <br>
 * date: 2022/12/7 13:03 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class Env {

    public static final String USER_HOME_DIR = System.getProperty("user.dir");

    public static final String DEFAULT_RESOURCE_LOCATION =
            Paths.get(USER_HOME_DIR, "image").toAbsolutePath().toString();

    /**
     * description: loadEnvs 加载公共环境变量<br>
     * version: 1.0 <br>
     * date: 2022/12/7 13:03 <br>
     * author: haiqiang0225@gmail.com <br>
     */
    public static void loadEnvs() {
        loadJasyptEnvs();
    }

    /**
     * description: loadJasyptEnvs 加载加密所需要的环境变量<br>
     * version: 1.0 <br>
     * date: 2022/12/7 13:04 <br>
     * author: haiqiang0225@gmail.com <br>
     */
    public static void loadJasyptEnvs() {
        System.setProperty("jasypt.encryptor.password", System.getenv("JASYPT_PASS"));
    }


    public static String getResourceLocations() {
        String val = get("resource_location");
        return val != null ? val : DEFAULT_RESOURCE_LOCATION;
    }

    public static String get(String key) {
        String val = System.getenv(key);
        if (Objects.isNull(val)) {
            val = System.getProperty(key);
        }
        return val;
    }

    public static boolean isMacOs() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.startsWith("Mac OS");
    }

    public static boolean isLinux() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.startsWith("Linux");
    }

    public static boolean isWindows() {
        String osName = System.getProperty("os.name");
        return osName != null && osName.startsWith("Windows");
    }

}
