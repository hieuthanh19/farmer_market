package com.example.farmersmarket.viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.R;
import com.example.farmersmarket.object.Orders;

import java.util.ArrayList;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private OnItemClickListener mlistner;
    private final ArrayList<Orders> arrCart;
    // Lưu Context để dễ dàng truy cập
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
        holder.order_product_name.setText(Integer.toString(carts.orderID));
    }

    @Override
    public int getItemCount() {
        return arrCart.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listner) {
        mlistner = listner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageDelete;
        public TextView order_product_name;
        public TextView order_provider;
        public TextView order_price;
        public TextView order_status;

        public ViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            imageDelete = view.findViewById(R.id.imageViewDelete);
            order_product_name = view.findViewById(R.id.order_product_name);
            order_provider = view.findViewById(R.id.order_provider);
            order_price = view.findViewById(R.id.order_price);
            order_status = view.findViewById(R.id.order_status);

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
