package com.hamzeh.moraghebehapp.ui.sabegheh;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hamzeh.moraghebehapp.OnClickNavigation;
import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.databinding.ListItemDaysBinding;
import com.hamzeh.moraghebehapp.ui.amal.AmalAdapter;
import com.hamzeh.moraghebehapp.ui.arbayiin.ArbayiinAdapter;
import com.hamzeh.moraghebehapp.ui.arbayiin.HomeFragmentDirections;
import com.hamzeh.moraghebehapp.ui.utils.PersianNumbersToLettersConverter;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder> {
//    private final int duration;
    PersianNumbersToLettersConverter pntlc;
    private final int arbayiinId;
    int daysCount;

    public DayAdapter(int daysCount, int duration, int arbayiinId) {
        this.daysCount = daysCount;
//        this.duration = duration;
        this.arbayiinId = arbayiinId;
        pntlc = new PersianNumbersToLettersConverter();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemDaysBinding binding = ListItemDaysBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindCircle(String.valueOf(position+1));
        holder.bindTitle("روز " + pntlc.getParsedString(String.valueOf(position+1)));
        holder.setOnCLick(arbayiinId, position, daysCount);
    }

    @Override
    public int getItemCount() {
        return daysCount;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView circleTv;
        public ViewHolder(@NonNull ListItemDaysBinding binding) {
            super(binding.getRoot());
            titleTv = binding.tvDayTitle;
            circleTv = binding.circleNumberTvDay;
        }

        public void bindCircle(final String dayNumber) {
            circleTv.setText(dayNumber);
            circleTv.setBackground(ContextCompat.getDrawable(circleTv.getContext(), R.drawable.circle));
        }

        public void bindTitle(final String dayTitle) {
            titleTv.setText(dayTitle);
        }

        public void setOnCLick( int arbayiinId, int duration, int resultCount) {
            titleTv.setOnClickListener(new OnClickNavigation(arbayiinId, getAdapterPosition(), resultCount));
        }

        private static class OnClickNavigation implements View.OnClickListener {
            DaysFragmentDirections.ActionDaysFragmentToPastAmalsFragment action;

            public OnClickNavigation( int arbId, int duration, int resultCount) {
                Log.d("resultCount: ", resultCount + "");

                this.action = DaysFragmentDirections.actionDaysFragmentToPastAmalsFragment();
                this.action.setArbId(arbId);
                this.action.setDuration(duration);
                this.action.setResultCount(resultCount);
            }

            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(action);
                action = null;
            }

        }
    }
}
