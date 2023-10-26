package com.example.assingment3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder> {
    private List<Facility> facilities;
    private String lastDisplayedFacilityName;

    public FacilityAdapter(List<Facility> facilities) {
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

        // show or hide facility name to prevent repetition
        if (!currentFacility.getCurrentFacilityName().equals(lastDisplayedFacilityName)) {
            lastDisplayedFacilityName = currentFacility.getCurrentFacilityName();
            holder.currentFacilityNameTextView.setText(currentFacility.getCurrentFacilityName());
            holder.currentFacilityNameTextView.setVisibility(View.VISIBLE);
        } else {
            holder.currentFacilityNameTextView.setVisibility(View.GONE);
        }

        holder.bind(currentFacility);
    }

    @Override
    public int getItemCount() {
        return facilities.size();
    }

    class FacilityViewHolder extends RecyclerView.ViewHolder {
        TextView currentFacilityNameTextView;
        TextView facilityNameTextView;
        ImageView facilityStatusIcon;
        TextView facilityStatusTextView;

        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            currentFacilityNameTextView = itemView.findViewById(R.id.currentFacilityNameTextView);
            facilityNameTextView = itemView.findViewById(R.id.facilityNameTextView);
            facilityStatusIcon = itemView.findViewById(R.id.facilityStatusIcon);
            facilityStatusTextView = itemView.findViewById(R.id.facilityStatusTextView);
        }

        public void bind(Facility facility) {
            facilityNameTextView.setText(facility.getCategory());
            // set icon and text displaying if facility is open or closed
            if (facility.isOpen()) {
                facilityStatusIcon.setImageResource(R.drawable.open_icon);
                facilityStatusTextView.setText("OPEN");
            } else {
                facilityStatusIcon.setImageResource(R.drawable.closed_icon);
                facilityStatusTextView.setText("CLOSED");
            }
        }
    }
}
