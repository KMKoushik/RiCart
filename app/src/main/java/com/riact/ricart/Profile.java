package com.riact.ricart;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.riact.ricart.utils.Constants;

/**
 * Created by koushik on 9/6/17.
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

        Button updateBtn= (Button)myView.findViewById(R.id.btn_update);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Under Construction",Toast.LENGTH_SHORT).show();
            }
        });


        return myView;
    }


}
