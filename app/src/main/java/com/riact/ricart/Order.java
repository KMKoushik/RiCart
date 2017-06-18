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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.riact.ricart.utils.RiactDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    List list;
    TableLayout stk1,stk;
    RelativeLayout linearLayout;
    TextView close;


    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        p=new Point();
        myView= inflater.inflate(R.layout.order,container,false);
        linearLayout = (RelativeLayout) myView.findViewById(R.id.order_layout);
        String jsonData="{\n" +
                "\"data\" : [\n" +
                "{\n" +
                "\"Description\": \"QBB 400GM DOZ\", \"UOM\":\"DOZ\",\"Qty\":\"12.0000\",\"Price\":\"1.00\",\"Amt\":\"12.00\"\n" +
                "},{\n" +
                "\"Description\": \"MEAT CURRY POWDER 25KG BAG\", \"UOM\":\"BAG\",\"Qty\":\"12.0000\",\"Price\":\"12.00\",\"Amt\":\"144.00\"\n" +
                "},\n" +
                "{\n" +
                "\"Description\": \"MEAT CURRY POWDER 100G PACKET\", \"UOM\":\"PKT\",\"Qty\":\"10.0000\",\"Price\":\"1.10\",\"Amt\":\"11.00\"\n" +
                "}\n" +
                "\n" +
                "]\n" +
                "}";


       /* try {
            showTable(myView,jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        RiactDbHandler db = new RiactDbHandler(getActivity());
        //Toast.makeText(getActivity(),db.getAllOrder().toString(),Toast.LENGTH_LONG).show();

        List<List> Orderlist=db.getAllOrder();
        stk1 = (TableLayout) myView.findViewById(R.id.ordertable);
        int count=1;



        for(final List list:Orderlist)
        {

            TableRow tbrow0 = new TableRow(getActivity());
            tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
            float textSize=11;
            TextView tv = new TextView(getActivity());
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setHeight(100);
            tv.setTextSize(textSize);
            tv.setText(""+count);
            tbrow0.addView(tv);

            TextView tv0 = new TextView(getActivity());
            tv0.setTextColor(Color.BLACK);
            tv0.setGravity(Gravity.CENTER);
            tv0.setHeight(100);
            tv0.setTextSize(textSize);
            tv0.setText((String)list.get(0));
            tbrow0.addView(tv0);


            TextView tv1 = new TextView(getActivity());
            tv1.setTextColor(Color.BLACK);
            tv1.setGravity(Gravity.CENTER);
            tv1.setHeight(100);
            tv1.setTextSize(textSize);
            tv1.setText((String)list.get(2));
            tbrow0.addView(tv1);

            TextView tv2 = new TextView(getActivity());
            tv2.setTextColor(Color.BLACK);
            tv2.setGravity(Gravity.CENTER);
            tv2.setHeight(100);
            tv2.setTextSize(textSize);
            String txt=(String)list.get(3);
            String value;
            if(txt.equals("true"))
                value="submitted";
            else
                value="saved";
            tv2.setText(value);

            tbrow0.addView(tv2);

            TextView tv3 = new TextView(getActivity());
            tv3.setTextColor(Color.BLACK);
            tv3.setGravity(Gravity.CENTER);
            tv3.setHeight(100);
            tv3.setTextSize(textSize);
            tv3.setText(Html.fromHtml("<u>View</u>"));
            tbrow0.addView(tv3);
            tv3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showTable(myView,(String)list.get(1),(String) list.get(0));
                        linearLayout.removeView(stk1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


            count++;

            stk1.addView(tbrow0);


        }


        return myView;
    }

    public void showTable(View myView,String json,String date) throws JSONException {
        int drawableResId=R.drawable.cell_shape_header;
        stk=new TableLayout(getActivity());
        stk.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        stk.setStretchAllColumns(true);
        stk.setShrinkAllColumns(true);
        //stk=(TableLayout)myView.findViewById(R.id.order_items) ;
        TableRow tbrow0 = new TableRow(getActivity());

        tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        float textSize=11;
        TextView tv0 = new TextView(getActivity());
        tv0.setText(Html.fromHtml(" <b>Description</b>"));
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(drawableResId);
        tv0.setGravity(Gravity.CENTER);
        tv0.setHeight(65);
        tv0.setTextSize(textSize);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(Html.fromHtml(" <b>UOM</b> "));
        tv1.setTextColor(Color.WHITE);
        tv1.setHeight(65);
        tv1.setTextSize(textSize);
        tv1.setBackgroundResource(drawableResId);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(Html.fromHtml(" <b>Qty</b>"));
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(drawableResId);
        tv2.setGravity(Gravity.CENTER);
        tv2.setHeight(65);
        tv2.setTextSize(textSize);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(Html.fromHtml(" <b>Price</b>"));
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(drawableResId);
        tv3.setGravity(Gravity.CENTER);
        tv3.setHeight(65);
        tv3.setTextSize(textSize);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(getActivity());
        tv4.setText(Html.fromHtml(" <b>Amt</b>"));
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundResource(drawableResId);
        tv4.setGravity(Gravity.CENTER);
        tv4.setHeight(65);
        tv4.setTextSize(textSize);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);

        JSONArray jsonObj = new JSONArray(json);
        for (int i = 0; i < jsonObj.length(); i++) {
            if(i%2==0)
                drawableResId=R.drawable.cell_shape;
            else
                drawableResId=R.drawable.cell_shape2;

            JSONObject c = jsonObj.getJSONObject(i);
            String description = c.getString("name");
            String uom = c.getString("uom");
            String Qty = c.getString("quantity");
            String Price = c.getString("price");
            String amt= c.getString("amount");
            TableRow tbrow = new TableRow(getActivity());
            tbrow.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView t1v = new TextView(getActivity());
            t1v.setText(description);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setHeight(100);

            t1v.setTextSize(textSize);
            t1v.setBackgroundResource(drawableResId);
            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText(uom);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setHeight(100);
            t2v.setTextSize(textSize);
            t2v.setBackgroundResource(drawableResId);
            tbrow.addView(t2v);
            TextView t3v = new TextView(getActivity());
            t3v.setText(Qty);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            t3v.setHeight(100);
            t3v.setTextSize(textSize);
            t3v.setBackgroundResource(drawableResId);
            tbrow.addView(t3v);
            TextView t4v = new TextView(getActivity());
            t4v.setText(Price);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            t4v.setHeight(100);
            t4v.setTextSize(textSize);
            t4v.setBackgroundResource(drawableResId);
            tbrow.addView(t4v);

            TextView t5v = new TextView(getActivity());
            t5v.setText(amt);
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            t5v.setHeight(100);
            t5v.setTextSize(textSize);
            t5v.setBackgroundResource(drawableResId);
            tbrow.addView(t5v);
            stk.addView(tbrow);
            // Phone node is JSON Object
        }

        close=new TextView(getActivity());
        close.setText("close");
        close.setGravity(Gravity.CENTER);
        stk.addView(close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeView(stk);
                linearLayout.addView(stk1);
            }
        });

        linearLayout.addView(stk);





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