package com.hamzeh.moraghebehapp.ui.sabegheh;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.databinding.FragmentDaysBinding;
import com.hamzeh.moraghebehapp.ui.amal.SlideshowFragmentArgs;
import com.hamzeh.moraghebehapp.ui.arbayiin.CustomLinearLayoutManager;
import com.hamzeh.moraghebehapp.ui.utils.SimpleDividerItemDecoration;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DaysFragment extends Fragment {
    FragmentDaysBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDaysBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        int arbayiinId = DaysFragmentArgs.fromBundle(getArguments()).getArbId();
        int duration = DaysFragmentArgs.fromBundle(getArguments()).getDuration();
        int resultsCount = DaysFragmentArgs.fromBundle(getArguments()).getResultsCount();
        Log.d("resCounts: ", "DaysFragment" + resultsCount);
        Toast.makeText(getContext(), "daysFragment", Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = binding.recyclerviewDays;
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
        recyclerView.setLayoutManager(linearLayoutManager);

        DayAdapter dayAdapter = new DayAdapter(resultsCount, duration, arbayiinId);
        recyclerView.setAdapter(dayAdapter);

        return root;
    }
}