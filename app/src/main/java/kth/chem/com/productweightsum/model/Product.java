package kth.chem.com.productweightsum.model;

public class Product {
    private String name;
    private int count;
    private int quantity;
    private String unit;
    private double weightOfOne;

    public Product(String name, int count, int quantity, String unit, double weightOfOne) {
        this.name = name;
        this.count = count;
        this.quantity = quantity;
        this.unit = unit;
        this.weightOfOne = weightOfOne;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getWeightOfOne() {
        return weightOfOne;
    }

    public void setWeightOfOne(double weightOfOne) {
        this.weightOfOne = weightOfOne;
    }

    public double gettotalWeight(){
        if (weightOfOne != 0 && quantity != 0){
            return weightOfOne * quantity;
        }
        return 0;
    }
}
