package com.example.farmersmarket.viewadapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.ProductDetail;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.object.ProductImage;

import java.util.List;

public class ProductHorizontalViewAdapter extends RecyclerView.Adapter<ProductHorizontalViewAdapter.ViewHolder> {
    private final List<Product> products;
//    private Context mContext;

    public ProductHorizontalViewAdapter(List<Product> products) {
        this.products = products;
//        this.mContext = context;
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
    public ProductHorizontalViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_vertical, parent, false);
        return new ProductHorizontalViewAdapter.ViewHolder(view);
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(final ProductHorizontalViewAdapter.ViewHolder holder, int position) {
        // Get current product
        Product product = products.get(position);
        //Populate the view with data
        holder.bindTo(product);

    }

    /**
     * Required method for determining the size of the data set.
     *
     * @return Size of the data set.
     */
    @Override
    public int getItemCount() {
        return products.size();
    }

    /**
     * ViewHolder class that represents each row of data in the RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView productName;
        public final TextView productAmount;
        public final TextView productPrice;
        public final ImageView productImage;
        public final TextView productSale;

        public ViewHolder(View view) {
            super(view);

            productName = view.findViewById(R.id.product_name);
            productAmount = view.findViewById(R.id.product_amount);
            productPrice = view.findViewById(R.id.product_price);
            productImage = view.findViewById(R.id.product_image);
            productSale = view.findViewById(R.id.sale_tag);

            view.setOnClickListener(this);
        }

        void bindTo(Product product) {
            // Populate views with data
            // If product is on sale
            if (product.price > product.currentPrice) {
                // display sale tag and find sale percentage
                productSale.setVisibility(View.VISIBLE);
                int salePercentage = (int) Math.ceil(((product.price - product.currentPrice) / product.price) * 100);
                productSale.setText(itemView.getContext().getString(R.string.sale_tag, salePercentage));
                // Set price color to text_sale
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    productPrice.setTextColor(itemView.getContext().getColor(R.color.text_sale));
                }
            } else {
                // Hide sale tag & change price color to normal
                productSale.setVisibility(View.INVISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    productPrice.setTextColor(itemView.getContext().getColor(R.color.black));
                }
            }
            productName.setText(product.name);
            productAmount.setText(itemView.getResources().getString(R.string.product_amount, product.amount));
            productPrice.setText(itemView.getResources().getString(R.string.product_price, product.currentPrice));
            // load images of product
            AppDatabase appDatabase = AppDatabase.getAppDatabase(itemView.getContext());
            List<ProductImage> productImageList =
                    appDatabase.productImageDAO().getProductImageByProductID(product.productID);
            // if product have image -> load first image
            if (productImageList.size() > 0)
                Glide.with(itemView.getContext()).load(Uri.parse(productImageList.get(0).URL)).centerCrop().into(productImage);
                // if not -> load empty image
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Glide.with(itemView.getContext()).load(R.drawable.empty).centerCrop().into(productImage);
            }
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            int productID = products.get(getLayoutPosition()).productID;
            Intent intent = new Intent(v.getContext(), ProductDetail.class);
            intent.putExtra(ProductDetail.PRODUCT_ID, productID);
            v.getContext().startActivity(intent);
        }
    }
}
