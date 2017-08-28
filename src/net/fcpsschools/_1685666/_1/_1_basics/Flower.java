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
}
