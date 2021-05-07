package com.retailstreet.mobilepos.View.ui.Sales;

import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome;
import com.retailstreet.mobilepos.Controller.ControllerShiftTrans;
import com.retailstreet.mobilepos.Controller.ControllerStockMaster;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.SalesRecyclerView.SalesListAdapter;
import com.retailstreet.mobilepos.View.SalesRecyclerView.UpdateRecyclerView;
import com.retailstreet.mobilepos.View.dialog.ClickListeners;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class SalesFragment extends Fragment implements UpdateRecyclerView {

    //private SalesViewModel homeViewModel;
    SearchView mSearchView;
    RecyclerView recyclerView;
    SalesListAdapter salesListAdapter;
    static  String handlerData;
    private boolean doubleBackToExitPressedOnce;
    private final Handler queryLatencyHandler =new Handler(Looper.getMainLooper());
    public static Menu optionsMenu;
    static Cursor cursor;
     ControllerStockMaster controllerStockMaster;
     TextView emptyListView;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        Log.d("SalesFragment", "onCreateView: Invoked ");
        //homeViewModel = new ViewModelProvider(this).get(SalesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sales, container, false);
            setHasOptionsMenu(true); // Add this!
            emptyListView=root.findViewById(R.id.empty_sales_text);
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
       // final TextView textView = root.findViewById(R.id.text_home);
        controllerStockMaster= new ControllerStockMaster(getContext());
        recyclerView = root.findViewById(R.id.sales_recycler_view);
        salesListAdapter = new SalesListAdapter(ApplicationContextProvider.getContext(),cursor,getActivity(),this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(salesListAdapter);

        root.setFocusableInTouchMode(true);
        root.requestFocus();


        root.setOnKeyListener((v, keyCode, event) -> {
            if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction()== KeyEvent.ACTION_DOWN)
            {
                if (!mSearchView.isIconified()) {
                    mSearchView.setIconified(true);
                }

                if (doubleBackToExitPressedOnce) {
                   requireActivity().finish();

                }else {

                    Toast.makeText(getContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                }

              doubleBackToExitPressedOnce = true;

                new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
                return true;
            }
            return false;
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!isSessionOpen()){

            LottieAlertDialogs alertDialog = new LottieAlertDialogs.Builder(getActivity(), DialogTypes.TYPE_WARNING)
                    .setTitle("Shift Status")
                    .setDescription("Your Shift is not running!")
                    .setNegativeText("Back")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setPositiveText("Open Day")
                    .setNegativeButtonColor(Color.parseColor("#297999"))
                    .setNegativeTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(new ClickListeners() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialogs dialog) {
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_sales_to_nav_dayopen);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeListener(new ClickListeners() {
                        @Override
                        public void onClick(@NotNull LottieAlertDialogs dialog) {
                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_home);
                            dialog.dismiss();
                }
            }).build();
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
    }

    @Override
    public void updateIndicator(int size) {

        if (size >0 ) {
            ActionItemBadge.update(getActivity(), optionsMenu.findItem(R.id.appCart), FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.RED, size);
        }else {
            ActionItemBadge.update(getActivity(), optionsMenu.findItem(R.id.appCart), FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
        }

    }

    @Override
    public void onResume() {
        Log.d("SalesFragment", "onResumeView: Invoked ");
        super.onResume();
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
            cursor = null;
        }
        salesListAdapter.swapCursor(null);
        GetSalesCursorData scd = new GetSalesCursorData(null);
        scd.execute();
        if (mSearchView!=null && !mSearchView.isIconified()) {
            mSearchView.setIconified(true);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

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
                queryLatencyHandler.postDelayed(queryLatencyRunnable, 500);
                return false;
            }

        });



        mSearchView.setOnCloseListener(() -> {
            restoreData();
            return false;
        });

        ActionItemBadge.update(getActivity(), menu.findItem(R.id.appCart), FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
        optionsMenu=menu;
        salesListAdapter.updateCountIndicator(menu);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.appCart) {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_sales_to_nav_cart);
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
        if(cursor != null && !cursor.isClosed())
        cursor.close();

        GetSalesCursorData scd = new GetSalesCursorData(pattern);
        scd.execute();

    }
    private  void restoreData(){
        if(cursor != null && !cursor.isClosed())
        cursor.close();
        GetSalesCursorData scd = new GetSalesCursorData(null);
        scd.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            salesListAdapter = null;
           // if(cursor != null && !cursor.isClosed())
               // cursor.close();
            controllerStockMaster.close();
            controllerStockMaster=null;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @SuppressWarnings("deprecation")
    class GetSalesCursorData extends AsyncTask<Void, Void, String> {

        String pattern;
        public GetSalesCursorData(String pattern){
            this.pattern = pattern;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            emptyListView.setText("Loading...");
            Log.d("AsyncTask", "onPreExecute: Invoked");
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            swapRecyclerViewData();
            Log.d("AsyncTask", "onPostExecute: ");
        }



        @Override
        protected String doInBackground(Void... params) {
            Log.d("AsyncTask", "doInBackground: ");
            if(pattern==null) {
                cursor = controllerStockMaster.getStockMasterCursor();
            }else {
                cursor = controllerStockMaster.searchStockMasterCursor(pattern,"PROD_NM");
            }
            return null;
        }
    }


    protected void swapRecyclerViewData(){

        if(cursor == null || cursor.isClosed()) {
            salesListAdapter.swapCursor(null);
            Log.d("SettingRecycler", "swapRecyclerViewData: Cursor Is Null");
        }
        else {

            salesListAdapter.swapCursor(cursor);
            Log.d("SettingRecycler", "swapRecyclerViewData: Cursor Is OK");
        }
        if(cursor==null){
            recyclerView.setVisibility(View.GONE);
            emptyListView.setVisibility(View.VISIBLE);
            emptyListView.setText("No Sales Found!");
        }else
        {
            recyclerView.setVisibility(View.VISIBLE);
            emptyListView.setVisibility(View.GONE);
        }
    }

    private Boolean isSessionOpen(){

        String shiftTransID = new ControllerShiftTrans().geShiftSession();
        return !(shiftTransID==null || shiftTransID.isEmpty());
    }
}