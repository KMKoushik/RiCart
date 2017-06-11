package com.riact.ricart.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.riact.ricart.R;

import java.util.ArrayList;


/**
 * Created by koushik on 11/6/17.
 */

public class MyAdapter extends ArrayAdapter implements AdapterView.OnItemClickListener {
        ArrayList<Model> modelItems = null;
        Context context;
        CheckBox cb;
        TextView name;

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

                name.setText(modelItems.get(position).getName());
                if(modelItems.get(position).getValue() == 1)
                        cb.setChecked(true);
                else
                        cb.setChecked(false);
                return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.print("hello:");
        }
}
