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

import com.example.farmersmarket.App;
import com.example.farmersmarket.CheckoutSuccess;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.example.farmersmarket.object.OrderDetail;
import com.example.farmersmarket.object.ShippingUnit;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

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
        spu = appDatabase.shippingUnitDAO().getShippingUnit(1);
        ac = appDatabase.accountDAO().getAccount(1);
        arrCart = appDatabase.orderDetail().getAllOrderDetailByOrderID(1);
        totalPriceProduct = appDatabase.orderDetail().getTotalCostOfOrderDetailByOrderID(1);

        fragmentCheckouttxtShippingUnitResult.setText(spu.name);
        fragmentCheckouttxtAddressResult.setText(ac.address);
        fragmentCheckouttxtProductCostResult.setText(Double.toString(totalPriceProduct));
        fragmentCheckouttxtShippingCostResult.setText(Double.toString(spu.transportFee));
        fragmentCheckouttxtTotalCostResult.setText(Double.toString(totalPriceProduct+spu.transportFee));

        imgClose = v.findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CheckoutSuccess.class);
                startActivity(intent);
            }
        });

        return v;
    }

    public void findView(View v){
        fragmentCheckouttxtShippingUnitResult = v.findViewById(R.id.fragmentCheckouttxtShippingUnitResult);
        fragmentCheckouttxtAddressResult = v.findViewById(R.id.fragmentCheckouttxtAddressResult);
        fragmentCheckouttxtProductCostResult = v.findViewById(R.id.fragmentCheckouttxtProductCostResult);
        fragmentCheckouttxtShippingCostResult = v.findViewById(R.id.fragmentCheckouttxtShippingCostResult);
        fragmentCheckouttxtTotalCostResult = v.findViewById(R.id.fragmentCheckouttxtTotalCostResult);
        btnCheckOut = v.findViewById(R.id.btnCheckOut);
    }
}
