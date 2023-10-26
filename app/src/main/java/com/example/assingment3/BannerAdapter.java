package com.example.assingment3;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerViewHolder> {
    private String skiAreaName;
    private int skiAreaLogo;
    private String skiResortStatus;
    private String temperature;
    private String weatherCondition;
    private String timeString;

    public BannerAdapter(String skiAreaName, int skiAreaLogo, String skiResortStatus, String temperature, String weatherCondition, String timeString) {
        this.skiAreaName = skiAreaName;
        this.skiAreaLogo = skiAreaLogo;
        this.skiResortStatus = skiResortStatus;
        this.temperature = temperature;
        this.weatherCondition = weatherCondition;
        this.timeString = timeString;
    }

    @NonNull
    @Override
    public BannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.banner_layout, parent, false);
        return new BannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerViewHolder holder, int position) {
        holder.bind(skiAreaName, skiAreaLogo, skiResortStatus, temperature, weatherCondition, timeString);
    }

    @Override
    public int getItemCount() {
        return 1;
    } // can only have one banner

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        TextView skiResortStatusTextView;
        ImageView skiAreaLogoImageView;
        TextView skiAreaNameTextView;
        ImageView weatherIconImageView;
        TextView weatherDescriptionTextView;
        TextView weatherTempTextView;
        TextView lastUpdatedTextView;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            skiResortStatusTextView = itemView.findViewById(R.id.skiResortStatusTextView);
            skiAreaLogoImageView = itemView.findViewById(R.id.skiAreaLogoImageView);
            skiAreaNameTextView = itemView.findViewById(R.id.skiAreaNameTextView);
            weatherIconImageView = itemView.findViewById(R.id.weatherIconImageView);
            weatherDescriptionTextView = itemView.findViewById(R.id.weatherDescriptionTextView);
            weatherTempTextView = itemView.findViewById(R.id.weatherTempTextView);
            lastUpdatedTextView = itemView.findViewById(R.id.lastUpdatedTextView);
        }

        public void bind(String skiAreaName, int skiAreaLogo, String skiResortStatus, String temperature, String weatherCondition, String timeString) {
            skiAreaNameTextView.setText(skiAreaName);
            skiAreaLogoImageView.setImageResource(skiAreaLogo);

            // display resort status - green if open red if closed
            String text = "RESORT STATUS: " + skiResortStatus;
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, "RESORT STATUS: ".length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if ("OPEN".equalsIgnoreCase(skiResortStatus)) {
                spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), text.length() - "OPEN".length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if ("CLOSED".equalsIgnoreCase(skiResortStatus)) {
                spannableString.setSpan(new ForegroundColorSpan(Color.RED), text.length() - "CLOSED".length(), text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            skiResortStatusTextView.setText(spannableString);

            // display weather forecast
            weatherTempTextView.setText(temperature);
            String modifiedWeatherCondition = "a" + weatherCondition;
            int resourceId = itemView.getContext().getResources().getIdentifier(modifiedWeatherCondition, "drawable", itemView.getContext().getPackageName());
            weatherIconImageView.setImageResource(resourceId);

            // display last generated string
            lastUpdatedTextView.setText(timeString);
        }
    }
}
