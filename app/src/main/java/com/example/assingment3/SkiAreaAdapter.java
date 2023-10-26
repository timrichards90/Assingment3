package com.example.assingment3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SkiAreaAdapter extends RecyclerView.Adapter<SkiAreaAdapter.SkiAreaViewHolder> {
    // list of ski ares to display in recycler view
    private static List<SkiArea> skiAreas;

    // initialise ski area list
    public SkiAreaAdapter(List<SkiArea> skiAreas) {
        SkiAreaAdapter.skiAreas = skiAreas;
    }

    public static class SkiAreaViewHolder extends RecyclerView.ViewHolder {
        // button for each ski area in the list
        Button skiAreaButton;

        public SkiAreaViewHolder(@NonNull View itemView) {
            super(itemView);
            skiAreaButton = itemView.findViewById(R.id.skiAreaButton);
            skiAreaButton.setOnClickListener(view -> {
                int position = getAdapterPosition();
                SkiArea clickedSkiArea = skiAreas.get(position);

                Context context = view.getContext();

                // pass information to ski area activity when button is clicked
                Intent intent = new Intent(context, SkiAreaActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("skiAreaName", clickedSkiArea.getName());
                bundle.putInt("skiAreaLogo", clickedSkiArea.getLogoId());
                bundle.putString("skiAreaUrl", clickedSkiArea.getUrl());
                intent.putExtras(bundle);
                context.startActivity(intent);

            });
        }

        public void bind(SkiArea skiArea) {
            // set ski area name as the text on the buttons
            skiAreaButton.setText(skiArea.getName());
            // get the context from the button to access resources
            Context context = skiAreaButton.getContext();
            // declare desired size for the icon
            int desiredIconSizeInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());
            // get the resized icon
            Drawable drawable = resizeIcon(context, skiArea.getLogoId(), desiredIconSizeInPixels, desiredIconSizeInPixels);
            // set icon to the left of buttons text
            skiAreaButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }

        // resize ski area icon so they are all the same size
        public Drawable resizeIcon(Context context, int resId, int width, int height) {
            Bitmap originalIcon = BitmapFactory.decodeResource(context.getResources(), resId);
            Bitmap resizedIcon = Bitmap.createScaledBitmap(originalIcon, width, height, false);
            return new BitmapDrawable(context.getResources(), resizedIcon);
        }
    }

    @NonNull
    @Override
    // inflate layout for ski area items
    public SkiAreaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skifield_item, parent, false);
        return new SkiAreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkiAreaViewHolder holder, int position) {
        SkiArea skiArea = skiAreas.get(position);
        holder.bind(skiArea);
    }

    @Override
    public int getItemCount() {
        return skiAreas.size();
    }
}
