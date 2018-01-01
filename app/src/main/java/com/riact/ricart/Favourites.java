package com.riact.ricart;

import android.app.Fragment;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Model;
import com.riact.ricart.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by koushik on 25/12/17.
 */

public class Favourites extends Fragment {

    View myView;
    Point p;
    Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        p=new Point();
        myView= inflater.inflate(R.layout.favourites,container,false);
        LinearLayout linearLayout = (LinearLayout) myView.findViewById(R.id.menu_layout);
        btn = (Button)myView.findViewById(R.id.cartId);
        try {
            JSONArray jsonObj = new JSONArray(Constants.favourites);
            int drawableResId=R.drawable.dashboard_row;


            for (int i =0; i<jsonObj.length();i++){
                JSONObject itemJson = jsonObj.getJSONObject(i);
                LinearLayout convertView = (LinearLayout) LayoutInflater.from(
                        getActivity()).inflate(
                        R.layout.favourite_item, null);

                TextView groupName = (TextView) convertView.findViewById(R.id.item_name);
                TextView lastOrdered = (TextView) convertView.findViewById(R.id.last_ordered);
                TextView ordered = (TextView) convertView.findViewById(R.id.ordered);
                TextView orderqty = (TextView) convertView.findViewById(R.id.orderqty);
                final CheckBox cb= (CheckBox) convertView.findViewById(R.id.favcheckbox);


                String group = itemJson.getString("group_desc");
                String itemCode = itemJson.getString("item_code");
                String itemDesc = itemJson.getString("item_desc");
                String uom = itemJson.getString("price_uom_trx");
                String noOfOrder = itemJson.getString("no_of_orders");
                String qty = itemJson.getString("last_ordered_qty");
                String orderDate = itemJson.getString("last_order_date");
                groupName.setText(itemDesc);
                lastOrdered.setText("Last Ordered at "+ orderDate);
                ordered.setText("Ordered "+noOfOrder+" items");
                orderqty.setText("Last ordered Qty "+qty);
                linearLayout.addView(convertView);
                convertView.setBackgroundResource(drawableResId);
                float price = Utils.getPriceOfItem(group,itemCode);
                int index = Utils.getIndexOfItem(group,itemCode);
                final Model item = new Model(itemDesc,1,itemCode,uom,Float.valueOf(price),group,index);
                item.setQuantity(Float.valueOf(qty));
                convertView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!cb.isChecked()) {


                            Constants.orderList.add(item);
                            //Constants.orderList.add(groupItemList.get(position));

                            //groupItemList.get(position).putValue(1);

                        } else {
                            //Constants.orderList.remove(groupItemList.get(position));

                            //groupItemList.get(position).putValue(0);
                            Constants.orderList.remove(item);

                        }

                        cb.setChecked(!cb.isChecked());
                    }
                });
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        android.app.FragmentManager fm=getFragmentManager();
                        fm.beginTransaction().replace(R.id.content_menu,new NewOrder()).commit();

                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return myView;
    }
}
