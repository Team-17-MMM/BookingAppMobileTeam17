package com.example.bookingappteam17.fragments.accommodations;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AccommodationPageViewModel extends ViewModel {
    private final MutableLiveData<String> searchText;
    public AccommodationPageViewModel(){
        searchText = new MutableLiveData<>();
        searchText.setValue("This is search help!");
    }
    public LiveData<String> getText(){
        return searchText;
    }
}
