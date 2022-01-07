package com.example.vaccisafe;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class LogActivity extends AppCompatActivity {

    EditText email_gui;
    EditText password_gui;
    private CoordinatorLayout coordinatorLayout;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
    }

    public void log_in_user(View view) {
        email_gui = (EditText) findViewById(R.id.email);
        password_gui = (EditText) findViewById(R.id.password_log_in);

        String email = email_gui.getText().toString();
        String password = password_gui.getText().toString();

        // String stored_password = "";

        String url = "https://hello-world-1-fvonreigsq-el.a.run.app/log_in";

        Map<String, String> req_email = new HashMap<String, String>();
        req_email.put("email", email);
        req_email.put("password", password);
        /*
        JsonObjectRequest
                jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(req_email),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String is_valid = response.getString("is_valid");
                            Log.d(TAG, "onResponse: logged in "+is_valid);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LogActivity", "onErrorResponse: error"+error);
            }
        });

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        mQueue.add(jsonObjectRequest);
        */

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(req_email),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String is_valid = response.getString("is_valid");
                            Log.d(TAG, "onResponse: from server: " + is_valid);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue = VolleySingleton.getInstance(this).getRequestQueue();
        mQueue.add(jsonObjectRequest);

        /*
        if (!stored_password.equals("")) {

        } else {
            show_snackbar("Error logging in (system)");
        }
        */

    }

}