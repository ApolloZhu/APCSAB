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
 * R-1.10
 *
 * @author Apollo/Zhiyu Zhu/朱智语
 */
public class Flower {
    private String name;
    private int petalCount;
    private float price;

    public Flower(String name, int petalCount, float price) {
        setName(name);
        setNumberOfPetals(petalCount);
        setPrice(price);
    }

    public String getName() {
        return name;
    }

    public int getNumberOfPetals() {
        return petalCount;
    }

    public float getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfPetals(int petalCount) {
        this.petalCount = petalCount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return getName()
                + " with " + getNumberOfPetals() + " petal(s)"
                + " worth $" + getPrice();
    }

    public static void main(String[] args) {
        // Rose with 50 petal(s) worth $1.5
        System.out.print(new Flower("Rose", 50, 1.5f));
    }
}
