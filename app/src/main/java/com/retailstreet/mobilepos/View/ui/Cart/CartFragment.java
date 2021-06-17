package com.retailstreet.mobilepos.View.ui.Cart;

import android.app.Dialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.Controller.ControllerCart;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.CartRecyclerView.CartListAdapter;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    SearchView mSearchView;
    RecyclerView recyclerView;
    CartListAdapter cartListAdapter;
    ConstraintLayout checkout_lyt;
    static Cursor cursor;
    ControllerCart controllerCart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        controllerCart= new ControllerCart(ApplicationContextProvider.getContext());
         cursor = controllerCart.getCartCursor();
        recyclerView = (RecyclerView) root.findViewById(R.id.cart_recycler_view);
        checkout_lyt = root.findViewById(R.id.checkout_layout);
        cartListAdapter = new CartListAdapter(getContext(),cursor,root,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartListAdapter);

        if(cursor==null){
            recyclerView.setVisibility(View.GONE);
            checkout_lyt.setVisibility(View.GONE);
            root.findViewById(R.id.empty_cart_view).setVisibility(View.VISIBLE);
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

            if(cartListAdapter.isCartEmpty())
            {
                Toast.makeText(getContext(),"Cart is already Empty!",Toast.LENGTH_LONG).show();
                return true;
            }

            Log.d("EmptyCartListening", "onOptionsItemSelected: Invoked ");

            LottieAlertDialogs alertDialog= new LottieAlertDialogs.Builder(getContext(), DialogTypes.TYPE_WARNING)
                    .setTitle("Empty Cart")
                    .setDescription("Do you want to clear all items?")
                    .setPositiveText("Yes")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setNegativeText("No")
                    .setNegativeButtonColor(Color.parseColor("#297999"))
                    .setNegativeTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(lottieAlertDialog -> {
                        cartListAdapter.EmptyCart();
                        lottieAlertDialog.dismiss();
                    })
                    .setNegativeListener(Dialog::dismiss)
                    .build();
            alertDialog.setCancelable(true);
            alertDialog.show();

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            cartListAdapter=null;
            if(cursor != null && !cursor.isClosed())
            cursor.close();
            controllerCart.close();
            controllerCart=null;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

