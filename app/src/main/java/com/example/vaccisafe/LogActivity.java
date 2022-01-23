package com.example.vaccisafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class LogActivity extends AppCompatActivity {

    EditText email_gui;
    EditText password_gui;
    List<HashMap> vaccines_data = new ArrayList<HashMap>();
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

        String url = "http://192.168.29.201:8000/log_in/";

        Map<String, String> req_email = new HashMap<String, String>();
        req_email.put("email", email);
        req_email.put("password", password);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(req_email),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "onResponse: " + response);
                            String is_valid = response.getString("is_valid");
                            Log.d(TAG, "onResponse: from server: " + is_valid);

                            if (is_valid.equals("true")) {
                                // Start new activity of await for server response for vaccines list
                                startVerifyAct(response.getString("data"), email);

                            } else if (is_valid.equals("false")) {
                                Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Some error occured!", Toast.LENGTH_SHORT).show();
                            }

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
    }

    private void startHomAct() {

        Intent home_intent = new Intent(this, HomeActivity.class);
        startActivity(home_intent);
    }

    // for testing purposes
    // to avoid cumbersome and rather longer registering process
    private void startVerifyAct(String data, String email) {
        Intent myIntent = new Intent(this, VerifyActivity.class);
        myIntent.putExtra("data", data);
        myIntent.putExtra("email", email);
        startActivity(myIntent);
    }

    /*
    private void getVaccineResponseFromServer(String email, String password) throws JSONException {
        //String url = "https://hello-world-1-fvonreigsq-el.a.run.app/recommended_vaccines?email=" + email + "&password=" + password;
        String url = "http://192.168.29.201:8000/get_recommended_vaccines/";
        Log.d(TAG, "getVaccineResponseFromServer: hey from Dev!");

        Map<String, String> identification = new HashMap<String, String>();
        identification.put("email", email);
        identification.put("password", password);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.POST, url, new JSONArray(identification),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d(TAG, "onResponse: "+response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d(TAG, "onResponse: " + response);
                        JSONObject jsonResponse = null;
                        try {
                            jsonResponse = new JSONObject(response);
                            JSONArray data = jsonResponse.getJSONArray("vaccines_data");

                            for (int i = 0; i < data.length(); i++) {
                                Log.d(TAG, "onResponse: " + i);
                                Log.d(TAG, "onResponse: " + data.getString(i));
                            }

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
        mQueue.add(stringRequest);
    }
    */
}