package com.retailstreet.mobilepos.View.ui.VendorPayReport.TableViewComponents;

import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.PopupMenu;

import com.evrencoskun.tableview.ITableView;
import com.evrencoskun.tableview.sort.SortState;
import com.retailstreet.mobilepos.View.ui.VendorPayReport.TableViewInterface;


/**
 * Created by evrencoskun on 26.12.2017.
 */

public class ColumnHeaderLongPressPopup extends PopupMenu implements PopupMenu.OnMenuItemClickListener {
    private static final String LOG_TAG = ColumnHeaderLongPressPopup.class.getSimpleName();

    // Sort states
    private static final int SHOWTODAY = 0;
    private static final int SHOWALL = 1;
    private static final int DATERANGE = 2;


    //
    private static final int TEST_ROW_INDEX = 4;


    private ColumnHeaderViewHolder m_iViewHolder;
    private ITableView m_iTableView;
    private Context mContext;
    private int mXPosition;
    TableViewInterface tableViewInterface;


    public ColumnHeaderLongPressPopup(ColumnHeaderViewHolder p_iViewHolder, ITableView p_jTableView, TableViewInterface tableViewInterface) {
        super(p_iViewHolder.itemView.getContext(), p_iViewHolder.itemView);
        this.m_iViewHolder = p_iViewHolder;
        this.m_iTableView = p_jTableView;
        this.mContext = p_iViewHolder.itemView.getContext();
        this.mXPosition = m_iViewHolder.getAdapterPosition();
        this.tableViewInterface = tableViewInterface;

        // find the view holder
        m_iViewHolder = (ColumnHeaderViewHolder) m_iTableView.getColumnHeaderRecyclerView().findViewHolderForAdapterPosition(mXPosition);

        initialize();
    }

    private void initialize() {
        createMenuItem();
       // changeMenuItemVisibility();

        this.setOnMenuItemClickListener(this);
    }

    private void createMenuItem() {
        this.getMenu().add(Menu.NONE, SHOWTODAY, 0, "SHOW TODAY'S");
        this.getMenu().add(Menu.NONE, SHOWALL, 1, "SHOW ALL");
        this.getMenu().add(Menu.NONE, DATERANGE, 2, "DATE-RANGE");

        // add new one ...

    }

    private void changeMenuItemVisibility() {
        // Determine which one shouldn't be visible
        SortState sortState = m_iTableView.getSortingStatus(mXPosition);
        if (sortState == SortState.UNSORTED) {
            // Show others
        } else if (sortState == SortState.DESCENDING) {
            // Hide DESCENDING menu item
            getMenu().getItem(1).setVisible(false);
        } else if (sortState == SortState.ASCENDING) {
            // Hide ASCENDING menu item
            getMenu().getItem(0).setVisible(false);
        }

        // Control whether 5. row is visible or not.
        if (m_iTableView.isRowVisible(TEST_ROW_INDEX)) {
            // Show row menu item will be invisible
            getMenu().getItem(3).setVisible(false);
        } else {
            //  Hide row menu item will be invisible
            getMenu().getItem(2).setVisible(false);
        }
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        // Note: item id is index of menu item..

      /*  switch (menuItem.getItemId()) {
            case SHOWTODAY:

                break;
            case SHOWALL:

                break;
            case DATERANGE:
              //  m_iTableView.sortColumn(mXPosition, SortState.DESCENDING);

                break;
        }*/

        // Recalculate of the width values of the columns
        try {
            m_iTableView.remeasureColumnWidth(mXPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

}
