package com.retailstreet.mobilepos.View.ui.Cart;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.retailstreet.mobilepos.Controller.ControllerCart;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.CartRecyclerView.CartListAdapter;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    SearchView mSearchView;
    RecyclerView recyclerView;
    CartListAdapter cartListAdapter;
      ConstraintLayout checkout_lyt;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);


        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        Cursor cursor = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
        recyclerView = (RecyclerView) root.findViewById(R.id.cart_recycler_view);
        checkout_lyt = root.findViewById(R.id.checkout_layout);
        cartListAdapter = new CartListAdapter(getContext(),cursor,root,getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartListAdapter);
        if(cursor==null){
            recyclerView.setVisibility(View.GONE);
            checkout_lyt.setVisibility(View.GONE);
            root.findViewById(R.id.empty_cart_text).setVisibility(View.VISIBLE);
        }

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        MenuItem emptyCart = menu.findItem(R.id.appEmptyCart);
        emptyCart.setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Log.d("MainMenuListening", "onOptionsItemSelected: Invoked ");
        if (item.getItemId() == R.id.appEmptyCart) {
            Log.d("EmptyCartListening", "onOptionsItemSelected: Invoked ");
            new AlertDialog.Builder(getActivity())
                    .setTitle("Empty Cart")
                    .setMessage("Confirm Empty Cart?")
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        cartListAdapter.EmptyCart();

                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }


}

