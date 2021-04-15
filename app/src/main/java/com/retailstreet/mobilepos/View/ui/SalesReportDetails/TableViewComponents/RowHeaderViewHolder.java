package com.retailstreet.mobilepos.View.ui.SalesReportDetails.TableViewComponents;

import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.retailstreet.mobilepos.R;


/**
 * Created by evrencoskun on 1.12.2017.
 */

public class RowHeaderViewHolder extends AbstractViewHolder {
    public final TextView row_header_textview;
    public String rowId = "";

    public RowHeaderViewHolder(View p_jItemView) {
        super(p_jItemView);
        row_header_textview = p_jItemView.findViewById(R.id.row_header_textview);
    }

    @Override
    public void setSelected(SelectionState p_nSelectionState) {
        super.setSelected(p_nSelectionState);

        int nBackgroundColorId;
        int nForegroundColorId;

        if (p_nSelectionState == SelectionState.SELECTED) {
            nBackgroundColorId = R.color.secondary;
            nForegroundColorId = R.color.white;

        } else if (p_nSelectionState == SelectionState.UNSELECTED) {
            nBackgroundColorId = R.color.primary;
            nForegroundColorId = R.color.white;

        } else { // SelectionState.SHADOWED

            nBackgroundColorId = R.color.primary;
            nForegroundColorId = R.color.white;
        }

        itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(),
                nBackgroundColorId));
        row_header_textview.setTextColor(ContextCompat.getColor(row_header_textview.getContext(),
                nForegroundColorId));
    }
}
