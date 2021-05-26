package com.retailstreet.mobilepos.View.ui.Payment;

import android.app.Activity;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.levitnudi.legacytableview.LegacyTableView;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;
import static com.levitnudi.legacytableview.LegacyTableView.BOLD;
import static com.levitnudi.legacytableview.LegacyTableView.LEVICI;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class BillPreviewDialog extends DialogFragment {

    String billNo ="";
    String custId ="";
    double itemCount=0.0;
    double total=0.00;


            @NonNull
            @Override
            public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
                return super.onCreateDialog(savedInstanceState);


            }

        @Override
        public void onStart() {
            super.onStart();
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            Objects.requireNonNull(getDialog()).getWindow().setLayout((6 * width)/6, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

    @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                Objects.requireNonNull(getDialog()).setTitle("Sale Completed ☑");
                setCancelable(false);

                return inflater.inflate(R.layout.dialog_bill_preview, container, false);
            }

            @Override
            public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);



                final Button cancel = view.findViewById(R.id.btn_bill_preview_back);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //dismiss();
                        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, requireActivity().getIntent());

                    }
                });


                TextView billNoTextV = view.findViewById(R.id.bp_bill_num_value);
                TextView custNameTextV = view.findViewById(R.id.bp_cust_name_value);
                TextView custMobTextV = view.findViewById(R.id.bp_cust_mob_value);
                TextView totalItems = view.findViewById(R.id.bp_total_items_value);
                TextView totalAmount = view.findViewById(R.id.bp_total_amount_value);

                String mobile = "Not Available";
                String custName= "Not Available";

                if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("BILLNum")))
                    billNo = getArguments().getString("BILLNum");

                if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("CUSTID"))){
                    custId = getArguments().getString("CUSTID");
                    custName = getFromMasterCustomer(custId,"NAME");
                    mobile = getFromMasterCustomer(custId,"MOBILE_NO");
                }

                custNameTextV.setText(custName);
                custMobTextV.setText(mobile);
                billNoTextV.setText(billNo);

                LegacyTableView legacyTableView = view.findViewById(R.id.bill_details_table_view);
                LegacyTableView.insertLegacyTitle("PROD NAME", "MRP", "S.PRICE","QTY", "TOTAL");
                getTabledata();
                //set table contents as string arrays

              legacyTableView.setTitle(LegacyTableView.readLegacyTitle());
              legacyTableView.setContent(LegacyTableView.readLegacyContent());

                //Add your preferred theme like this:
                legacyTableView.setTheme(LEVICI);

                //depending on the phone screen size default table scale is 100
                //you can change it using this method
                legacyTableView.setInitialScale(80);//default initialScale is zero (0)

                //if you want a smaller table, change the padding setting
                legacyTableView.setTablePadding(10);

                //to enable users to zoom in and out:

                //to enable users to zoom in and out:
                legacyTableView.setZoomEnabled(true);
                legacyTableView.setShowZoomControls(true);
                legacyTableView.setTitleFont(BOLD);
                legacyTableView.setContentTextSize(30);

                //remember to build your table as the last step
                legacyTableView.build();


                totalItems.setText(String.valueOf("Item(s): "+itemCount));
                totalAmount.setText(String.valueOf("Grand Total: "+total));

            }

            @Override
            public void onResume() {
                super.onResume();
            }

            @Override
            public void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

                Log.d("API123", "onCreate");

                boolean setFullScreen = false;
                if (getArguments() != null) {
                    setFullScreen = getArguments().getBoolean("fullScreen");
                }

                if (setFullScreen)
                    setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);


            }

            @Override
            public void onDestroyView() {
                super.onDestroyView();
            }

    public void getTabledata() {
        try {
            SQLiteDatabase mydb = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String query = "select * from tmp_bill_preview";
            Cursor result = mydb.rawQuery(query, null);

            if (result.moveToFirst()) {
                while (!result.isAfterLast()) {
                    LegacyTableView.insertLegacyContent(result.getString(0), result.getString(1), result.getString(2), result.getString(3), result.getString(4));
                    itemCount++;
                    total+=Double.parseDouble(result.getString(4));
                    result.moveToNext();
                }

            }

            result.close();
            mydb.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String getFromMasterCustomer(String custId, String column){
        String result= null;
        try {
            SQLiteDatabase mydb  = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_cust where CUSTOMERGUID ='"+custId+"'";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromCustomerMaster: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

        }