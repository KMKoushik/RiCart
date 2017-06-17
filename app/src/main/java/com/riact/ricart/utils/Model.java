package com.riact.ricart.utils;

/**
 * Created by koushik on 11/6/17.
 */

public class Model {
    String name,itemCode,uom;
    int value;
    float price,quantity,amount;

    public Model(String name, int value, String itemCode,String uom,float price) {
        this.name = name;
        this.value = value;
        this.itemCode=itemCode;
        this.uom=uom;
        this.price=price;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }

    public void putValue(int value){this.value=value;}

    public String getItemCode(){ return this.itemCode;}

    public String getUom(){return this.uom;}

    public void setQuantity(float quantity){ this.quantity=quantity;}

    public float getPrice(){return this.price;}

    public void setAmount(float amount){this.amount=amount;}

    public float getAmount(){return this.amount;}

}
