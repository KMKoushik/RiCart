package com.riact.ricart;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by koushik on 28/5/17.
 */

public class Dashboard extends Fragment {




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



        myView= inflater.inflate(R.layout.dasboard,container,false);
        String outStanding="Your outstanding Invoice is "+"<font color='#EE0000'>SGD 2550.78</font><br>"+"Last updated on 2015-08-03 21:17 ";
        final TextView welcome=(TextView) myView.findViewById(R.id.welcome_text);
        welcome.setText(Html.fromHtml("Welcome <b>"+name+"!</b> "));
        TextView signUp = (TextView) myView.findViewById(R.id.dashboard_text);
        signUp.setText(Html.fromHtml(outStanding));
          LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.dashboard_layout);


              TextView detiledview=   (TextView) myView.findViewById(R.id.get_details);

        detiledview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        init(myView,json);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        return myView;
    }

    public void init(View myView,String json) throws JSONException {

        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.dashboard_layout);
        linearLayout.removeView(myView.findViewById(R.id.get_details));
        TextView tv=(TextView) myView.findViewById(R.id.customer_invoice);
        tv.setVisibility(View.VISIBLE);
        int drawableResId=R.drawable.cell_shape_header;
        TableLayout stk = (TableLayout) myView.findViewById(R.id.tableLayout1);
        TableRow tbrow0 = new TableRow(getActivity());
        tbrow0.setGravity(Gravity.CENTER_HORIZONTAL);
        TextView tv0 = new TextView(getActivity());
        tv0.setText(Html.fromHtml(" <b>IV NO </b>"));
        tv0.setTextColor(Color.WHITE);
        tv0.setBackgroundResource(drawableResId);
        tv0.setGravity(Gravity.CENTER);
        tv0.setHeight(65);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText(Html.fromHtml(" <b>IV DATE</b> "));
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
        tv3.setText(Html.fromHtml(" <b>BALANCE</b>"));
        tv3.setTextColor(Color.WHITE);
        tv3.setBackgroundResource(drawableResId);
        tv3.setGravity(Gravity.CENTER);
        tv3.setHeight(65);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);

        JSONObject jsonObj = new JSONObject(json);
        JSONArray contacts = jsonObj.getJSONArray("data");
        for (int i = 0; i < contacts.length(); i++) {
            if(i%2==0)
                drawableResId=R.drawable.cell_shape;
            else
                drawableResId=R.drawable.cell_shape2;

            JSONObject c = contacts.getJSONObject(i);
            String iv_no = c.getString("iv_no");
            String date = c.getString("date");
            String amount = c.getString("amount");
            String balance = c.getString("balance");
            TableRow tbrow = new TableRow(getActivity());
            tbrow.setGravity(Gravity.CENTER_HORIZONTAL);

            TextView t1v = new TextView(getActivity());
            t1v.setText(iv_no);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setHeight(65);
            t1v.setBackgroundResource(drawableResId);
            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText(date);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setHeight(65);
            t2v.setBackgroundResource(drawableResId);
            tbrow.addView(t2v);
            TextView t3v = new TextView(getActivity());
            t3v.setText(amount);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            t3v.setHeight(65);
            t3v.setBackgroundResource(drawableResId);
            tbrow.addView(t3v);
            TextView t4v = new TextView(getActivity());
            t4v.setText(balance);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            t4v.setHeight(65);
            t4v.setBackgroundResource(drawableResId);
            tbrow.addView(t4v);
            stk.addView(tbrow);

            // Phone node is JSON Object


        }


    }
}