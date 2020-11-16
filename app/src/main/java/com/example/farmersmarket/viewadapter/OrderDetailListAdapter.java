package com.example.farmersmarket.viewadapter;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.ProductImage;

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

        //LOAD IMAGE
        List<ProductImage> productImageList =
                appDatabase.productImageDAO().getProductImageByProductID(orders.productID);
        // if product have image -> load first image
        if (productImageList.size() > 0) {
            if (productImageList.get(0).URL.startsWith("@drawable")) {
                int resource = mContext.getResources().getIdentifier(productImageList.get(0).URL, "drawable",
                        mContext.getPackageName());
                Glide.with(mContext).load(resource).centerCrop().into(holder.imageViewOrderDetailProduct);
            } else
                Glide.with(mContext).load(Uri.parse(productImageList.get(0).URL)).centerCrop().into(holder.imageViewOrderDetailProduct);
        }
        // if not -> load empty image
        else {
            Glide.with(mContext).load(R.drawable.empty).centerCrop().into(holder.imageViewOrderDetailProduct);
        }

        holder.txtOrderDetailProductName.setText(appDatabase.productDAO().getProduct(orders.productID).name);
        holder.txtOrdeDetailAmount.setText(Integer.toString(orders.quantity));
        holder.txtOrderDetailPrice.setText(mContext.getString(R.string.product_price,orders.totalPrice));
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
