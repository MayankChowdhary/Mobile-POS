package com.retailstreet.mobilepos.View.ui.gallery;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.retailstreet.mobilepos.Controller.ControllerCart;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.CartRecyclerView.CartListAdapter;
import com.retailstreet.mobilepos.View.SalesRecyclerView.SalesListAdapter;

public class CartFragment extends Fragment {

    private CartViewModel cartViewModel;

    SearchView mSearchView;
    RecyclerView recyclerView;
    CartListAdapter cartListAdapter;
    static  String handlerData;
    private final Handler queryLatencyHandler =new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cart, container, false);

        Cursor cursor = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
        recyclerView = (RecyclerView) root.findViewById(R.id.sales_recycler_view);
        cartListAdapter = new CartListAdapter(getContext(),cursor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(cartListAdapter);

        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        MenuItem mSearch = menu.findItem(R.id.appSearchBar);
        mSearch.setVisible(true);
       // MenuItem mCart = menu.findItem(R.id.appCart);
       // mCart.setVisible(true);
    }
}