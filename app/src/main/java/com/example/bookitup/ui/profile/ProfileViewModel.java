package com.example.bookitup.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel {
    private MutableLiveData<String> mText;

    /**
     * Live Data Instance
     */
    private MutableLiveData<String> mName = new MutableLiveData<>();

    public void setName(String name) {
        mName.setValue(name);
    }

    public LiveData<String> getName() {
        return mName;
    }
}

