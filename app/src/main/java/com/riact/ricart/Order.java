package com.riact.ricart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koushik on 28/5/17.
 */

public class Order extends Fragment {
    MultiSelectionSpinner tv0;
    MultiSelectionSpinner spinner;
    Button submitBtn;
    Point p;


    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        p=new Point();
        myView= inflater.inflate(R.layout.order,container,false);
        submitBtn = (Button) myView.findViewById(R.id.order_submit);
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.order_layout);

        TextView item1=new TextView(getActivity());
        item1.setText("OIL");
        item1.setTextColor(Color.BLACK);
        linearLayout.addView(item1);
        List<String> list = new ArrayList<String>();
        list.add("Palm");
        list.add("Crude");
        list.add("Sun flower");
        list.add("Coconut");
        spinner= new MultiSelectionSpinner(getActivity());
        spinner.setItems(list);
        linearLayout.addView(spinner);
        TextView item2=new TextView(getActivity());
        item2.setText("RICE");
        item2.setTextColor(Color.BLACK);
        linearLayout.addView(item2);
        List<String> list1 = new ArrayList<String>();
        list1.add("Raw");
        list1.add("Half Baked");
        list1.add("Basmati");
        list1.add("IR 8");
        tv0= new MultiSelectionSpinner(getActivity());
        tv0.setItems(list1);
        linearLayout.addView(tv0);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data=spinner.getSelectedItemsAsString()+" , "+tv0.getSelectedItemsAsString();
                System.out.print("hiii:"+data);

                showPopup(getActivity(),p,data);


                Toast.makeText(getActivity(),data,Toast.LENGTH_LONG).show();
            }
        });


        return myView;
    }

    private void showPopup(final Activity context,Point p,String data) {
        int popupWidth = 500;
        int popupHeight = 800;

        String dats[]=data.split(",");
        data=" ";
        for (String tweet : dats) {
            data+=tweet+"\n";
        }




        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);



        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        TextView txt=(TextView) layout.findViewById(R.id.textView2);
        txt.setText(data);

        // Some offset to align the popup a bit to the right, and a bit down, relative to button's position.
        int OFFSET_X = 30;
        int OFFSET_Y = 30;

        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());

        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.CENTER_HORIZONTAL, p.x , p.y);

        // Getting a reference to Close button, and close the popup when clicked.
        Button close = (Button) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }
}