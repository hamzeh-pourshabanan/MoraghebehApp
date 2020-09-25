package com.hamzeh.moraghebehapp.ui.sabegheh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aminography.primecalendar.PrimeCalendar;
import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;
import com.hamzeh.moraghebehapp.databinding.FragmentPastAmalsBinding;
import com.hamzeh.moraghebehapp.ui.amal.AmalAdapter;
import com.hamzeh.moraghebehapp.ui.amal.ResultsViewModel;
import com.hamzeh.moraghebehapp.ui.amal.SlideshowFragmentArgs;
import com.hamzeh.moraghebehapp.ui.amal.SlideshowViewModel;
import com.hamzeh.moraghebehapp.ui.arbayiin.CustomLinearLayoutManager;
import com.hamzeh.moraghebehapp.ui.utils.SimpleDividerItemDecoration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import okhttp3.ResponseBody;


@AndroidEntryPoint
public class PastAmalsFragment extends Fragment {
    @Inject
    public PrimeCalendar today;  // today date. Installed in activityRetaindComponent

    FragmentPastAmalsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPastAmalsBinding.inflate(inflater, container, false);

        int arbayiinId = SlideshowFragmentArgs.fromBundle(getArguments()).getArbId();
        int position = SlideshowFragmentArgs.fromBundle(getArguments()).getDuration();
        int resultsCount = SlideshowFragmentArgs.fromBundle(getArguments()).getResultsCount();

        Log.d(getClass().getName(), "arbID: " + arbayiinId +
                "adapterPosition: " + position +
                "resultsCount: " + resultsCount);
        AmalSabeghAdapter amalSabeghAdapter = new AmalSabeghAdapter();
        RecyclerView recyclerView = binding.recyclerviewSabegh;
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(amalSabeghAdapter);
        amalSabeghAdapter.setToday(today);
        amalSabeghAdapter.isPast(true);

        amalSabeghAdapter.notifyDataSetChanged();
        SlideshowViewModel slideshowViewModel = new ViewModelProvider(requireActivity()).get(SlideshowViewModel.class);
        slideshowViewModel.saveId(arbayiinId);
        slideshowViewModel.getAmalItems().observe(getViewLifecycleOwner(), new Observer<List<AmalAdapterItem>>() {
            @Override
            public void onChanged(List<AmalAdapterItem> amalAdapterItems) {

                amalSabeghAdapter.setAmalItems(amalAdapterItems);
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        amalSabeghAdapter.notifyDataSetChanged();
                    }
                });

//                amalSize.put("size", amalAdapterItems.size());

            }
        });

        slideshowViewModel.checkAmalsInDatabase();

        ResultsViewModel resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);

        resultsViewModel.getResultsLiveDataByIDAndDay(arbayiinId, resultsCount).observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> results) {
                if (results != null) {
                    Log.d(getClass().getName(), results+ " - " );
//                    amalAdapter.setTextMatni(results);
                }
            }
        });

        resultsViewModel.getmAllResults(arbayiinId).observe(getViewLifecycleOwner(), new Observer<List<ResultsEntity>>() {
            @Override
            public void onChanged(List<ResultsEntity> resultsEntities) {
                if (resultsEntities != null) {
                    Log.d("resultsinpastamals", resultsEntities.get(position).getResults() + "");
                    HashMap<Integer, String> hashMap = new HashMap<>();

                    for (int i = 0; i < resultsEntities.get(position).getResults().size(); i++) {
                        hashMap.put(i, resultsEntities.get(position).getResults().get(i));
                    }
                    amalSabeghAdapter.setTextMatni(hashMap);
                    recyclerView.post(amalSabeghAdapter::notifyDataSetChanged);
                }
            }
        });


        return binding.getRoot();
    }
}