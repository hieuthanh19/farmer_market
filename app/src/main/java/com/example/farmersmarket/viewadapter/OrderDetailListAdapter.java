package com.example.farmersmarket.viewadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class OrderDetailListAdapter extends RecyclerView.Adapter<OrderDetailListAdapter.ViewHolder>{

    private final List<OrderDetail> arrOrder;
    private final Context mContext;
    private AppDatabase appDatabase;

    public OrderDetailListAdapter(List<OrderDetail> orderList, Context mContext) {
        this.arrOrder = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderDetailListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_detail_list, parent, false);
        return new OrderDetailListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailListAdapter.ViewHolder holder, int position) {
        appDatabase = AppDatabase.getAppDatabase(mContext);
        OrderDetail orders = arrOrder.get(position);
        holder.txtOrderDetailProductName.setText(appDatabase.orderDetail().getProductNameByProductID(orders.productID));
        holder.txtOrdeDetailAmount.setText(Integer.toString(orders.quantity));
        holder.txtOrderDetailPrice.setText(Double.toString(orders.totalPrice));
    }

    @Override
    public int getItemCount() {
        return arrOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewOrderDetailProduct;
        public TextView txtOrderDetailProductName;
        public TextView txtOrdeDetailAmount;
        public TextView txtOrderDetailPrice;

        public ViewHolder(View view) {
            super(view);
            imageViewOrderDetailProduct = view.findViewById(R.id.imageViewOrderDetailProduct);
            txtOrderDetailProductName = view.findViewById(R.id.txtOrderDetailProductName);
            txtOrdeDetailAmount = view.findViewById(R.id.txtOrdeDetailAmount);
            txtOrderDetailPrice = view.findViewById(R.id.txtOrderDetailPrice);

        }

    }

}
