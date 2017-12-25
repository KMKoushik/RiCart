package com.riact.ricart;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riact.ricart.utils.AppSingleton;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.RiactDbHandler;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;


/**
 * Created by koushik on 28/5/17.
 */

public class Dashboard extends Fragment {

    private NetworkImageView imageView;
    private ImageLoader imageLoader;
    Button submitBtn,close,delete,editBtn;
    ImageView submitImg, closeImg, deleteImg, editImg;
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
    int showSaved = 0;
    int submittedCount=0;
    int acceptedCount = 0;


    View myView;
    String json="{\n" +
            "\"data\":  [ {\"iv_no\":\"352240\",\"date\":\"2015-04-30\",\"amount\":\"246.96\",\"balance\":\"246.96\"},\n" +
            "{\"iv_no\":\"352476\",\"date\":\"2015-05-08\",\"amount\":\"129.47\",\"balance\":\"376.43\"},\n" +
            " {\"iv_no\":\"352706\",\"date\":\"2015-05-15\",\"amount\":\"153.87\",\"balance\":\"530.30\"},\n" +
            "{\"iv_no\":\"352801\",\"date\":\"2015-06-15\",\"amount\":\"150.00\",\"balance\":\"680.30\"}]\n" +
            "\n" +

            "}";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        String name=(String)Constants.userData.get(0);
        //Toast.makeText(getActivity(),Constants.items,Toast.LENGTH_LONG).show();

        //int submittedCount=0;
        //int acceptedCount = 0;

