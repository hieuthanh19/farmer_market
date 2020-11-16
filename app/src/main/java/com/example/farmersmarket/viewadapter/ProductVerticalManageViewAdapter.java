package com.example.farmersmarket.viewadapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.AddProduct;
import com.example.farmersmarket.ProductDetail;
import com.example.farmersmarket.R;
import com.example.farmersmarket.WarehouseDetail;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Product;
import com.example.farmersmarket.object.ProductImage;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class ProductVerticalManageViewAdapter extends RecyclerView.Adapter<ProductVerticalManageViewAdapter.ViewHolder> {
    private final List<Product> products;
    private Context mContext;

    public ProductVerticalManageViewAdapter(Context context, List<Product> products) {
        this.products = products;
        this.mContext = context;
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
    public ProductVerticalManageViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_manage, parent, false);
        return new ProductVerticalManageViewAdapter.ViewHolder(view);
    }

    /**
     * Required method that binds the data to the viewholder.
     *
     * @param holder   The viewholder into which the data should be put.
     * @param position The adapter position.
     */
    @Override
    public void onBindViewHolder(final ProductVerticalManageViewAdapter.ViewHolder holder, int position) {
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
        public final TextView productEdit;
        public final TextView productDelete;
        public final ImageView productImage;
        public Context context = itemView.getContext();

        public ViewHolder(View view) {
            super(view);
            productImage = view.findViewById(R.id.product_image);
            productName = view.findViewById(R.id.product_name);
            productAmount = view.findViewById(R.id.product_amount);
            productPrice = view.findViewById(R.id.product_price);
            productEdit = view.findViewById(R.id.product_edit);
            productDelete = view.findViewById(R.id.product_delete);

            view.setOnClickListener(this);
        }

        void bindTo(Product product) {
            // Populate views with data
            productName.setText(product.name);
            productAmount.setText(itemView.getResources().getString(R.string.product_amount, product.amount));
            productPrice.setText(itemView.getResources().getString(R.string.product_price, product.currentPrice));
            productEdit.setOnClickListener(onClickEdit(product.productID));
            productDelete.setOnClickListener(onClickDelete(product));
            // load images of product
            AppDatabase appDatabase = AppDatabase.getAppDatabase(itemView.getContext());
            List<ProductImage> productImageList =
                    appDatabase.productImageDAO().getProductImageByProductID(product.productID);
            // if product have image -> load first image
            if (productImageList.size() > 0)
                if (productImageList.get(0).URL.startsWith("@drawable")) {
                    int resource = context.getResources().getIdentifier(productImageList.get(0).URL, "drawable",
                            context.getPackageName());
                    Glide.with(itemView.getContext()).load(resource).centerCrop().into(productImage);
                } else
                    Glide.with(context).load(Uri.parse(productImageList.get(0).URL)).centerCrop().into(productImage);
                // if not -> load empty image
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Glide.with(context).load(R.drawable.empty).centerCrop().into(productImage);
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

        /**
         * Set action for Edit button. This will open {@link ProductDetail} activity with {@code Warehouse.MODE_EDIT}
         *
         * @param productID ID of product to edit
         * @return a {@link View.OnClickListener}
         */
        private View.OnClickListener onClickEdit(int productID) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), AddProduct.class);
                    intent.putExtra(WarehouseDetail.PRODUCT_ID, productID);
                    intent.putExtra(WarehouseDetail.PRODUCT_MODE, WarehouseDetail.MODE_EDIT);
                    ((Activity) mContext).startActivityForResult(intent, WarehouseDetail.REQUEST_CODE);
                }
            };
        }

        /**
         * Set action for Delete button. This will display a {@link MaterialAlertDialogBuilder} with 2 options:
         * Cancel and Delete
         *
         * @param product Product to delete
         * @return a {@link View.OnClickListener}
         */
        private View.OnClickListener onClickDelete(Product product) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create a dialog to confirm
                    new MaterialAlertDialogBuilder(itemView.getContext())
                            .setTitle(v.getResources().getString(R.string.dialog_title_delete, product.name))
                            .setMessage(R.string.dialog_delete_message)
                            .setNegativeButton(R.string.dialog_option_cancel, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setPositiveButton(R.string.dialog_option_delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Soft delete product
                                    product.status = 0;
                                    AppDatabase.getAppDatabase(itemView.getContext()).productDAO().updateProduct(product);
                                    products.remove(product);
                                    notifyDataSetChanged();
                                    dialog.dismiss();
                                    // Display message
                                    Toast.makeText(v.getContext(),
                                            v.getContext().getResources().getString(R.string.toast_delete_success,
                                                    product.name), Toast.LENGTH_SHORT).show();
                                }
                            }).show();
                }
            };
        }
    }
}
