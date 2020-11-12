package com.example.farmersmarket.viewadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.R;
import com.example.farmersmarket.object.ProductImage;

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ViewHolder> {
    public final List<ProductImage> productImages;
//    private Context mContext;

    public ProductImageAdapter(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent   The ViewGroup into which the new View will be added
     *                 after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public ProductImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_image, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(final ProductImageAdapter.ViewHolder holder, int position) {
        // Get current product
        ProductImage productImage = productImages.get(position);
        //Populate the view with data
        holder.bindTo(productImage);
        holder.removeImage.setOnClickListener(view -> {
            productImages.remove(productImage);
            notifyDataSetChanged();
        });

    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return productImages.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView productImageImage, removeImage;

        public ViewHolder(View view) {
            super(view);
            productImageImage = view.findViewById(R.id.product_image_image);
            removeImage = view.findViewById(R.id.product_image_remove);
        }

        void bindTo(ProductImage productImage) {
            // Populate views with data
            Glide.with(itemView.getContext()).load(productImage.URL).centerCrop().into(productImageImage);
        }
    }
}
