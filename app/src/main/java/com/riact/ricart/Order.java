package com.riact.ricart;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
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

/**
 * Created by koushik on 28/5/17.
 */

public class Order extends Fragment {
    Button submitBtn,close,delete,editBtn;
    Point p;
    TableLayout stk1,stk;
    LinearLayout buttonLayout,linearLayout;
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

        db=new RiactDbHandler(getActivity());


            showOrder();

        return myView;
    }

    public void showOrder()
    {
        Constants.orderList.clear();
        Orderlist=db.getAllOrder();
        stk1 = new TableLayout(getActivity());
        TableLayout.LayoutParams lp=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,5,0,5);
        stk1.setLayoutParams(lp);
        stk1.setStretchAllColumns(true);
        stk1.setShrinkAllColumns(true);
        stk1.setGravity(Gravity.CENTER);
        int count=1;
        float textSize=11;
        TableRow tbrow0 = new TableRow(getActivity());
        tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView tv0 = new TextView(getActivity());
        tv0.setText(Html.fromHtml(" <b>SL NO</b>"));
        tv0.setTextColor(Color.BLACK);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(textSize);
        tv0.setHeight(65);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(Html.fromHtml(" <b>DATE</b> "));
        tv1.setTextColor(Color.BLACK);
        tv1.setHeight(65);
        tv1.setTextSize(textSize);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(Html.fromHtml(" <b>AMOUNT</b>"));
        tv2.setTextColor(Color.BLACK);
        tv2.setGravity(Gravity.RIGHT);
        tv2.setHeight(65);
        tv2.setTextSize(textSize);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(Html.fromHtml(" <b>STATUS</b>"));
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.RIGHT);
        tv3.setTextSize(textSize);
        tv3.setHeight(65);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(getActivity());
        tv4.setText(Html.fromHtml(" <b>ACTION</b>"));
        tv4.setTextColor(Color.BLACK);
        tv4.setGravity(Gravity.RIGHT);
        tv4.setTextSize(textSize);
        tv4.setHeight(65);
        tbrow0.addView(tv4);

        stk1.addView(tbrow0);

        for(final List list:Orderlist)
        {

            TableRow tbrow1 = new TableRow(getActivity());
            tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView tv = new TextView(getActivity());
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setHeight(50);
            tv.setTextSize(textSize);
            tv.setText(""+count);
            tbrow1.addView(tv);

            TextView tv01 = new TextView(getActivity());
            tv01.setTextColor(Color.BLACK);
            tv01.setGravity(Gravity.CENTER);
            tv01.setHeight(50);
            tv01.setTextSize(textSize);
            tv01.setText((String)list.get(0));
            tbrow1.addView(tv01);


            TextView tv11 = new TextView(getActivity());
            tv11.setTextColor(Color.BLACK);
            tv11.setGravity(Gravity.RIGHT);
            tv11.setHeight(50);
            tv11.setTextSize(textSize);
            tv11.setText(String.format("%.2f", Float.parseFloat((String)list.get(2))));
            tbrow1.addView(tv11);

            TextView tv21 = new TextView(getActivity());
            tv21.setTextColor(Color.BLACK);
            tv21.setGravity(Gravity.RIGHT);
            tv21.setHeight(50);
            tv21.setTextSize(textSize);
            final String txt=(String)list.get(3);
            String value;
            if(txt.equals("true"))
                value="Submitted";
            else
                value="Saved";
            tv21.setText(value);

            tbrow1.addView(tv21);

            TextView tv31 = new TextView(getActivity());
            tv31.setTextColor(Color.BLACK);
            tv31.setGravity(Gravity.RIGHT);
            tv31.setHeight(50);
            tv31.setTextSize(textSize);
            tv31.setText(Html.fromHtml("<u>View</u>"));
            tbrow1.addView(tv31);
            tv31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showTable(myView,(String)list.get(1),(String) list.get(0),(String) list.get(2),txt);
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

    public void showTable(final View myView, String json, final String date,String total ,final String status) throws JSONException, ParseException {
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

        orderItems=new Gson().fromJson(db.getOrder(date),listType);
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
        itemJson=new JSONObject();

        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMddhhmmss");
        String dateStr = ndf.format(sd.parse(date));
        mainArray=new JSONArray();


        JSONArray jsonObj = new JSONArray(json);
        itemJson.put("no_of_orders",""+jsonObj.length());
        itemJson.put("order_number",dateStr);
        itemJson.put("order_date",date.substring(0,date.indexOf(" ")));
        itemJson.put("pda_number","");
        itemJson.put("sales_man","");
        itemJson.put("route_code","");
        itemJson.put("cust_code",""+Constants.userData.get(3));
        itemJson.put("cust_name",""+Constants.userData.get(0));
        itemJson.put("status_code","N");
        JSONArray array = new JSONArray();

        for (int i = 0; i < jsonObj.length(); i++) {
            if(i%2==0)
                drawableResId=R.drawable.cell_shape;
            else
                drawableResId=R.drawable.cell_shape2;

            JSONObject item =new JSONObject();

            JSONObject c = jsonObj.getJSONObject(i);
            String description = c.getString("name");
            String uom = c.getString("uom");
            String Qty = c.getString("quantity");
            String Price = c.getString("price");
            String amt= c.getString("amount");


            item.put("item_code",c.getString("itemCode"));
            item.put("item_desc",description);
            item.put("uom",uom);
            item.put("qty",Qty);
            item.put("unit_price",Price);
            array.put(item);
            TableRow tbrow = new TableRow(getActivity());
            tbrow.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView t1v = new TextView(getActivity());
            t1v.setText(description);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setHeight(90);
            t1v.setWidth(250);
            t1v.setMaxLines(2);
            t1v.setGravity(Gravity.LEFT);

            t1v.setTextSize(textSize);
            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText(uom);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setHeight(90);
            t2v.setTextSize(textSize);
            tbrow.addView(t2v);
            TextView t3v = new TextView(getActivity());
            t3v.setText(String.format("%.4f",Float.parseFloat(Qty)));
            t3v.setGravity(Gravity.RIGHT);
            t3v.setTextColor(Color.BLACK);
            t3v.setHeight(90);
            t3v.setTextSize(textSize);
            tbrow.addView(t3v);
            TextView t4v = new TextView(getActivity());
            t4v.setText(String.format("%.2f",Float.parseFloat(Price)));
            t4v.setGravity(Gravity.RIGHT);
            t4v.setTextColor(Color.BLACK);
            t4v.setHeight(90);
            t4v.setTextSize(textSize);
            tbrow.addView(t4v);

            TextView t5v = new TextView(getActivity());
            t5v.setText(String.format("%.2f",Float.parseFloat(amt)));
            t5v.setGravity(Gravity.RIGHT);
            t5v.setTextColor(Color.BLACK);
            t5v.setHeight(90);
            t5v.setTextSize(textSize);
            tbrow.addView(t5v);
            stk.addView(tbrow);
            // Phone node is JSON Object
        }

        itemJson.put("order_items",array);
        mainArray.put(itemJson);


        TextView tot=new TextView(getActivity());
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp2.topMargin=30;

        tot.setLayoutParams(lp2);
        tot.setGravity(Gravity.RIGHT);
        tot.setTextColor(Color.BLACK);
        tot.setText("Total : SGD "+String.format("%.2f",Float.parseFloat(total)));
        stk.addView(tot);
        f=Float.parseFloat(total);
        float gstVal=roundOff((f*7)/100);
        float finalamt=(f+gstVal);
        TextView gst=new TextView(getActivity());
        gst.setGravity(Gravity.RIGHT);
        gst.setTextColor(Color.BLACK);
        gst.setText("GST @ 7% : SGD "+String.format("%.2f",gstVal));
        stk.addView(gst);
        TextView toBePaid=new TextView(getActivity());
        toBePaid.setGravity(Gravity.RIGHT);
        toBePaid.setTextColor(Color.BLACK);
        toBePaid.setText(Html.fromHtml("<b>Grant Total : SGD "+String.format("%.2f",finalamt)+"</b>"));
        stk.addView(toBePaid);
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(150,70);
        if(status.equals("false")) {

            editBtn = new Button(getActivity());
            editBtn.setText("Edit");
            editBtn.setTextSize(12);
            editBtn.setTextColor(Color.WHITE);
            editBtn.setBackgroundResource(R.drawable.newbutton);
            editBtn.setGravity(Gravity.CENTER);
            editBtn.setLayoutParams(lp1);
            buttonLayout.addView(editBtn);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //showPopup(getActivity(),p,date);
                    Constants.orderList=new Gson().fromJson(db.getOrder(date),listType);
                    Constants.date=date;
                    android.app.FragmentManager fm=getFragmentManager();
                    fm.beginTransaction().replace(R.id.content_menu,new NewOrder()).commit();


                }
            });

            lp1.leftMargin = 10;
            lp1.topMargin=20;


            delete = new Button(getActivity());
            delete.setText("Delete");
            delete.setTextSize(12);
            delete.setTextColor(Color.WHITE);
            delete.setBackgroundResource(R.drawable.newbutton);
            delete.setLayoutParams(lp1);
            delete.setGravity(Gravity.CENTER);
            buttonLayout.addView(delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                            db.deleteOrder(date);
                            Orderlist = db.getAllOrder();
                            linearLayout.removeView(stk);
                            showOrder();


                }
            });

            submitBtn = new Button(getActivity());
            submitBtn.setText("Submit");
            submitBtn.setTextColor(Color.WHITE);
            submitBtn.setTextSize(12);
            submitBtn.setBackgroundResource(R.drawable.newbutton);
            submitBtn.setLayoutParams(lp1);
            submitBtn.setGravity(Gravity.CENTER);
            buttonLayout.addView(submitBtn);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   volleyStringRequst("http://www.riact.com/Riact/Ricart/placeorder.php",mainArray.toString(),date);
                }
            });




        }
        close= new Button(getActivity());
        close.setText("Close");
        close.setTextColor(Color.WHITE);
        close.setTextSize(12);
        close.setBackgroundResource(R.drawable.newbutton);
        close.setGravity(Gravity.CENTER);
        close.setLayoutParams(lp1);
        buttonLayout.addView(close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeView(stk);
                linearLayout.addView(stk1);
                linearLayout.removeView(buttonLayout);
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
                        showOrder();
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