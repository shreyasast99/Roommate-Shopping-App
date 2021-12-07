package edu.uga.cs.roommateshoppingapp;

/**
 * This class represents a single item, including the name,
 * price, if it has been purchased, and who the buyer is.
 */
public class Item {

    private String name;
    private double price;
    private boolean purchased;
    private String buyer;

    /**
     * Default constructor
     * */
    public Item()
    {

        this.name = null;
        this.price = 0.00;
        this.purchased = false;
        this.buyer = "";

    }

    /**
     * Constructor with a name of the item
     * */
    public Item( String name) {

        this.name = name;
        this.price=0.00;
        this.purchased = false;
        this.buyer = "";

    }

    /**
     * Get name
     * */
    public String getName() {
        return name;
    }

    /**
     * Set name
     * */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get price
     * */
    public double getPrice() {
        return price;
    }

    /**
     * Set price
     * */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get buyer
     * */
    public String getBuyer() {
        return buyer;
    }

    /**
     * Set buyer
     * */
    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    /**
     * Get purchased
     * */
    public boolean getPurchased() {
        return purchased;
    }

    /**
     * Set purchased
     * */
    public void setPurchased(boolean purchased) {
        this.purchased = purchased;
    }

    /**
    public String toString() {
        return name + " " + price;
    }*/
}
