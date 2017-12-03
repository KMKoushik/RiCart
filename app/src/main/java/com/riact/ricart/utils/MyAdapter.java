package com.riact.ricart.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.riact.ricart.R;

import java.util.ArrayList;


/**
 * Created by koushik on 11/6/17.
 */

public class MyAdapter extends ArrayAdapter  {
        ArrayList<Model> modelItems = null;
        Context context;
        CheckBox cb;
        TextView name;

        TextView itemUom;
        TextView itemPrice;
        public MyAdapter(Context context, ArrayList resource) {
                super(context, R.layout.list_item,resource);
                // TODO Auto-generated constructor stub
                this.context = context;
                this.modelItems = resource;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
                // TODO Auto-generated method stub
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                name = (TextView) convertView.findViewById(R.id.textView1);
                 cb= (CheckBox) convertView.findViewById(R.id.checkBox1);
                itemUom = (TextView) convertView.findViewById(R.id.itemuom);
                itemPrice = (TextView) convertView.findViewById(R.id.itemprice);

                NetworkImageView imageView = (NetworkImageView) convertView.findViewById(R.id.imageView);

                // Image url
                String image_url = Constants.webAddress+"/items/";

                // ImageLoader class instance
                ImageLoader imageLoader = AppSingleton.getInstance(convertView.getContext())
                        .getImageLoader();
                imageLoader.get(image_url, ImageLoader.getImageListener(imageView,
                        R.drawable.ricart_logo, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(image_url, imageLoader);

                name.setText(modelItems.get(position).getName());
                itemUom.setText("UOM : "+modelItems.get(position).getUom());
                itemPrice.setText("Price : "+String.format("%.2f",modelItems.get(position).getPrice()));

                if(position%2==0)
                        convertView.setBackgroundResource(R.drawable.itemclr1);
                else
                        convertView.setBackgroundResource(R.drawable.itemclr2);


                if(modelItems.get(position).getValue() == 1)
                        cb.setChecked(true);
                else
                        cb.setChecked(false);
                return convertView;
        }

}
