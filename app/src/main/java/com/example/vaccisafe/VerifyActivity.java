package com.example.vaccisafe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class VerifyActivity extends AppCompatActivity {

    private ArrayList<Vaccine> vaccineArrayList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        vaccineArrayList = new ArrayList<>();

        // this activity will only be called when patient is created and is only called from RegisterActivity
        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        Log.d(TAG, "onCreate: " + data);
        try {
            JSONArray jsonarray = new JSONArray(data);
            for (int i = 0; i < jsonarray.length(); i++) {
                JSONObject vaccines_json = jsonarray.getJSONObject(i);

                String years = vaccines_json.getString("years");
                String months = vaccines_json.getString("months");
                String weeks = vaccines_json.getString("weeks");
                String taken_at = "";

                if (years.equals("0") && months.equals("0") && weeks.equals("0")) {
                    taken_at = "Birth";
                }

                if (!years.equals("0")) {
                    taken_at += years + "Y ";
                }
                if (!months.equals("0")) {
                    taken_at += months + "M ";
                }
                if (!weeks.equals("0")) {
                    taken_at += weeks + "W";
                }

                String mark_as;

                if (vaccines_json.getString("vac_taken_date").equals("null")) {
                    mark_as = "Not Taken";
                } else {
                    mark_as = "Taken";
                }

                Log.d(TAG, "onCreate: "+taken_at + " "+mark_as);

                Vaccine vaccine = new Vaccine(vaccines_json.getString("name"), taken_at, mark_as);
                vaccineArrayList.add(vaccine);

            }
        } catch (JSONException exception) {
            exception.printStackTrace();
        }
        setAdapter();
    }

    private void setAdapter() {
        /*VaccineRecyclerAdapter adapter = new VaccineRecyclerAdapter(vaccineArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        */
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.vaccine_recycler_view);
        VaccineRecyclerAdapter adapter = new VaccineRecyclerAdapter(vaccineArrayList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }
}