package com.riact.ricart;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.riact.ricart.utils.AppSingleton;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.RiactDbHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by koushik on 9/6/17.
 */



public class Profile extends Fragment {

    RiactDbHandler userDb;


    View myView;
    String resp;
    EditText name,phone,address,password,confirm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        myView= inflater.inflate(R.layout.profile,container,false);
         name=(EditText)myView.findViewById(R.id.profile_name);
         phone=(EditText)myView.findViewById(R.id.profile_phone);
         address=(EditText)myView.findViewById(R.id.profile_address);
        password=(EditText)myView.findViewById(R.id.profile_password);
        confirm=(EditText)myView.findViewById(R.id.confirm_password);
        userDb=new RiactDbHandler(myView.getContext());

        name.setText(Constants.userData.get(0));
        phone.setText(Constants.userData.get(1));
        address.setText(Constants.userData.get(2));

        Button updateBtn= (Button)myView.findViewById(R.id.btn_update);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTxt=name.getText().toString();
                String phoneTxt=phone.getText().toString();
                String addressTxt=address.getText().toString();
                String passwordTxt=password.getText().toString();
                String confirmPwdTxt=confirm.getText().toString();

                if(nameTxt.equals("")||nameTxt.equals(""))
                {
                    Toast.makeText(getActivity(),"Enter all data",Toast.LENGTH_LONG).show();
                }
                else {
                    if (passwordTxt.equals(confirmPwdTxt)) {

                        String url=new String();
                        try {
                            if(!passwordTxt.equals(""))
                            url=Constants.webAddress+ "set_customers.php?cust_name="+URLEncoder.encode(nameTxt,"UTF-8")+"&cust_email="+Constants.userData.get(3)+"&cust_address="+ URLEncoder.encode(addressTxt,"UTF-8")+"&cust_phone="+phoneTxt+"&user_password="+passwordTxt;
                            else
                                url=Constants.webAddress+ "set_customers.php?cust_name="+URLEncoder.encode(nameTxt,"UTF-8")+"&cust_email="+Constants.userData.get(3)+"&cust_address="+ URLEncoder.encode(addressTxt,"UTF-8")+"&cust_phone="+phoneTxt;

                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        System.out.println(url);
                        volleyStringRequst(url);

                    } else {
                        Toast.makeText(getActivity(), "Password Mismatch", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });


        return myView;
    }

    public void  volleyStringRequst(String url){

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                resp= response.toString();
                progressDialog.hide();
                if(resp.equals("SUCCESS"))
                {
                    getUserRequest(Constants.webAddress+"get_customers.php?user_code="+Constants.userData.get(3));


                }
                else {
                    Toast.makeText(getActivity(),"Failed to Signup",Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                resp="Some error occured";
                progressDialog.hide();
                Toast.makeText(getActivity(),resp,Toast.LENGTH_LONG).show();


            }
        });
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, REQUEST_TAG);


    }

    public void getUserRequest(String url)
    {
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";



        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                resp= response.toString();
                try{
                    userDb.deleteUser();
                    JSONArray userData=new JSONArray(resp);
                    for (int i = 0; i < userData.length(); i++) {
                        JSONObject user = userData.getJSONObject(i);
                        String name=user.getString("cust_name");
                        String email=user.getString("cust_email");
                        String address=user.getString("cust_address");
                        String phone=user.getString("cust_phone");
                        userDb.addUsers(name,phone,address,email);
                        Constants.userData=(ArrayList<String>)userDb.getUser();
                        Intent intent = new Intent(getActivity(), MenuActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }



                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                resp="Some error occured";
                Toast.makeText(getActivity(),resp,Toast.LENGTH_LONG).show();


            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(strReq, REQUEST_TAG);

    }



}
