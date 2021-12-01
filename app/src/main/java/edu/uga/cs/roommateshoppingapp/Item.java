package edu.uga.cs.roommateshoppingapp;

/**
 * This class represents a single job lead, including the company name,
 * phone number, URL, and some comments.
 */
public class Item {

    private String name;
    private double price;

    public Item()
    {

        this.name = null;
        this.price = 0;

    }

    public Item( String name,double price ) {

        this.name=name;
        this.price=price;

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



    public String toString() {
        return name + " " + price;
    }
}
