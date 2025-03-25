package com.example;

public class Items_model_with_sale extends Items_model {
    private double price_with_sale;

    public Items_model_with_sale(String name, double price, double price_with_sale, String description,
                                 boolean isForMans, boolean isForWomens, boolean isForChilds, int imageId, boolean isHits, boolean isForYou,  boolean isFavorite) {
        super(name, price, description, isForMans, isForWomens, isForChilds, imageId, isHits, isForYou, isFavorite);
        this.price_with_sale = price_with_sale;
    }

    public double getPrice_with_sale() {
        return price_with_sale;
    }

    public void setPrice_with_sale(double price_with_sale) {
        this.price_with_sale = price_with_sale;
    }
}
