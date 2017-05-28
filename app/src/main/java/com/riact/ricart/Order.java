package com.riact.ricart;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
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

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView= inflater.inflate(R.layout.order,container,false);
        submitBtn = (Button) myView.findViewById(R.id.order_submit);
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.order_layout);

        TextView item1=new TextView(getActivity());
        item1.setText("OIL");
        item1.setTextColor(Color.BLACK);
        linearLayout.addView(item1);
        List<String> list = new ArrayList<String>();
        list.add("List1");
        list.add("List2");
        list.add("List3");
        list.add("List4");
        tv0= new MultiSelectionSpinner(getActivity());
        tv0.setItems(list);
        linearLayout.addView(tv0);
        TextView item2=new TextView(getActivity());
        item2.setText("RICE");
        item2.setTextColor(Color.BLACK);
        linearLayout.addView(item2);
        tv0= new MultiSelectionSpinner(getActivity());
        tv0.setItems(list);
        linearLayout.addView(tv0);




        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),tv0.getSelectedItemsAsString(),Toast.LENGTH_LONG).show();
            }
        });


        return myView;
    }
}