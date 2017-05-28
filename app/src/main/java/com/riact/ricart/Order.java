package com.riact.ricart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        return myView;
    }
}