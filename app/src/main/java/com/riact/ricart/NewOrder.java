package com.riact.ricart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.MeasureFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.MyAdapter;
import com.riact.ricart.utils.RiactDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Created by anton on 05/29/17.
 */

public class NewOrder extends Fragment {

    View myView;
    public ListView lv,listview;
    ArrayAdapter<String> adapter;
    private LayoutInflater menuInflater;
    String selectedFromList;
    String chumma;
    float f=0;
    LinearLayout linearLayout;
    Point p=new Point();
    EditText inputSearch;
    ArrayList<Model> ItemList;
    HashMap<String,ArrayList<Model>> itemMap=new HashMap<String, ArrayList<Model>>();
    RiactDbHandler userDb;
    TextView total,gst,tobepaid ;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.new_order, container, false);
        userDb=new RiactDbHandler(myView.getContext());
        linearLayout = (LinearLayout) myView.findViewById(R.id.neworder_layout);
        listview= (ListView) myView.findViewById(R.id.listView1);







        ArrayList<String > prod=new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(Constants.items);
            Iterator keys = jsonObj.keys();

            while(keys.hasNext()) {
                ArrayList modelItems = new ArrayList<>();
                String currentDynamicKey = (String)keys.next();
                JSONArray jsonArray=jsonObj.getJSONArray(currentDynamicKey);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item= jsonArray.getJSONObject(i);
                    String itemCode=item.getString("item_code");
                    String itemdesc=item.getString("item_desc");
                    String priceUom=item.getString("price_uom");
                    float price=(float) item.getDouble("selling_price");
                    modelItems.add(new Model(itemdesc,0,itemCode,priceUom,price,currentDynamicKey,i));

                   // modelItems.ad(new Model(itemdesc,0,itemCode,priceUom,price));
                }
                itemMap.put(currentDynamicKey,modelItems);
                prod.add(currentDynamicKey);
            }

            if(!Constants.orderList.isEmpty())
            {
                for(Model model:Constants.orderList)
                {
                    Model item=itemMap.get(model.getGroup()).get(model.getIndex());
                    item.putValue(1);
                    item.setQuantity(model.getQuantity());
                    item.setAmount(model.getAmount());

                }
                showPopup(getActivity(),p);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        listview.setVisibility(View.INVISIBLE);

        lv = (ListView) myView.findViewById(R.id.list_view);
        inputSearch = (EditText) myView.findViewById(R.id.inputSearch);




        adapter = new ArrayAdapter<String>(getActivity(), R.layout.listitem1, R.id.label, prod);
        lv.setAdapter(adapter);
        Button cart=(Button)myView.findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showPopup(getActivity(),p);

            }
        });

        Button clear=(Button)myView.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputSearch.setText("");
            }
        });
        lv.setVisibility(View.INVISIBLE);




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
                listview.setVisibility(View.INVISIBLE);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                         selectedFromList =(String) (lv.getItemAtPosition(myItemInt));
                        inputSearch.setText(selectedFromList);
                        ItemList=itemMap.get(selectedFromList);
                        MyAdapter itemAdapter = new MyAdapter(getActivity(), ItemList);
                        listview.setAdapter(itemAdapter);
                        lv.setVisibility(View.INVISIBLE);
                        listview.setVisibility(View.VISIBLE);
                        listview.setItemsCanFocus(false);
                        listview.setOnItemClickListener( new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView , View view , int position ,long arg3)
                            {
                                CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox1);
                                if(!cb.isChecked()) {
                                    Constants.orderList.add(ItemList.get(position));

                                    ItemList.get(position).putValue(1);

                                }
                                else {
                                    Constants.orderList.remove(ItemList.get(position));

                                    ItemList.get(position).putValue(0);
                                }

                                cb.setChecked(!cb.isChecked());

                            }
                        });
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("Sethuru");

            }


        });

        inputSearch.setText("");

        return myView;





    }

    private void showPopup(Activity context, Point p) {



        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int popupWidth = displayMetrics.widthPixels;
        int popupHeight = displayMetrics.heightPixels;
        System.out.println("chumma");



        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) getActivity().findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


       final View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);
        total = new TextView(getActivity());
        total.setTextColor(Color.BLACK);
        total.setGravity(Gravity.RIGHT);
        gst=new TextView(getActivity());
        gst.setTextColor(Color.BLACK);
        gst.setGravity(Gravity.RIGHT);
        tobepaid=new TextView(getActivity());
        tobepaid.setTextColor(Color.BLACK);
        tobepaid.setGravity(Gravity.RIGHT);

        final TableLayout stk = (TableLayout)layout.findViewById(R.id.cart_table);
       final int drawableResId=R.drawable.cell_shape_header;
        float textSize=11;
        TableRow tbrow0 = new TableRow(context);
        TableLayout.LayoutParams tp=new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tp.topMargin=25;
        tbrow0.setLayoutParams(tp);
        tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView tv0 = new TextView(getActivity());
        tv0.setText(Html.fromHtml(" <b>ITEM NAME</b>"));
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(drawableResId);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(textSize);
        tv0.setHeight(65);
        tbrow0.addView(tv0);
        TextView tv5 = new TextView(getActivity());
        tv5.setText(Html.fromHtml(" <b>UOM</b>"));
        tv5.setTextColor(Color.WHITE);
        tv5.setBackgroundResource(drawableResId);
        tv5.setGravity(Gravity.CENTER);
        tv5.setTextSize(textSize);
        tv5.setHeight(65);
        tbrow0.addView(tv5);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(Html.fromHtml(" <b>PRICE</b> "));
        tv1.setTextColor(Color.WHITE);
        tv1.setHeight(65);
        tv1.setTextSize(textSize);
        tv1.setBackgroundResource(drawableResId);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(Html.fromHtml(" <b>QTY</b>"));
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(drawableResId);
        tv2.setGravity(Gravity.CENTER);
        tv2.setHeight(65);
        tv2.setTextSize(textSize);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(Html.fromHtml(" <b>AMT</b>"));
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(drawableResId);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(textSize);
        tv3.setHeight(65);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(getActivity());
        tv4.setText(Html.fromHtml(" <b>ACTION</b>"));
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundResource(drawableResId);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextSize(textSize);
        tv4.setHeight(65);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);
        final int count=0;
        for (final Model model : Constants.orderList) {
            int background=R.drawable.cell_shape;
            final TableRow tbrow1 = new TableRow(context);
            tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView tv00 = new TextView(getActivity());
            tv00.setText(model.getName());
            tv00.setTextColor(Color.BLACK);
            //tv00.setBackgroundResource(background);
            tv00.setMaxLines(2);
            tv00.setWidth(250);
            tv00.setGravity(Gravity.LEFT);
            tv00.setHeight(75);
            tv00.setTextSize(textSize);
            tbrow1.addView(tv00);
            TextView tv55 = new TextView(getActivity());
            tv55.setText(model.getUom());
            tv55.setTextColor(Color.BLACK);
            //tv00.setBackgroundResource(background);
            tv55.setGravity(Gravity.CENTER);
            tv55.setHeight(75);
            tv55.setTextSize(textSize);
            tbrow1.addView(tv55);
            final TextView tv11 = new TextView(getActivity());
            tv11.setText(String.valueOf(model.getPrice()));
            tv11.setTextColor(Color.BLACK);
            tv11.setHeight(75);
            //tv11.setBackgroundResource(background);
            tv11.setGravity(Gravity.CENTER);
            tv11.setTextSize(textSize);
            tbrow1.addView(tv11);
            final TextView tv22 = new TextView(getActivity());

            tv22.setTextColor(Color.BLACK);
            //tv22.setBackgroundResource(background);
            tv22.setGravity(Gravity.RIGHT);
            tv22.setHeight(75);
            tv22.setTextSize(textSize);


            final EditText tv33 = new EditText(getActivity());
            tv33.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            Float quantiy=model.getQuantity();
            tv33.setSelectAllOnFocus(true);
            tv33.setTextColor(Color.BLACK);
            tv33.setGravity(Gravity.CENTER);
            tv33.setHeight(75);
            tv33.setTextSize(textSize);
            tv33.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                        chumma = s.toString();
                    if(chumma.equals("")||chumma.equals("."))
                        chumma="0";

                        tv22.setText(String.format("%.2f",roundOff(Float.parseFloat(chumma) * model.getPrice())));


                    model.setQuantity(Float.parseFloat(chumma));
                    model.setAmount(roundOff(Float.parseFloat(chumma) * model.getPrice()));
                    f=calculateTotal();
                        total.setText("Total : " + roundOff(f)+" SGD");
                    float gstVal=(f*7)/100;
                    float finalamt=f+gstVal;
                    total.setText("Total : SGD  " + String.format("%.2f",roundOff(f)));
                    gst.setText("GST @ 7%: SGD  "+String.format("%.2f",roundOff(gstVal)));
                    tobepaid.setText(Html.fromHtml("<b>Grant Total : SGD  "+String.format("%.2f",roundOff(finalamt))+"</b>"));
                }
            });



            // model.setQuantity(tv22.);

            tbrow1.addView(tv33);
            tbrow1.addView(tv22);
            if(quantiy!=0.0)
                tv33.setText(""+String.format("%.4f",quantiy));
            else
                tv33.setText("1.0000");

                ImageButton tv44 = new ImageButton(getActivity());
            tv44.setBackgroundColor(getResources().getColor(R.color.background));
            tv44.setImageResource(R.drawable.delete);
            tbrow1.addView(tv44);

            tv44.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stk.removeView(tbrow1);
                    Constants.orderList.remove(model);
                    final ArrayList<Model> ItemList=itemMap.get(model.getGroup());
                    MyAdapter itemAdapter = new MyAdapter(getActivity(), ItemList);
                    listview.setAdapter(itemAdapter);
                    listview.setAdapter(itemAdapter);
                    lv.setVisibility(View.INVISIBLE);
                    listview.setVisibility(View.VISIBLE);
                    listview.setItemsCanFocus(false);
                    ItemList.get(model.getIndex()).putValue(0);
                    listview.setOnItemClickListener( new AdapterView.OnItemClickListener()
                    {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView , View view , int position ,long arg3)
                        {
                            CheckBox cb = (CheckBox)view.findViewById(R.id.checkBox1);
                            if(!cb.isChecked()) {
                                Constants.orderList.add(ItemList.get(position));

                                ItemList.get(position).putValue(1);

                            }
                            else {
                                Constants.orderList.remove(ItemList.get(position));

                                ItemList.get(position).putValue(0);
                            }

                            cb.setChecked(!cb.isChecked());

                        }
                    });

                    f=roundOff(calculateTotal());
                    float gstVal=(f*7)/100;
                    float finalamt=f+gstVal;
                    total.setText("Total : SGD  " + String.format("%.2f",roundOff(f)));
                    gst.setText("GST @ 7%: SGD  "+String.format("%.2f",roundOff(gstVal)));
                    tobepaid.setText(Html.fromHtml("<b>Grant Total : SGD  "+String.format("%.2f",roundOff(finalamt))+"</b>"));
                }
            });

            stk.addView(tbrow1);

        }
        stk.addView(total);
        stk.addView(gst);
        stk.addView(tobepaid);

        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

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

        Button submit = (Button)layout.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!Constants.orderList.isEmpty()) {
                    Gson gson = new Gson();
                    String arrayList = gson.toJson(Constants.orderList);
                    if (Constants.date.equals("")) {
                        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        Date date = new Date();
                        String dateStr = sd.format(date);


                        userDb.addOrder(dateStr, arrayList, "" + roundOff(f), "false");
                        Type listType = new TypeToken<ArrayList<Model>>() {
                        }.getType();

                        ArrayList<Model> arr = new Gson().fromJson(arrayList, listType);
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                        Constants.orderList.clear();
                        popup.dismiss();
                        getActivity().finish();

                    } else {
                        userDb.updateOrder(Constants.date, arrayList, "" + roundOff(f));
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                        Constants.orderList.clear();
                        Constants.date = "";
                        popup.dismiss();
                        getActivity().finish();
                    }
                }
                else {
                    Toast.makeText(getActivity(),"Cart is empty. Unable to save!",Toast.LENGTH_LONG).show();
                }



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



    public float calculateTotal()
    {
        float total=0;
        for(Model model:Constants.orderList)
        {
            total=total+(model.getPrice()*model.getQuantity());

        }

        return total;
    }

    public float roundOff(float val)
    {
        double value=Math.round(val*100)/100D;
        return (float)value;
    }

}
