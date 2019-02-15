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

package net.fcpsschools._1685666._1._2_oo_design._1_class;

/**
 * C-2.21
 *
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class C extends B {
    int x = -1;

    void checkedSetAX(int i)
            throws NoSuchFieldException, IllegalAccessException {
        A.class.getDeclaredField("x").setInt(this, i);
    }

    void uncheckedSetAX(int i) throws ClassCastException {
        ((A) this).x = i;
    }

    public static void main(String[] args) {
        C c = new C(); // A.x = 0, B.x = 13, C.x = -1

        try {
            c.checkedSetAX(1);
            System.out.println(
                    A.class.getDeclaredField("x").getInt(c) + " " +
                            c.getClass().getSuperclass().getDeclaredField("x").getInt(c)
                            + " " + c.x
            );
        } catch (NoSuchFieldException | IllegalAccessException e) {

        }

        c.uncheckedSetAX(2);
        System.out.println(((A) c).x + " " + ((B) c).x + " " + c.x);
    }
}
