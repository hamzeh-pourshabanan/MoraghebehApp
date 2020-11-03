package com.hamzeh.moraghebehapp.ui.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private FragmentLoginBinding binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
//        View root = inflater.inflate(R.layout.fragment_login, container, false);

        EditText usernameEditText = binding.username;
        EditText passwordEditText= binding.password;
        CardView buttonCardView = binding.cardview;

        NavController navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.mobile_navigation);

        ViewModelProvider viewModelProvider = new ViewModelProvider(
                backStackEntry.getViewModelStore(),
                getDefaultViewModelProviderFactory());
        loginViewModel = viewModelProvider.get(LoginViewModel.class);

        buttonCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.authenticate(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                hideKeyboardFrom(requireContext(), requireView());


            }
        });

        loginViewModel.authenticationState.observe(getViewLifecycleOwner(),
                new Observer<LoginViewModel.AuthenticationState>() {
                    @Override
                    public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
                        switch (authenticationState) {
                            case AUTHENTICATED:
                                navController.navigate(R.id.nav_home);
                                break;
                            case INVALID_AUTHENTICATION:
                                Snackbar.make(root,
                                        R.string.invalid_credentials,
                                        Snackbar.LENGTH_LONG
                                ).show();
                                break;
                            case FAILD_RESPONSE:
                                Snackbar.make(root,
                                        R.string.failed_response,
                                        Snackbar.LENGTH_LONG
                                ).show();
                        }
                    }
                });
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
