package com.retailstreet.mobilepos.View.ui.VendorPayReport.TableViewComponents;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.listener.ITableViewListener;
import com.retailstreet.mobilepos.View.ui.VendorPayReport.TableViewInterface;

/**
 * Created by evrencoskun on 2.12.2017.
 */

public class MyTableViewListener implements ITableViewListener {
    private static final String LOG_TAG = MyTableViewListener.class.getSimpleName();

    private ITableView mTableView;
    TableViewInterface tableViewInterface;

    public MyTableViewListener(ITableView pTableView, TableViewInterface launcher) {
        this.mTableView = pTableView;
        this.tableViewInterface = launcher;
    }

    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Log.d(LOG_TAG, "onCellClicked has been clicked for x= " + column + " y= " + row);
        CellViewHolder viewHolder = (CellViewHolder) cellView;
        tableViewInterface.cellToRowSelector(row, viewHolder.cellId,viewHolder.masterID);
        Log.d("Cell Bill ID Retrieved", "onCellClicked: "+viewHolder.cellId);
    }

    @Override
    public void onCellDoubleClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

    }

    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        Log.d(LOG_TAG, "onCellLongPressed has been clicked for " + row);
    }

    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {
        Log.d(LOG_TAG, "onColumnHeaderClicked has been clicked for " + column);
    }

    @Override
    public void onColumnHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {

    }

    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
       /* if (columnHeaderView != null && columnHeaderView instanceof ColumnHeaderViewHolder) {

            // Create Long Press Popup
            ColumnHeaderLongPressPopup popup = new ColumnHeaderLongPressPopup(
                    (ColumnHeaderViewHolder) columnHeaderView, mTableView,tableViewInterface);

            // Show
            popup.show();
        }*/
    }

    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        Log.d(LOG_TAG, "onRowHeaderClicked has been clicked for " + row);

        RowHeaderViewHolder rowHeaderViewHolder = (RowHeaderViewHolder) rowHeaderView;
       tableViewInterface.launchDetailsFragment(rowHeaderViewHolder.rowId,rowHeaderViewHolder.rowMasterId);
      // Toast.makeText(ApplicationContextProvider.getContext(),rowHeaderViewHolder.rowId, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRowHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {

    }

    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder owHeaderView, int row) {
        Log.d(LOG_TAG, "onRowHeaderLongPressed has been clicked for " + row);
    }
}
