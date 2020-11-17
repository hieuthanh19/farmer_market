package com.example.farmersmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.farmersmarket.App;
import com.example.farmersmarket.OrderDetailAct;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Orders;
import com.example.farmersmarket.viewadapter.OrderListAdapter;

import java.sql.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {

    public AppDatabase appDatabase;
    RecyclerView recyclerView ;
    OrderListAdapter orderListAdapter;
    List<Orders> arrOrder;
    Date dateOrder = null;
    TextView txtEmpty;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order, container, false);

        appDatabase = AppDatabase.getAppDatabase(view.getContext());
        arrOrder = appDatabase.ordersDAO().getOrdersOfAccountOrderByDESC(App.ACCOUNT_ID);
        findView(view);

        if (arrOrder.size()!=0){
            txtEmpty.setVisibility(View.GONE);
            builAdapter(view);
        }else{
            recyclerView.setVisibility(View.INVISIBLE);
        }

        // Inflate the layout for this fragment
        return view;
    }


    public void builAdapter(View view){
        recyclerView.setHasFixedSize(true);
        orderListAdapter = new OrderListAdapter(arrOrder,view.getContext());
        recyclerView.setAdapter(orderListAdapter);

        orderListAdapter.setOnItemClickListener(new OrderListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onButtonClick(int position) {
                Intent intent = new Intent(getActivity(), OrderDetailAct.class);
                intent.putExtra("ORDER_ID", arrOrder.get(position).orderID);
                startActivity(intent);
            }
        });
    }

    public void findView(View view){
        txtEmpty = view.findViewById(R.id.txtOrderEmpty);
        recyclerView = view.findViewById(R.id.rc1);
    }


}