        try {
            JSONArray jsonObj = new JSONArray(Constants.pastOrders);
            for(int i =0;i<jsonObj.length();i++){

                JSONObject item = jsonObj.getJSONObject(i);
                String uploadedStatus = (String) item.get("upload_status");
                if(uploadedStatus.equals("1"))
                    submittedCount++;
                if(uploadedStatus.equals("2"))
                    acceptedCount++;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        myView= inflater.inflate(R.layout.dasboard,container,false);
        linearLayout = (LinearLayout) myView.findViewById(R.id.order_layout);
        buttonLayout = (LinearLayout)myView.findViewById(R.id.menu_layout);
        db=new RiactDbHandler(getActivity());

        String outStanding="Your outstanding Invoice is "+"<font color='#EE0000'>SGD 2550.78</font><br>"+"Last updated on 2015-08-03 21:17 ";
        final TextView welcome=(TextView) myView.findViewById(R.id.welcome_text);
        welcome.setText(Html.fromHtml("Welcome <b>"+name+"!</b> "));

        final TextView totalOder=(TextView)myView.findViewById(R.id.total_selected);
        totalOder.setText(Html.fromHtml(totalOder.getText().toString()+"<b><font size=15>"+submittedCount+"</font></b>"));

        if(Orderlist!=null)
                Orderlist.clear();
        Orderlist = db.getAllOrder();



        final TextView totalSaved=(TextView)myView.findViewById(R.id.total_saved);
        totalSaved.setText(Html.fromHtml(totalSaved.getText().toString()+"<b><font size=15>"+db.getSavedCount()+"</font></b>"));
        //TextView detiledview=   (TextView) myView.findViewById(R.id.get_details);
        totalSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaved = 0;
                try {
                    if(stk1!=null)
                        linearLayout.removeView(stk1);
                    if(stk!=null)
                        linearLayout.removeView(stk);
                    showOrder();
                    totalSaved.setBackgroundColor(getResources().getColor(R.color.Selected));
                    totalOder.setBackgroundColor(getResources().getColor(R.color.NonSelected));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        totalOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaved = 1;
                try {
                    if(stk1!=null)
                        linearLayout.removeView(stk1);
                    if(stk!=null)
                        linearLayout.removeView(stk);


                    showOrder();
                    totalOder.setBackgroundColor(getResources().getColor(R.color.Selected));
                    totalSaved.setBackgroundColor(getResources().getColor(R.color.NonSelected));
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            showOrder();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        totalSaved.setBackgroundColor(getResources().getColor(R.color.Selected));


        return myView;
    }

    public void showOrder() throws JSONException, ParseException {
        Constants.orderList.clear();
        int drawableResId = R.drawable.dashboard_header;

        final TextView totalSaved=(TextView)myView.findViewById(R.id.total_saved);
        totalSaved.setText(Html.fromHtml("<b><font size=15>"+db.getSavedCount()+"</font></b>"));

        final TextView totalOder=(TextView)myView.findViewById(R.id.total_selected);
        totalOder.setText(Html.fromHtml("<b><font size=15>"+submittedCount+"</font></b>"));

        stk1 = new TableLayout(getActivity());
        TableLayout.LayoutParams lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 5, 0, 5);
        stk1.setLayoutParams(lp);
        stk1.setStretchAllColumns(true);
        stk1.setShrinkAllColumns(true);
        stk1.setGravity(Gravity.CENTER);
        int count = 1;
        float textSize = 13;
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
        TextView tv3 = new TextView(getActivity());
        tv3.setText(Html.fromHtml(" <b>Order No</b>"));
        tv3.setTextColor(Color.BLACK);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(textSize);
        tv3.setHeight(65);
        tv3.setBackgroundResource(drawableResId);

        tbrow0.addView(tv3);
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

        TextView tv4 = new TextView(getActivity());
        tv4.setText(Html.fromHtml(" <b>ACTION</b>"));
        tv4.setTextColor(Color.BLACK);
        tv4.setGravity(Gravity.CENTER);
        tv4.setTextSize(textSize);
        tv4.setHeight(65);
        tv4.setBackgroundResource(drawableResId);
        tbrow0.addView(tv4);

        stk1.addView(tbrow0);
*/
        if("1".equals(String.valueOf(showSaved)))
        {
            JSONArray jsonObj = new JSONArray(Constants.pastOrders);
        for (int i = 0; i < jsonObj.length(); i++) {
            JSONObject item = jsonObj.getJSONObject(i);
            final String orderDate = item.getString("order_date");
            final String totalAmmount = item.getString("total_ord_amount");
            String status = item.getString("upload_status");
            final String jsonText = item.getString("order_dtl");
            final String orderNo = item.getString("order_ref_no");
            drawableResId = R.drawable.dashboard_row;

            Float totAmt = Float.parseFloat(totalAmmount);
            float gstVal = roundOff((totAmt*7)/100);
            float finalamt = (totAmt+gstVal);

            if (status.equals(String.valueOf(showSaved))) {

                LinearLayout convertView = (LinearLayout) LayoutInflater.from(
                        getActivity()).inflate(
                        R.layout.orderitem_layout, null);


                TableRow tbrow1 = new TableRow(getActivity());
                tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
                TextView tv = new TextView(getActivity());
                tv.setTextColor(Color.BLACK);
                tv.setGravity(Gravity.CENTER);
                tv.setHeight(50);
                tv.setTextSize(textSize);
                tv.setText("" + count);
                //tv.setBackgroundResource(drawableResId);
                //tbrow1.addView(tv);

                TextView tv21 = (TextView) convertView.findViewById(R.id.order_no);
                /*tv21.setTextColor(Color.BLACK);
                tv21.setGravity(Gravity.CENTER);
                tv21.setHeight(50);
                tv21.setTextSize(textSize);*/
                final String txt = status;

                tv21.setText("Order No : "+orderNo);
                //tv21.setBackgroundResource(drawableResId);

                //tbrow1.addView(tv21);

                TextView tv01 = (TextView) convertView.findViewById(R.id.date);
                /*tv01.setTextColor(Color.BLACK);
                tv01.setGravity(Gravity.CENTER);
                tv01.setHeight(50);
                tv01.setTextSize(textSize);
                */
//            tv01.setBackgroundResource(drawableResId);
                tv01.setText("Date : "+orderDate);

//                tbrow1.addView(tv01);


                TextView tv11 = (TextView) convertView.findViewById(R.id.ammout);
               /* tv11.setTextColor(Color.BLACK);
                tv11.setGravity(Gravity.END);
                tv11.setHeight(50);
                tv11.setTextSize(textSize);*/
                //tv11.setText("Amount : " + totalAmmount);
                tv11.setText("Amount : " + String.format("%.2f",finalamt));
//            tv11.setBackgroundResource(drawableResId);

                ImageView iv21 = (ImageView) convertView.findViewById(R.id.status);
                iv21.setVisibility(View.INVISIBLE);

                tbrow1.addView(convertView);



           /* TextView tv31 = new TextView(getActivity());
            tv31.setTextColor(Color.BLACK);
            tv31.setGravity(Gravity.CENTER);
            tv31.setHeight(50);
            tv31.setTextSize(textSize);
            tv31.setText(Html.fromHtml("<u>View</u>"));
//            tv31.setBackgroundResource(drawableResId);*/

                tbrow1.setBackgroundResource(drawableResId);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            showTable1(myView, jsonText, orderNo, orderDate, totalAmmount, txt);
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
        }
    }
    else {


            for(final List list:Orderlist) {

                final String txt = (String) list.get(3);
                if (!txt.equals("true"))
                {
                    drawableResId = R.drawable.dashboard_row;

                    LinearLayout convertView = (LinearLayout) LayoutInflater.from(
                            getActivity()).inflate(
                            R.layout.orderitem_layout, null);

                    TableRow tbrow1 = new TableRow(getActivity());
                    tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
                    TextView tv = new TextView(getActivity());
                    tv.setTextColor(Color.BLACK);
                    tv.setGravity(Gravity.CENTER);
                    tv.setHeight(50);
                    tv.setTextSize(textSize);
                    //tv.setText("" + count);
                    //tbrow1.addView(tv);

                    Float totAmt = Float.parseFloat((String) list.get(2));
                    float gstVal = roundOff((totAmt*7)/100);
                    float finalamt = (totAmt+gstVal);

                    String date = (String) list.get(0);
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    SimpleDateFormat ndf = new SimpleDateFormat("yyyyMMddhhmmss");
                    final String dateStr = ndf.format(sd.parse(date));

                    TextView tv41 = (TextView) convertView.findViewById(R.id.order_no);
                    /*tv41.setTextColor(Color.BLACK);
                    tv41.setGravity(Gravity.CENTER);
                    tv41.setHeight(50);
                    tv41.setTextSize(textSize);*/
                    tv41.setText("Order No : " + dateStr);
                    //tbrow1.addView(tv41);

                    TextView tv01 = (TextView) convertView.findViewById(R.id.date);
                    /*tv01.setTextColor(Color.BLACK);
                    tv01.setGravity(Gravity.CENTER);
                    tv01.setHeight(50);
                    tv01.setTextSize(textSize);*/

                    //tv01.setText((date.substring(0, date.indexOf(" "))));
                    tv01.setText("Date : " + (date.substring(0, date.indexOf(" "))));
                    //tbrow1.addView(tv01);


                    TextView tv11 = (TextView) convertView.findViewById(R.id.ammout);
                    /*tv11.setTextColor(Color.BLACK);
                    tv11.setGravity(Gravity.RIGHT);
                    tv11.setHeight(50);
                    tv11.setTextSize(textSize);*/
                    //tv11.setText(String.format("%.2f", Float.parseFloat((String) list.get(2))));
                    //tv11.setText("Amount : " + String.format("%.2f", Float.parseFloat((String) list.get(2))));
                    tv11.setText("Amount : " + String.format("%.2f",finalamt));
                    tbrow1.addView(convertView);

                    ImageView iv21 = (ImageView) convertView.findViewById(R.id.status);
                    iv21.setVisibility(View.INVISIBLE);

                   /* TextView tv21 = new TextView(getActivity());
                    tv21.setTextColor(Color.BLACK);
                    tv21.setGravity(Gravity.CENTER);
                    tv21.setHeight(50);
                    tv21.setTextSize(textSize);
                    String value;
                    if (txt.equals("true"))
                        value = "SB";
                    else
                        value = "SV";
                    tv21.setText(value);

                    tbrow1.addView(tv21);*/

         /*   TextView tv31 = new TextView(getActivity());
            tv31.setTextColor(Color.BLACK);
            tv31.setGravity(Gravity.CENTER);
            tv31.setHeight(50);
            tv31.setTextSize(textSize);
            tv31.setText(Html.fromHtml("<u>View</u>"));*/

                    tbrow1.setBackgroundResource(drawableResId);
                    convertView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                showTable(myView, (String) list.get(1), dateStr, (String) list.get(0), (String) list.get(2), txt);
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


            }
        }
        linearLayout.removeView(buttonLayout);

        linearLayout.addView(stk1);


    }

    public void showTable1(final View myView, String json, final String ordernumber, final String date,String total ,final String status) throws JSONException, ParseException {
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

        TextView tv2 = new TextView(getActivity());
        tv2.setText("Date : " + date);
        tv2.setTextColor(Color.BLACK);
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

            JSONObject item =new JSONObject();

            JSONObject c = jsonObj.getJSONObject(i);
            String description = c.getString("item_desc");
            String uom = c.getString("Uom");
            String Qty = c.getString("Qty");
            String Price = c.getString("unit_price");
            String amt= c.getString("amount");

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
            }
        });

        linearLayout.addView(stk);
        linearLayout.addView(buttonLayout);
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
        float textSize=13;

        orderItems=new Gson().fromJson(db.getOrder(date),listType);
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
        itemJson.put("status_code","");
        JSONArray array = new JSONArray();

        for (int i = 0; i < jsonObj.length(); i++) {
            drawableResId=R.drawable.dashboard_row;


            JSONObject item =new JSONObject();
            LinearLayout convertView = (LinearLayout) LayoutInflater.from(
                    getActivity()).inflate(
                    R.layout.cartitem_layout, null);

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

            TextView t1v = (TextView) convertView.findViewById(R.id.itemName);
            t1v.setText(description);
            t1v.setWidth(600);
            /*t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setHeight(90);
            t1v.setWidth(250);*/
            t1v.setMaxLines(3);
            // t1v.setGravity(Gravity.LEFT);

            /*t1v.setTextSize(textSize);
            tbrow.addView(t1v);*/
            TextView t2v = (TextView) convertView.findViewById(R.id.itemuom);
            t2v.setText("UOM : "+uom);
            /*t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setHeight(90);
            t2v.setTextSize(textSize);
            tbrow.addView(t2v);*/
            tbrow.addView(convertView);

            TextView t3v = new TextView(getActivity());
            t3v.setText(String.format("%.3f",Float.parseFloat(Qty)));
            t3v.setGravity(Gravity.RIGHT);
            t3v.setTextColor(Color.BLACK);
            // t3v.setHeight(90);
            t3v.setTextSize(textSize);
            tbrow.addView(t3v);
            TextView t4v = (TextView) convertView.findViewById(R.id.itemprice);
            t4v.setText("Price : "+String.format("%.2f",Float.parseFloat(Price)));
            /*t4v.setGravity(Gravity.RIGHT);
            t4v.setTextColor(Color.BLACK);
            t4v.setHeight(90);
            t4v.setTextSize(textSize);*/

            TextView t5v = new TextView(getActivity());
            t5v.setText(String.format("%.2f",Float.parseFloat(amt)));
            t5v.setGravity(Gravity.RIGHT);
            t5v.setTextColor(Color.BLACK);
            //t5v.setHeight(90);
            t5v.setTextSize(textSize);
            tbrow.addView(t5v);
            tbrow.setBackgroundResource(drawableResId);
            stk.addView(tbrow);
            // Phone node is JSON Object
        }

        itemJson.put("order_items",array);
        mainArray.put(itemJson);


        TextView tot=new TextView(getActivity());
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp2.topMargin=30;

        tot.setLayoutParams(lp2);
        tot.setPadding(30,30,0,0);
        tot.setGravity(Gravity.LEFT);
        tot.setTextColor(Color.BLACK);
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
        if(status.equals("false")) {

            /*editBtn = new Button(getActivity());
            editBtn.setText("Edit");
            editBtn.setTextSize(12);
            editBtn.setTextColor(Color.BLACK);
            editBtn.setBackgroundResource(R.drawable.newbutton);
            editBtn.setGravity(Gravity.CENTER);
            editBtn.setLayoutParams(lp1);
            buttonLayout.addView(editBtn);*/

            editImg = new ImageView(getActivity());
            editImg.setImageResource(R.drawable.edit);
            editImg.setLayoutParams(lp1);
            buttonLayout.addView(editImg);

            editImg.setOnClickListener(new View.OnClickListener() {
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


            /*delete = new Button(getActivity());
            delete.setText("Delete");
            delete.setTextSize(12);
            delete.setTextColor(Color.BLACK);
            delete.setBackgroundResource(R.drawable.newbutton);
            delete.setLayoutParams(lp1);
            delete.setGravity(Gravity.CENTER);
            buttonLayout.addView(delete);*/

            deleteImg = new ImageView(getActivity());
            deleteImg.setImageResource(R.drawable.delete1);
            deleteImg.setLayoutParams(lp1);
            buttonLayout.addView(deleteImg);
            deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    db.deleteOrder(date);
                    Orderlist = db.getAllOrder();
                    linearLayout.removeView(stk);
                    /*try {
                        showOrder();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/
                    try {
                        showOrder();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                }
            });

            /*submitBtn = new Button(getActivity());
            submitBtn.setText("Submit");
            submitBtn.setTextColor(Color.BLACK);
            submitBtn.setTextSize(12);
            submitBtn.setBackgroundResource(R.drawable.newbutton);
            submitBtn.setLayoutParams(lp1);
            submitBtn.setGravity(Gravity.CENTER);
            buttonLayout.addView(submitBtn);*/
            submitImg = new ImageView(getActivity());
            submitImg.setImageResource(R.drawable.submit);
            submitImg.setLayoutParams(lp1);
            buttonLayout.addView(submitImg);
            submitImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    volleyStringRequst("http://www.riact.com/Riact/Ricart/placeorder.php",mainArray.toString(),date);
                    submittedCount++;
                }
            });

        }

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
            }
        });

        linearLayout.addView(stk);
        linearLayout.addView(buttonLayout);
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
                        getPastOrders(Constants.webAddress+"get_orders_by_cust.php?cust_code="+Constants.userData.get(3));
                        /*try {
                            showOrder();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }*/
                        try {
                            showOrder();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
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
    public void getPastOrders(String url)
    {
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Constants.pastOrders = response;

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),"Failed to fetch past orders",Toast.LENGTH_LONG).show();
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, REQUEST_TAG);

    }
    /*public void showTable(final View myView, String json, final String date,String total ,final String status) throws JSONException, ParseException {
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




        JSONArray jsonObj = new JSONArray(json);

        for (int i = 0; i < jsonObj.length(); i++) {
            if(i%2==0)
                drawableResId=R.drawable.itemclr1;
            else
                drawableResId=R.drawable.itemclr2;

            JSONObject item =new JSONObject();
            JSONObject c = jsonObj.getJSONObject(i);
            String description,uom,Qty,Price,amt;

            if("false".equals(status)) {

                 description = c.getString("name");
                 uom = c.getString("uom");
                 Qty = c.getString("quantity");
                 Price = c.getString("price");
                 amt = c.getString("amount");
            }
            else {
                 description = c.getString("item_desc");
                 uom = c.getString("Uom");
                 Qty = c.getString("Qty");
                 Price = c.getString("unit_price");
                 amt = c.getString("amount");

            }

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
            tbrow.setBackgroundResource(drawableResId);
            stk.addView(tbrow);
            // Phone node is JSON Object
        }




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

        close= new Button(getActivity());
        close.setText("Back");
        close.setTextColor(Color.BLACK);
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
    }*/


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
}