package com.techelevator;

import java.math.BigDecimal;

public class Items {

    private String slot;
    private String name;
    private BigDecimal price;
    private String type;
    private static Integer soldOut = 0;
    private static Integer maxStock = 5;


    public Items(String slot, String name, BigDecimal price, String type) {
        this.slot = slot;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public String getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static Integer getMaxStock() {
        return maxStock;
    }

    public static Integer getSoldOut() {
        return soldOut;
    }
}


