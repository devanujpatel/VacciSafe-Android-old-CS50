package com.example.vaccisafe;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
            holder.mark_as_txt.setBackgroundColor(Color.parseColor("#00ff00"));
        } else {
            holder.mark_as_txt.setBackgroundColor(Color.parseColor("#FFA500"));
        }
    }

    @Override
    public int getItemCount() {
        return vaccines.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView vaccine_name_txt;
        private TextView age_txt;
        private TextView mark_as_txt;

        public MyViewHolder(final View view) {
            super(view);
            vaccine_name_txt = view.findViewById(R.id.vaccine_name);
            age_txt = view.findViewById(R.id.age);
            mark_as_txt = view.findViewById(R.id.mark_as_txt);
        }

    }
}
