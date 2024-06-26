package com.retailstreet.mobilepos.View.SalesReturnRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.retailstreet.mobilepos.Controller.ControllerCustomerReturn;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.CartRecyclerView.RefreshRecyclerView;
import com.retailstreet.mobilepos.View.SalesRecyclerView.CustomRecyclerViewAdapter;

import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
public class SalesReturnListAdapter2 extends CustomRecyclerViewAdapter<SalesReturnListAdapter2.ViewHolder> implements RefreshRecyclerView {


    static HashMap<String, String> returnList = new HashMap<>();
     View myParentLayout;
    public   TextView totalItems_view;
    public   TextView totalPrice_view;
    private Button returnSubmit;
    private static ConstraintLayout returnSubmitView;
    private Activity myActivity;
    private static Context context;


    public SalesReturnListAdapter2(Context context, Cursor cursor , View parentLayout , Activity activity){
        super(context,cursor);
        myParentLayout = parentLayout;
        myActivity = activity;
        this.context =context;
        //ControllerCart.printCart();
        totalItems_view = myParentLayout.findViewById(R.id.total_item);
        totalPrice_view = myParentLayout.findViewById(R.id.total_rupees);
        returnSubmitView = myParentLayout.findViewById(R.id.return_submit_layout);
        returnSubmit = myParentLayout.findViewById(R.id.btn_return_submit);
        totalPrice_view.setText(getAmountTotal());
        totalItems_view.setText(getCursorCount()+ " Item(s)");


    }

    @Override
    public void refreshIt(View v) {
        notifyItemRemoved(getmRecyclerView().getChildAdapterPosition(v));

    }

    @Override
    public void swapCursors(Cursor cursor) {
        swapCursor(  new ControllerCustomerReturn().refreshNoBillCursor());
        totalPrice_view.setText(getAmountTotal());
        totalItems_view.setText(getCursorCount()+ " Item(s)");

    }

    @Override
    public void refreshAll() {
    }

    @Override
    public void setCheckout(String totalPrice, String totalItems) {
        totalPrice_view.setText(totalPrice);
        totalItems_view.setText(totalItems);
    }

    @Override
    public void setRecyclerVisibility(int visibility) {
        getmRecyclerView().setVisibility(visibility);
    }

    @Override
    public void emptyCartTextVisibility(int visibility) {
        myParentLayout.findViewById(R.id.empty_cart_view).setVisibility(visibility);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productTitle;
        public TextView product_detail_2;
        public TextView product_detail_3;
        public TextView product_detail_5;
        public  TextView qtyView;
        public ImageButton add_order;
        public ImageButton clear_order;
        public  ImageButton remove_order;
        int cursorCount =0;
        public View root;
        SalesReturnListItem2 myListItem;

        public RefreshRecyclerView refreshRecyclerView;

        public ViewHolder(View view, RefreshRecyclerView refreshView) {
            super(view);
            add_order=view.findViewById(R.id.btn_order_add);
            remove_order=view.findViewById(R.id.btn_order_remove);
            productTitle = view.findViewById(R.id.product_title);
            product_detail_2 = view.findViewById(R.id.product_detail_II);
            product_detail_3 = view.findViewById(R.id.product_detail_III);
            product_detail_5 = view.findViewById(R.id.product_detail_V);
            qtyView = view.findViewById(R.id.textview_order_count);
            clear_order=view.findViewById(R.id.clear_order);
            root = view;
            this.refreshRecyclerView = refreshView;

            remove_order.setOnClickListener(v -> {

                TextView countorder =qtyView;
                String countText=countorder.getText().toString();
                String primary= myListItem.getPrimary();
                int count= Integer.parseInt(countText);

                Log.d("Cursorclick", "onClick: "+myListItem.getPrimary());

                if (count>1) {
                    countText = String.valueOf(count-1);
                    countorder.setText(countText);
                    upDateReturnData(primary,countText);

                }else if(count==1) {


                    refreshRecyclerView.refreshIt(root);
                    countText = String.valueOf(count-1);
                    countorder.setText(countText);
                    deletefromList(primary);
                    Cursor cursor1 = new ControllerCustomerReturn().refreshNoBillCursor();                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        refreshRecyclerView.swapCursors(cursor1);
                        if(cursor1==null){
                            returnSubmitView.setVisibility(View.GONE);
                        }else{
                            returnSubmitView.setVisibility(View.VISIBLE);
                        }

                    }, 500);


                }

                refreshRecyclerView.setCheckout(getAmountTotal(), cursorCount+ " Item(s)");
                Cursor cursor1 = new ControllerCustomerReturn().refreshNoBillCursor();
                refreshRecyclerView.swapCursors(cursor1);
            });

