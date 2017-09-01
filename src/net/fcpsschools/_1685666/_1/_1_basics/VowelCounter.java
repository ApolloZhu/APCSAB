package net.fcpsschools._1685666._1._1_basics;

// R-1.8
public class VowelCounter {
    public enum CountingMethod {REGEX, LOOP}

    public static int count(String s, CountingMethod method) {
        switch (method) {
            case REGEX:
                return s.replaceAll("[^aeiouAEIOU]", "").length();

            case LOOP:
                int count = 0;
                for (int i = 0; i < s.length(); i++)
                    if ("aeiouAEIOU".indexOf(s.charAt(i)) != -1)
                        count++;
                return count;

            default:
                throw new EnumConstantNotPresentException(method.getClass(), method.name());
        }
    }


    public static void main(String[] args) {
        String[] toCount = {
                "The quick brown fox jumps Over the lazy dog.", // 11 == 11 is true
                "", // 0 == 0 is true
                "HELLO, world!" // 3 == 3 is true
        };
        for (String s : toCount) {
            int result1 = count(s, CountingMethod.REGEX);
            int result2 = count(s, CountingMethod.LOOP);
            System.out.println(result1 + " == " + result2 + " is " + (result1 == result2));
        }
    }
}
