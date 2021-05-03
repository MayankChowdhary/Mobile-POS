package com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.Vibration;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewInterface;
import com.tsongkha.spinnerdatepicker.DatePicker;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by evrencoskun on 1.12.2017.
 */

public class CellViewHolder extends AbstractViewHolder implements DatePickerDialog.OnDateSetListener {
    public final EditText cell_textview;
    public final LinearLayout cell_container;
    public  String cellId = "";
    public String masterID = "";
    TableViewInterface tableViewInterface;
    Context mContext;


    public CellViewHolder(View itemView, TableViewInterface launcher,Context context) {
        super(itemView);
        cell_textview = itemView.findViewById(R.id.cell_data);
        cell_container = itemView.findViewById(R.id.cell_container);
        tableViewInterface=launcher;
        mContext = context;
    }

    public void setCellModel(CellModel p_jModel, int pColumnPosition) {

        // Change textView align by column
        cellId = p_jModel.getId();
        masterID = String.valueOf(pColumnPosition);
        cell_textview.setGravity(ColumnHeaderViewHolder.COLUMN_TEXT_ALIGNS[pColumnPosition] |
                Gravity.CENTER_VERTICAL);

        // Set text
        cell_textview.setText(String.valueOf(p_jModel.getData()));

        FocusListener focusListener = new FocusListener(cellId,masterID);
        cell_textview.setOnFocusChangeListener(focusListener);
        // It is necessary to remeasure itself.
        cell_container.getLayoutParams().width = LinearLayout.LayoutParams.WRAP_CONTENT;
        cell_textview.requestLayout();

        /*if(pColumnPosition==0 || pColumnPosition==10||pColumnPosition==11){
            cell_textview.setEnabled(false);
        }else {
            cell_textview.setEnabled(true);
            cell_textview.setFocusable(true);
            cell_textview.setFocusableInTouchMode(true);
        }*/

       /* if(pColumnPosition==4 || pColumnPosition==5 || pColumnPosition==6|| pColumnPosition==7|| pColumnPosition==8|| pColumnPosition==9){

            cell_textview.setHorizontallyScrolling(true);
            cell_textview.setInputType(EditorInfo.TYPE_NUMBER_FLAG_SIGNED|EditorInfo.TYPE_CLASS_NUMBER);        }*/
    }

    @Override
    public void setSelected(SelectionState p_nSelectionState) {
        super.setSelected(p_nSelectionState);

        if (p_nSelectionState == SelectionState.SELECTED) {
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .black));
            cell_container.setBackgroundColor(ContextCompat.getColor(cell_container.getContext(), R.color.cell_selected));
        } else {
            cell_textview.setTextColor(ContextCompat.getColor(cell_textview.getContext(), R.color
                    .black));
            cell_container.setBackgroundColor(ContextCompat.getColor(cell_container.getContext(), R.color.white));

        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        SQLiteDatabase mydb = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        String expiryDate = year +"-"+monthOfYear+"-"+dayOfMonth+" 00:00:00";
        mydb.execSQL("UPDATE tmp_purchase_invoice SET EXP_DATE = '"+ expiryDate+"' WHERE  ITEM_GUID = '"+cellId+"'");
        mydb.close();
        cell_textview.setText(expiryDate);

    }

    public class FocusListener implements View.OnFocusChangeListener{

        String cellId, columnPOs;

        FocusListener(String cellid,String columnPos){
            this.cellId = cellid;
            this.columnPOs = columnPos;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {

            if(hasFocus && columnPOs.equals("3")){

                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                new SpinnerDatePickerDialogBuilder()
                        .context(mContext)
                        .callback(CellViewHolder.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(true)
                        .showDaySpinner(true)
                        .defaultDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .maxDate(2099, 0, 1)
                        .minDate(calendar.get(Calendar.YEAR), 0, 1)
                        .build()
                        .show();
            }

            if (!hasFocus) {
                String data = null;
                try {
                    SQLiteDatabase mydb = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
                    EditText currentCell = (EditText)v;
                    data = currentCell.getText().toString();
                    switch (columnPOs){

                        case "1" :
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET EXTERNALPRODUCTID = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            break;

                        case "2" :
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET BARCODE = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            break;
                        case "4" :
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET MRP = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            break;
                        case "5" :
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET S_PRICE = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            break;
                        case "6" :
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET P_PRICE = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))- CAST(DISC AS REAL) WHERE ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            tableViewInterface.refreshTableView();
                            break;

                        case "7" :
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET QTY = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))-  CAST(DISC AS REAL) WHERE ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            tableViewInterface.refreshTableView();

                            break;
                        case "8" :
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET FQTY = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            break;
                        case "9" :
                            if(getTotal(cellId)<Double.parseDouble(data)){
                                Toast.makeText(mContext,"Enter P.Price And Quantity first!",Toast.LENGTH_LONG).show();
                                Vibration.Companion.vibrate(300);
                                currentCell.setText("0.00");
                                return;
                            }
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET DISC = '"+ data+"' WHERE  ITEM_GUID = '"+cellId+"'");
                            mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))-  CAST(DISC AS REAL) WHERE ITEM_GUID = '"+cellId+"'");
                            mydb.close();
                            currentCell.setText(data);
                            tableViewInterface.refreshTableView();
                            break;

                        default:
                    }


                    Toast.makeText(ApplicationContextProvider.getContext(), "Updated Column: " + columnPOs + "  Data: " + data, Toast.LENGTH_SHORT).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private double getTotal(String guid){
        double totalAmount=0.00;

        try {
            SQLiteDatabase mydb = mContext.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
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
