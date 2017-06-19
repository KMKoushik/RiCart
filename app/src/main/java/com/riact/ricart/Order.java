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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.RiactDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by koushik on 28/5/17.
 */

public class Order extends Fragment {
    MultiSelectionSpinner tv0;
    MultiSelectionSpinner spinner;
    Button submitBtn,close,delete,editBtn;
    Point p;
    TableLayout stk1,stk;
    LinearLayout buttonLayout;
    LinearLayout linearLayout;
    RiactDbHandler db;
    String txt,chumma;
    List<List> Orderlist;
    TextView total;
    ArrayList<Model> orderItems;
    float f;


    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        p=new Point();
        myView= inflater.inflate(R.layout.order,container,false);
        linearLayout = (LinearLayout) myView.findViewById(R.id.order_layout);
        buttonLayout = (LinearLayout)myView.findViewById(R.id.menu_layout);

        db=new RiactDbHandler(getActivity());
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

            showOrder();

        return myView;
    }

    public void showOrder()
    {
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
            tv11.setText((String)list.get(2));
            tbrow1.addView(tv11);

            TextView tv21 = new TextView(getActivity());
            tv21.setTextColor(Color.BLACK);
            tv21.setGravity(Gravity.RIGHT);
            tv21.setHeight(50);
            tv21.setTextSize(textSize);
            final String txt=(String)list.get(3);
            String value;
            if(txt.equals("true"))
                value="submitted";
            else
                value="saved";
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
                    }
                }
            });


            count++;

            stk1.addView(tbrow1);


        }
        linearLayout.removeView(buttonLayout);

        linearLayout.addView(stk1);


    }

    public void showTable(final View myView, String json, final String date,String total ,final String status) throws JSONException {
        int drawableResId=R.drawable.cell_shape_header;
        buttonLayout=new LinearLayout(getActivity());
        stk=new TableLayout(getActivity());
        TableLayout.LayoutParams lp=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,5,0,5);
        stk.setLayoutParams(lp);
        stk.setStretchAllColumns(true);
        stk.setShrinkAllColumns(true);
        TableRow tbrow0 = new TableRow(getActivity());
        Type listType = new TypeToken<ArrayList<Model>>() {}.getType();

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

        EditText text=new EditText(getActivity());
        text.setGravity(Gravity.RIGHT);
        text.setText("total : "+total);
        stk.addView(text);
        LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(150,40);





        if(status.equals("false")) {

            editBtn = new Button(getActivity());
            editBtn.setText("EDIT");
            editBtn.setTextSize(12);
            editBtn.setTextColor(Color.WHITE);
            editBtn.setBackgroundResource(R.drawable.newbutton);
            editBtn.setGravity(Gravity.CENTER);
            editBtn.setLayoutParams(lp1);
            buttonLayout.addView(editBtn);

            editBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopup(getActivity(),p,date);
                }
            });

            lp1.leftMargin = 10;
            lp1.topMargin=20;


            delete = new Button(getActivity());
            delete.setText("delete");
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
            submitBtn.setText("submit");
            submitBtn.setTextColor(Color.WHITE);
            submitBtn.setTextSize(12);
            submitBtn.setBackgroundResource(R.drawable.newbutton);
            submitBtn.setLayoutParams(lp1);
            submitBtn.setGravity(Gravity.CENTER);
            buttonLayout.addView(submitBtn);
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.updateSubmittedStatus(date, "true");
                    Orderlist = db.getAllOrder();
                    linearLayout.removeView(stk);
                    showOrder();
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

    private void showPopup(Activity context, Point p, final String date) {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int popupWidth = displayMetrics.widthPixels;
        int popupHeight = displayMetrics.heightPixels;
        System.out.println("chumma");

        LinearLayout viewGroup = (LinearLayout) getActivity().findViewById(R.id.popup);
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        final View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);
        total = (TextView) layout.findViewById(R.id.total);

        TableLayout stk = (TableLayout)layout.findViewById(R.id.cart_table);
        final int drawableResId=R.drawable.cell_shape_header;
        float textSize=11;
        TableRow tbrow0 = new TableRow(context);
        tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView tv0 = new TextView(getActivity());
        tv0.setText(Html.fromHtml(" <b>ITEM NAME</b>"));
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(drawableResId);
        tv0.setGravity(Gravity.CENTER);
        tv0.setTextSize(textSize);
        tv0.setHeight(65);
        tbrow0.addView(tv0);
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
        tv3.setText(Html.fromHtml(" <b>AMMOUNT</b>"));
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(drawableResId);
        tv3.setGravity(Gravity.CENTER);
        tv3.setTextSize(textSize);
        tv3.setHeight(65);

        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        final int count=0;
        for (final Model model : orderItems) {
            TableRow tbrow1 = new TableRow(context);
            tbrow1.setGravity(Gravity.CENTER_HORIZONTAL);
            TextView tv00 = new TextView(getActivity());
            tv00.setText(model.getName()+"("+model.getUom()+")");
            tv00.setTextColor(Color.BLACK);
            tv00.setMaxLines(2);
            tv00.setWidth(250);
            tv00.setGravity(Gravity.LEFT);
            tv00.setHeight(75);
            tv00.setTextSize(textSize);
            tbrow1.addView(tv00);

            final TextView tv11 = new TextView(getActivity());
            tv11.setText(String.valueOf(model.getPrice()));
            tv11.setTextColor(Color.BLACK);
            tv11.setHeight(75);
            tv11.setGravity(Gravity.CENTER);
            tv11.setTextSize(textSize);
            tbrow1.addView(tv11);

            final TextView tv22 = new TextView(getActivity());
            tv22.setTextColor(Color.BLACK);
            tv22.setText(""+model.getAmount());
            tv22.setGravity(Gravity.CENTER);
            tv22.setHeight(75);
            tv22.setTextSize(textSize);


            final EditText tv33 = new EditText(getActivity());
            tv33.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            System.out.println("ff"+chumma);
            tv33.setText(""+model.getQuantity());
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

                    tv22.setText(String.valueOf(roundOff(Float.parseFloat(chumma) * model.getPrice())));


                    model.setQuantity(roundOff(Float.parseFloat(chumma)));
                    model.setAmount(roundOff(Float.parseFloat(chumma) * model.getPrice()));
                    f=calculateTotal();
                    total.setText("Total : " + roundOff(f));
                }
            });
            tbrow1.addView(tv33);
            tbrow1.addView(tv22);
            stk.addView(tbrow1);
        }

        total.setText(""+roundOff(calculateTotal()));
        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);

        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(layout, Gravity.CENTER_HORIZONTAL, p.x , p.y);
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

                Gson gson = new Gson();
                String arrayList = gson.toJson(orderItems);
                db.updateOrder(date,arrayList,""+roundOff(f));
                Intent intent = new Intent(getActivity(), MenuActivity.class);
                startActivity(intent);
                orderItems.clear();
                Constants.orderList.clear();
                popup.dismiss();
                getActivity().finish();
            }
        });
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