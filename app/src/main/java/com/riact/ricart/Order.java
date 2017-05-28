package com.riact.ricart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koushik on 28/5/17.
 */

public class Order extends Fragment {

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView= inflater.inflate(R.layout.order,container,false);
        //TextView signUp = (TextView) myView.findViewById(R.id.dashboard_text);
        //signUp.setText(Html.fromHtml(outStanding));

        MultiSelectionSpinner spinner=(MultiSelectionSpinner)myView.findViewById(R.id.input1);

        List<String> list = new ArrayList<String>();
        list.add("List1");
        list.add("List2");
        spinner.setItems(list);

        return myView;
    }
}