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
import com.example.farmersmarket.object.Orders;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private OnItemClickListener mlistner;
    private final ArrayList<Orders> arrCart;
    private final Context mContext;

    public CarAdapter(ArrayList<Orders> cartList, Context mContext) {
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
        Orders carts = arrCart.get(position);
        holder.cart_product_name.setText(Integer.toString(carts.orderID));
        Picasso.with(mContext).load("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Four_Super_Hornets.jpg/240px-Four_Super_Hornets.jpg").into(holder.cart_product_image);
        holder.cart_provider.setText(Integer.toString(carts.storeHouseID));
        holder.cart_price.setText(Double.toString(carts.total));
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

    public void setOnItemClickListener(OnItemClickListener listner) {
        mlistner = listner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageDelete;
        public ImageView cart_product_image;
        public TextView cart_product_name;
        public TextView cart_provider;
        public TextView cart_price;
        public ElegantNumberButton cart_amount;

        public ViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            imageDelete = view.findViewById(R.id.imageViewDelete);
            cart_product_image = view.findViewById(R.id.cart_product_image);
            cart_product_name = view.findViewById(R.id.cart_product_name);
            cart_provider = view.findViewById(R.id.cart_provider);
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
