package com.retailstreet.mobilepos.View.ui.Checkout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class CheckoutFragment extends Fragment {

    private CheckoutViewModel checkoutViewModel;
    static StringWithTag spinnerSelected;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        checkoutViewModel =
                new ViewModelProvider(this).get(CheckoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_checkout, container, false);
        spinnerSelected = new StringWithTag("","");
        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        checkoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        SearchableSpinner spinner = root.findViewById(R.id.customer_selector);
        ArrayAdapter<StringWithTag> adap = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_layout, getSpinnerItems());
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adap);
        spinner.setTitle("Select Customer");
        spinner.setPositiveButton("OK");
        spinner.setGravity(Gravity.START);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                spinnerSelected = (StringWithTag) parent.getItemAtPosition(pos);
                String tag = spinnerSelected.tag;
                Log.d("SpinnerSelected", "onItemSelected: Tag= "+tag);

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        return root;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

    }




    private List<StringWithTag> getSpinnerItems(){
        List<StringWithTag> list = new ArrayList<StringWithTag>();
        list.add(new StringWithTag("No Customer", ""));

        SQLiteDatabase mydb = getActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select CUST_ID, NAME from retail_cust",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(1);
                String name = cursor.getString(0);
                list.add(new StringWithTag(id, name));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return  list;

    }
    public static StringWithTag getSpinnerSelect(){

        return spinnerSelected;
    }

}
