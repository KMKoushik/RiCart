package com.riact.ricart;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riact.ricart.utils.AppSingleton;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.MyAdapter;
import com.riact.ricart.utils.RiactDbHandler;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by koushik on 28/5/17.
 */

public class Dashboard extends Fragment {

    private NetworkImageView imageView;
    private ImageLoader imageLoader;
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
    int showSaved = 1;




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

        int submittedCount=0;
        int acceptedCount = 0;

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
        totalOder.setText(Html.fromHtml(totalOder.getText().toString()+"<b><font size=15>"+acceptedCount+"</font></b>"));



        final TextView totalSaved=(TextView)myView.findViewById(R.id.total_saved);
        totalSaved.setText(Html.fromHtml(totalSaved.getText().toString()+"<b><font size=15>"+submittedCount+"</font></b>"));
        //TextView detiledview=   (TextView) myView.findViewById(R.id.get_details);
        totalSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaved = 1;
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
                }
            }
        });

        totalOder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSaved = 2;
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
                }
            }
        });
        try {
            showOrder();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        totalSaved.setBackgroundColor(getResources().getColor(R.color.Selected));


        return myView;
    }

    public void showOrder() throws JSONException {
        Constants.orderList.clear();
        int drawableResId=R.drawable.dashboard_header;

        stk1 = new TableLayout(getActivity());
        TableLayout.LayoutParams lp=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,5,0,5);
        stk1.setLayoutParams(lp);
        stk1.setStretchAllColumns(true);
        stk1.setShrinkAllColumns(true);
        stk1.setGravity(Gravity.CENTER);
        int count=1;
        float textSize=13;
        TableRow tbrow0 = new TableRow(getActivity());
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

        JSONArray jsonObj = new JSONArray(Constants.pastOrders);
        for(int i =0;i<jsonObj.length();i++) {
            JSONObject item = jsonObj.getJSONObject(i);
            final String orderDae = item.getString("order_date");
            final String totalAmmount = item.getString("total_ord_amount");
            String status = item.getString("upload_status");
            final String jsonText = item.getString("order_dtl");
            String orderNo = item.getString("order_ref_no");
            drawableResId=R.drawable.dashboard_row;

            if(status.equals(String.valueOf(showSaved)))
            {


            TableRow tbrow1 = new TableRow(getActivity());
            tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView tv = new TextView(getActivity());
            tv.setTextColor(Color.BLACK);
            tv.setGravity(Gravity.CENTER);
            tv.setHeight(50);
            tv.setTextSize(textSize);
            tv.setText("" + count);
            //tv.setBackgroundResource(drawableResId);
            tbrow1.addView(tv);

            TextView tv21 = new TextView(getActivity());
            tv21.setTextColor(Color.BLACK);
            tv21.setGravity(Gravity.CENTER);
            tv21.setHeight(50);
            tv21.setTextSize(textSize);
            final String txt = status;

            tv21.setText(orderNo);
            //tv21.setBackgroundResource(drawableResId);

            tbrow1.addView(tv21);

            TextView tv01 = new TextView(getActivity());
            tv01.setTextColor(Color.BLACK);
            tv01.setGravity(Gravity.CENTER);
            tv01.setHeight(50);
            tv01.setTextSize(textSize);
            tv01.setText(orderDae);
//            tv01.setBackgroundResource(drawableResId);

            tbrow1.addView(tv01);


            TextView tv11 = new TextView(getActivity());
            tv11.setTextColor(Color.BLACK);
            tv11.setGravity(Gravity.END);
            tv11.setHeight(50);
            tv11.setTextSize(textSize);
            tv11.setText(totalAmmount);
//            tv11.setBackgroundResource(drawableResId);

            tbrow1.addView(tv11);



           /* TextView tv31 = new TextView(getActivity());
            tv31.setTextColor(Color.BLACK);
            tv31.setGravity(Gravity.CENTER);
            tv31.setHeight(50);
            tv31.setTextSize(textSize);
            tv31.setText(Html.fromHtml("<u>View</u>"));
//            tv31.setBackgroundResource(drawableResId);*/
                ImageButton tv31 = new ImageButton(getActivity());

                tv31.setImageResource(R.drawable.search);
                tv31.setBackgroundResource(R.drawable.dashboard_row);
            tbrow1.addView(tv31);
            tbrow1.setBackgroundResource(drawableResId);
            tv31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        showTable(myView, jsonText, orderDae, totalAmmount, txt);
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




        JSONArray jsonObj = new JSONArray(json);

        for (int i = 0; i < jsonObj.length(); i++) {
            if(i%2==0)
                drawableResId=R.drawable.itemclr1;
            else
                drawableResId=R.drawable.itemclr2;

            JSONObject item =new JSONObject();

            JSONObject c = jsonObj.getJSONObject(i);
            String description = c.getString("item_desc");
            String uom = c.getString("Uom");
            String Qty = c.getString("Qty");
            String Price = c.getString("unit_price");
            String amt= c.getString("amount");

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
}