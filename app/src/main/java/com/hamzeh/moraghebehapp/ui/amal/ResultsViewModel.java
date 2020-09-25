package com.hamzeh.moraghebehapp.ui.amal;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aminography.primecalendar.PrimeCalendar;
import com.aminography.primecalendar.persian.PersianCalendar;
import com.hamzeh.moraghebehapp.data.SharedPrefsKeys;
import com.hamzeh.moraghebehapp.data.database.entities.ResultsEntity;
import com.hamzeh.moraghebehapp.data.database.repositories.ResultsRepository;
import com.hamzeh.moraghebehapp.data.pojo.ResultsFirstAndLastDate;
import com.hamzeh.moraghebehapp.retrofit.Api;
import com.hamzeh.moraghebehapp.retrofit.InsertStreams;
import com.hamzeh.moraghebehapp.ui.amal.resultmemento.CareTaker;
import com.hamzeh.moraghebehapp.ui.amal.resultmemento.Originator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class ResultsViewModel extends ViewModel {
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sharedPrefs;
    private final int userId;
    public Originator originator;
    CareTaker careTaker;
    public MutableLiveData<List<String>> hashMapMutableLiveData;
    private int arbayiinId;
    private ArrayList<String> sortedResults;
    private PrimeCalendar mStartDate;
    ResultsRepository mRepository;
    private String firstDate;
    private MutableLiveData<Boolean> hastFirstDate;
    private MutableLiveData<Integer> resultsCount = new MutableLiveData<>();

    Api retrofit;

    @ViewModelInject
    public ResultsViewModel(ResultsRepository repository, SharedPreferences sharedPreferences, SharedPreferences.Editor editor, Api retrofit) {
        this.sharedPrefs = sharedPreferences;
        this.hastFirstDate = new MutableLiveData<>();
        this.editor = editor;
        originator = new Originator();
        careTaker = new CareTaker();
        hashMapMutableLiveData = new MutableLiveData<>();
        this.sortedResults = new ArrayList<>();
        mRepository = repository;
        this.retrofit = retrofit;
        this.userId = sharedPreferences.getInt(SharedPrefsKeys.SAVED_USER_ID, 0);
    }

    public void insertFirstDay(int arbayiinId, String firstDate) {
        setStartDateForFirstTime(firstDate);
        editor.putString(""+arbayiinId, firstDate);
        editor.apply();
    }

    public void deleteFirstDay(int arbayiinId) {
        editor.remove(""+arbayiinId);
        editor.apply();
    }

    public LiveData<Boolean> hasFirstDate(int arbayiinId) {
        hastFirstDate.setValue(sharedPrefs.contains("" +arbayiinId));
        return hastFirstDate;
    }

    public String retrieveFirstDate(int arbayiinId) {
        return sharedPrefs.getString("" + arbayiinId, "تعیین نشده");
    }

    public void setPosition(int position) {
        originator.setPosition(position);
    }

    public void setArbayiinId(int arbayiinId) {
        this.arbayiinId = arbayiinId;
    }


    public void getStateFromMemento() {
        if (careTaker.get(arbayiinId) != null) {
            originator.getStatesFromMemento(careTaker.get(arbayiinId));

            populateSortedResults(originator.getState().size());
            Log.d("resultValues", "getStateFromMemento sortedValusArraylist: " + sortedResults);
            hashMapMutableLiveData.setValue(sortedResults);
            if (!originator.getStartDate().equals(""))
            setStartDate(originator.getStartDate());
            Log.d("startDate", "getStateFromMemento startDate: " + originator.getStartDate());
        }
    }
    public void setText(String text, int amalSize, int arbayiinId) {

        this.arbayiinId = arbayiinId;
        Log.d("arbIdInSetText", "arbID: " + arbayiinId);
        originator.setText(text);
        originator.updatehashMapState();
        populateSortedResults(amalSize);
        Log.d("resultValues", "savedState sortedValusArraylist: " + sortedResults);

        hashMapMutableLiveData.setValue(sortedResults);

    }

    private void populateSortedResults(int size) {
        sortedResults.clear();
        for (int i = 0; i < size; i++) {
            if (originator.getState().containsKey(i)){
                sortedResults.add(originator.getState().get(i));
            } else {
                originator.populateHashMapState(i, "0");
                sortedResults.add("0");
            }
        }
    }

    public void saveStateToMomento() {
        Log.d("saveStatetetoMemento", "saveStatetetoMemento: ");
            careTaker.add(arbayiinId, originator.saveStateToMemento());

            originator.clearState();
            hashMapMutableLiveData.setValue(null);
            sortedResults.clear();

    }

    public void refreshOriginator() {
        originator.clearState();
        hashMapMutableLiveData.setValue(null);
    }

    public LiveData<List<String>> getHashMap() {
       return hashMapMutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mStartDate = null;
        Log.d("onCleared", "called");
        arbayiinId = 0;
    }

    public String getResults() {
        if (originator.getState().size() != 0){
            StringBuilder stringBuilder = new StringBuilder();
            List<String> results = new ArrayList<>();
            for (int i = 0; i < originator.getState().size(); i++) {
                results.add(String.valueOf(originator.getState().get(i)));
                stringBuilder.append(originator.getState().get(i)).append(",");
            }
            Log.d("pastSaving", results + "");
            return stringBuilder.toString();

        }
        return "";
    }

    public int getUserId() {
        return userId;
    }

    public void insertIntoDb(int resultId, String currentDate, HashMap<Integer, String> results, int arbayiinId) {

        Log.d("insertIntoDb", originator.getState().size() + "");
        if (results.size() != 0){
            List<String> mResults = new ArrayList<>();
            for (int i = 0; i < results.size(); i++) {
                mResults.add(String.valueOf(results.get(i)));
            }
            Log.d("pastSaving", results + "");
            Log.d("completable", "arbID: " + arbayiinId);

            ResultsEntity resultsEntity = new ResultsEntity(resultId, arbayiinId, currentDate,mResults);
            mRepository.insertIntoDb(resultsEntity);

        }
    }

    public LiveData<Boolean> saveResultsToDb(int resultId, String currentDate) {
        MutableLiveData<Boolean> isSavedLD = new MutableLiveData<>();
        if (originator.getState().size() != 0){
            List<String> results = new ArrayList<>();
            for (int i = 0; i < originator.getState().size(); i++) {
                results.add(String.valueOf(originator.getState().get(i)));
            }
            Log.d("pastSaving", results + "");
            ResultsEntity resultsEntity = new ResultsEntity(resultId, arbayiinId, currentDate,results);
            boolean isSaved = mRepository.insertResults(resultsEntity);

            isSavedLD.setValue(isSaved);
            return isSavedLD;
        } else {
            isSavedLD.setValue(false);
            return isSavedLD;
        }

    }

    public void deletResultsFromDb() {
        mRepository.deleteAllResults();
    }

    public void setStartDateForFirstTime(String startDate) {
        this.firstDate = startDate;
        originator.setStartDate(firstDate);
    }
    public void setStartDate(String startDateString) {
        this.firstDate = startDateString;
        originator.setStartDate(startDateString);
//        dayResultsOriginator.setDay(startDateString);
        Log.d("setStartDate", "startDateString: " + startDateString);
        // convert dayString to PrimeCalendar
        String[] startDayArray = getStringArray(startDateString);
//        Log.d("setStartDate", "startDateArray: " + startDayArray[0] + " " + startDayArray[1] + " " + startDayArray[2]);
        PrimeCalendar startDate = new PersianCalendar();
        Log.d("setStartDate", "startDateHolder: " + startDate.getShortDateString());
        startDate.set(Integer.parseInt(startDayArray[0]), Integer.parseInt(startDayArray[1]) -1, Integer.parseInt(startDayArray[2]));
        this.mStartDate = startDate;
        if (mStartDate != null)
        Log.d("setstartdate", "startDate: " + mStartDate.getLongDateString());
    }

    public String[] getStringArray(String string) {
        if (string != null){
            return string.split("/");
        }
        else return new String[]{"1", "1", "1"};
    }

    public LiveData<Integer> getDuration() {
        return mRepository.getDuration(arbayiinId);
    }

    public LiveData<List<String>> getResultsLiveDataByIDAndDay(int arbayiinId, int day) {
        return mRepository.getResultsLiveDataByIDAndDay(arbayiinId, day);
    }

    public LiveData<List<ResultsEntity>> getmAllResults(int arbayiinId) {
        return mRepository.getmAllResults(arbayiinId);
    }

    public LiveData<List<ResultsFirstAndLastDate>> getResultsFirstAndLastDate(int arbayiinId) {
        return mRepository.getResultsFirstAndLastDate(arbayiinId);
    }

    public MutableLiveData<Integer> getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount.setValue(resultsCount);
    }
}
