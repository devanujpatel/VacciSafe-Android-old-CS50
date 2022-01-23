package com.example.vaccisafe;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class VaccineRecyclerAdapter extends RecyclerView.Adapter<VaccineRecyclerAdapter.MyViewHolder> {

    private ArrayList<Vaccine> vaccines;
    public VaccineRecyclerAdapter(ArrayList<Vaccine> vaccines) {
        this.vaccines = vaccines;
    }

    @NonNull
    @Override
    public VaccineRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vaccine_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VaccineRecyclerAdapter.MyViewHolder holder, int position) {
        String vaccine_name = vaccines.get(position).getVaccine_name();
        String age = vaccines.get(position).getTaken_at();
        String mark_as = vaccines.get(position).getMark_as();

        holder.vaccine_name_txt.setText(vaccine_name);
        holder.age_txt.setText(age);
        holder.mark_as_txt.setText(mark_as);

        if (mark_as.equals("Taken")) {
            holder.mark_as_txt.setBackgroundColor(Color.parseColor("#4caf50"));
        } else {
            holder.mark_as_txt.setBackgroundColor(Color.parseColor("#f97444"));
        }
    }

    @Override
    public int getItemCount() {
        return vaccines.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView vaccine_name_txt;
        private TextView age_txt;
        private Button mark_as_txt;

        public MyViewHolder(final View view) {
            super(view);
            vaccine_name_txt = view.findViewById(R.id.vaccine_name);
            age_txt = view.findViewById(R.id.age);
            mark_as_txt = view.findViewById(R.id.mark_as_txt);

            mark_as_txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("BUTTON CLICK", "onClick: " + getAbsoluteAdapterPosition());
                    int index = getAbsoluteAdapterPosition();
                    Vaccine vaccine_obj = vaccines.get(index);
                    vaccine_obj.update_in_server(mark_as_txt.getText());
                    if (mark_as_txt.getText().equals("Not Taken")) {
                        vaccines.get(getAbsoluteAdapterPosition()).setMark_as("Taken");
                        mark_as_txt.setText("Taken");
                        mark_as_txt.setBackgroundColor(Color.parseColor("#4caf50"));
                    } else {
                        mark_as_txt.setText("Not Taken");
                        vaccines.get(getAbsoluteAdapterPosition()).setMark_as("Not Taken");
                        mark_as_txt.setBackgroundColor(Color.parseColor("#f97444"));
                    }
                }
            });

        }
    }
}
