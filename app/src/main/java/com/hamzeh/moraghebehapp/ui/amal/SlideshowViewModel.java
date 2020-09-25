package com.hamzeh.moraghebehapp.ui.amal;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.hamzeh.moraghebehapp.data.adapteritems.AmalAdapterItem;
import com.hamzeh.moraghebehapp.data.database.entities.AmalEntity;
import com.hamzeh.moraghebehapp.retrofit.calls.AmalCall;

import java.util.ArrayList;
import java.util.List;

public class SlideshowViewModel extends ViewModel {

    private static final String NAME_KEY = "arbayiin_id";

    AmalCall amalCall;

    public MutableLiveData<String> arbTitleMutable;

    @ViewModelInject
    public SlideshowViewModel(@Assisted SavedStateHandle savedStateHandle, AmalCall amalCall) {
       this.amalCall = amalCall;
       arbTitleMutable = new MutableLiveData<>();

    }

    public void saveId(int id) {
        this.amalCall.setArbayiinId(id);
    }


    /**
     * Returns a List of AmalEntities, Amal's saved in Database from the server
     */
    public LiveData<List<AmalEntity>> getAmalsLiveData() {
        return amalCall.getAmalsLiveData();
    }

    /**
     * Get's getAmalsLiveData LiveData List of AmalEntities and manipulate theme
     * by switchMap and returns a list of AmalAdapterItems, the pojo that we need
     * to populate AmalItems in AmalFragment(SlideshowFragment)
     */
    public LiveData<List<AmalAdapterItem>> getAmalItems() {
        return Transformations.switchMap(getAmalsLiveData(), amalEntities -> {

            List<AmalAdapterItem> amalAdapterItems = new ArrayList<>();

            for (AmalEntity amalEntity : amalEntities) {
                amalAdapterItems.add(new AmalAdapterItem(amalEntity));
            }

            // For changing actionbar title in each fragment
            if (amalEntities.size() != 0){
                arbTitleMutable.setValue(amalEntities.get(0).getArbayiinName());
            }

            return settAmalItems(amalAdapterItems);
        });
    }

    /**
     *
     */
    public void checkAmalsInDatabase() {
        amalCall.checkAmalsInDatabase();
    }


    /**
     * Transforms the List<AmalAdapterItem> into LiveData
     * @param amalItems of type List<AmalAdapterItem>
     * @return amalItemsLiveData of type LiveData<List<AmalAdapterItem>>
     */
    public LiveData<List<AmalAdapterItem>> settAmalItems(List<AmalAdapterItem> amalItems) {
        final MutableLiveData<List<AmalAdapterItem>> amalItemsLiveData = new MutableLiveData<>();
        amalItemsLiveData.setValue(amalItems);
        return amalItemsLiveData;
    }


    /**
     *
     */
    public void refreshAmals() {
        amalCall.enqueAmalCall();
    }


}