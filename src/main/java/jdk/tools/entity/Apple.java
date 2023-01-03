package jdk.tools.entity;

import java.util.Objects;

public class Apple implements Comparable<Apple>{
    private String type;
    private String color;
    private String desc;
    private int price;

    public Apple(String type, String color, String desc, int price) {
        this.type = type;
        this.color = color;
        this.desc = desc;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apple apple = (Apple) o;
        return price == apple.price && type.equals(apple.type) && color.equals(apple.color) && desc.equals(apple.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, color, desc, price);
    }

    @Override
    public int compareTo(Apple o) {
        return this.price-o.getPrice();
    }
}
