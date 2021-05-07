package com.retailstreet.mobilepos.View.SalesRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.iconics.typeface.library.fontawesome.FontAwesome;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;

import java.util.HashMap;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class SalesListAdapter extends CustomRecyclerViewAdapter<SalesListAdapter.ViewHolder>  {

    Activity myParentActivity;
     static HashMap<String, String> orderList = new HashMap<>();
     Context context;
     public static UpdateRecyclerView updateRecyclerView;


    public SalesListAdapter(Context context, Cursor cursor, Activity myParentActivity, UpdateRecyclerView updateRecyclerViews){
        super(context,cursor);
        this.myParentActivity = myParentActivity;
        this.context=context;
        updateRecyclerView = updateRecyclerViews;
        //SQLiteDbInspector.PrintTableSchema(ApplicationContextProvider.getContext(),"MasterDB");
       // EmptyCart();
       initMap();


        Log.d("SalesRecyclerInvoked", "SalesListAdapter: ");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements Animation.AnimationListener {
        public TextView productTitle;
        public TextView product_detail_2;
        public TextView product_detail_3;
        public TextView product_detail_4;
        public TextView product_detail_V;
        public ImageButton add_order;
        public  ImageButton remove_order;
        public  TextView order_count;
        public Button add_to_cart;
        public TextView sales_qty;
        LinearLayout addRemoveWrapper;
        Animation  FadeIn, FadeOut,FadeInX, FadeOutX;
        SalesListItem myListItem;
        int qty;

        public void setMyListItem(Cursor cursor) {
            this.myListItem = SalesListItem.fromCursor(cursor);

            String mrp ="MRP: "+myListItem.getProduct_detail_2()+" ₹";
            String sp ="Price: "+myListItem.getProduct_detail_4()+" ₹";
            double discount = Double.parseDouble(myListItem.getProduct_detail_3());
            String discountString = "Discount: "+myListItem.getProduct_detail_3()+" ₹";
            qty = (int)Double.parseDouble(myListItem.getQty());
            if(discount==0){
               product_detail_3.setVisibility(View.GONE);
            }else {
                product_detail_3.setText(discountString);
                product_detail_3.setVisibility(View.VISIBLE);
            }

            productTitle.setText(myListItem.getName());
            product_detail_2.setText(mrp);
            product_detail_4.setText(sp);
            product_detail_V.setText(myListItem.getProduct_detail_v());
            sales_qty.setText("Avail: "+qty);



            String oc = orderList.get(myListItem.getPrimary());
            if(oc ==null) {
                order_count.setText("0");
                add_to_cart.setVisibility(View.VISIBLE);
                sales_qty.setVisibility(View.INVISIBLE);
            }
            else {
                sales_qty.setVisibility(View.VISIBLE);
                order_count.setText(orderList.get(myListItem.getPrimary()));
                add_to_cart.setVisibility(View.GONE);
                order_count.setSelected(true);

            }

            if(qty==0){

                sales_qty.setText("Unavailable!");
                sales_qty.setVisibility(View.VISIBLE);
                sales_qty.setTextColor( Color.GRAY);
            }else {
                sales_qty.setTextColor(  Color.parseColor("#303F9F"));
            }
        }


        public ViewHolder(View view) {
            super(view);
            productTitle = view.findViewById(R.id.product_title);
            product_detail_2=view.findViewById(R.id.product_detail_II);
            product_detail_3=view.findViewById(R.id.product_detail_III);
            product_detail_4=view.findViewById(R.id.product_detail_IV);
            product_detail_V=view.findViewById(R.id.product_detail_V);
            sales_qty = view.findViewById(R.id.sales_qty);
            add_order=view.findViewById(R.id.btn_order_add);
            remove_order=view.findViewById(R.id.btn_order_remove);
            order_count = view.findViewById(R.id.textview_order_count);
            add_to_cart = view.findViewById(R.id.add_cart_botton);
            addRemoveWrapper = view.findViewById(R.id.addremovewrapper);
            FadeIn = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_in_left);
            FadeOut = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_out_right);
            FadeInX = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_in_right);
            FadeOutX = AnimationUtils.loadAnimation(view.getContext(),
                    R.anim.slide_out_left);
            FadeIn.setAnimationListener(this);
            FadeOut.setAnimationListener(this);
            FadeInX.setAnimationListener(this);
            FadeOutX.setAnimationListener(this);

          add_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Cursorclick", "onClick: "+myListItem.getPrimary());
                    int qty = (int)Double.parseDouble(myListItem.getQty());
                    TextView countorder =order_count;
                    String countText=countorder.getText().toString();
                    String primary= myListItem.getPrimary();
                    int count= Integer.parseInt(countText);

                    if(count==qty) {
                        if(count==1)
                            Toast.makeText(ApplicationContextProvider.getContext(),"Sorry only "+qty+" quantity is available!",Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ApplicationContextProvider.getContext(),"Sorry only "+qty+" quantities are available!",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    countText = String.valueOf(count+1);
                    countorder.setText(countText);
                    orderList.put(primary, countText);
                    putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3(),myListItem.getGst(),myListItem.getSgst(),myListItem.getCgst(),myListItem.getQty());
                    updateRecyclerView.updateIndicator(orderList.size());
//                for (String i : orderList.keySet()) {
//                    System.out.println("key: " + i + " value: " + orderList.get(i));
//                    System.out.println("Size: "+orderList.size());
//                }


                }
            });

           remove_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String countText=order_count.getText().toString();
                    String primary= myListItem.getPrimary();
                    int count= Integer.parseInt(countText);

                    Log.d("Cursorclick", "onClick: "+myListItem.getPrimary());

                    if (count>1) {
                        countText = String.valueOf(count-1);
                        order_count.setText(countText);
                        orderList.put(primary, countText);
                        putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3(),myListItem.getGst(),myListItem.getSgst(),myListItem.getCgst(),myListItem.getQty());

                    }else if(count==1) {
                        addRemoveWrapper.startAnimation(FadeOutX);
                        add_to_cart.startAnimation(FadeInX);
                        orderList.remove(primary);
                        countText = String.valueOf(count-1);
                        order_count.setText(countText);
                        deletefromCart(primary);
                      sales_qty.setVisibility(View.INVISIBLE);
                        add_to_cart.setVisibility(View.VISIBLE);



                    }else {
                        addRemoveWrapper.startAnimation(FadeOutX);
                        add_to_cart.startAnimation(FadeInX);
                        order_count.setText("0");
                        orderList.remove(primary);
                        deletefromCart(primary);
                        sales_qty.setVisibility(View.INVISIBLE);
                        add_to_cart.setVisibility(View.VISIBLE);


                    }
                    updateRecyclerView.updateIndicator(orderList.size());

               /* for (String i : orderList.keySet()) {
                    System.out.println("key: " + i + " value: " + orderList.get(i));
                     System.out.println("Size: "+orderList.size());
                }*/
                }
            });

            add_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (qty==0){

                        Toast.makeText(ApplicationContextProvider.getContext(),"Sorry Item Unavailable!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    add_to_cart.startAnimation(FadeOut);
                    addRemoveWrapper.startAnimation(FadeIn);
                    String countText=order_count.getText().toString();
                    String primary= myListItem.getPrimary();
                    int count= Integer.parseInt(countText);
                    countText = String.valueOf(count+1);

                    order_count.setText(countText);
                    orderList.put(primary, countText);
                    putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3(),myListItem.getGst(),myListItem.getSgst(),myListItem.getCgst(),myListItem.getQty());
                    // v.setVisibility(View.GONE);
                    updateRecyclerView.updateIndicator(orderList.size());
                    sales_qty.setVisibility(View.VISIBLE);


                }
            });
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == FadeOut) {
                add_to_cart.setVisibility(View.GONE);
            }else if(animation == FadeIn){
                addRemoveWrapper.setVisibility(View.VISIBLE);
            }else if(animation == FadeInX){
                add_to_cart.setVisibility(View.VISIBLE);
            }else if(animation == FadeOutX){
               // addRemoveWrapper.setVisibility(View.GONE);
            }

            add_to_cart.setClickable(true);
            remove_order.setClickable(true);
        }
        @Override
        public void onAnimationRepeat(Animation animation) {
        }
        @Override
        public void onAnimationStart(Animation animation) {
            add_to_cart.setClickable(false);
            remove_order.setClickable(false);
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

        viewHolder.setMyListItem(cursor);

    }

    public static void putCartData(String id,String PROD_NM,String count,String MRP, String S_PRICE,String SALESDISCOUNTBYAMOUNT,String GST, String SGST, String CGST,String Qty){
        try {
            String query = "INSERT INTO cart (STOCK_ID,PROD_NM,count,MRP,S_PRICE,SALESDISCOUNTBYAMOUNT,GST,SGST,CGST,QTY ) VALUES('"+id+"', '"+PROD_NM+"','"+count+"','"+MRP+"', '"+S_PRICE+"','"+SALESDISCOUNTBYAMOUNT+"','"+GST+"','"+SGST+"','"+CGST+"','"+Qty+"');";
            SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
        } catch (SQLException e) {
            try {
                SQLiteDatabase db = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
                String strSQL = "UPDATE cart SET count = "+count+" WHERE STOCK_ID = "+ id;
                db.execSQL(strSQL);
                db.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }
    public static void deletefromCart(String key){
        try {
            SQLiteDatabase  db = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            db.delete("cart", "STOCK_ID" + "=" + key, null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initMap(){
        try {
            orderList.clear();
            SQLiteDatabase  db = ApplicationContextProvider.getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            Cursor cursor  = db.rawQuery("SELECT * FROM cart", null);
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String id = cursor.getString(0);
                    String cnt = cursor.getString(2);
                    orderList.put(id,cnt);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void updateCountIndicator(Menu myMenu){
        int badgeCount = orderList.size();
        if (badgeCount >0 ) {
            ActionItemBadge.update(myParentActivity, myMenu.findItem(R.id.appCart), FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.RED, badgeCount);
        }else {
            ActionItemBadge.update(myParentActivity, myMenu.findItem(R.id.appCart), FontAwesome.Icon.faw_shopping_cart, ActionItemBadge.BadgeStyles.RED, Integer.MIN_VALUE);
        }
    }
}