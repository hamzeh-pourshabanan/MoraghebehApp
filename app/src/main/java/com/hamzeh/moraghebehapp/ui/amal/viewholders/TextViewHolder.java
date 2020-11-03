package com.hamzeh.moraghebehapp.ui.amal.viewholders;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.data.pojo.RatingSubjectPojo;
import com.hamzeh.moraghebehapp.databinding.ListItemMatniAmalBinding;
import com.hamzeh.moraghebehapp.ui.amal.AmalAdapter;
import com.hamzeh.moraghebehapp.ui.utils.FaNum;

import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class TextViewHolder extends AmalAdapter.AmalViewHolder {
    public ListItemMatniAmalBinding matniAmalBinding;

    public TextViewHolder(@NonNull ListItemMatniAmalBinding matniAmalBinding) {
        super(matniAmalBinding.getRoot());
        this.matniAmalBinding = matniAmalBinding;
    }

    @Override
    public void bind(String name, int position) {
        matniAmalBinding.tvAmalMatniName.setText(name);
    }

    public void bindValue(String value) {
        matniAmalBinding.tvAmalmatniValue.setText(value.equals("0")?"":value);
    }

    @Override
    public void bindCircle(int position) {
        matniAmalBinding.circleNumberTvMatni.setText(FaNum.convert(position + ""));
        matniAmalBinding.circleNumberTvMatni.setBackground(ContextCompat.getDrawable(matniAmalBinding.getRoot().getContext(), R.drawable.circle));
    }

    @Override
    public void unsetClickable(int position) {

        matniAmalBinding.tvAmalMatniName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        matniAmalBinding.getRoot().setAlpha(0.2f);
    }

    @Override
    public void isPast(int position) {
        matniAmalBinding.tvAmalMatniName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        matniAmalBinding.getRoot().setAlpha(0.7f);
    }

    @Override
    public void setResults(int position, HashMap<Integer, String> results) {
//        results.put(position, results.containsKey(position)?results.get(position):" ");
        Log.d(getClass().getName(), "txtpos: " + position + " res: " + results);
        if (results.size() != 0) {
//            if (!results.get(position).equals("0")){
                bindValue(results.get(position));
//            } else bindValue("");

        } else bindValue("");
    }

    @Override
    public PublishSubject<RatingSubjectPojo> onUserInteraction(int position, List<AmalAdapterItem> adapterItems, PublishSubject<RatingSubjectPojo> onRatingClickSubject) {
        matniAmalBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRatingClickSubject.onNext(
                        new RatingSubjectPojo.Builder(position)
                                .setArbayiinTitle(adapterItems.get(position).getAmalTitle())
                                .setViewType(AmalAdapterItem.types.MATNI_VIEW)
                                .build()
                );
             }
        });

        return onRatingClickSubject;
    }

}
