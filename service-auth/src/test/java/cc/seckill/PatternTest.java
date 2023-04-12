package cc.seckill;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * description: PatternTest <br>
 * date: 2022/12/27 20:56 <br>
 * author: haiqiang0225@gmail.com <br>
 * version: 1.0 <br>
 */
public class PatternTest {
    public static void main(String[] args) {
        String regex = "^.*(?=.{6,})(?=.*\\d)(?=.*[A-Z])(?=.*[a-z])(?=.*[!@#$%^&*? ]).*$";
        Pattern compile = Pattern.compile(regex);
        Matcher matcher = compile.matcher("");
        System.out.println(matcher.matches());
    }
}
