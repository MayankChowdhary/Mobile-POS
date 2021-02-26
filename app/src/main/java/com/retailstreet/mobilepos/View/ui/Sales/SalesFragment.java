package com.retailstreet.mobilepos.View.ui.Sales;

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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.retailstreet.mobilepos.Controller.ControllerStockMaster;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.SalesRecyclerView.SalesListAdapter;

public class SalesFragment extends Fragment {

    private SalesViewModel homeViewModel;

    SearchView mSearchView;
    RecyclerView recyclerView;
    SalesListAdapter salesListAdapter;
    static  String handlerData;
    private final Handler queryLatencyHandler =new Handler();

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(SalesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sales, container, false);
            setHasOptionsMenu(true); // Add this!
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
       // final TextView textView = root.findViewById(R.id.text_home);
        Cursor cursor = new ControllerStockMaster(getContext()).getStockMasterCursor();
        recyclerView = (RecyclerView) root.findViewById(R.id.sales_recycler_view);
        salesListAdapter = new SalesListAdapter(ApplicationContextProvider.getContext(),cursor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(salesListAdapter);

       /* homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        MenuItem mSearch = menu.findItem(R.id.appSearchBar);
        mSearch.setVisible(true);
        MenuItem mCart = menu.findItem(R.id.appCart);
        mCart.setVisible(true);
       mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint("Search");
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                queryLatencyHandler.removeCallbacks(queryLatencyRunnable);

                if(query.isEmpty())
                {
                    restoreData();

                }else {
                    processQuery(query);

                }
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //processQuery(newText);
                handlerData=newText;
                queryLatencyHandler.removeCallbacks(queryLatencyRunnable);
                queryLatencyHandler.postDelayed(queryLatencyRunnable, 1000);
                return false;
            }

        });


        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                restoreData();
                return false;
            }
        });

        }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.appCart) {
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.nav_cart);
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    final Runnable queryLatencyRunnable = new Runnable() {
        @Override
        public void run() {
            queryLatencyHandler.removeCallbacksAndMessages(null);
            if(handlerData.isEmpty())
            {
                restoreData();

            }else {
                processQuery(handlerData);

            }


        }};

    private  void processQuery(String pattern){

        Cursor cursor = new ControllerStockMaster(getContext()).searchStockMasterCursor(pattern,"PROD_NM");
        salesListAdapter.swapCursor(cursor);

    }
    private  void restoreData(){

        Cursor cursor = new ControllerStockMaster(getContext()).getStockMasterCursor();
        salesListAdapter.swapCursor(cursor);

    }
}