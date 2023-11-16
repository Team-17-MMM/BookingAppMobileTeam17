package com.example.bookingappteam17.fragments.resorts;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ResortPageViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;
    public ResortPageViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("This is search help!");
    }
    public LiveData<String> getText(){
        return searchText;
    }
}
