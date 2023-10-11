package com.example.assingment3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FacilitiesStatusAdapter extends RecyclerView.Adapter<FacilitiesStatusAdapter.FacilityViewHolder> {
    private List<Facility> facilities;

    public FacilitiesStatusAdapter(List<Facility> facilities) {
        this.facilities = facilities;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_item, parent, false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        Facility currentFacility = facilities.get(position);
        holder.bind(currentFacility);

        if(position == 0 || !currentFacility.getCurrentFacilityName().equals(facilities.get(position-1).getCurrentFacilityName())) {
            holder.currentFacilityNameTextView.setText(currentFacility.getCurrentFacilityName());
            holder.currentFacilityNameTextView.setVisibility(View.VISIBLE);
        } else {
            holder.currentFacilityNameTextView.setVisibility(View.GONE);
        }
    }



    @Override
    public int getItemCount() {
        return facilities.size();
    }

    public void updateData(List<Facility> newFacilityList) {
        this.facilities = newFacilityList;
        notifyDataSetChanged();
    }

    static class FacilityViewHolder extends RecyclerView.ViewHolder {
        TextView currentFacilityNameTextView;
        TextView nameTextView;
        ImageView statusImageView;
        TextView statusTextView;

        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            currentFacilityNameTextView = itemView.findViewById(R.id.currentFacilityNameTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusImageView = itemView.findViewById(R.id.statusImageView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }

        public void bind(Facility facility) {
            nameTextView.setText(facility.getName());
            statusImageView.setImageResource(facility.isOpen() ? R.drawable.open_icon : R.drawable.closed_icon);
            statusTextView.setText(facility.isOpen() ? "OPEN" : "CLOSED");

            if(facility.getCurrentFacilityName() != null && !facility.getCurrentFacilityName().isEmpty()) {
                currentFacilityNameTextView.setText(facility.getCurrentFacilityName());
                currentFacilityNameTextView.setVisibility(View.VISIBLE);
            } else {
                currentFacilityNameTextView.setVisibility(View.GONE);
            }
        }
    }
}
