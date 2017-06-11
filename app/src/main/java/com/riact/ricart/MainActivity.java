package com.riact.ricart;

/**
 * Created by koushik on 28/5/17.
 *
 * A PIECE OF ADVICE: Dont touch my code if you don't know what you are doing.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.riact.ricart.utils.AppSingleton;
import com.riact.ricart.utils.Constants;
import com.riact.ricart.utils.Requests;
import com.riact.ricart.utils.UserDbHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.android.volley.VolleyLog.TAG;

public class MainActivity extends AppCompatActivity {

    EditText email,password;
    String resp;
    UserDbHandler userDb=new UserDbHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        email=(EditText)findViewById(R.id.input_email);
        password=(EditText)findViewById(R.id.input_password);
        TextView signUp = (TextView) findViewById(R.id.titlename);
        Constants.userData=(ArrayList<String>) userDb.getUser();
        if(!Constants.userData.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            finish();

        }
        String first = "No account yet? ";
        String next = "<font color='#EE0000'><u>Register<u></font>";
        signUp.setText(Html.fromHtml(first + next));
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        Button loginBtn=   (Button) findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId=email.getText().toString();
                String passwordText=password.getText().toString();
                if(emailId.equals("")||passwordText.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter all data",Toast.LENGTH_LONG).show();
                }
                else {
                    String url = Constants.webAddress + "valid_user.php?user_code=" + emailId + "&user_password=" + passwordText;
                    volleyStringRequst(url);
                }

            }
        });
    }

    public void  volleyStringRequst(String url){

        String  REQUEST_TAG = "com.androidtutorialpoint.volleyStringRequest";
        final ProgressDialog progressDialog=new ProgressDialog(this);
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
                    getUserRequest(Constants.webAddress+"get_customers.php?user_code="+email.getText().toString());
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed to login",Toast.LENGTH_LONG).show();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                resp="Some error occured";
                progressDialog.hide();
                Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();


            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);

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
                    JSONArray userData=new JSONArray(resp);
                    for (int i = 0; i < userData.length(); i++) {
                        JSONObject user = userData.getJSONObject(i);
                        String name=user.getString("cust_name");
                        String email=user.getString("cust_email");
                        String address=user.getString("cust_address");
                        String phone=user.getString("cust_phone");
                        userDb.addUsers(name,phone,address,email);
                        Constants.userData=(ArrayList<String>) userDb.getUser();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        finish();
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
                Toast.makeText(getApplicationContext(),resp,Toast.LENGTH_LONG).show();


            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);

    }


}
