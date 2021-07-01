package com.retailstreet.mobilepos.View.ui.PurchaseInvoice;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.retailstreet.mobilepos.Controller.ControllerStoreConfig;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.DecimalRounder;
import com.retailstreet.mobilepos.Utils.Vibration;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class PurchaseInvoiceEditDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    TextInputEditText expiry;
    String cellId="";
    boolean discColVis = true;
    boolean freeColVis = true;
    boolean expiryColVis = true;
    boolean barcodeColVis = true;
   ControllerStoreConfig config= new ControllerStoreConfig();

            @NonNull
            @Override
            public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

                return super.onCreateDialog(savedInstanceState);

            }

            @Nullable
            @Override
            public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

                getDialog().setTitle("Enter Row Data");
                setCancelable(false);
                return inflater.inflate(R.layout.dialog_fragment_purchase_invoice, container, false);

            }

            @Override
            public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
                super.onViewCreated(view, savedInstanceState);

                final Button cancel = view.findViewById(R.id.Canceldisc);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dismiss();


                    }
                });


                TextInputEditText prodName = view.findViewById(R.id.productname);


               if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("GUID")))
                    cellId = getArguments().getString("GUID");




                discColVis = config.getInvoiceDiscVis();
                freeColVis = config.getInvoiceFreeVis();
                expiryColVis = config.getInvoiceExpiryVis();
                barcodeColVis = config.getInvoiceBarcodeVis();

                TextInputEditText sPrice = view.findViewById(R.id.pi_sprice);
                TextInputEditText quantity = view.findViewById(R.id.editqtyid);
                 expiry = view.findViewById(R.id.editexpiryid);
                TextInputEditText extID = view.findViewById(R.id.editexternalid);
                TextInputEditText mrpEditText = view.findViewById(R.id.editmrpid);
                TextInputEditText editPprice = view.findViewById(R.id.editppriceid);
                TextInputEditText editFQty = view.findViewById(R.id.editfqtyid);
                TextInputEditText editBarCode = view.findViewById(R.id.editbarcodeid);
                TextInputEditText editDiscount = view.findViewById(R.id.editdiscid);

                editDiscount.setEnabled(discColVis);
                editFQty.setEnabled(freeColVis);
                expiry.setEnabled(expiryColVis);
                editBarCode.setEnabled(barcodeColVis);

             //   ITEM_GUID  text not null PRIMARY KEY, PROD_NM text,EXTERNALPRODUCTID text , BARCODE text, EXP_DATE text DEFAULT '00-00-00', MRP text DEFAULT '0.00', S_PRICE text DEFAULT '0.00' , P_PRICE text DEFAULT '0.00', QTY INTEGER DEFAULT 1,FQTY INTEGER DEFAULT 0,DISC text DEFAULT '0.00', UOM text, TOTAL REAL DEFAULT 0.00,TAX text DEFAULT '0.00')

                SQLiteDatabase mydb = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
                Cursor c = mydb.rawQuery("SELECT * FROM tmp_purchase_invoice WHERE ITEM_GUID = '"+cellId+"'", null);
                if (c.moveToFirst()){
                    do {
                        // Passing values
                        prodName.setText( c.getString(c.getColumnIndex("PROD_NM")));
                        sPrice.setText( c.getString(c.getColumnIndex("S_PRICE")));
                        quantity.setText( c.getString(c.getColumnIndex("QTY")));
                        expiry.setText(c.getString(c.getColumnIndex("EXP_DATE")));
                        extID.setText( c.getString(c.getColumnIndex("EXTERNALPRODUCTID")));
                        mrpEditText.setText( c.getString(c.getColumnIndex("MRP")));
                        editPprice.setText(c.getString(c.getColumnIndex("P_PRICE")));
                        editFQty.setText( c.getString(c.getColumnIndex("FQTY")));
                        editBarCode.setText(c.getString(c.getColumnIndex("BARCODE")));
                        editDiscount.setText( c.getString(c.getColumnIndex("DISC")));


                    } while(c.moveToNext());
                }
                c.close();
                mydb.close();




                expiry.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {

                        if(hasFocus) {
                            Date date = new Date();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);
                            expiry.clearFocus();

                            new SpinnerDatePickerDialogBuilder()
                                    .context(getActivity())
                                    .callback(PurchaseInvoiceEditDialog.this)
                                    .spinnerTheme(R.style.NumberPickerStyle)
                                    .showTitle(true)
                                    .showDaySpinner(true)
                                    .defaultDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                                    .maxDate(2099, 0, 1)
                                    .minDate(calendar.get(Calendar.YEAR), 0, 1)
                                    .build()
                                    .show();
                        }
                    }
                });


                Button btnDone = view.findViewById(R.id.submitdisc);
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*DialogListener dialogListener = (DialogListener) getActivity();
                        dialogListener.onFinishEditDialog("");*/
                        SQLiteDatabase mydb = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
                       String  data;

                        if(!Objects.requireNonNull(extID.getText()).toString().isEmpty()) {
                            data= extID.getText().toString().trim();
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET EXTERNALPRODUCTID = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");

                        }

                        if(!Objects.requireNonNull(editBarCode.getText()).toString().isEmpty()) {
                            data= editBarCode.getText().toString().trim();
                        mydb.execSQL("UPDATE tmp_purchase_invoice SET BARCODE = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");


                        }
                        if(!Objects.requireNonNull(mrpEditText.getText()).toString().isEmpty()) {
                            data = DecimalRounder.roundMRP(mrpEditText.getText().toString().trim());
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET MRP = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");


                        }
                        if(!Objects.requireNonNull(sPrice.getText()).toString().isEmpty()) {
                            data = DecimalRounder.roundSPrice(sPrice.getText().toString().trim());
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET S_PRICE = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");


                        }
                        if(!Objects.requireNonNull(editPprice.getText()).toString().isEmpty()) {
                            data = DecimalRounder.roundPPrice(editPprice.getText().toString().trim());
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET P_PRICE = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))- CAST(DISC AS REAL) WHERE ITEM_GUID = '" + cellId + "'");


                           // tableViewInterface.refreshTableView();
                        }

                        if(!Objects.requireNonNull(quantity.getText()).toString().isEmpty()) {
                            data = DecimalRounder.roundDecimal(2,quantity.getText().toString().trim());
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET QTY = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))-  CAST(DISC AS REAL) WHERE ITEM_GUID = '" + cellId + "'");


                            //tableViewInterface.refreshTableView();
                        }

                        if(!Objects.requireNonNull(editFQty.getText()).toString().isEmpty()) {
                            data = editFQty.getText().toString().trim();
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET FQTY = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");


                        }
                        if(!Objects.requireNonNull(editDiscount.getText()).toString().isEmpty()) {
                            data = DecimalRounder.roundSPrice(editDiscount.getText().toString().trim());
                            if (getTotal(cellId) < Double.parseDouble(data)) {
                                Toast.makeText(getActivity(), "Enter P.Price And Quantity first!", Toast.LENGTH_LONG).show();
                                Vibration.Companion.vibrate(300);
                                return;
                            }
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET DISC = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))-  CAST(DISC AS REAL) WHERE ITEM_GUID = '" + cellId + "'");

                           // tableViewInterface.refreshTableView();
                        }

                        if(!Objects.requireNonNull(expiry.getText()).toString().isEmpty()) {
                            data = expiry.getText().toString().trim();
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET EXP_DATE = '" + data + "' WHERE  ITEM_GUID = '" + cellId + "'");

                        }

                        mydb.close();
                        dismiss();
                        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, requireActivity().getIntent());
                    }
                });

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

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String expiryDate="";
                if((monthOfYear+1)<10 && dayOfMonth<10){
                    expiryDate = year +"-0"+(monthOfYear+1)+"-0"+dayOfMonth+" 00:00:00";
                }else if((monthOfYear+1)<10){
                    expiryDate = year +"-0"+(monthOfYear+1)+"-"+dayOfMonth+" 00:00:00";
                }else if(dayOfMonth<10){
                    expiryDate = year +"-"+(monthOfYear+1)+"-0"+dayOfMonth+" 00:00:00";
                }
        // expiryDate = year +"-"+(monthOfYear+1)+"-"+dayOfMonth+" 00:00:00";
        expiry.setText(expiryDate);
        expiry.clearFocus();


    }


    public interface DialogListener {
                void onFinishEditDialog(String inputText);
            }
    private double getTotal(String guid){
        double totalAmount=0.00;

        try {
            SQLiteDatabase mydb =requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select TOTAL from tmp_purchase_invoice where ITEM_GUID = '"+guid+"'";
            Cursor cursor = mydb.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                totalAmount= cursor.getDouble(0);

            }
            Log.d("TotalDataTable", "getTotal: Successfully Fetched:"+totalAmount);
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return totalAmount;
    }

        }