package com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.evrencoskun.tableview.adapter.recyclerview.holder.AbstractViewHolder;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewInterface;

/**
 * Created by Mayank Choudhary on 12-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class RemoveCellViewHolder extends AbstractViewHolder {
    @NonNull
    public final ImageView cell_image;
    public  String cellId = "";
    public String masterID = "";
    TableViewInterface tableViewInterface;
    Context mContext;

    public RemoveCellViewHolder(@NonNull View itemView) {
        super(itemView);
        cell_image = itemView.findViewById(R.id.cell_image);
    }
    public void setCellModel(CellModel p_jModel, int pColumnPosition) {

        // Change textView align by column
        cellId = p_jModel.getId();
        masterID = String.valueOf(pColumnPosition);

    }

    public void setData(Object data) {
        /*int mood = (int) data;
        int moodDrawable =  R.drawable.remove_cross;
        cell_image.setImageResource(moodDrawable);*/
    }
}