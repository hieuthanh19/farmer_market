package com.example.farmersmarket.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.farmersmarket.App;
import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.CurrentAccount;
import com.example.farmersmarket.object.Utils;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginConfirmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginConfirmFragment extends Fragment {


    private TextInputLayout inputPassword;
    private TextView loginError;

    public LoginConfirmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginConfirm.
     */
    public static LoginConfirmFragment newInstance() {

        return new LoginConfirmFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_confirm, container, false);

        inputPassword = view.findViewById(R.id.login_confirm_password);
        loginError = view.findViewById(R.id.login_error);
        ImageView back = view.findViewById(R.id.login_confirm_back);
        ImageView loginConfirm = view.findViewById(R.id.login_confirm_finish);

        // Set action listener
        back.setOnClickListener(v -> getActivity().onBackPressed());
        loginConfirm.setOnClickListener(v -> login());
        inputPassword.getEditText().requestFocus();
        inputPassword.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
            // If user press done -> log in
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login();
                handled = true;
            }
            return handled;
        });

        return view;
    }

    /**
     * Handle log in
     */
    private void login() {
        if (isFilled()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String encryptedPassword =
                        Utils.encryptPassword(inputPassword.getEditText().getText().toString().trim());
                if (LoginPhoneFragment.tempAccount.password.equals(encryptedPassword)) {
                    // set account ID
                    App.ACCOUNT_ID = LoginPhoneFragment.tempAccount.accountID;
                    // remember user
                    AppDatabase.getAppDatabase(getContext())
                            .currentAccountDAO().insertCurrentAccount(new CurrentAccount(LoginPhoneFragment.tempAccount.accountID));
                    Intent intent = new Intent(getContext(), App.class);
                    startActivity(intent);
                    getFragmentManager().popBackStack();
                    // Remove activity from backstack

                    getActivity().finish();
                } else {
                    // password not correct -> display error message
                    loginError.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * Check whether password is filled
     *
     * @return true if filled, else false
     */
    private boolean isFilled() {
        boolean isFilled = true;
        if (inputPassword.getEditText().getText().toString().trim().length() == 0) {
            inputPassword.setError(getString(R.string.login_error_password));
            isFilled = false;
        } else {
            inputPassword.setError(null);
        }
        return isFilled;
    }


}