package com.riact.ricart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.MyAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.R.attr.fragment;
import static android.R.attr.value;
import static com.riact.ricart.CheckBoxClick.selectedStrings;

/**
 * Created by anton on 05/29/17.
 */

public class NewOrder extends Fragment {

    View myView;
    public ListView lv;
    public ListView listview;
    ArrayAdapter<String> adapter;
    private LayoutInflater menuInflater;
    CheckedTextView checkstat;
    String selectedFromList;
    LinearLayout linearLayout;
    Point p=new Point();
     EditText inputSearch;
    Model[] modelItems;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // p=new Point();
        myView = inflater.inflate(R.layout.new_order, container, false);

        linearLayout = (LinearLayout) myView.findViewById(R.id.neworder_layout);
        listview= (ListView) myView.findViewById(R.id.listView1);

        modelItems = new Model[5];
        modelItems[0] = new Model("pizza", 0);
        modelItems[1] = new Model("burger", 1);
        modelItems[2] = new Model("olives", 1);
        modelItems[3] = new Model("orange", 0);
        modelItems[4] = new Model("tomato", 1);

        MyAdapter myAdapter = new MyAdapter(getActivity(), modelItems);
        listview.setAdapter(myAdapter);
        listview.setVisibility(View.VISIBLE);

        ArrayList<String > prod=new ArrayList<>();



        try {
            JSONObject jsonObj = new JSONObject(Constants.items);
            Iterator keys = jsonObj.keys();

            while(keys.hasNext()) {

                String currentDynamicKey = (String)keys.next();
                prod.add(currentDynamicKey);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        lv = (ListView) myView.findViewById(R.id.list_view);
        inputSearch = (EditText) myView.findViewById(R.id.inputSearch);
        lv.setVisibility(View.INVISIBLE);


        adapter = new ArrayAdapter<String>(getActivity(), R.layout.listitem1, R.id.label, prod);
        lv.setAdapter(adapter);
        Button cart=(Button)myView.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(getActivity(),p,CheckBoxClick.selectedStrings);
                Toast.makeText(getActivity(),CheckBoxClick.selectedStrings.toString(),Toast.LENGTH_LONG).show();
            }
        });





        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                NewOrder.this.adapter.getFilter().filter(cs);
                lv.setVisibility(View.VISIBLE);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                         selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
                        linearLayout.removeView(lv);

                        inputSearch.setText(selectedFromList);
                        Toast.makeText(getActivity(),selectedFromList,Toast.LENGTH_LONG).show();




                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("mudingichu");

            }


        });

        return myView;





    }

    private void showPopup(final Activity context, Point p, ArrayList<String> data) {
        int popupWidth = 750;
        int popupHeight = 1300;
        String dats="";
        for (String temp : data) {
            dats+="\n"+temp;
            System.out.println(temp);
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

   // @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.new_order,null);
        return true;
    }

    public LayoutInflater getMenuInflater() {
        return menuInflater;
    }
}
