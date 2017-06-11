package com.riact.ricart.utils;

/**
 * Created by koushik on 11/6/17.
 */

public class Model {
    String name;
    int value; /* 0 -&gt; checkbox disable, 1 -&gt; checkbox enable */
    String itemCode;
    String uom;
    float price;

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

    public float getPrice(){return this.price;}
}
