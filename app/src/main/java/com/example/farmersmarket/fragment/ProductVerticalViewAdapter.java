package com.example.farmersmarket.fragment;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.farmersmarket.R;
import com.example.farmersmarket.fragment.dummy.DummyContent.DummyItem;
import com.example.farmersmarket.object.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Product}.
 */
public class ProductVerticalViewAdapter extends RecyclerView.Adapter<ProductVerticalViewAdapter.ViewHolder> {

    private final List<Product> products;
//    private Context mContext;

    public ProductVerticalViewAdapter(List<Product> products) {
        this.products = products;
//        this.mContext = context;
    }

    /**
     * Required method for creating the viewholder objects.
     *
     * @param parent The ViewGroup into which the new View will be added
     *               after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     * @return The newly created ViewHolder.
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(final ProductVerticalViewAdapter.ViewHolder holder, int position) {
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
    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView productID;
        public final TextView productName;
        public final TextView productAmount;
        public final TextView productRating;
        public final TextView productPrice;
        public final ImageView productImage;


        public ViewHolder(View view) {
            super(view);

            productID = view.findViewById(R.id.product_id);
            productName = view.findViewById(R.id.product_name);
            productAmount = view.findViewById(R.id.product_amount);
            productRating = view.findViewById(R.id.product_rating);
            productPrice = view.findViewById(R.id.product_price);
            productImage = view.findViewById(R.id.product_image);
        }

        void bindTo(Product product) {
            // Populate views with data
            productID.setText(String.valueOf(product.productID));
            productName.setText(product.name);
            productAmount.setText(String.valueOf(product.amount));
            productPrice.setText(product.price + "VND/kg");
        }
    }
}