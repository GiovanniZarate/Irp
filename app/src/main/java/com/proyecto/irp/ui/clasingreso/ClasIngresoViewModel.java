package com.proyecto.irp.ui.clasingreso;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClasIngresoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClasIngresoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}