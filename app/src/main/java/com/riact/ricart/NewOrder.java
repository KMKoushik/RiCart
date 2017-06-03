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

import java.util.ArrayList;
import java.util.HashMap;

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
    public  ListView listview1;
    ArrayAdapter<String> adapter;
    private LayoutInflater menuInflater;
    CheckedTextView checkstat;
    String selectedFromList;
    Point p=new Point();
//    final CheckedTextView checkBox = (CheckedTextView) myView.findViewById(R.id.checkedTextView1);
    //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
   // final SharedPreferences.Editor editor = preferences.edit();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // p=new Point();
        myView = inflater.inflate(R.layout.new_order, container, false);
        //submitBtn = (Button) myView.findViewById(R.id.order_submit);
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.neworder_layout);


        // Listview Adapter


        // Search EditText
        final EditText inputSearch;


        // ArrayList for Listview
        ArrayList<HashMap<String, String>> productList;






            // Listview Data
        String products[] = {"oil", "rice", "snacks", "cosmetics", "vegetables"};

        lv = (ListView) myView.findViewById(R.id.list_view);
        listview = (ListView)myView.findViewById(R.id.listView1);
       // listview1 = (ListView)myView.findViewById(R.id.listView2) ;
        checkstat=(CheckedTextView)myView.findViewById(R.id.checkedTextView1);
        inputSearch = (EditText) myView.findViewById(R.id.inputSearch);
        lv.setVisibility(View.INVISIBLE);

        // Adding items to listview
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.listitem1, R.id.label, products);
        lv.setAdapter(adapter);
        Button cart=(Button)myView.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(getActivity(),p,CheckBoxClick.selectedStrings);
                Toast.makeText(getActivity(),CheckBoxClick.selectedStrings.toString(),Toast.LENGTH_LONG).show();
            }
        });





      /*  lstview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Toast.makeText(getActivity(), "An item of the ListView is clicked.", Toast.LENGTH_LONG).show();
            }
        });*/
        // Bind data to the ListView
