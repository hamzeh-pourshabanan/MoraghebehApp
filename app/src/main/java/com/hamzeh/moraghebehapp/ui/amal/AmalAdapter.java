package com.hamzeh.moraghebehapp.ui.amal;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aminography.primecalendar.PrimeCalendar;
import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.data.pojo.RatingSubjectPojo;
import com.hamzeh.moraghebehapp.ui.amal.viewholders.ViewHolderTypeFactory;
import com.hamzeh.moraghebehapp.ui.amal.viewholders.ViewHolderTypeFactoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class AmalAdapter extends RecyclerView.Adapter<AmalAdapter.AmalViewHolder>  {
    private List<AmalAdapterItem> amalItems;
    private ViewHolderTypeFactory typeFactory = new ViewHolderTypeFactoryImpl();
    private HashMap<Integer, String> results;
    private PrimeCalendar today;
    private final PublishSubject<RatingSubjectPojo> onRatingClickSubject = PublishSubject.create();
    private boolean isPast;

    @Inject
    public AmalAdapter() {
        results = new HashMap<>();
    }

    @NonNull
    @Override
    public AmalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return typeFactory.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull AmalAdapter.AmalViewHolder holder, int position) {
        AmalAdapterItem amalItem = amalItems.get(position);
        holder.bind(amalItem.getAmalTitle(), position);
        holder.bindCircle(position + 1);
        holder.setResults(position, results);
        onRatingClickSubject.buffer(holder.onUserInteraction(position, amalItems, onRatingClickSubject));

//        holder.decreaseAlpha(position, amalItems, today, isPast);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        today = null;
        results = null;
        amalItems = null;
        Log.d("onDetached", "called");
        super.onDetachedFromRecyclerView(recyclerView);

    }

    @Override
    public int getItemCount() {
        return amalItems == null ? 0 : amalItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return amalItems.get(position).type(typeFactory);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull AmalViewHolder holder) {
//        onRatingClickSubject.onComplete();
        super.onViewDetachedFromWindow(holder);
    }


    public void setAmalItems(List<AmalAdapterItem> amalItems) {
        this.amalItems = amalItems;
    }

    public void setTextMatni(HashMap<Integer, String> results) {
        this.results = results;
    }

    public void setToday(PrimeCalendar persianCalendar) {
        this.today = persianCalendar;
    }

    public void isPast(boolean isPast) {
        this.isPast = isPast;
    }

    public static abstract class AmalViewHolder extends RecyclerView.ViewHolder {

        public AmalViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public abstract void bind(String name, int position);
        public abstract void bindCircle(int position);
        public abstract void unsetClickable(int position);
        public abstract void isPast(int position);
        public abstract void setResults(int position, HashMap<Integer, String> results);
        public abstract PublishSubject<RatingSubjectPojo> onUserInteraction(int position, List<AmalAdapterItem> adapterItems,
                                               PublishSubject<RatingSubjectPojo> onRatingClickSubject);

        public void decreaseAlpha(int position){
                isPast(position);


//            if (adapterItems.get(getAdapterPosition()).getWeekDay() != null && !adapterItems.get(getAdapterPosition()).getWeekDay().equals(today.getWeekDayName())) {
//                Log.d("decreaseAlpha: ", getAdapterPosition() + " - " + adapterItems.get(position).getWeekDay());
//                unsetClickable(getAdapterPosition());
//            }
        }
    }

    public Observable<RatingSubjectPojo> getRatingsClicks(){
        return onRatingClickSubject;
    }

}
