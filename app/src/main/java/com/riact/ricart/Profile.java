

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


        UserDbHandler db=new UserDbHandler(getActivity());
        ArrayList userData=(ArrayList) db.getUser();
        String name=(String)userData.get(0);




        return myView;
    }


}
