package com.example.farmersmarket.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.R;
import com.example.farmersmarket.object.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {


    private final ArrayList<Orders> arrOrder;
    // Lưu Context để dễ dàng truy cập
    private final Context mContext;

    public OrderListAdapter(ArrayList<Orders> orderList, Context mContext) {
        this.arrOrder = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orders orders = arrOrder.get(position);
        holder.order_product_name.setText(Integer.toString(orders.orderID));
        holder.order_provider.setText(Integer.toString(orders.storeHouseID));
        holder.order_price.setText(Double.toString( orders.total));
        holder.order_product_name.setText(Integer.toString(orders.orderID));

    }

    @Override
    public int getItemCount() {
        return arrOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView order_product_name;
        public TextView order_provider;
        public TextView order_price;
        public TextView order_status;

        public ViewHolder(View view) {
            super(view);
            order_product_name = view.findViewById(R.id.order_product_name);
            order_provider = view.findViewById(R.id.order_provider);
            order_price = view.findViewById(R.id.order_price);
            order_status = view.findViewById(R.id.order_status);
        }

    }
}
