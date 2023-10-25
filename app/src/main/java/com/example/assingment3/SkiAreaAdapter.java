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

public class SkiAreaAdapter extends RecyclerView.Adapter<SkiAreaAdapter.SkifieldViewHolder> {
    // list of ski ares to display in recycler view
    private static List<SkiArea> skiAreas;

    // initialise ski area list
    public SkiAreaAdapter(List<SkiArea> skiAreas) {
        SkiAreaAdapter.skiAreas = skiAreas;
    }

    public static class SkifieldViewHolder extends RecyclerView.ViewHolder {
        // button for each ski area in the list
        Button skifieldButton;

        public SkifieldViewHolder(@NonNull View itemView) {
            super(itemView);
            skifieldButton = itemView.findViewById(R.id.skifieldButton);
            skifieldButton.setOnClickListener(view -> {
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

                context.startActivity(intent);
            });
        }

        // resize ski area icon so they are all the same size
        public Drawable resizeIcon(Context context, int resId, int width, int height) {
            // load original icon using its resource ID
            Bitmap originalIcon = BitmapFactory.decodeResource(context.getResources(), resId);
            // create new bitmap with the correct size
            Bitmap resizedIcon = Bitmap.createScaledBitmap(originalIcon, width, height, false);
            // convert back to drawable
            return new BitmapDrawable(context.getResources(), resizedIcon);
        }

        public void bind(SkiArea skiArea) {
            // set skifield as buttons text
            skifieldButton.setText(skiArea.getName());
            // get the context from the button to access resources
            Context context = skifieldButton.getContext();
            // declare desired size for the icon
            int desiredIconSizeInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, context.getResources().getDisplayMetrics());
            // get the resized icon
            Drawable drawable = resizeIcon(context, skiArea.getLogoId(), desiredIconSizeInPixels, desiredIconSizeInPixels);
            // set icon to the left of buttons text
            skifieldButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    @NonNull
    @Override
    // inflate layout for ski area items
    public SkifieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skifield_item, parent, false);
        return new SkifieldViewHolder(view);
    }

    @Override
    // bind ski area to viewholder
    public void onBindViewHolder(@NonNull SkifieldViewHolder holder, int position) {
        SkiArea skiArea = skiAreas.get(position);
        holder.bind(skiArea);
    }

    @Override
    // return number of ski areas in list
    public int getItemCount() {
        return skiAreas.size();
    }
}
