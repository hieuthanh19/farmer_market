package com.example.farmersmarket.viewadapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.object.ProductImage;

import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private OnItemClickListener mlistner;
    private final List<Orders> arrOrder;
    private final Context mContext;
    private AppDatabase appDatabase;

    public OrderListAdapter(List<Orders> orderList, Context mContext) {
        this.arrOrder = orderList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_order_list, parent, false);
        return new ViewHolder(view,mlistner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Orders orders = arrOrder.get(position);
        appDatabase = AppDatabase.getAppDatabase(mContext);

        List<OrderDetail> arrOrder =
                appDatabase.orderDetailDAO().getAllOrderDetailByOrderID(orders.orderID);

        //LOAD IMAGE
        List<ProductImage> productImageList =
                appDatabase.productImageDAO().getProductImageByProductID(arrOrder.get(0).productID);
        // if product have image -> load first image
        if (productImageList.size() > 0) {
            if (productImageList.get(0).URL.startsWith("@drawable")) {
                int resource = mContext.getResources().getIdentifier(productImageList.get(0).URL, "drawable",
                        mContext.getPackageName());
                Glide.with(mContext).load(resource).centerCrop().into(holder.product_image);
            } else
                Glide.with(mContext).load(Uri.parse(productImageList.get(0).URL)).centerCrop().into(holder.product_image);
        }
        // if not -> load empty image
        else {
            Glide.with(mContext).load(R.drawable.empty).centerCrop().into(holder.product_image);
        }

        holder.order_product_name.setText(Integer.toString(orders.orderID));
        holder.order_price.setText(mContext.getString(R.string.product_price,orders.total));
        holder.order_status.setText(getStatus(orders.status));
    }

    //Create event for item
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onButtonClick(int position);
    }
    public void setOnItemClickListener(OrderListAdapter.OnItemClickListener listner) {
        mlistner = listner;
    }

    @Override
    public int getItemCount() {
        return arrOrder.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Button order_button_view;
        public ImageView product_image;
        public TextView order_product_name;
        public TextView order_price;
        public TextView order_status;

        public ViewHolder(View view, final OrderListAdapter.OnItemClickListener listener) {
            super(view);
            order_button_view = view.findViewById(R.id.order_button_view);
            product_image = view.findViewById(R.id.product_image);
            order_product_name = view.findViewById(R.id.order_product_name);
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

            order_button_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onButtonClick(position);
                        }
                    }
                }
            });
        }

    }

    public String getStatus(int status){
        String statusString="";
        switch(status) {
            case 2:
                statusString =  "Đang giao";
                break;
            case 3:
                statusString = "Đã giao";
                break;
        }
        return statusString;
    }

}
