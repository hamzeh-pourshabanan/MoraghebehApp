package com.hamzeh.moraghebehapp.ui.arbayiin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hamzeh.moraghebehapp.data.adapteritems.ArbAdapterItem;
import com.hamzeh.moraghebehapp.data.database.daoes.ResultsDao;
import com.hamzeh.moraghebehapp.data.pojo.ArbIdAndDuration;
import com.hamzeh.moraghebehapp.databinding.ListItemArbayiinBinding;
import com.hamzeh.moraghebehapp.ui.sabegheh.SabeghehFragmentDirections;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.android.AndroidEntryPoint;
import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.android.components.ApplicationComponent;
import io.reactivex.rxjava3.subjects.PublishSubject;


public class ArbayiinAdapter extends RecyclerView.Adapter<ArbayiinAdapter.ViewHolder> {

    private List<ArbAdapterItem> arbItems;
    private HashMap<Integer, String> startDate;
    private PublishSubject<ArbAdapterItem> arbIdPublishSubject = PublishSubject.create();

    public void setStartDate(HashMap<Integer, String> startDate) {
        this.startDate = startDate;
    }

    public void clear() {
        startDate.clear();
        arbItems.clear();
        arbIdPublishSubject.onComplete();
    }

    public void start() {
    }

    public enum ARBAYIIN_FLAG {
        CURRENT,        // From HomeFragment
        PAST            // FROM SabeghehFragment
    }
    public static ARBAYIIN_FLAG ARBAYIIN_FRAGMENT;

    public ArbayiinAdapter() {
        Log.d(getClass().getName(), this.toString());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemArbayiinBinding listItemBinding =ListItemArbayiinBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(listItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(arbItems.get(position).getArbTitle());
//        holder.setOnCLick(arbItems.get(position).getArbId(), arbItems.get(position).getDuration(), arbItems.get(position).getResultCount());
//        arbIdAndDuration.setArbId(arbItems.get(position).getArbId());
        holder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arbIdPublishSubject.onNext(arbItems.get(position));
            }
        });

        if (startDate != null) {
            holder.bindStartDate(startDate.get(arbItems.get(position).getArbId()));
        }
        Log.d("arbnamesinonbind", "inOnBindVH: ");

    }

    @Override
    public int getItemCount() {
        return arbItems == null ? 0 : arbItems.size();

    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.setAdapter(null);
        arbItems = null;


    }

    public void setArbItemData(List<ArbAdapterItem> arbAdapterItems) {
        this.arbItems = null;
        this.arbItems = arbAdapterItems;
    }

    public void setArbayiinFlag(ARBAYIIN_FLAG ARBAYIIN_FRAGMENT) {
        ArbayiinAdapter.ARBAYIIN_FRAGMENT = ARBAYIIN_FRAGMENT;
        notifyDataSetChanged();
    }

    public PublishSubject<ArbAdapterItem> getArbIdPublishSubject() {
        return arbIdPublishSubject;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView tv_title;
        final TextView tv_startDate;

        public ViewHolder(ListItemArbayiinBinding listItemBinding) {
            super(listItemBinding.getRoot());
            this.tv_title = listItemBinding.tvArbayiinName;
            this.tv_startDate = listItemBinding.textView5;
            Log.d("viewHolder", "insideVH");

        }

        public void bind(final String arbayiinName) {
            tv_title.setText(arbayiinName);
        }


        public void setOnCLick( int arbayiinId, int duration, int resultCount) {
            itemView.setOnClickListener(new ViewHolder.OnClickNavigation(arbayiinId, duration, resultCount));

        }

        public void bindStartDate(String startDate) {
            tv_startDate.setText(startDate);
        }



        private static class OnClickNavigation implements View.OnClickListener {
            HomeFragmentDirections.ActionNavHomeToNavSlideshow action;
            SabeghehFragmentDirections.ActionNavGalleryToDaysFragment pastAction;

            public OnClickNavigation( int arbId, int duration, int resultCount) {
                Log.d("resultCount: ", resultCount + "");

                if (ARBAYIIN_FRAGMENT == ARBAYIIN_FLAG.CURRENT) { // we're inside HomeFragment
                    this.action = HomeFragmentDirections.actionNavHomeToNavSlideshow();
                    this.action.setArbId(arbId);
                    this.action.setDuration(duration);
                    this.action.setResultsCount(resultCount);
                } else { // we're inside SabeghehFragment
                    this.pastAction = SabeghehFragmentDirections.actionNavGalleryToDaysFragment();
                    this.pastAction.setArbId(arbId);
                    this.pastAction.setDuration(duration);
                    this.pastAction.setResultsCount(resultCount);
                }
            }

            @Override
            public void onClick(View v) {
                if (ARBAYIIN_FRAGMENT == ARBAYIIN_FLAG.CURRENT) { // we're inside HomeFragment
                    Navigation.findNavController(v).navigate(action);
                } else { // we're inside SabeghehFragment
                    Navigation.findNavController(v).navigate(pastAction);
                }

                action = null;
            }

        }

    }

    @InstallIn(ApplicationComponent.class)
    @EntryPoint
    interface LogsContentProviderEntryPoint {
        ResultsDao resultsDao();
    }

    private ResultsDao getResultsDao(Context context){
        LogsContentProviderEntryPoint hiltEntryPoint = EntryPointAccessors.fromApplication(context, LogsContentProviderEntryPoint.class);
        return hiltEntryPoint.resultsDao();
    }
}
