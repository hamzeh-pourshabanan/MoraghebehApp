package com.hamzeh.moraghebehapp.ui.sabegheh;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.data.adapteritems.ArbAdapterItem;
import com.hamzeh.moraghebehapp.data.pojo.RatingSubjectPojo;
import com.hamzeh.moraghebehapp.databinding.FragmentHomeBinding;
import com.hamzeh.moraghebehapp.ui.amal.ResultsViewModel;
import com.hamzeh.moraghebehapp.ui.arbayiin.ArbayiinAdapter;
import com.hamzeh.moraghebehapp.ui.arbayiin.CustomLinearLayoutManager;
import com.hamzeh.moraghebehapp.ui.arbayiin.HomeFragmentDirections;
import com.hamzeh.moraghebehapp.ui.arbayiin.HomeViewModel;
import com.hamzeh.moraghebehapp.ui.utils.SimpleDividerItemDecoration;

import java.util.HashMap;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

@AndroidEntryPoint
public class SabeghehFragment extends Fragment {


    private FragmentHomeBinding binding;
    private NavController navController;
    private Disposable disposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        navController = NavHostFragment.findNavController(this);
        NavBackStackEntry backStackEntry = navController.getBackStackEntry(R.id.mobile_navigation);

        SabeghehViewModel sabeghehViewModel = new ViewModelProvider(this).get(SabeghehViewModel.class);
        sabeghehViewModel.getText().observe(getViewLifecycleOwner(), s -> {
            binding.textHome.setVisibility(View.VISIBLE);
            binding.textHome.setText(s);
        });

        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        RecyclerView recyclerView = binding.recyclerviewArbayiin;
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
        recyclerView.setLayoutManager(linearLayoutManager);
        ArbayiinAdapter arbayiinAdapter = new ArbayiinAdapter();
        recyclerView.setAdapter(arbayiinAdapter);
        arbayiinAdapter.setArbayiinFlag(ArbayiinAdapter.ARBAYIIN_FLAG.PAST);

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        ResultsViewModel resultsViewModel = new ViewModelProvider(this).get(ResultsViewModel.class);

        homeViewModel.getSabeghArbAdapterItemsLiveData().observe(getViewLifecycleOwner(), (List<ArbAdapterItem> arbAdapterItems) -> {
            if (arbAdapterItems != null) {
                if (arbAdapterItems.size() == 0){
                    binding.getRoot().getRootView().setBackground(getActivity().getResources().getDrawable(R.drawable.empty_page_results));
                    return;
                }
                binding.getRoot().getRootView().setBackgroundColor(getResources().getColor(R.color.white));

//                binding.getRoot().getRootView().setBackgroundResource(0);

                Log.d("sabeghfragment", arbAdapterItems.size() + "");
                HashMap<Integer, String> startDays = new HashMap<>();
                for (ArbAdapterItem arbAdapterItem : arbAdapterItems) {
                    startDays.put(arbAdapterItem.getArbId(), resultsViewModel.retrieveFirstDate(arbAdapterItem.getArbId()));
                }
                arbayiinAdapter.setArbItemData(arbAdapterItems);

                arbayiinAdapter.setStartDate(startDays);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        arbayiinAdapter.notifyDataSetChanged();
                    }
                });
            }
        });

        Consumer<ArbAdapterItem> arbIdConsumer = new Consumer<ArbAdapterItem>() {
            @Override
            public void accept(ArbAdapterItem arbayiinItem) throws Throwable {
                SabeghehFragmentDirections.ActionNavGalleryToDaysFragment action = SabeghehFragmentDirections.actionNavGalleryToDaysFragment();
                action.setArbId(arbayiinItem.getArbId());
                action.setDuration(arbayiinItem.getDuration());
                action.setResultsCount(arbayiinItem.getResultCount());

                navController.navigate(action);
            }
        };

        disposable = arbayiinAdapter.getArbIdPublishSubject().subscribe(arbIdConsumer);
                Log.d("jariId",  "sdsd");
        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem fav = menu.add("اربعینیات جاری");
//        fav.setIcon(R.drawable.ic_baseline_history_24);
        fav.setShowAsAction(2);
        Log.d("jariId", fav.getItemId() + "");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 0) {
            navController.navigate(R.id.action_global_homefragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        disposable.dispose();
        super.onDestroyView();
        binding = null;
    }
}