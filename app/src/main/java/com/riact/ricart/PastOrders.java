package com.riact.ricart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
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
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.riact.ricart.utils.AppSingleton;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.RiactDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Created by koushik on 28/5/17.
 */

public class PastOrders extends Fragment {
    Button submitBtn,close,delete,editBtn;
    Point p;
    TableLayout stk1,stk;
    LinearLayout buttonLayout,linearLayout;
    ImageView closeImg,editImg;
    RiactDbHandler db;
    List<List> Orderlist;
    ArrayList<Model> orderItems;
    float f;
    Boolean flag;
    JSONObject itemJson;
    JSONArray mainArray;


    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        p=new Point();
        myView= inflater.inflate(R.layout.order,container,false);
        linearLayout = (LinearLayout) myView.findViewById(R.id.order_layout);
        buttonLayout = (LinearLayout)myView.findViewById(R.id.menu_layout);
        TextView textView = (TextView) myView.findViewById(R.id.order_header);
        textView.setText("Order History");

        db=new RiactDbHandler(getActivity());


        try {
            showOrder();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return myView;
    }

    public void showOrder() throws JSONException {
        Constants.orderList.clear();

        stk1 = new TableLayout(getActivity());
        int drawableResId=R.drawable.dashboard_header;
        TableLayout.LayoutParams lp=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,5,0,5);
        stk1.setLayoutParams(lp);
        stk1.setStretchAllColumns(true);
        stk1.setShrinkAllColumns(true);
        stk1.setGravity(Gravity.CENTER);
        int count=1;
        float textSize=13;
        /*TableRow tbrow0 = new TableRow(getActivity());
        tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView tv0 = new TextView(getActivity());
        tv0.setText(Html.fromHtml(" <b>SL NO</b>"));
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(textSize);
        tv0.setHeight(65);
        tv0.setBackgroundResource(drawableResId);
        tbrow0.addView(tv0);
        TextView tv5 = new TextView(getActivity());
        tv5.setText(Html.fromHtml(" <b>ORDER</b> "));
        tv5.setTextColor(Color.BLACK);
        tv5.setHeight(65);
        tv5.setTextSize(textSize);
        tv5.setGravity(Gravity.CENTER);
        tv5.setBackgroundResource(drawableResId);
        tbrow0.addView(tv5);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(Html.fromHtml(" <b>DATE</b> "));
        tv1.setTextColor(Color.BLACK);
        tv1.setHeight(65);
        tv1.setTextSize(textSize);
        tv1.setGravity(Gravity.CENTER);
        tv1.setBackgroundResource(drawableResId);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(Html.fromHtml(" <b>AMOUNT</b>"));
        tv2.setTextColor(Color.BLACK);
        tv2.setGravity(Gravity.CENTER);
        tv2.setHeight(65);
        tv2.setTextSize(textSize);
        tv2.setBackgroundResource(drawableResId);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(Html.fromHtml(" <b>STATUS</b>"));
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(textSize);
        tv3.setHeight(65);
        tv3.setBackgroundResource(drawableResId);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(getActivity());
        tv4.setText(Html.fromHtml(" <b>ACTION</b>"));
        tv4.setTextColor(Color.BLACK);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextSize(textSize);
        tv4.setBackgroundResource(drawableResId);
        tv4.setHeight(65);
        tbrow0.addView(tv4);

        stk1.addView(tbrow0);*/

