package p16modelmapperexercise.service.DTOs;

import java.time.LocalDate;

public class GameDetailsDTO {
    private String title;
    private double price;
    private String description;
    private LocalDate releaseDate;

    public GameDetailsDTO() {}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Title: %s", this.title)).append(System.lineSeparator());
        sb.append(String.format("Price %.2f", this.price)).append(System.lineSeparator());
        sb.append(String.format("Description: %S", this.description)).append(System.lineSeparator());
        sb.append(String.format("Release date: %s", this.releaseDate.toString()));
        return sb.toString();
    }
}
