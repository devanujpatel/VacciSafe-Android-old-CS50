package com.example.vaccisafe;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

public class Vaccine {
    private String vaccine_name;
    private String taken_at;
    private String mark_as;
    private RequestQueue mQueue;
    private Context context;
    private String email;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Vaccine(String vaccine_name, String taken_at, String mark_as, Context context, String email) {
        this.vaccine_name = vaccine_name;
        this.taken_at = taken_at;
        this.mark_as = mark_as;
        this.context = context;
        this.email = email;
    }

    public String getMark_as() {
        return mark_as;
    }

    public void setMark_as(String mark_as) {
        this.mark_as = mark_as;
    }

    public String getVaccine_name() {
        return vaccine_name;
    }

    public void setVaccine_name(String vaccine_name) {
        this.vaccine_name = vaccine_name;
    }

    public String getTaken_at() {
        return taken_at;
    }

    public void setTaken_at(String taken_at) {
        this.taken_at = taken_at;
    }

    public void update_in_server(CharSequence text) {
        String make_null;
        if (text.equals("Taken")) {
            make_null = "vac_taken_date";
        } else {
            make_null = "reminder_date";
        }

        Map<String, String> update_query_data = new HashMap<String, String>();
        update_query_data.put("make_null", make_null);
        update_query_data.put("email", email);
        update_query_data.put("vac_name", vaccine_name);

        String url = "http://192.168.29.201:8000/update_appt_records/";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(update_query_data),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "onResponse: update success");
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

        mQueue = VolleySingleton.getInstance(context).getRequestQueue();
        mQueue.add(jsonObjectRequest);
    }
}
