package com.hamzeh.moraghebehapp.ui.arbayiin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primedatepicker.common.OnDayPickedListener;
import com.aminography.primedatepicker.common.PickType;
import com.aminography.primedatepicker.picker.PrimeDatePicker;
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback;
import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.data.adapteritems.ArbAdapterItem;
import com.hamzeh.moraghebehapp.databinding.FragmentHomeBinding;
import com.hamzeh.moraghebehapp.retrofit.CheckNetworkConnection;
import com.hamzeh.moraghebehapp.ui.amal.DayPickCallbackImpl;
import com.hamzeh.moraghebehapp.ui.amal.ResultsViewModel;
import com.hamzeh.moraghebehapp.ui.login.LoginViewModel;
import com.hamzeh.moraghebehapp.ui.utils.SimpleDividerItemDecoration;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

import static android.content.ContentValues.TAG;

@AndroidEntryPoint
public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArbayiinAdapter mArbayiinAdapter;
    NavBackStackEntry backStackEntry;
    NavController navController;
    private ViewModelProvider viewModelProvider;
    private HomeViewModel homeViewModel;

    @Inject
    public DayPickCallbackImpl dayPickCallback;  // installed in FragmentComponent

    @Inject
    public PrimeDatePicker datePicker;
    private Disposable disposable;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mArbayiinAdapter  = new ArbayiinAdapter();
        Log.d(getClass().getName(), this.toString());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
//        final TextView textView = root.findViewById(R.id.text_home);

        navController = NavHostFragment.findNavController(this);
        backStackEntry = navController.getBackStackEntry(R.id.mobile_navigation);
        viewModelProvider = new ViewModelProvider(
                backStackEntry.getViewModelStore(),
                getDefaultViewModelProviderFactory());
//        Log.d("HomeFragment", "VMProvider: " + viewModelProvider);

        loginAuthentication(navController);

        RecyclerView recyclerView = binding.recyclerviewArbayiin;
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mArbayiinAdapter);
        mArbayiinAdapter.setArbayiinFlag(ArbayiinAdapter.ARBAYIIN_FLAG.CURRENT);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        ResultsViewModel resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);
        homeViewModel.getArbAdapterItemsLiveData().observe(getViewLifecycleOwner(), arbAdapterItems -> {
            if (arbAdapterItems != null) {
                if (arbAdapterItems.size() == 0) {
                    binding.getRoot().getRootView().setBackground(getResources().getDrawable(R.drawable.empty_page_current));
                    return;
                }
                binding.getRoot().getRootView().setBackgroundColor(getResources().getColor(R.color.white));
                HashMap<Integer, String> startDays = new HashMap<>();
                for (ArbAdapterItem arbAdapterItem : arbAdapterItems) {
                    startDays.put(arbAdapterItem.getArbId(), resultsViewModel.retrieveFirstDate(arbAdapterItem.getArbId()));
                }
                mArbayiinAdapter.setArbItemData(arbAdapterItems);
                mArbayiinAdapter.setStartDate(startDays);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mArbayiinAdapter.notifyDataSetChanged();
                    }
                });

            }

        });

        Consumer<ArbAdapterItem> arbIdConsumer = new Consumer<ArbAdapterItem>() {
            @Override
            public void accept(ArbAdapterItem arbayiinItem) throws Throwable {
                Log.d("arbaccept", "arbId: " + arbayiinItem.getArbId());
                HomeFragmentDirections.ActionNavHomeToNavSlideshow action = HomeFragmentDirections.actionNavHomeToNavSlideshow();
                action.setArbId(arbayiinItem.getArbId());
                action.setDuration(arbayiinItem.getDuration());
                action.setResultsCount(arbayiinItem.getResultCount());

                if (resultsViewModel.retrieveFirstDate(arbayiinItem.getArbId()).equals("تعیین نشده")){
                    Log.d("arbaccept", "startDate: " + resultsViewModel.retrieveFirstDate(arbayiinItem.getArbId()));
                    Log.d("arbaccept", "dayPickCallback: " + dayPickCallback);
//                    resultsViewModel.deleteFirstDay(arbId);

                    datePicker.show(getActivity().getSupportFragmentManager(), "SOME_TAG");
                    datePicker.setDayPickCallback(new SingleDayPickCallback() {
                        @Override
                        public void onSingleDayPicked(PrimeCalendar singleDay) {
                            Log.d("arbaccept", "setDayPickCallback: " + singleDay.getShortDateString());
                            resultsViewModel.insertFirstDay(arbayiinItem.getArbId(), singleDay.getShortDateString());

                            navController.navigate(action);
                        }
                    });

                } else {
                    action.setArbId(arbayiinItem.getArbId());
                    navController.navigate(action);
                }
            }
        };

            disposable = mArbayiinAdapter.getArbIdPublishSubject().subscribe(arbIdConsumer,  throwable -> Log.e(TAG, "Throwable " + throwable.getMessage()));


        onSwipeRefresh(homeViewModel);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem fav = menu.add("اربعینیات سابق");
//        fav.setIcon(R.drawable.ic_baseline_history_24);
        fav.setShowAsAction(2);
        Log.d("sabeghId", fav.getItemId() + "");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            navController.navigate(R.id.action_global_sabeghfragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        disposable.dispose();
        navController = null;
        backStackEntry = null;


//        disposable.dispose();
        binding = null;
        viewModelProvider = null;
        homeViewModel.getArbItems().removeObservers(getViewLifecycleOwner());
        super.onDestroyView();

    }


    private void loginAuthentication(NavController navController) {

        LoginViewModel loginViewModel = viewModelProvider.get(LoginViewModel.class);
        loginViewModel.authenticationState.observe(getViewLifecycleOwner(),
                new Observer<LoginViewModel.AuthenticationState>() {
                    @Override
                    public void onChanged(LoginViewModel.AuthenticationState authenticationState) {
                        switch (authenticationState) {
                            case AUTHENTICATED:

                                break;
                            case UNAUTHENTICATED:
                                navController.navigate(R.id.nav_login);
                                break;
                        }
                    }
                });
    }

    private void onSwipeRefresh(HomeViewModel homeViewModel) {
        SwipeRefreshLayout swipeRefreshLayout = binding.swipeContainer;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (CheckNetworkConnection.hasConnection(requireActivity().getApplication())){
                    homeViewModel.refreshArbayiins();
                    Toast.makeText(requireActivity(), "لیست اربعینیات با موفقیت به روز رسانی شد", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(requireActivity(), "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                }


                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}