//        lstview.setAdapter(adapter);


        /**
         * Enabling Search Filter
         * */
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
               /// listview.setVisibility(View.INVISIBLE);
                NewOrder.this.adapter.getFilter().filter(cs);

                listview.setVisibility(View.INVISIBLE);
                listview.setVisibility(View.INVISIBLE);
                //lv.setVisibility(View.VISIBLE);

                lv.setVisibility(View.INVISIBLE);
                lv.setVisibility(View.VISIBLE);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                         selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
                        lv.setVisibility(View.INVISIBLE);

                        inputSearch.setText(selectedFromList);
                        String search= inputSearch.getText().toString();
                        Toast.makeText(getActivity(),selectedFromList,Toast.LENGTH_LONG).show();
                       // listview = (ListView)myView.findViewById(R.id.list_view);

                       // listview.setVisibility(View.INVISIBLE);
                        //listview.setVisibility(View.INVISIBLE);
                        //string array
                        String[] foody = {"sunflower", "palm", "groundnut", "coconut", "navrathna", "edho"};
                        String[] rice1 = {"raw rice", "sambha" ,"IR8" ,"full boiled rice"};
                        String[] snacks = {"lays", "cheetos" ,"bingo" ,"potato chips"};
                        String[] cosmetics = {"fairness cream", "eyeliner" ,"eye lashes" ,"mehandi"};
                        String[] vegetables = {"carrot", "beetrot" ,"beans" ,"bitter gourd"};
                        String[] value = {};
                        //ArrayList<String> selectedStrings = new ArrayList<String>();
                        // set adapter for listview

                        if(selectedFromList=="oil")
                        {
                            System.out.println(selectedFromList);
                            value=foody;
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, value);
                            listview.setAdapter(adapter1);
                            listview.setVisibility(View.VISIBLE);

                           /* for( int i = 0,j=0; i <= selectedStrings.size() - 1; i++)
                            {
                                for(j=0; j <= adapter1.getCount() - 1; i++)
                                    // get element number 0 and 1 and put it in a variable,
                                    // and the next time get element      1 and 2 and put this in another variable.
                                    if(selectedStrings.get(i)==adapter1.getItem(j))
                                    {
                                        System.out.println(selectedStrings.get(i));
                                        System.out.println(adapter1.getItem(i));
                                        checkstat.setChecked(true);
                                    }

                            }*/
                        }
                        if(selectedFromList=="rice")
                        {
                            System.out.println(selectedFromList);
                            value=rice1;
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, value);
                            listview.setAdapter(adapter1);
                            listview.setVisibility(View.VISIBLE);

                           /* for( int i = 0,j=0; i <= selectedStrings.size() - 1; i++)
                            {
                                for(j=0; j <= adapter1.getCount() - 1; i++)
                                    // get element number 0 and 1 and put it in a variable,
                                    // and the next time get element      1 and 2 and put this in another variable.
                                    if(selectedStrings.get(i)==adapter1.getItem(j))
                                    {
                                        System.out.println(selectedStrings.get(i));
                                        System.out.println(adapter1.getItem(i));
                                        checkstat.setChecked(true);
                                    }

                            }*/
                        }
                        if(selectedFromList=="snacks")
                        {
                            System.out.println(selectedFromList);
                            value=snacks;
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, value);
                            listview.setAdapter(adapter1);
                            listview.setVisibility(View.VISIBLE);

                           /* for( int i = 0,j=0; i <= selectedStrings.size() - 1; i++)
                            {
                                for(j=0; j <= adapter1.getCount() - 1; i++)
                                    // get element number 0 and 1 and put it in a variable,
                                    // and the next time get element      1 and 2 and put this in another variable.
                                    if(selectedStrings.get(i)==adapter1.getItem(j))
                                    {
                                        System.out.println(selectedStrings.get(i));
                                        System.out.println(adapter1.getItem(i));
                                        checkstat.setChecked(true);
                                    }

                            }*/
                        }
                        if(selectedFromList=="cosmetics")
                        {
                            System.out.println(selectedFromList);
                            value=cosmetics;
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, value);
                            listview.setAdapter(adapter1);
                            listview.setVisibility(View.VISIBLE);

                           /* for( int i = 0,j=0; i <= selectedStrings.size() - 1; i++)
                            {
                                for(j=0; j <= adapter1.getCount() - 1; i++)
                                    // get element number 0 and 1 and put it in a variable,
                                    // and the next time get element      1 and 2 and put this in another variable.
                                    if(selectedStrings.get(i)==adapter1.getItem(j))
                                    {
                                        System.out.println(selectedStrings.get(i));
                                        System.out.println(adapter1.getItem(i));
                                        checkstat.setChecked(true);
                                    }

                            }*/
                        }
                        if(selectedFromList=="vegetables")
                        {
                            System.out.println(selectedFromList);
                            value=vegetables;
                            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, value);
                            listview.setAdapter(adapter1);
                            listview.setVisibility(View.VISIBLE);

                           /* for( int i = 0,j=0; i <= selectedStrings.size() - 1; i++)
                            {
                                for(j=0; j <= adapter1.getCount() - 1; i++)
                                    // get element number 0 and 1 and put it in a variable,
                                    // and the next time get element      1 and 2 and put this in another variable.
                                    if(selectedStrings.get(i)==adapter1.getItem(j))
                                    {
                                        System.out.println(selectedStrings.get(i));
                                        System.out.println(adapter1.getItem(i));
                                        checkstat.setChecked(true);
                                    }

                            }*/
                        }
                        /*else
                        {

                            System.out.println(selectedFromList);
                            value=rice1;
                            listview.setVisibility(View.VISIBLE);
                            //listview.setVisibility(View.VISIBLE);
                        }*/
                     //  ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, value);
                       // listview.setAdapter(adapter1);

                        listview.setItemsCanFocus(false);
                        //listview1.setAdapter(adapter1);
                        //listview1.setItemsCanFocus(false);


                        // we want multiple clicks
                        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        listview.setOnItemClickListener(new CheckBoxClick());
                        //listview1.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                        //listview1.setOnItemClickListener(new CheckBoxClick());


                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("mudingichu");

            }

            /*@Override
            //public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }*/
        });

       /* listview = (ListView)myView.findViewById(R.id.listView1);
        listview.setVisibility(View.INVISIBLE);
        //listview.setVisibility(View.INVISIBLE);
        //string array
        String[] foody = {"sunflower", "palm", "groundnut", "coconut", "navrathna", "edho"};
        String[] rice1 = {"ponni", "sambha" ,"pacha arisi" ,"pulungal arisi"};
        String[] value = {};
        ArrayList<String> selectedStrings = new ArrayList<String>();
        // set adapter for listview
        if(selectedFromList=="oil")
        {
            System.out.println(selectedFromList);
            value=foody;
            listview.setVisibility(View.VISIBLE);
        }
        else
        {

            value=rice1;
            listview.setVisibility(View.VISIBLE);
        }*/

        /*ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.list_item, value);
        listview.setAdapter(adapter1);
        listview.setItemsCanFocus(false);
        // we want multiple clicks
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setOnItemClickListener(new CheckBoxClick());*/
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
