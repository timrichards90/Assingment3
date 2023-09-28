package com.example.assingment3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SkifieldAdapter extends RecyclerView.Adapter<SkifieldAdapter.SkifieldViewHolder> {
    private List<Skifield> skifields;

    public SkifieldAdapter(List<Skifield> skifields) {
        this.skifields = skifields;
    }

    public static class SkifieldViewHolder extends RecyclerView.ViewHolder {

        Button skifieldButton;
        ImageView skifieldLogo;

        public SkifieldViewHolder(@NonNull View itemView) {
            super(itemView);
            skifieldButton = itemView.findViewById(R.id.skifieldButton);
            skifieldLogo = itemView.findViewById(R.id.skifieldLogo);
        }

        // resize skifield icon
        public Drawable resizeIcon(Context context, int resId, int width, int height) {
            // load original icon using its resource ID
            Bitmap originalIcon = BitmapFactory.decodeResource(context.getResources(), resId);

            // create new bitmap with the correct size
            Bitmap resizedIcon = Bitmap.createScaledBitmap(originalIcon, width, height, false);

            // convert back to drawable
            return new BitmapDrawable(context.getResources(), resizedIcon);
        }

        public void bind(Skifield skifield) {
            // set skifield as buttons text
            skifieldButton.setText(skifield.getName());

            // get the context from the button to access resources
            Context context = skifieldButton.getContext();

            // declare desired size for the icon
            int desiredIconSizeInPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, context.getResources().getDisplayMetrics());

            // get the resized icon
            Drawable drawable = resizeIcon(context, skifield.getLogoId(), desiredIconSizeInPixels, desiredIconSizeInPixels);

            // set icon to the left of buttons text
            skifieldButton.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        }
    }

    @NonNull
    @Override
    public SkifieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skifield_item, parent, false);
        return new SkifieldViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkifieldViewHolder holder, int position) {
        Skifield skifield = skifields.get(position);
        holder.bind(skifield);
    }

    @Override
    public int getItemCount() {
        return skifields.size();
    }


}
