package com.example.farmersmarket.viewadapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.Orders;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private OnItemClickListener mlistner;
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
        return new ViewHolder(view,mlistner);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, int position) {
        appDatabase = AppDatabase.getAppDatabase(mContext);
        OrderDetail carts = arrCart.get(position);
        String path = appDatabase.productImageDAO().getOneProductImageByProductID(carts.productID);
        double price = appDatabase.orderDetail().getProductPriceByProductID(carts.productID);

        holder.cart_product_name.setText(appDatabase.orderDetail().getProductNameByProductID(carts.productID));
        Picasso.with(mContext).load(path).into(holder.cart_product_image);
        holder.cart_price.setText("$"+Double.toString(price)+"/kg");
        holder.cart_price_change.setText("$"+Double.toString(price*carts.quantity)+"/kg");
        holder.cart_amount.setNumber(Integer.toString(carts.quantity));
        holder.cart_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                holder.cart_price_change.setText("$"+Double.toString(price*newValue)+"/kg");
                appDatabase.orderDetail().updateQuantityAndPrice(carts.productID,carts.ordersID, Integer.parseInt(holder.cart_amount.getNumber()),price*newValue);

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

    public int getOrderId(int position){
        return arrCart.get(position).ordersID;
    }

    public int getProductId(int position){
        return arrCart.get(position).productID;
    }

    public void setOnItemClickListener(OnItemClickListener listner) {
        mlistner = listner;
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
