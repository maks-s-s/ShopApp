package com.example;

public class Items_model {
    private String name;
    private double price;
    private String description;
    private boolean isForMans;
    private boolean isForWomens;
    private boolean isForChilds;
    private int imageId;
    private boolean isHits;
    private boolean isForYou;
    private boolean isFavorite;
    public Items_model(String name, double price, String description, boolean isForMans, boolean isForWomens, boolean isForChilds, int imageId,
                       boolean isHits, boolean isForYou,   boolean isFavorite) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.isForMans = isForMans;
        this.isForWomens = isForWomens;
        this.isForChilds = isForChilds;
        this.imageId = imageId;
        this.isHits = isHits;
        this.isForYou = isForYou;
        this.isFavorite = isFavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isForMans() {
        return isForMans;
    }

    public void setForMans(boolean forMans) {
        isForMans = forMans;
    }

    public boolean isForWomens() {
        return isForWomens;
    }

    public void setForWomens(boolean forWomens) {
        isForWomens = forWomens;
    }

    public boolean isForChilds() {
        return isForChilds;
    }

    public void setForChilds(boolean forChilds) {
        isForChilds = forChilds;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isHits() {
        return isHits;
    }

    public void setHits(boolean hits) {
        isHits = hits;
    }

    public boolean isForYou() {
        return isForYou;
    }

    public void setForYou(boolean forYou) {
        isForYou = forYou;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}