        JSONArray jsonObj = new JSONArray(Constants.pastOrders);
        for(int i =0;i<jsonObj.length();i++)
        {
            drawableResId=R.drawable.dashboard_row;
            LinearLayout convertView = (LinearLayout) LayoutInflater.from(
                    getActivity()).inflate(
                    R.layout.orderitem_layout, null);

            JSONObject item = jsonObj.getJSONObject(i);
            final String orderDae = item.getString("order_date");
            final String totalAmmount = item.getString("total_ord_amount");
            String status = item.getString("upload_status");
            final String jsonText = item.getString("order_dtl");
            final String orderNo = item.getString("order_ref_no");

            Float totAmt = Float.parseFloat(totalAmmount);
            float gstVal = roundOff((totAmt*7)/100);
            float finalamt = (totAmt+gstVal);

            TableRow tbrow1 = new TableRow(getActivity());
            tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView tv = new TextView(getActivity());
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setHeight(50);
            tv.setTextSize(textSize);
            tv.setText(""+count);
           // tbrow1.addView(tv);

            TextView tv41 = (TextView) convertView.findViewById(R.id.order_no);
           /* tv41.setTextColor(Color.BLACK);
            tv41.setGravity(Gravity.CENTER);
            tv41.setHeight(50);
            tv41.setTextSize(textSize);*/
            tv41.setText("Order No : "+orderNo);
            //tbrow1.addView(tv41);

            TextView tv01 = (TextView) convertView.findViewById(R.id.date);
            /*tv01.setTextColor(Color.BLACK);
            tv01.setGravity(Gravity.CENTER);
            tv01.setHeight(50);
            tv01.setTextSize(textSize);*/
            tv01.setText(orderDae);
            //tbrow1.addView(tv01);


            TextView tv11 = (TextView) convertView.findViewById(R.id.ammout);
           /* tv11.setTextColor(Color.BLACK);
            tv11.setGravity(Gravity.RIGHT);
            tv11.setHeight(50);
            tv11.setTextSize(textSize);*/
            //tv11.setText(totalAmmount);
            tv11.setText("Amount : " + String.format("%.2f",finalamt));
            //tbrow1.addView(tv11);

            //TextView tv21 = (TextView) convertView.findViewById(R.id.status);
            ImageView iv21 = (ImageView) convertView.findViewById(R.id.status);
           /* tv21.setTextColor(Color.BLACK);
            tv21.setGravity(Gravity.CENTER);
            tv21.setHeight(50);
            tv21.setTextSize(textSize);*/
            final String txt = status;
            //if (status.equals("1"))
            //     tv21.setText("Submitted");
            //else
            //     tv21.setText("Delivered");
            if (status.equals("1"))
                iv21.setImageResource(R.drawable.submitted);
            else if (status.equals("2"))
                iv21.setImageResource(R.drawable.accepted);
            else
                iv21.setImageResource(R.drawable.delivered);

            tbrow1.addView(convertView);

            /*TextView tv31 = new TextView(getActivity());
            tv31.setTextColor(Color.BLACK);
            tv31.setGravity(Gravity.CENTER);
            tv31.setHeight(50);
            tv31.setTextSize(textSize);
            tv31.setText(Html.fromHtml("<u>View</u>"));*/
            /*ImageButton tv31 = new ImageButton(getActivity());

            tv31.setImageResource(R.drawable.search);
            tv31.setBackgroundResource(R.drawable.dashboard_row);
            tbrow1.addView(tv31);*/
            tbrow1.setBackgroundResource(drawableResId);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showTable(myView,jsonText,orderNo,orderDae,totalAmmount,txt);
                        linearLayout.removeView(stk1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });


            count++;

