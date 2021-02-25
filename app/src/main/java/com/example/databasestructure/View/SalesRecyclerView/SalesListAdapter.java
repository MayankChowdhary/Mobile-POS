package com.example.databasestructure.View.SalesRecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databasestructure.R;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class SalesListAdapter extends CustomRecyclerViewAdapter<SalesListAdapter.ViewHolder> {

    public SalesListAdapter(Context context, Cursor cursor){
        super(context,cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productTitle;
        public TextView product_detail_2;
        public TextView product_detail_3;

        public ViewHolder(View view) {
            super(view);
            productTitle = view.findViewById(R.id.product_title);
            product_detail_2=view.findViewById(R.id.product_detail_II);
            product_detail_3=view.findViewById(R.id.product_detail_III);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_list_view_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        SalesListItem myListItem = SalesListItem.fromCursor(cursor);
        viewHolder.productTitle.setText(myListItem.getName());
        viewHolder.product_detail_2.setText(myListItem.getProduct_detail_2());
        viewHolder.product_detail_3.setText(myListItem.getProduct_detail_3());
    }
}