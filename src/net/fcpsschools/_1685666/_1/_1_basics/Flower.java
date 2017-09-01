package net.fcpsschools._1685666._1._1_basics;

// R-1.10
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
