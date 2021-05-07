package com.retailstreet.mobilepos.View.CartRecyclerView;

import android.database.Cursor;
import android.view.View;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public interface RefreshRecyclerView {
    void refreshIt(View v);
    void swapCursors(Cursor cursor);
    void setCheckout(String totalPrice, String totalItems);
    void setRecyclerVisibility(int visibility);
    void emptyCartTextVisibility(int visibility);
    void refreshAll();

}