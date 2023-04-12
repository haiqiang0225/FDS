package cc.seckill.util;

import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

/**
 * description: UserAgentUtils <br>
 * date: 2022/10/25 18:57 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class UserAgentUtils {


    public static String getDeviceType(HttpServletRequest request) {
        String agentString = request.getHeader("SysUser-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(agentString);
        OperatingSystem operatingSystem = userAgent.getOperatingSystem(); // 操作系统信息
        eu.bitwalker.useragentutils.DeviceType deviceType = operatingSystem.getDeviceType(); // 设备类型

        switch (deviceType) {
            case COMPUTER:
                return "PC:" + operatingSystem;
            case TABLET: {
                if (agentString.contains("Android")) return "Android Pad";
                if (agentString.contains("iOS")) return "iPad";
                return "Unknown";
            }
            case MOBILE: {
                if (agentString.contains("Android")) return "Android";
                if (agentString.contains("iOS")) return "IOS";
                return "Unknown";
            }
            default:
                return "Unknown";
        }

    }


}