            add_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Cursorclick", "onClick: "+myListItem.getPrimary());
                    TextView countorder =qtyView;
                    String countText=countorder.getText().toString();
                    String primary= myListItem.getPrimary();
                    int count= Integer.parseInt(countText);
                    countText = String.valueOf(count+1);
                    countorder.setText(countText);
                    upDateReturnData(primary,countText);
                    refreshRecyclerView.setCheckout(getAmountTotal(), cursorCount+ " Item(s)");
                    Cursor cursor1 = new ControllerCustomerReturn().refreshNoBillCursor();
                    refreshRecyclerView.swapCursors(cursor1);
                }

            });



            clear_order.setOnClickListener(v -> {
                refreshRecyclerView.refreshIt(root);
                String primary= myListItem.getPrimary();
                deletefromList(primary);
                Cursor cursor1 = new ControllerCustomerReturn().refreshNoBillCursor();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    refreshRecyclerView.swapCursors(cursor1);
                    if(cursor1==null){
                        returnSubmitView.setVisibility(View.GONE);
                    }else{
                        returnSubmitView.setVisibility(View.VISIBLE);
                    }

                }, 500);

                refreshRecyclerView.setCheckout(getAmountTotal(), cursorCount+ " Item(s)");
            });


        }




        public void setItem(Cursor cursor) {
            cursorCount = getCursorCount();
            refreshRecyclerView.setCheckout(getAmountTotal(), cursorCount+ " Item(s)");
            this.myListItem = SalesReturnListItem2.fromCursor(cursor);
            String  qtyRound= String.valueOf((int)(Double.parseDouble(myListItem.getQty())));
            product_detail_3.setText("UOM: "+myListItem.getProduct_detail_3()+"  QTY: "+myListItem.getProduct_detail_4());
            productTitle.setText(myListItem.getName());
            product_detail_2.setText("BARCODE: "+myListItem.getProduct_detail_2());
            product_detail_5.setText(myListItem.getProduct_detail_5());
            qtyView.setText(qtyRound);
            qtyView.setSelected(true);


        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sales_return_list_view_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.setItem(cursor);



    }

    public static void deletefromList(String key){
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            db.delete("tmp_sr_no_bill", "STOCK_ID = '" + key +"'", null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void upDateReturnData(String id,String count){
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String strSQL = "UPDATE tmp_sr_no_bill SET QTY = "+count+" WHERE STOCK_ID = '"+ id+"'";
            db.execSQL(strSQL);
            db.execSQL("UPDATE tmp_sr_no_bill SET TOTAL = CAST(S_PRICE AS REAL)* CAST(QTY AS REAL) WHERE STOCK_ID = '"+id+"'");
            db.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

    }
    public static  String getAmountTotal(){
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select TOTAL from tmp_sr_no_bill";
            Cursor result = db.rawQuery( query, null );
            double total=0.00;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                String resultString = result.getString(0);
                total += Double.parseDouble(resultString);
            }


            result.close();
            db.close();
            return   new DecimalFormat("#0.00").format(total);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getCursorCount(){
        SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        return (int)DatabaseUtils.queryNumEntries(db, "tmp_sr_no_bill");
    }

}