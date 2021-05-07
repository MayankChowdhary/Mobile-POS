package com.retailstreet.mobilepos.View.ui.Sales;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class SalesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SalesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Sales fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}