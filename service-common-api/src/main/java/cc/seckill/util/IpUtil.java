package cc.seckill.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * description: IpUtil IP工具类<br>
 * date: 2022/7/28 19:40 <br>
 * author: hq <br>
 * version: 1.0 <br>
 */
@Slf4j
public class IpUtil {

    private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"};


    /**
     * description: getClientIpAddress 获取请求的IP<br>
     * version: 1.0 <br>
     * date: 2022/7/28 19:46 <br>
     * author: objcat <br>
     *
     * @param request HttpServletRequest
     * @return java.lang.String ip地址
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    public static boolean containsCookie(String key, HttpServletRequest request) {
        if (key == null) {
            return false;
        }
        Cookie[] cookies = request.getCookies();
        // 是否6个小时内访问过
        boolean c = false;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie != null && key.equals(cookie.getName())) {
                    c = true;
                }
            }
        }
        return c;
    }


    /**
     * description: validIPAddress 验证ip地址是否合法<br>
     * version: 1.0 <br>
     * date: 2022/10/25 16:00 <br>
     * author: haiqiang0225@gmail.com <br>
     *
     * @param queryIP IP地址
     * @return java.lang.String  Neither 不合法
     */
    public static boolean validIPAddress(String queryIP) {
        int last = -1;
        if (queryIP.indexOf('.') >= 0) {
            // IPv4
            for (int i = 0; i < 4; ++i) {
                int cur = (i == 3 ? queryIP.length() : queryIP.indexOf('.', last + 1));
                if (cur < 0) {
                    return false;
                }
                if (cur - last - 1 < 1 || cur - last - 1 > 3) {
                    return false;
                }
                int addr = 0;
                for (int j = last + 1; j < cur; ++j) {
                    if (!Character.isDigit(queryIP.charAt(j))) {
                        return false;
                    }
                    addr = addr * 10 + (queryIP.charAt(j) - '0');
                }
                if (addr > 255) {
                    return false;
                }
                if (addr > 0 && queryIP.charAt(last + 1) == '0') {
                    return false;
                }
                if (addr == 0 && cur - last - 1 > 1) {
                    return false;
                }
                last = cur;
            }
            return true;
        } else {
            // IPv6
            for (int i = 0; i < 8; ++i) {
                int cur = (i == 7 ? queryIP.length() : queryIP.indexOf(':', last + 1));
                if (cur < 0) {
                    return false;
                }
                if (cur - last - 1 < 1 || cur - last - 1 > 4) {
                    return false;
                }
                for (int j = last + 1; j < cur; ++j) {
                    if (!Character.isDigit(queryIP.charAt(j)) && !('a' <= Character.toLowerCase(queryIP.charAt(j)) && Character.toLowerCase(queryIP.charAt(j)) <= 'f')) {
                        return false;
                    }
                }
                last = cur;
            }
            return true;
        }
    }

    public static String getIpPhysicalLocation(String ip) {
        if ("127.0.0.1".equals(ip)) {
            return "本机测试地址";
        }
        if (!validIPAddress(ip)) {
            log.warn("ip地址出错了!请排查!");
            return "ip地址出错了!请排查!";
        }
        String loc = null;
        for (IpSelector selector : IpSelector.values()) {
            loc = selector.getLocation(ip);
            if (loc != null && !"".equals(loc)) {
                break;
            }
        }

        log.info("resolve ip({}) location: {}", ip, loc);
        return loc;
    }


    private static final Pattern PATTERN = Pattern.compile("\\{}");

    public enum IpSelector {
        BaiDuApi("http://opendata.baidu.com/api.php?query={}&co=&resource_id=6006&oe=utf8"),
        ;


        private static final RestTemplate restTemplate = new RestTemplate();


        final String url;

        final Matcher matcher;

        IpSelector(String url) {
            this.url = url;
            matcher = PATTERN.matcher(this.url);

        }

        /**
         * description: getRequestUrl 获取请求地址<br>
         * version: 1.0 <br>
         * date: 2022/10/25 16:14 <br>
         * author: haiqiang0225@gmail.com <br>
         *
         * @param args
         * @return java.lang.String
         */
        private String getRequestUrl(String... args) {
            StringBuffer res = new StringBuffer();
            matcher.reset();
            int idx = 0;
            while (matcher.find()) {
                matcher.appendReplacement(res, args[idx++]);
            }
            matcher.appendTail(res);
            return res.toString();
        }

        public String getLocation(String ip) {
            String requestUrl = getRequestUrl(ip);
            ResponseEntity<String> forEntity = restTemplate.getForEntity(requestUrl, String.class);
            JSONObject parse = (JSONObject) JSON.parse(forEntity.getBody());
            if (parse == null) {
                log.warn("resolve ip location failed!");
                return "";
            }
            JSONArray data = (JSONArray) parse.get("data");
            if (data.size() == 0) {
                log.warn("resolve ip location failed!");
                return "";
            }
            JSONObject jsonObject = (JSONObject) data.get(0);
            return jsonObject.get("location").toString();
        }

    }
}
