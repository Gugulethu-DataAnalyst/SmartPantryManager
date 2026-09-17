//creating a package that show how the connection is
//recieved
package com.example.smartpantrymanager;

public class PantryItem {
    //creating private attributes
    private int ID;
    private String name;
    private double quantity;
    private String unit;
    private String expiryDate;

    //constructor
    public PantryItem(int ID, String name, double quantity, String unit, String expiryDate){
        this.ID = ID;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    //get attributes
    //get id
    public int getID(){
        return ID;
    }

    //get name
    public String getName(){
        return name;
    }

    //get quantity
    public double getQuantity(){
        return quantity;
    }

    //get unit
    public String getUnit(){
        return unit;
    }

    //get expiry date
    public String getExpiryDate(){
        return expiryDate;
    }

}
