package com.example.assingment3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FacilitiesStatusAdapter extends RecyclerView.Adapter<FacilitiesStatusAdapter.FacilitiesViewHolder> {
    private static List<FacilitiesStatus> facilitiesStatuses;

    public FacilitiesStatusAdapter(List<FacilitiesStatus> facilitiesStatuses) {
        FacilitiesStatusAdapter.facilitiesStatuses = facilitiesStatuses;
    }

    public static class FacilitiesViewHolder extends RecyclerView.ViewHolder {
        TextView liftName, serviceName, roadName;
        ImageView liftStatusIcon, serviceStatusIcon, roadStatusIcon;
        TextView liftStatus, serviceStatus, roadStatus;

        public FacilitiesViewHolder(@NonNull View itemView) {
            super(itemView);
            liftName = itemView.findViewById(R.id.liftName);
            serviceName = itemView.findViewById(R.id.serviceName);
            roadName = itemView.findViewById(R.id.roadName);

            liftStatusIcon = itemView.findViewById(R.id.liftStatusIcon);
            serviceStatusIcon = itemView.findViewById(R.id.serviceStatusIcon);
            roadStatusIcon = itemView.findViewById(R.id.roadStatusIcon);

            liftStatus = itemView.findViewById(R.id.liftStatus);
            serviceStatus = itemView.findViewById(R.id.serviceStatus);
            roadStatus = itemView.findViewById(R.id.roadStatus);
        }

        public void bind(FacilitiesStatus facilitiesStatus) {
            resetViews();
            int statusIconRes = facilitiesStatus.isOpen() ? R.drawable.open_icon : R.drawable.closed_icon;
            String statusText = facilitiesStatus.isOpen() ? "OPEN" : "CLOSED";

            if(facilitiesStatus.getLiftName() != null) {
                liftName.setText(facilitiesStatus.getLiftName());
                liftStatusIcon.setImageResource(statusIconRes);
                liftStatus.setText(statusText);

                serviceName.setVisibility(View.GONE);
                serviceStatusIcon.setVisibility(View.GONE);
                serviceStatus.setVisibility(View.GONE);

                roadName.setVisibility(View.GONE);
                roadStatusIcon.setVisibility(View.GONE);
                roadStatus.setVisibility(View.GONE);
            } else if(facilitiesStatus.getServiceName() != null) {
                serviceName.setText(facilitiesStatus.getServiceName());
                serviceStatusIcon.setImageResource(statusIconRes);
                serviceStatus.setText(statusText);

                liftName.setVisibility(View.GONE);
                liftStatusIcon.setVisibility(View.GONE);
                liftStatus.setVisibility(View.GONE);

                roadName.setVisibility(View.GONE);
                roadStatusIcon.setVisibility(View.GONE);
                roadStatus.setVisibility(View.GONE);
            } else if(facilitiesStatus.getRoadName() != null) {
                roadName.setText(facilitiesStatus.getRoadName());
                roadStatusIcon.setImageResource(statusIconRes);
                roadStatus.setText(statusText);

                liftName.setVisibility(View.GONE);
                liftStatusIcon.setVisibility(View.GONE);
                liftStatus.setVisibility(View.GONE);

                serviceName.setVisibility(View.GONE);
                serviceStatusIcon.setVisibility(View.GONE);
                serviceStatus.setVisibility(View.GONE);
            }
        }


        public void resetViews() {
            liftName.setVisibility(View.VISIBLE);
            liftStatusIcon.setVisibility(View.VISIBLE);
            liftStatus.setVisibility(View.VISIBLE);

            serviceName.setVisibility(View.VISIBLE);
            serviceStatusIcon.setVisibility(View.VISIBLE);
            serviceStatus.setVisibility(View.VISIBLE);

            roadName.setVisibility(View.VISIBLE);
            roadStatusIcon.setVisibility(View.VISIBLE);
            roadStatus.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public FacilitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facilities_status, parent, false);
        return new FacilitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilitiesViewHolder holder, int position) {
        FacilitiesStatus facilitiesStatus = facilitiesStatuses.get(position);
        holder.bind(facilitiesStatus);
    }

    @Override
    public int getItemCount() {
        return facilitiesStatuses.size();
    }

    public void updateData(List<FacilitiesStatus> newFacilitiesStatusList) {
        this.facilitiesStatuses = newFacilitiesStatusList;
        notifyDataSetChanged();
    }
}
