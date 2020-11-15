package com.example.farmersmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.farmersmarket.CheckoutSuccess;
import com.example.farmersmarket.Order;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.ShippingUnit;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InfomationCheckoutFragment extends BottomSheetDialogFragment {
    public AppDatabase appDatabase;
    ImageView imgClose ;
    ShippingUnit spu ;
    Account ac;
    double totalPriceProduct;
    List<OrderDetail> arrCart;

    TextView fragmentCheckouttxtShippingUnitResult;
    TextView fragmentCheckouttxtAddressResult;
    TextView fragmentCheckouttxtProductCostResult;
    TextView fragmentCheckouttxtShippingCostResult;
    TextView fragmentCheckouttxtTotalCostResult;
    Button btnCheckOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infomation_checkout,container,false);
        spu = new ShippingUnit();
        arrCart = new ArrayList<>();


        findView(v);

        appDatabase = AppDatabase.getAppDatabase(v.getContext());

        Order.ORDER_ID = appDatabase.ordersDAO().getCurrentOrder().orderID;
        spu = appDatabase.shippingUnitDAO().getShippingUnit(1);
        ac = appDatabase.accountDAO().getAccount(1);
        arrCart = appDatabase.orderDetailDAO().getAllOrderDetailByOrderID(Order.ORDER_ID);
        totalPriceProduct = appDatabase.orderDetailDAO().getTotalCostOfOrderDetailByOrderID(Order.ORDER_ID);

        fragmentCheckouttxtShippingUnitResult.setText(spu.name);
        fragmentCheckouttxtAddressResult.setText(ac.address);
        fragmentCheckouttxtProductCostResult.setText(getString(R.string.product_price, totalPriceProduct));
        fragmentCheckouttxtShippingCostResult.setText(getString(R.string.product_price, spu.transportFee));
        fragmentCheckouttxtTotalCostResult.setText(getString(R.string.cart_price_change,
                totalPriceProduct + spu.transportFee));

        imgClose = v.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(view -> dismiss());

        btnCheckOut.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), CheckoutSuccess.class);
            startActivity(intent);
            appDatabase.ordersDAO().checkOut(Order.ORDER_ID, totalPriceProduct + spu.transportFee);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            long time = timestamp.getTime();
            appDatabase.ordersDAO().updateDate(time, Order.ORDER_ID);
            appDatabase.orderDetailDAO().setStatusOrderDetail(2);
        });

        return v;
    }

    public void findView(View v) {
        fragmentCheckouttxtShippingUnitResult = v.findViewById(R.id.fragmentCheckoutTxtShippingUnitResult);
        fragmentCheckouttxtAddressResult = v.findViewById(R.id.fragmentCheckoutTxtAddressResult);
        fragmentCheckouttxtProductCostResult = v.findViewById(R.id.fragmentCheckoutTxtProductCostResult);
        fragmentCheckouttxtShippingCostResult = v.findViewById(R.id.fragmentCheckoutTxtShippingCostResult);
        fragmentCheckouttxtTotalCostResult = v.findViewById(R.id.fragmentCheckoutTxtTotalCostResult);
        btnCheckOut = v.findViewById(R.id.btnCheckOut);
    }


    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);

    }
}
