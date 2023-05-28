package cc.seckill.util;

/**
 * @author : Ji HaiQiang
 * @version : 1.0.0
 * @Package : cc.seckill.util
 * @Description : NumberSystemConverter
 * @Create on : 2023/5/25 16:42
 */
public class NumberSystemConverter {
    private static final String BASE62_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE62 = 62;

    public static String decimalToBase62(long decimal) {
        StringBuilder result = new StringBuilder();
        while (decimal > 0) {
            int remainder = (int) (decimal % BASE62);
            result.insert(0, BASE62_CHARACTERS.charAt(remainder));
            decimal /= BASE62;
        }
        return result.toString();
    }

    public static long base62ToDecimal(String base62) {
        long result = 0;
        long multiplier = 1;
        for (int i = base62.length() - 1; i >= 0; i--) {
            char c = base62.charAt(i);
            int digit = BASE62_CHARACTERS.indexOf(c);
            result += digit * multiplier;
            multiplier *= BASE62;
        }
        return result;
    }

    public static String decimalToBase62(String decimal) {
        return decimalToBase62(Long.parseLong(decimal));
    }
}
