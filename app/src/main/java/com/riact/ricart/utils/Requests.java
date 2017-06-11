package com.riact.ricart.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by koushik on 4/6/17.
 */

public class Requests {
    static String resp=new String();
    public static String volleyStringRequst(String url, Context context){

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";


        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                resp= response.toString();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                resp="Some error occured";

            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);

        return resp;
    }
}
