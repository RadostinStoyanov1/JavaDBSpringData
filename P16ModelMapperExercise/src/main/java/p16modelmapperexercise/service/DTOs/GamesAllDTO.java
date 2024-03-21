package p16modelmapperexercise.service.DTOs;

public class GamesAllDTO {
    private String title;
    private double price;

    public GamesAllDTO() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", this.getTitle(), this.getPrice());
    }
}
