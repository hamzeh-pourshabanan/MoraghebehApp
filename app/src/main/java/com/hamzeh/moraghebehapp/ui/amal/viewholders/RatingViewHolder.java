package com.hamzeh.moraghebehapp.ui.amal.viewholders;

import android.util.Log;

import androidx.core.content.ContextCompat;

import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.data.pojo.RatingSubjectPojo;
import com.hamzeh.moraghebehapp.databinding.ListItemAmalBinding;
import com.hamzeh.moraghebehapp.ui.amal.AmalAdapter;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.subjects.PublishSubject;


public class RatingViewHolder extends AmalAdapter.AmalViewHolder {
        public final ListItemAmalBinding amalBinding;

        public RatingViewHolder(ListItemAmalBinding amalBinding) {
                super(amalBinding.getRoot());
                this.amalBinding = amalBinding;
        }

        public void bind(final String amalName, int position) {
                amalBinding.tvAmalName.setText(amalName);
        }

        @Override
        public void bindCircle(int position) {
                amalBinding.circleNumberTv.setText(position + "");
                amalBinding.circleNumberTv.setBackground(ContextCompat.getDrawable(amalBinding.getRoot().getContext(), R.drawable.circle));
        }


        public void setRating(String rating) {
                amalBinding.ratingBar.setRating(Integer.parseInt(rating));
                setRatingsDesign(Integer.parseInt(rating));
        }

        public void setRatingsDesign(int rating) {
                switch (rating) {
                        case 1:
                                setRatingDesignIngredients("ضعیف", amalBinding.getRoot().getResources().getColor(R.color.bad));
                                Log.d("design", "zayiif");
                                break;
                        case 2:
                                setRatingDesignIngredients("خوب", amalBinding.getRoot().getResources().getColor(R.color.good));
                                Log.d("design", "khoob");
                                break;
                        case 3:
                                setRatingDesignIngredients("عالی", amalBinding.getRoot().getResources().getColor(R.color.very_good));
                                Log.d("design", "aaaliii");
                                break;
                        default:
                                setRatingDesignIngredients("انجام نشده", amalBinding.getRoot().getResources().getColor(R.color.very_bad));
                                Log.d("design", "zayiif");
                                break;

                }
        }

        public void setRatingDesignIngredients(String text, int color) {
                amalBinding.ratingBar.setFillColor(color);
                amalBinding.tvRateText.setText(text);
                amalBinding.tvRateText.setTextColor(color);
        }

        @Override
        public void unsetClickable(int position) {
                Log.d("RatingVH", position + "");
                amalBinding.ratingBar.setIndicator(true);
                amalBinding.getRoot().setAlpha(0.2f);
        }

        @Override
        public void isPast(int position) {
                amalBinding.ratingBar.setIndicator(true);
                amalBinding.getRoot().setAlpha(0.7f);
        }

        @Override
        public void setResults(int position, HashMap<Integer, String> results) {
//                results.put(position, results.containsKey(position)?results.get(position):"0");
                Log.d(getClass().getName(), "pos: " + position + " res: " + results.toString());
                if (results.size() != 0) {
                        if (results.size() > position) {
                                //See if a string contains only numbers and not letters
                                if (!Pattern.matches("[a-zA-Z]+", results.get(position))) {
                                        String rating = results.get(position);
                                        setRating(rating);
//                                        setRating(rating.equals("")?"0":rating);
                                }
                        } else {
                                setRating("0");
                        }

                } else {
                        setRating("0");
                }
        }

        @Override
        public PublishSubject<RatingSubjectPojo> onUserInteraction(int position, List<AmalAdapterItem> adapterItems, PublishSubject<RatingSubjectPojo> onRatingClickSubject) {
                amalBinding.ratingBar.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                                if (fromUser) {

                                        onRatingClickSubject.onNext(
                                                new RatingSubjectPojo.Builder(position)
                                                        .setAmalSize(adapterItems.size())
                                                        .setArbayiinId(adapterItems.get(position).getArbayiinId())
                                                        .setArbayiinTitle(adapterItems.get(position).getAmalTitle())
                                                        .setRating(String.valueOf((int) rating))
                                                        .setViewType(AmalAdapterItem.types.RATING_VIEW)
                                                        .build()
                                        );

                                        setRatingsDesign((int) rating);
                                }

                        }
                });

                return onRatingClickSubject;
        }


}
