package com.riact.ricart.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.riact.ricart.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by koushik on 11/6/17.
 */

public class ItemAdapter extends ArrayAdapter  {
        ArrayList<Model> modelItems = null;
        Context context;
        CheckBox cb;
        TextView name;
        private Filter filter;

        TextView itemUom;
        TextView itemPrice;
        public ItemAdapter(Context context, ArrayList resource) {
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
                String image_url = Constants.webAddress+"/items/"+modelItems.get(position).getItemCode()+".png";

                // ImageLoader class instance
                ImageLoader imageLoader = AppSingleton.getInstance(convertView.getContext())
                        .getImageLoader();
                imageLoader.get(image_url, ImageLoader.getImageListener(imageView,
                        R.drawable.loading, android.R.drawable
                                .ic_dialog_alert));
                imageView.setImageUrl(image_url, imageLoader);

                name.setText(modelItems.get(position).getName());
                itemUom.setText("UOM : "+modelItems.get(position).getUom());
                itemPrice.setText("Price : "+String.format("%.2f",modelItems.get(position).getPrice()));

                        convertView.setBackgroundResource(R.drawable.dashboard_row);



                if(modelItems.get(position).getValue() == 1)
                        cb.setChecked(true);
                else
                        cb.setChecked(false);
                return convertView;
        }

        @Override
        public Filter getFilter() {
                if (filter == null)
                        filter = new AppFilter<Model>(modelItems);
                return filter;
        }


        private class AppFilter<T> extends Filter {

                private ArrayList<T> sourceObjects;

                public AppFilter(List<T> objects) {
                        sourceObjects = new ArrayList<T>();
                        synchronized (this) {
                                sourceObjects.addAll(objects);
                        }
                }

                @Override
                protected FilterResults performFiltering(CharSequence chars) {
                        String filterSeq = chars.toString().toLowerCase();
                        FilterResults result = new FilterResults();
                        if (filterSeq != null && filterSeq.length() > 0) {
                                ArrayList<T> filter = new ArrayList<T>();

                                for (T object : sourceObjects) {
                                        // the filtering itself:
                                        if (object.toString().toLowerCase().contains(filterSeq))
                                                filter.add(object);
                                }
                                result.count = filter.size();
                                result.values = filter;
                        } else {
                                // add all objects
                                synchronized (this) {
                                        result.values = sourceObjects;
                                        result.count = sourceObjects.size();
                                }
                        }
                        return result;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {
                        // NOTE: this function is *always* called from the UI thread.
                        ArrayList<T> filtered = (ArrayList<T>) results.values;
                        notifyDataSetChanged();
                        clear();
                        for (int i = 0, l = filtered.size(); i < l; i++)
                                add((Model) filtered.get(i));
                        notifyDataSetInvalidated();
                }
        }

}
