package com.hamzeh.moraghebehapp.ui.amal;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.HttpAuthHandler;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aminography.primecalendar.PrimeCalendar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamzeh.moraghebehapp.MainViewModel;
import com.hamzeh.moraghebehapp.R;
import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.data.pojo.RatingSubjectPojo;
import com.hamzeh.moraghebehapp.databinding.FragmentSlideshowBinding;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.CheckNetworkConnection;
import com.hamzeh.moraghebehapp.retrofit.InsertStreams;
import com.hamzeh.moraghebehapp.ui.amal.viewholders.SlideshowFragmentUpdateUI;
import com.hamzeh.moraghebehapp.ui.arbayiin.CustomLinearLayoutManager;
import com.hamzeh.moraghebehapp.ui.utils.DateUtils;
import com.hamzeh.moraghebehapp.ui.utils.ResultsUtils;
import com.hamzeh.moraghebehapp.ui.utils.SharedPrefUtils;
import com.hamzeh.moraghebehapp.ui.utils.SimpleDividerItemDecoration;
import com.mancj.slideup.SlideUp;
import com.mancj.slideup.SlideUpBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;

import static com.hamzeh.moraghebehapp.ui.utils.DateUtils.arbayiinEndDate;
import static com.hamzeh.moraghebehapp.ui.utils.SharedPrefUtils.getResultsFromSharedPrefs;


@AndroidEntryPoint
public class SlideshowFragment extends Fragment {
    ResultsViewModel resultsViewModel;
    private SlideUp slideUp;
    HashMap<Integer, String> results = new HashMap<>();
    private FragmentSlideshowBinding binding;
    private NavController navController;
    private MainViewModel mainViewModel;
    public FloatingActionButton floatingActionButton;
    @Inject
    public PrimeCalendar today;  // today date. Installed in activityRetaindComponent

    public PrimeCalendar lastSubmitedDate;  // today date. Installed in activityRetaindComponent

//    @Inject
//    public PrimeDatePicker datePicker;  // installed in FragmentComponent
    private RecyclerView recyclerView;
    private CustomLinearLayoutManager linearLayoutManager;
    private PrimeCalendar startDate;
    private int arbayiinId;
    private PrimeCalendar mCurrentDate;
    private MutableLiveData<Integer> todayCount = new MutableLiveData<>();
    private int resultsCount;
    private int duration;
    private DisposableObserver<Integer> disposable;
    @Inject
    public Api retrofit;
    private Disposable subjectDisposable;
    @Inject
    public AmalAdapter amalAdapter;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).show();
        floatingActionButton = requireActivity().findViewById(R.id.fab);
        floatingActionButton.show();

        initRecyclerView(amalAdapter);

        initSlideUpView();

        slideshowViewModel = new ViewModelProvider(requireActivity()).get(SlideshowViewModel.class);

        assert getArguments() != null;
        arbayiinId = SlideshowFragmentArgs.fromBundle(getArguments()).getArbId();
        duration = SlideshowFragmentArgs.fromBundle(getArguments()).getDuration();
        resultsCount = SlideshowFragmentArgs.fromBundle(getArguments()).getResultsCount();
        binding.currentDayTv.setText(String.valueOf(resultsCount + 1));
        todayCount.setValue(resultsCount);
        resultsViewModel = new ViewModelProvider(requireActivity()).get(ResultsViewModel.class);
        resultsViewModel.setArbayiinId(arbayiinId);
        resultsViewModel.getStateFromMemento();
        String start = resultsViewModel.retrieveFirstDate(arbayiinId);
        Log.d("SF: ", "startdate: " + start);
        Log.d("SF: ", "resultCount: " + resultsCount);
        slideshowViewModel.saveId(arbayiinId);
        binding.startValue.setText(start);
        startDate = DateUtils.getStartDate(start);

        mCurrentDate = startDate.clone();
        mCurrentDate.add(5, resultsCount);

        binding.endValue.setText(arbayiinEndDate(startDate, duration));

        amalAdapter.setToday(today);

//        if (getResultsFromSharedPrefs(getActivity(), getString(R.string.dayPreference_file_key), arbayiinId) == null)
//

        List<String> resultList = getResultsFromSharedPrefs(getActivity(), getString(R.string.dayPreference_file_key), arbayiinId);
        results.putAll(ResultsUtils.getHashMapResults(resultList));
        amalAdapter.setTextMatni(results);

        observeAmalAdapterItems(amalAdapter, slideshowViewModel);

        Log.d("resultsInSharedPRefs","results: " + results + "");
        slideshowViewModel.checkAmalsInDatabase();

        onSwipeRefresh(slideshowViewModel);

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        mainViewModel.updateActionBarTitle(slideshowViewModel.arbTitleMutable);

        navController = NavHostFragment.findNavController(this);
        onBackPressedCall(navController);

        observeOnItemClick(amalAdapter);

