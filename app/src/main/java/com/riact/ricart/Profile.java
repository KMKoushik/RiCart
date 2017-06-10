

/**
 * Created by koushik on 9/6/17.
 */


package com.riact.ricart;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.UserDbHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by koushik on 28/5/17.
 */

public class Profile extends Fragment {

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView= inflater.inflate(R.layout.profile,container,false);
        EditText name=(EditText)myView.findViewById(R.id.profile_name);
        EditText phone=(EditText)myView.findViewById(R.id.profile_phone);
        EditText address=(EditText)myView.findViewById(R.id.profile_address);
        name.setText(Constants.userData.get(0));
        phone.setText(Constants.userData.get(1));
        address.setText(Constants.userData.get(2));


        return myView;
    }


}
