package com.riact.ricart.utils;

import android.support.annotation.NonNull;

import java.util.Comparator;

/**
 * Created by koushik on 11/6/17.
 */

public class Model implements Comparable<Model> {
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

    @Override
    public String toString() {
        return this.name;
    }


    @Override
    public int compareTo(@NonNull Model o) {
        return 0;
    }


    public static Comparator<Model> ItemNameComparotor
            = new Comparator<Model>() {

        public int compare(Model fruit1, Model fruit2) {

            String fruitName1 = fruit1.getName().toUpperCase();
            String fruitName2 = fruit2.getName().toUpperCase();

            //ascending order
            return fruitName1.compareTo(fruitName2);

            //descending order
            //return fruitName2.compareTo(fruitName1);
        }

    };


}
