package com.retailstreet.mobilepos.View.ui.Checkout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckoutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CheckoutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Feature Coming Soon!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}