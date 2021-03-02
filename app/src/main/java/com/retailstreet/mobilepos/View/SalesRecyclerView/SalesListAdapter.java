package com.retailstreet.mobilepos.View.SalesRecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.retailstreet.mobilepos.Controller.ControllerCart;
import com.retailstreet.mobilepos.Database.SQLiteDbInspector;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.HashMap;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class SalesListAdapter extends CustomRecyclerViewAdapter<SalesListAdapter.ViewHolder> {

    static SQLiteDatabase mydb;
    static HashMap<String, String> orderList = new HashMap<>();

    public SalesListAdapter(Context context, Cursor cursor){
        super(context,cursor);
        mydb = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        //SQLiteDbInspector.PrintTableSchema(ApplicationContextProvider.getContext(),"MasterDB");
       // EmptyCart();
       initMap();
        Log.d("SalesRecyclerInvoked", "SalesListAdapter: ");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productTitle;
        public TextView product_detail_2;
        public TextView product_detail_3;
        public TextView product_detail_4;
        public ImageButton add_order;
        public  ImageButton remove_order;
        public  TextView order_count;
        public Button add_to_cart;



        public ViewHolder(View view) {
            super(view);
            productTitle = view.findViewById(R.id.product_title);
            product_detail_2=view.findViewById(R.id.product_detail_II);
            product_detail_3=view.findViewById(R.id.product_detail_III);
            product_detail_4=view.findViewById(R.id.product_detail_IV);
            add_order=view.findViewById(R.id.btn_order_add);
            remove_order=view.findViewById(R.id.btn_order_remove);
            order_count = view.findViewById(R.id.textview_order_count);
            add_to_cart = view.findViewById(R.id.add_cart_botton);


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
        String mrp ="MRP: "+myListItem.getProduct_detail_2()+" ₹";
        String sp ="Price: "+myListItem.getProduct_detail_4()+" ₹";
        double discount = Double.parseDouble(myListItem.getProduct_detail_3());
        String discountString = "Discount: "+myListItem.getProduct_detail_3()+" ₹";
        if(discount==0){
            viewHolder.product_detail_3.setVisibility(View.GONE);
        }else {
            viewHolder.product_detail_3.setText(discountString);
        }

        viewHolder.productTitle.setText(myListItem.getName());
        viewHolder.product_detail_2.setText(mrp);
        viewHolder.product_detail_4.setText(sp);

        String oc = orderList.get(myListItem.getPrimary());
        if(oc ==null) {
            viewHolder.order_count.setText("0");
            viewHolder.add_to_cart.setVisibility(View.VISIBLE);
        }
      else {
            viewHolder.order_count.setText(orderList.get(myListItem.getPrimary()));
            viewHolder.add_to_cart.setVisibility(View.GONE);
        }

        viewHolder.add_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Cursorclick", "onClick: "+myListItem.getPrimary());

                TextView countorder =viewHolder.order_count;
                String countText=countorder.getText().toString();
                String primary= myListItem.getPrimary();
                int count= Integer.parseInt(countText);
                countText = String.valueOf(count+1);

                countorder.setText(countText);
                orderList.put(primary, countText);
                putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3());

                for (String i : orderList.keySet()) {
                    System.out.println("key: " + i + " value: " + orderList.get(i));
                    System.out.println("Size: "+orderList.size());
                }
            }
        });

        viewHolder.remove_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView countorder =viewHolder.order_count;
                String countText=countorder.getText().toString();
                String primary= myListItem.getPrimary();
                int count= Integer.parseInt(countText);

                Log.d("Cursorclick", "onClick: "+myListItem.getPrimary());

                if (count>1) {
                    countText = String.valueOf(count-1);
                    countorder.setText(countText);
                    orderList.put(primary, countText);
                    putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3());

                }else if(count==1) {
                    orderList.remove(primary);
                    countText = String.valueOf(count-1);
                    countorder.setText(countText);
                    deletefromCart(primary);
                    viewHolder.add_to_cart.setVisibility(View.VISIBLE);
                }else {
                    countorder.setText("0");
                    orderList.remove(primary);
                    deletefromCart(primary);
                    viewHolder.add_to_cart.setVisibility(View.VISIBLE);
                }

               /* for (String i : orderList.keySet()) {
                    System.out.println("key: " + i + " value: " + orderList.get(i));
                     System.out.println("Size: "+orderList.size());
                }*/
            }
        });

        viewHolder.add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView countorder =viewHolder.order_count;
                String countText=countorder.getText().toString();
                String primary= myListItem.getPrimary();
                int count= Integer.parseInt(countText);
                countText = String.valueOf(count+1);

                countorder.setText(countText);
                orderList.put(primary, countText);
                putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3());
                v.setVisibility(View.GONE);

            }
        });
    }

    public  void putCartData(String id,String PROD_NM,String count,String MRP, String S_PRICE,String SALESDISCOUNTBYAMOUNT){
        try {
            String query = "INSERT INTO cart (STOCK_ID,PROD_NM,count,MRP,S_PRICE,SALESDISCOUNTBYAMOUNT ) VALUES('"+id+"', '"+PROD_NM+"','"+count+"','"+MRP+"', '"+S_PRICE+"','"+SALESDISCOUNTBYAMOUNT+"');";

            mydb.execSQL(query);
        } catch (SQLException e) {
            String strSQL = "UPDATE cart SET count = "+count+" WHERE STOCK_ID = "+ id;
            mydb.execSQL(strSQL);
        }
    }
    public void deletefromCart(String key){
        try {
            mydb.delete("cart", "STOCK_ID" + "=" + key, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void EmptyCart(){

        mydb.execSQL("delete from cart");
    }

    private void initMap(){
        orderList.clear();
        Cursor cursor  = mydb.rawQuery("SELECT * FROM cart", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(0);
                String cnt = cursor.getString(2);
                orderList.put(id,cnt);
                cursor.moveToNext();
            }
        }
    }
}