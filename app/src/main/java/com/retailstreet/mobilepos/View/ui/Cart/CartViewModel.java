package com.retailstreet.mobilepos.View.ui.Cart;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CartViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CartViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Cart fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}