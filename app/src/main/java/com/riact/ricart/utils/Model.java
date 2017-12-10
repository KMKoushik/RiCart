package com.riact.ricart.utils;

/**
 * Created by koushik on 11/6/17.
 */

public class Model {
    String name,itemCode,uom,group;
    int value,index;
    float price,quantity,amount;

    public Model(String name, int value, String itemCode,String uom,float price,String group,int index) {
        this.name = name;
        this.value = value;
        this.itemCode=itemCode;
        this.uom=uom;
        this.price=price;
        this.group=group;
        this.index=index;
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

    public Float getQuantity(){return this.quantity;}

    public Float getPrice(){return this.price;}

    public void setAmount(float amount){this.amount=amount;}

    public Float getAmount(){return this.amount;}

    public String getGroup(){return this.group;}

    public int getIndex(){return this.index;}


}
