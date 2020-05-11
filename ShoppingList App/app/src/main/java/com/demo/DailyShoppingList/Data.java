package com.demo.DailyShoppingList;

/**
 * Created by Belal on 9/30/2017.
 */

public class Data {
    private int id;
    private String type;
    private String amount;
    private String note;
    private String date;
    private String quantity;


    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Data(int id, String type, String amount, String quantity, String note, String date) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.quantity = quantity;
        this.note = note;
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setDate(String date) {
        this.date = date;
    }





    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public String getDate() {
        return date;
    }

    public String getQuantity() {
        return quantity;
    }

}
