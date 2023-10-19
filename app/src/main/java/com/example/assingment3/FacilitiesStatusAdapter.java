package com.example.assingment3;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FacilitiesStatusAdapter extends RecyclerView.Adapter<FacilitiesStatusAdapter.FacilityViewHolder> {
    private List<Facility> facilities;
    private String skiAreaName;
    private int skiAreaLogo;
    private String skiResortStatus;
    private String temperature;
    private String weatherCondition;

    public FacilitiesStatusAdapter(List<Facility> facilities, String skiAreaName, int skiAreaLogo, String skiResortStatus, String temperature, String weatherCondition) {
        this.facilities = facilities;
        this.skiAreaName = skiAreaName;
        this.skiAreaLogo = skiAreaLogo;
        this.skiResortStatus = skiResortStatus;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
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
        holder.bind(currentFacility, skiResortStatus, position, temperature, weatherCondition);

        if(skiAreaName != null && skiAreaLogo != -1 && position == 0) {  // Only show banner for the first item
            holder.skiAreaNameTextView.setText(skiAreaName);
            holder.skiAreaLogoImageView.setImageResource(skiAreaLogo);
            holder.skiAreaBanner.setVisibility(View.VISIBLE);
        } else {
            holder.skiAreaBanner.setVisibility(View.GONE);
        }

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
        TextView skiResortStatusTextView;
        TextView currentFacilityNameTextView;
        TextView nameTextView;
        ImageView statusImageView;
        TextView statusTextView;
        LinearLayout skiAreaBanner;
        TextView skiAreaNameTextView;
        ImageView skiAreaLogoImageView;
        ImageView weatherIconImageView;
        TextView weatherDescriptionTextView;
        TextView weatherTempTextView;

        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            skiResortStatusTextView = itemView.findViewById(R.id.skiResortStatusTextView);
            currentFacilityNameTextView = itemView.findViewById(R.id.currentFacilityNameTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            statusImageView = itemView.findViewById(R.id.statusImageView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            skiAreaBanner = itemView.findViewById(R.id.skiAreaBanner);
            skiAreaNameTextView = itemView.findViewById(R.id.skiAreaNameTextView);
            skiAreaLogoImageView = itemView.findViewById(R.id.skiAreaLogoImageView);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView);
            weatherDescriptionTextView = itemView.findViewById(R.id.weatherDescriptionTextView);
            weatherTempTextView = itemView.findViewById(R.id.weatherTempTextView);
        }

        public void bind(Facility facility, String skiResortStatus, int position, String temperature, String weatherCondition) {
            nameTextView.setText(facility.getName());
            statusImageView.setImageResource(facility.isOpen() ? R.drawable.open_icon : R.drawable.closed_icon);
            statusTextView.setText(facility.isOpen() ? "OPEN" : "CLOSED");

            if (position == 0 && skiResortStatus != null && !skiResortStatus.isEmpty()) {
                String text = "RESORT STATUS: " + skiResortStatus;
                weatherTempTextView.setText(temperature);
                // image resources must start with a letter so we add a to the original string here
                String modifiedWeatherCondition = "a" + weatherCondition;
                int resourceId = itemView.getContext().getResources().getIdentifier(modifiedWeatherCondition, "drawable", itemView.getContext().getPackageName());
                weatherIconImageView.setImageResource(resourceId);
                SpannableString spannableString = new SpannableString(text);

                spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, "RESORT STATUS: ".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                if ("OPEN".equalsIgnoreCase(skiResortStatus)) {
                    spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), text.length() - "OPEN".length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else if ("CLOSED".equalsIgnoreCase(skiResortStatus)) {
                    spannableString.setSpan(new ForegroundColorSpan(Color.RED), text.length() - "CLOSED".length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                skiResortStatusTextView.setText(spannableString);
            } else {
                skiResortStatusTextView.setVisibility(View.GONE);
            }


            if(facility.getCurrentFacilityName() != null && !facility.getCurrentFacilityName().isEmpty()) {
                currentFacilityNameTextView.setText(facility.getCurrentFacilityName());
                currentFacilityNameTextView.setVisibility(View.VISIBLE);
            } else {
                currentFacilityNameTextView.setVisibility(View.GONE);
            }
        }
    }
}
