package com.example.farmersmarket.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmersmarket.R;
import com.example.farmersmarket.database.AppDatabase;
import com.example.farmersmarket.object.Account;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginPhoneFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginPhoneFragment extends Fragment {

    public static Account tempAccount;
    private TextInputLayout inputPhone;
    private TextView signUp;

    public LoginPhoneFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginPhone.
     */
    public static LoginPhoneFragment newInstance() {

        return new LoginPhoneFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_phone, container, false);
        inputPhone = view.findViewById(R.id.login_phone_number);
        ImageView loginNext = view.findViewById(R.id.login_next);
        signUp = view.findViewById(R.id.login_signup);

        signUp.setText(Html.fromHtml(getString(R.string.login_phone_sign_up)));
        // Set action listener
        loginNext.setOnClickListener(v -> login());
        inputPhone.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            boolean handled = false;
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
            AppDatabase appDatabase = AppDatabase.getAppDatabase(this.getContext());
            tempAccount = appDatabase.accountDAO().getAccountByPhone(inputPhone.getEditText().getText().toString());
            if (tempAccount != null) {
                loadFragment(new LoginConfirmFragment());
            } else {
                inputPhone.setError(getString(R.string.login_error_phone_not_found));
            }

        }
    }

    /**
     * Check whether phone number of password is filled
     *
     * @return true if both are filled, false if not
     */
    private boolean isFilled() {
        boolean isFilled = true;
        if (inputPhone.getEditText().getText().toString().trim().length() == 0) {
            inputPhone.setError(getString(R.string.login_error_phone));
            isFilled = false;
        } else {
            inputPhone.setError(null);
        }
        return isFilled;
    }


    /**
     * Load fragment into current activity
     *
     * @param fragment fragment to display
     */
    private void loadFragment(Fragment fragment) {
        // load fragment
//        Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment).navigate(R.id
//        .action_loginPhoneFragment_to_loginConfirmFragment);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager()
                .beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,
                        R.anim.slide_in_left, R.anim.slide_out_right);
        transaction.replace(R.id.login_fragment_frame, fragment);
//        transaction.addToBackStack(null);
        transaction.commit();
    }
}