            stk1.addView(tbrow1);


        }
        linearLayout.removeView(buttonLayout);

        linearLayout.addView(stk1);


    }

    public void showTable(final View myView, String json, final String ordernumber, final String date,String total ,final String status) throws JSONException, ParseException {
        int drawableResId=R.drawable.cell_shape_header;
        buttonLayout=new LinearLayout(getActivity());
        stk=new TableLayout(getActivity());
        TableLayout.LayoutParams lp=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,5,0,5);
        stk.setLayoutParams(lp);
        stk.setStretchAllColumns(true);
        stk.setShrinkAllColumns(true);
        TableRow tbrow0 = new TableRow(getActivity());
        final Type listType = new TypeToken<ArrayList<Model>>() {}.getType();

        //orderItems=new Gson().fromJson(db.getOrder(date),listType);
        //tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        float textSize=13;
        TextView tv0 = new TextView(getActivity());
        tv0.setText("Order No : " + ordernumber);
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.LEFT);
        tv0.setHeight(65);
        //tv0.setTypeface(null, Typeface.BOLD);
        tv0.setTextSize(textSize+2);
        tbrow0.addView(tv0);
       /* TextView tv1 = new TextView(getActivity());
        tv1.setText(Html.fromHtml(" <b>UOM</b> "));
        tv1.setTextColor(Color.WHITE);
        tv1.setHeight(65);
        tv1.setTextSize(textSize);
        tv1.setBackgroundResource(drawableResId);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);*/
        TextView tv2 = new TextView(getActivity());
        tv2.setText(Html.fromHtml(" <b>Qty</b>"));
        tv2.setTextColor(Color.WHITE);
        tv2.setBackgroundResource(drawableResId);
        tv2.setGravity(Gravity.CENTER);
        tv2.setHeight(65);
        tv2.setTextSize(textSize);
        //tbrow0.addView(tv2);
        /*TextView tv3 = new TextView(getActivity());
        tv3.setText(Html.fromHtml(" <b>Price</b>"));
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(drawableResId);
        tv3.setGravity(Gravity.CENTER);
        tv3.setHeight(65);
        tv3.setTextSize(textSize);
        tbrow0.addView(tv3);*/
        TextView tv4 = new TextView(getActivity());
        tv4.setText(Html.fromHtml(" <b>Amt</b>"));
        tv4.setTextColor(Color.WHITE);
        tv4.setBackgroundResource(drawableResId);
        tv4.setGravity(Gravity.CENTER);
        tv4.setHeight(65);
        tv4.setTextSize(textSize);
        //tbrow0.addView(tv4);
        stk.addView(tbrow0);
        itemJson=new JSONObject();




        JSONArray jsonObj = new JSONArray(json);

        for (int i = 0; i < jsonObj.length(); i++) {
                drawableResId=R.drawable.dashboard_row;


            LinearLayout convertView = (LinearLayout) LayoutInflater.from(
                    getActivity()).inflate(
                    R.layout.cartitem_layout, null);


            JSONObject c = jsonObj.getJSONObject(i);
            String description = c.getString("item_desc");
            String uom = c.getString("Uom");
            String Qty = c.getString("Qty");
            String Price = c.getString("unit_price");
            String amt= c.getString("amount");
            String itemCode = c.getString("item_code");

            Model item = new Model(description,1,itemCode,uom,Float.valueOf(Price),"",i);

            Constants.orderList.add(item);

            TableRow tbrow = new TableRow(getActivity());
            tbrow.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView t1v = (TextView) convertView.findViewById(R.id.itemName);
            t1v.setText(description);
           // t1v.setTextColor(Color.BLACK);
            //t1v.setGravity(Gravity.CENTER);
            //t1v.setHeight(90);
            t1v.setWidth(600);
            t1v.setMaxLines(3);
            //t1v.setGravity(Gravity.LEFT);

            //t1v.setTextSize(textSize);
//            tbrow.addView(t1v);
            TextView t2v = (TextView) convertView.findViewById(R.id.itemuom);
            t2v.setText("UOM : "+uom);
            //t2v.setTextColor(Color.BLACK);
            //t2v.setGravity(Gravity.CENTER);
            //t2v.setHeight(90);
            //t2v.setTextSize(textSize);
            //tbrow.addView(t2v);
            tbrow.addView(convertView);

            TextView t3v = new TextView(getActivity());
            t3v.setText(String.format("%.3f",Float.parseFloat(Qty)));
            t3v.setGravity(Gravity.RIGHT);
            t3v.setPadding(2,25,10,0);
            t3v.setTextColor(Color.BLACK);
            t3v.setHeight(90);
            t3v.setTextSize(textSize);
            tbrow.addView(t3v);
            TextView t4v = (TextView) convertView.findViewById(R.id.itemprice);
            t4v.setText("Price : "+String.format("%.2f",Float.parseFloat(Price)));
            //t4v.setGravity(Gravity.RIGHT);
            //t4v.setTextColor(Color.BLACK);
            //t4v.setHeight(90);
            //t4v.setTextSize(textSize);

            TextView t5v = new TextView(getActivity());
            t5v.setText(String.format("%.2f",Float.parseFloat(amt)));
            t5v.setPadding(2,25,10,0);

            t5v.setGravity(Gravity.RIGHT);
            t5v.setTextColor(Color.BLACK);
            t5v.setHeight(90);
            t5v.setTextSize(textSize);
            tbrow.addView(t5v);
            tbrow.setBackgroundResource(drawableResId);
            stk.addView(tbrow);
            // Phone node is JSON Object
        }




        TextView tot=new TextView(getActivity());
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp2.topMargin=30;

        tot.setLayoutParams(lp2);
        tot.setGravity(Gravity.LEFT);
        tot.setTextColor(Color.BLACK);
        tot.setPadding(30,30,0,0);
        tot.setText("Total : SGD "+String.format("%.2f",Float.parseFloat(total)));
        stk.addView(tot);
        f=Float.parseFloat(total);
        float gstVal=roundOff((f*7)/100);
        float finalamt=(f+gstVal);
        TextView gst=new TextView(getActivity());
        gst.setGravity(Gravity.LEFT);
        gst.setTextColor(Color.BLACK);
        gst.setPadding(30,0,0,0);
        gst.setText("GST : SGD "+String.format("%.2f",gstVal));
        stk.addView(gst);
        TextView toBePaid=new TextView(getActivity());
        toBePaid.setGravity(Gravity.LEFT);
        toBePaid.setTextColor(Color.BLACK);
        toBePaid.setPadding(30,0,0,30);
        toBePaid.setText(Html.fromHtml("Order Total : SGD "+String.format("%.2f",finalamt)));
        stk.addView(toBePaid);
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(150,100);

        /*close= new Button(getActivity());
        close.setText("Back");
        close.setTextColor(Color.BLACK);
        close.setTextSize(12);
        close.setBackgroundResource(R.drawable.newbutton);
        close.setGravity(Gravity.CENTER);
        close.setLayoutParams(lp1);
        buttonLayout.addView(close);*/
        closeImg = new ImageView(getActivity());
        closeImg.setImageResource(R.drawable.back);
        closeImg.setLayoutParams(lp1);
        buttonLayout.addView(closeImg);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeView(stk);
                linearLayout.addView(stk1);
                linearLayout.removeView(buttonLayout);
                Constants.orderList.clear();
            }
        });

        editImg = new ImageView(getActivity());
        editImg.setImageResource(R.drawable.edit);
        editImg.setLayoutParams(lp1);
        buttonLayout.addView(editImg);

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showPopup(getActivity(),p,date);
                //Constants.orderList=new Gson().fromJson(db.getOrder(date),listType);
                //Constants.date=date;
                android.app.FragmentManager fm=getFragmentManager();
                fm.beginTransaction().replace(R.id.content_menu,new NewOrder()).commit();


            }
        });

        linearLayout.addView(stk);
        linearLayout.addView(buttonLayout);
    }


    public float calculateTotal()
    {
        float total=0;
        for(Model model:orderItems)
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

    public void  volleyStringRequst(String url, final String jsonData, final String date){

        System.out.println("fun:"+jsonData);
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),"Submitted successfully",Toast.LENGTH_LONG).show();
                        flag=true;
                        db.updateSubmittedStatus(date, "true");
                        Orderlist = db.getAllOrder();
                        linearLayout.removeView(stk);
                        try {
                            showOrder();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),"Failed to submit data",Toast.LENGTH_LONG).show();
                        flag=false;
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("order",jsonData);
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                // params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }

        };
        AppSingleton.getInstance(getActivity()).addToRequestQueue(stringRequest, REQUEST_TAG);

    }


}