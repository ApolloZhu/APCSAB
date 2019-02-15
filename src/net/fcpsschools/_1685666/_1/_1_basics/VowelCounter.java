/*
MIT License

Copyright (c) 2017-2019 Apollo/Zhiyu Zhu/朱智语 <public-apollonian@outlook.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package net.fcpsschools._1685666._1._1_basics;

/**
 * R-1.8
 *
 * @author Apollo/Zhiyu Zhu/朱智语
 */
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
