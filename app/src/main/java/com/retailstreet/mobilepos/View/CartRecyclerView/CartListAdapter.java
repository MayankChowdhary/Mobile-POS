package com.retailstreet.mobilepos.View.CartRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.retailstreet.mobilepos.Controller.ControllerCart;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.SalesRecyclerView.CustomRecyclerViewAdapter;
import com.retailstreet.mobilepos.View.ui.Cart.CartFragment;

import java.util.HashMap;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class CartListAdapter extends CustomRecyclerViewAdapter<CartListAdapter.ViewHolder> {

    static SQLiteDatabase mydb;
    static HashMap<String, String> orderList = new HashMap<>();
     View myParentLayout;
    public  TextView totalItems_view;
    public  TextView totalPrice_view;
    private ConstraintLayout checkout_View;
    private Button checkout_btn;
    private StringWithTag spinnerData;
    private Activity myActivity;
    public CartListAdapter(Context context, Cursor cursor , View parentLayout , Activity activity){
        super(context,cursor);
        myParentLayout = parentLayout;
        myActivity = activity;
        mydb = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        //ControllerCart.printCart();
        initMap();
        totalItems_view = myParentLayout.findViewById(R.id.total_item);
        totalPrice_view = myParentLayout.findViewById(R.id.total_rupees);
        checkout_View = myParentLayout.findViewById(R.id.checkout_layout);
        checkout_btn = myParentLayout.findViewById(R.id.btn_checkout);
        totalPrice_view.setText(getTotalPrice()+" ₹");
        totalItems_view.setText(orderList.size()+" Item(s)");

        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Navigation.findNavController(myActivity,R.id.nav_host_fragment).navigate(R.id.action_nav_cart_to_checkoutFragment);

            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productTitle;
        public TextView product_detail_2;
        public TextView product_detail_3;
        public TextView product_detail_4;
        public TextView product_detail_5;
        public ImageButton add_order;
        public ImageButton clear_order;
        public  ImageButton remove_order;
        public  TextView order_count;


        public ViewHolder(View view) {
            super(view);
            productTitle = view.findViewById(R.id.product_title);
            product_detail_2=view.findViewById(R.id.product_detail_II);
            product_detail_3=view.findViewById(R.id.product_detail_III);
            product_detail_4=view.findViewById(R.id.product_detail_IV);
            product_detail_5=view.findViewById(R.id.product_detail_V);
            add_order=view.findViewById(R.id.btn_order_add);
            remove_order=view.findViewById(R.id.btn_order_remove);
            clear_order=view.findViewById(R.id.clear_order);
            order_count = view.findViewById(R.id.textview_order_count);



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_view_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        CartListItem myListItem = CartListItem.fromCursor(cursor);

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
        viewHolder.product_detail_5.setText(myListItem.getProduct_detail_5());


        String oc = orderList.get(myListItem.getPrimary());
        if(oc ==null) {
            viewHolder.order_count.setText("0");
            checkout_View.setVisibility(View.GONE);
        }
      else {
            viewHolder.order_count.setText(orderList.get(myListItem.getPrimary()));
            totalPrice_view.setText(getTotalPrice()+" ₹");
            totalItems_view.setText(orderList.size()+" Item(s)");
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
                putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3(),myListItem.getGst(),myListItem.getSgst(),myListItem.getCgst());

                if(checkout_View.getVisibility() == View.GONE){
                    checkout_View.setVisibility(View.VISIBLE);
                }
                totalPrice_view.setText(getTotalPrice()+" ₹");
                totalItems_view.setText(orderList.size()+" Item(s)");

             /*   for (String i : orderList.keySet()) {
                    System.out.println("key: " + i + " value: " + orderList.get(i));
                    System.out.println("Size: "+orderList.size());
                }*/
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
                    putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3(),myListItem.getGst(),myListItem.getSgst(),myListItem.getCgst());

                }else if(count==1) {
                    orderList.remove(primary);
                    countText = String.valueOf(count-1);
                    countorder.setText(countText);
                    deletefromCart(primary);
                    Cursor cursor1 = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
                    swapCursor( cursor1);
                    if(cursor1==null){
                        getmRecyclerView().setVisibility(View.GONE);
                        myParentLayout.findViewById(R.id.empty_cart_text).setVisibility(View.VISIBLE);
                        checkout_View.setVisibility(View.GONE);                      }
                }else {
                    countorder.setText("0");
                    orderList.remove(primary);
                    deletefromCart(primary);
                    swapCursor(  new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor());
                    Cursor cursor1 = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
                    swapCursor( cursor1);
                    if(cursor1==null){
                        getmRecyclerView().setVisibility(View.GONE);
                        myParentLayout.findViewById(R.id.empty_cart_text).setVisibility(View.VISIBLE);
                        checkout_View.setVisibility(View.GONE);                    }
                }
                totalPrice_view.setText(getTotalPrice()+" ₹");
                totalItems_view.setText(orderList.size()+" Item(s)");

               /* for (String i : orderList.keySet()) {
                    System.out.println("key: " + i + " value: " + orderList.get(i));
                     System.out.println("Size: "+orderList.size());
                }*/
            }
        });

        viewHolder.clear_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String primary= myListItem.getPrimary();
                orderList.remove(primary);
                deletefromCart(primary);
                Cursor cursor1 = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
                swapCursor( cursor1);
                if(cursor1==null){
                    getmRecyclerView().setVisibility(View.GONE);
                    myParentLayout.findViewById(R.id.empty_cart_text).setVisibility(View.VISIBLE);
                    checkout_View.setVisibility(View.GONE);                      }
            }
        });
    }


    public  void putCartData(String id,String PROD_NM,String count,String MRP, String S_PRICE,String SALESDISCOUNTBYAMOUNT,String GST, String SGST, String CGST){
        try {
            String query = "INSERT INTO cart (STOCK_ID,PROD_NM,count,MRP,S_PRICE,SALESDISCOUNTBYAMOUNT,GST,SGST,CGST ) VALUES('"+id+"', '"+PROD_NM+"','"+count+"','"+MRP+"', '"+S_PRICE+"','"+SALESDISCOUNTBYAMOUNT+"','"+GST+"','"+SGST+"','"+CGST+"');";

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


    public String getTotalPrice(){
            String query = "select S_PRICE, count from cart";
            Cursor result = mydb.rawQuery( query, null );

             if(result==null)
                 return "0";

            double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                double itemprice = Double.parseDouble(result.getString(0));
                int itemcount= Integer.parseInt(result.getString(1));
                    total += itemprice*itemcount;
            }
            result.close();
            return String.valueOf(total);
    }

    public void EmptyCart(){
        mydb.execSQL("delete from cart");
        orderList.clear();
        getmRecyclerView().setVisibility(View.GONE);
        myParentLayout.findViewById(R.id.empty_cart_text).setVisibility(View.VISIBLE);
        checkout_View.setVisibility(View.GONE);
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