package jdk.tools.entity;

public class Apple {
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
}
