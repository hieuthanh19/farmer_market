package com.example.farmersmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.farmersmarket.Login;
import com.example.farmersmarket.R;
import com.example.farmersmarket.Warehouse;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.CurrentAccount;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    public AppDatabase appDatabase;

    private TextView warehouse;
    private Button logoutBtn;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        appDatabase = AppDatabase.getAppDatabase(view.getContext());

        warehouse = view.findViewById(R.id.account_warehouse);
        logoutBtn = view.findViewById(R.id.logout_btn);
        warehouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), Warehouse.class);
                startActivity(intent);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // remove saved account info
                CurrentAccount currentAccount =
                        appDatabase.currentAccountDAO().getAllCurrentAccounts().get(0);
                appDatabase.currentAccountDAO().deleteCurrentAccount(currentAccount);
                // redirect log in activity
                Intent intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
                // delete all previous activity from back stack
                ActivityCompat.finishAffinity(getActivity());
            }
        });
        return view;
    }

}