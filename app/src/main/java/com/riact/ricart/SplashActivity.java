package com.riact.ricart;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.riact.ricart.utils.AppSingleton;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.RiactDbHandler;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;


public class SplashActivity extends AppCompatActivity {
    RiactDbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db=new RiactDbHandler(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        volleyStringRequst(Constants.webAddress+"get_items.php");
    }

    public void  volleyStringRequst( String url){
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Constants.items=response;

                db.deleteItems();
                db.addItem(Constants.items);
                Constants.userData=(ArrayList<String>) db.getUser();
                if(!Constants.userData.isEmpty()){
                    getPastOrders(Constants.webAddress+"get_orders_by_cust.php?cust_code="+Constants.userData.get(3));

                }
                else{
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                Constants.items=db.getItems().get(0);
                startActivity(i);
                finish();
            }
        });
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

    public void getPastOrders(String url)
    {
        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";



        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Constants.pastOrders = response;
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                Constants.items=db.getItems().get(0);
                startActivity(i);
                finish();


            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);

    }

}