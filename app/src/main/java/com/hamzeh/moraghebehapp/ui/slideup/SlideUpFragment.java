package com.hamzeh.moraghebehapp.ui.slideup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hamzeh.moraghebehapp.databinding.FragmentSlideshowBinding;

public class SlideUpFragment extends Fragment {

    FragmentSlideshowBinding slideshowBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        slideshowBinding = FragmentSlideshowBinding.inflate(inflater,container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
