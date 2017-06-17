package com.riact.ricart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.MyAdapter;
import com.riact.ricart.utils.RiactDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

//import static com.riact.ricart.R.id.chumma;

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
    HashMap<String,ArrayList> itemMap=new HashMap<String, ArrayList>();
    RiactDbHandler userDb;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // p=new Point();
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
                    modelItems.add(new Model(itemdesc,0,itemCode,priceUom,price));

                   // modelItems.ad(new Model(itemdesc,0,itemCode,priceUom,price));
                }
                itemMap.put(currentDynamicKey,modelItems);
                prod.add(currentDynamicKey);
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

             /*   SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date=new Date();
                String dateStr=sd.format(date);
                Gson gson = new Gson();
                String arrayList = gson.toJson(Constants.orderList);
                userDb.addOrder(dateStr,arrayList);
                Type listType = new TypeToken<ArrayList<Model>>() {}.getType();

                ArrayList<Model> arr=new Gson().fromJson(arrayList,listType);

                Toast.makeText(getActivity(),userDb.getOrder(dateStr).get(0),Toast.LENGTH_LONG).show();*/
                showPopup(getActivity(),p,Constants.orderList);

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
                        Toast.makeText(getActivity(),selectedFromList,Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("Sethuru");

            }


        });

        return myView;





    }

    private void showPopup( Activity context, Point p, ArrayList<Model> data) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int popupWidth = displayMetrics.widthPixels;
        int popupHeight = displayMetrics.heightPixels;
        System.out.println("chumma");
        for (Model model : data) {
            System.out.println("chumma1");
            System.out.println(model.getName());
            System.out.println(model.getAmount());
            System.out.println(model.getItemCode());
            System.out.println(model.getPrice());
        }
        String dats="";
        for (Model temp : data) {
            dats+="\n"+temp.getName();
            System.out.println(temp.getName());
        }



        // Inflate the popup_layout.xml
        LinearLayout viewGroup = (LinearLayout) getActivity().findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


       final View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        TableLayout stk = (TableLayout)layout.findViewById(R.id.cart_table);
       final int drawableResId=R.drawable.cell_shape_header;
        TableRow tbrow0 = new TableRow(context);
        tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView tv0 = new TextView(getActivity());
        tv0.setText(Html.fromHtml(" <b>ITEM NAME</b>"));
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(drawableResId);
        tv0.setGravity(Gravity.CENTER);
        tv0.setHeight(65);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(Html.fromHtml(" <b>PRICE</b> "));
        tv1.setTextColor(Color.WHITE);
        tv1.setHeight(65);
        tv1.setBackgroundResource(drawableResId);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(Html.fromHtml(" <b>AMOUNT</b>"));
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(drawableResId);
        tv2.setGravity(Gravity.CENTER);
        tv2.setHeight(65);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(Html.fromHtml(" <b>QTY</b>"));
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(drawableResId);
        tv3.setGravity(Gravity.CENTER);
        tv3.setHeight(65);

        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (final Model model : data) {
            System.out.println("chumma1");
            System.out.println(model.getName());
            System.out.println(model.getAmount());
            System.out.println(model.getItemCode());
            System.out.println(model.getPrice());

            TableRow tbrow1 = new TableRow(context);
            tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView tv00 = new TextView(getActivity());
            tv00.setText(model.getName());
            tv00.setTextColor(Color.WHITE);
            tv00.setBackgroundResource(drawableResId);
            tv00.setGravity(Gravity.CENTER);
            tv00.setHeight(65);
            tbrow1.addView(tv00);
            final TextView tv11 = new TextView(getActivity());
            tv11.setText(String.valueOf(model.getPrice()));
            tv11.setTextColor(Color.WHITE);
            tv11.setHeight(65);
            tv11.setBackgroundResource(drawableResId);
            tv11.setGravity(Gravity.CENTER);
            tbrow1.addView(tv11);
           final TextView tv22 = new TextView(getActivity());

            tv22.setTextColor(Color.WHITE);
            tv22.setBackgroundResource(drawableResId);
            tv22.setGravity(Gravity.CENTER);
            tv22.setHeight(65);


           // model.setQuantity(tv22.);
            tbrow1.addView(tv22);
            EditText tv33 = new EditText(getActivity());

            System.out.println("ff"+chumma);
            tv33.setText(chumma);
            tv33.setTextColor(Color.WHITE);
            tv33.setBackgroundResource(drawableResId);
            tv33.setGravity(Gravity.CENTER);
            tv33.setHeight(65);
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
                    tv22.setText(String.valueOf(Float.parseFloat(chumma)*model.getPrice()));
                     f= f+Float.parseFloat(chumma)*model.getPrice();


                    System.out.println("aa"+chumma);

                }
            });

            tbrow1.addView(tv33);
           // String chumma = tv22.getText().toString();
            System.out.println("bb"+chumma);
            //tv33.setText(chumma);



            stk.addView(tbrow1);
        }
       // TextView total = new TextView(R.layout.popup_layout);

        TextView total=(TextView)layout.findViewById(R.id.chummaweee);
        total.setText("the total amount is "+f);

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