//        observeResultsCache(amalAdapter);

            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    executeHttpRequestWithRetrofit();
                }
            });

        return root;
    }

    // Should be in ViewModel


    private void observeAmalAdapterItems(AmalAdapter amalAdapter, SlideshowViewModel slideshowViewModel) {
        slideshowViewModel.getAmalItems().observe(getViewLifecycleOwner(), new Observer<List<AmalAdapterItem>>() {
            @Override
            public void onChanged(List<AmalAdapterItem> amalAdapterItems) {
                amalAdapter.setAmalItems(amalAdapterItems);
                if (getResultsFromSharedPrefs(getActivity(), getString(R.string.dayPreference_file_key), arbayiinId) == null){
                    for (int i = 0; i < amalAdapterItems.size(); i++) {
                        results.put(i, "0");
                    }
                    Log.d("rsultsInSharedPrefs", "iha: " + results );
                    amalAdapter.setTextMatni(results);
                }

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        amalAdapter.notifyDataSetChanged();
                    }
                });

                slideshowViewModel.getAmalItems().removeObservers(getViewLifecycleOwner());

            }
        });
    }



    private void observeOnItemClick(AmalAdapter amalAdapter) {
        subjectDisposable = amalAdapter.getRatingsClicks().subscribeWith(new DisposableObserver<RatingSubjectPojo>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull RatingSubjectPojo ratingSubjectPojo) {
                Log.d("amaltype", ratingSubjectPojo.getViewType() + "");
                if (ratingSubjectPojo.getViewType() == AmalAdapterItem.types.MATNI_VIEW) {
                    if (ratingSubjectPojo.getRating() == null) {
                        ratingSubjectPojo.setRating("0");
                    }

                    Log.d("positionOfAmal", ratingSubjectPojo.getPosition() + "");
                    Log.d("subjectDisposable", "onNext: "  + ratingSubjectPojo.getPosition() + "");
                    slideUp.show();
                    if (!results.get(ratingSubjectPojo.getPosition()).equals("0")){
                        binding.editTextSlideup.setText(results.get(ratingSubjectPojo.getPosition()));
                    } else binding.editTextSlideup.setText("");

                    binding.editTextSlideup.requestFocus();
                    Objects.requireNonNull(binding.tvSlideUpTitle).setText(ratingSubjectPojo.getArbTitle());
                    onSubmitClick(ratingSubjectPojo);

                }
                else {
//                    if (ratingSubjectPojo.getRating() == null) {
//                        ratingSubjectPojo.setRating("0");
//                    }
                    results.put(ratingSubjectPojo.getPosition(), ratingSubjectPojo.getRating());
                }
                amalAdapter.setTextMatni(results);
                recyclerView.post(amalAdapter::notifyDataSetChanged);


            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                Log.d("subjectDisposable", "onError: "  + Log.getStackTraceString(e) + "");
            }

            @Override
            public void onComplete() {

                Log.d("subjectDisposable", "onComplete: "  + "called");
            }
        });
    }

    private void onSubmitClick(RatingSubjectPojo ratingSubjectPojo) {
        binding.buttonSubmitSlide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingSubjectPojo.setRating(binding.editTextSlideup.getText().toString());
                results.put(ratingSubjectPojo.getPosition(), ratingSubjectPojo.getRating());
                resultsViewModel.setPosition(ratingSubjectPojo.getPosition());

                resultsViewModel.setText(ratingSubjectPojo.getRating(), ratingSubjectPojo.getAmalSize(), ratingSubjectPojo.getArbayiinId());
                slideUp.hideSoftInput();
                slideUp.hideImmediately();


                amalAdapter.setTextMatni(results);
                recyclerView.post(() -> {
                    amalAdapter.notifyDataSetChanged();
                });
            }
        });
    }

    private void onBackPressedCall(NavController navController) {
        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                navController.popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    private void initSlideUpView() {
        View slideView = binding.slideView;
        slideView.bringToFront();
        slideUp = new SlideUpBuilder(slideView)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.BOTTOM)
                .withListeners(new SlideUp.Listener.Events() {
                    @Override
                    public void onSlide(float percent) {
                        if (percent < 100) {
                            // slideUp started showing
                            floatingActionButton.hide();
                        } else if (percent == 100.0) {
                            floatingActionButton.show();
                        }
                    }

                    @Override
                    public void onVisibilityChanged(int visibility) {
                        if (visibility == View.GONE) {
                            hideKeyboardFrom(requireContext(), requireView());
                            binding.swipeContainerAmal.setAlpha(1f);
                            binding.swipeContainerAmal.setEnabled(true);
                        } else {
                            binding.swipeContainerAmal.setEnabled(false);
                            binding.swipeContainerAmal.setAlpha(0.3f);
                            binding.swipeContainerAmal.setClickable(false);
                            binding.swipeContainerAmal.setFocusable(false);
                        }
                    }
                })
                .build();
        slideUp.hideImmediately();
    }

    private void initRecyclerView(AmalAdapter amalAdapter) {
        recyclerView = binding.recyclerviewAmal;
        linearLayoutManager = new CustomLinearLayoutManager(getContext());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(amalAdapter);
    }

    private void onSwipeRefresh(SlideshowViewModel viewModel) {
        SwipeRefreshLayout swipeRefreshLayout = binding.swipeContainerAmal;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                resultsViewModel.deleteFirstDay(arbayiinId);
                resultsViewModel.deletResultsFromDb();
                if (CheckNetworkConnection.hasConnection(requireActivity().getApplication())){
                    viewModel.refreshAmals();
                    Toast.makeText(requireActivity(), "لیست اربعینیات با موفقیت به روز رسانی شد", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(requireActivity(), "عدم اتصال به اینترنت", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        slideUp = null;
        today = null;
        binding = null;
        Log.d("onDestroyView", "binding: " + binding);
        floatingActionButton.hide();

        navController = null;
        linearLayoutManager = null;

        resultsViewModel.saveStateToMomento();
        mainViewModel.onCleared();
        resultsViewModel.getHashMap().removeObservers(getViewLifecycleOwner());

        resultsViewModel.onCleared();

        super.onDestroyView();
    }



    @Override
    public void onDestroy() {
        Log.d("onDestroy", "sequenceTest " );
        super.onDestroy();
        this.disposeWhenDestroy();
        this.subjectDisposable.dispose();
        SharedPrefUtils.saveResultsToSharedPrefs(getActivity(), getString(R.string.dayPreference_file_key), results, arbayiinId);
    }



    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void executeHttpRequestWithRetrofit() {

        this.updateUIWhenStartingHTTPRequest();

        this.disposable = InsertStreams.streamInsertResults(ResultsUtils.getResultsInString(results), arbayiinId, mCurrentDate.getShortDateString(), resultsViewModel.getUserId(), retrofit)
                .subscribeWith(new DisposableObserver<Integer>() {
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull Integer postId) {
                        Log.d("TAG", "onNext");
                        updateUI(postId);
                        updateDb(postId, results);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("TAG","On Error: " + Log.getStackTraceString(e));
                        if (e.getCause() != null) {
                            if (e.getCause().getMessage().equals("Network is unreachable")) {
                                Toast.makeText(getContext(), "عدم اتصال به اینترنت...", Toast.LENGTH_SHORT).show();
                                binding.currentDayTv.setText("نتایج به دلیل عدم اتصال به اینترنت ثبت نشد... لطفا از اتصال به اینترنت مطمئن شوید و مجددا اعمال را ثبت بفرمایید.");
                            } else if (e.getCause().getMessage().equals("Connection refused")) {
                                Toast.makeText(getContext(), "اختلال در سرور...", Toast.LENGTH_SHORT).show();
                                binding.currentDayTv.setText("نتایج به دلیل اختلال در ارتباط با سرور ثبت نشد... لطفا پس از چند دقیقه مجددا نتایج را ثبت بفرمایید، و درصورت رفع نشدن مشکل با تیم پشتیبانی تماس حاصل فرمایید.");
                            }
                        }

                    }

                    @Override
                    public void onComplete() {
                        Log.d("TAG","On Complete !!");
                    }
                });

    }

    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private void updateDb(Integer postId, HashMap<Integer, String> results) {
        if (postId != 0) {
            resultsViewModel.insertIntoDb(postId, mCurrentDate.getShortDateString(), results, arbayiinId);
            Log.d("TAG", "postId: " + postId + " saved into db");
        } else {
            Log.d("TAG", "postId: " + postId + " didn't saved into db");
        }

    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUIWhenStartingHTTPRequest(){
        binding.currentDayTv.setText("درحال ثبت نتیجه...");
    }

    private void updateUIWhenStopingHTTPRequest(int dayNumber){
        binding.currentDayTv.setText(SlideshowFragmentUpdateUI.getCurrentDayText(dayNumber, duration, mCurrentDate, floatingActionButton));
        if (dayNumber <= duration) {
            mCurrentDate.add(5, 1);
            String current = String.valueOf(dayNumber).concat("(").concat(mCurrentDate.getShortDateString()).concat(")");
            binding.currentDayTv.setText(current);
        } else {
            floatingActionButton.hide();
            binding.currentDayTv.setText("پایان اربعین");
        }

        deleteSharedPRefs();
        observeAmalAdapterItems(amalAdapter, slideshowViewModel);

    }

    private void updateUI(int postId){
        if (postId != 0 ) {
            Toast.makeText(getContext(), "successfull!!! " + postId, Toast.LENGTH_SHORT).show();
            resultsCount++;
            updateUIWhenStopingHTTPRequest(resultsCount+1);
        } else {
            Toast.makeText(getContext(), "unsuccessfull!!! " + postId, Toast.LENGTH_SHORT).show();
        }

    }


    public void deleteSharedPRefs() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(
                getString(R.string.dayPreference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.clear();
        editor.apply();
    }
}