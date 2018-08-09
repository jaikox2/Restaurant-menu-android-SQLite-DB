package net.simplifiedcoding.bottomnavigationexample;

public class Food {
    private int id;
    private String name;
    private String Detail;
    private String Price;
    private String Stamp;
    private byte [] image;

    public Food() {
    }

    public Food(String name, String detail, String price,String stamp ,byte[] image) {
        this.name = name;
        this.Detail = detail;
        this.Price = price;
        this.Stamp = stamp;
        this.image = image;
    }
    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        this.Detail = detail;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        this.Price = price;
    }

    public String getStamp() {
        return Stamp;
    }

    public void setStamp(String stamp) {
        this.Stamp = stamp;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
