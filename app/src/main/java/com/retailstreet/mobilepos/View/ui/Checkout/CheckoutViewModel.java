package com.retailstreet.mobilepos.View.ui.Checkout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
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