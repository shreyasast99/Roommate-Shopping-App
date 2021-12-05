package edu.uga.cs.roommateshoppingapp;

/**
 * This class represents a single job lead, including the company name,
 * phone number, URL, and some comments.
 */
public class Item {

    private String name;
    private double price;
    private boolean purchased;
    private String buyer;

    public Item()
    {

        this.name = null;
        this.price = 0.00;
        this.purchased = false;
        this.buyer = null;

    }

    public Item( String name) {

        this.name = name;
        this.price=0.00;
        this.purchased = false;
        this.buyer = null;

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

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public boolean getPurchased() {
        return purchased;
    }

    public void setPurchased(boolean purchased) {
        this.name = name;
    }



    public String toString() {
        return name + " " + price;
    }
}
