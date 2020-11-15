package com.example.farmersmarket.viewadapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.ProductImage;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private OnItemClickListener mlistener;
    private final List<OrderDetail> arrCart;
    private final Context mContext;
    private AppDatabase appDatabase;


    public CarAdapter(List<OrderDetail> cartList, Context mContext) {
        this.arrCart = cartList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_cart_list, parent, false);
        return new ViewHolder(view, mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, int position) {
        appDatabase = AppDatabase.getAppDatabase(mContext);
        OrderDetail carts = arrCart.get(position);
        double price = appDatabase.productDAO().getProduct(carts.productID).currentPrice;

        //LOAD IMAGE
        List<ProductImage> productImageList =
                appDatabase.productImageDAO().getProductImageByProductID(carts.productID);
        // if product have image -> load first image
        if (productImageList.size() > 0) {
            if (productImageList.get(0).URL.startsWith("@drawable")) {
                int resource = mContext.getResources().getIdentifier(productImageList.get(0).URL, "drawable",
                        mContext.getPackageName());
                Glide.with(mContext).load(resource).centerCrop().into(holder.cart_product_image);
            } else
                Glide.with(mContext).load(Uri.parse(productImageList.get(0).URL)).centerCrop().into(holder.cart_product_image);
        }
        // if not -> load empty image
        else {
            Glide.with(mContext).load(R.drawable.empty).centerCrop().into(holder.cart_product_image);
        }

        holder.cart_product_name.setText(appDatabase.productDAO().getProduct(carts.productID).name);
        holder.cart_price.setText(mContext.getString(R.string.product_price, price));
        holder.cart_price_change.setText(mContext.getString(R.string.product_price, price * carts.quantity));
        holder.cart_amount.setNumber(Integer.toString(carts.quantity));
        holder.cart_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                holder.cart_price_change.setText(mContext.getString(R.string.product_price, price * newValue));
                appDatabase.orderDetailDAO().updateQuantityAndPrice(carts.productID, carts.orderID,
                        Integer.parseInt(holder.cart_amount.getNumber()), price * newValue);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrCart.size();
    }

    //Create event for item
    public interface OnItemClickListener {
        void onItemClick(int position);

        void onDeleteClick(int position);
    }

    public int getOrderId(int position) {
        return arrCart.get(position).orderID;
    }

    public int getProductId(int position) {
        return arrCart.get(position).productID;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageDelete;
        public ImageView cart_product_image;
        public TextView cart_product_name;
        public TextView cart_price_change;
        public TextView cart_price;
        public ElegantNumberButton cart_amount;

        public ViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            imageDelete = view.findViewById(R.id.imageViewDelete);
            cart_product_image = view.findViewById(R.id.cart_product_image);
            cart_product_name = view.findViewById(R.id.cart_product_name);
            cart_price_change =view.findViewById(R.id.cart_price_change);
            cart_price = view.findViewById(R.id.cart_price);
            cart_amount = view.findViewById(R.id.cart_amount);

            view.setOnClickListener((v) -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });

            imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }

    }
}
