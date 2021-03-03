package com.retailstreet.mobilepos.View.ui.Checkout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.retailstreet.mobilepos.R;
public class CheckoutFragment extends Fragment {

    private CheckoutViewModel checkoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        checkoutViewModel =
                new ViewModelProvider(this).get(CheckoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_checkout, container, false);

        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        checkoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        return root;

    }


}
