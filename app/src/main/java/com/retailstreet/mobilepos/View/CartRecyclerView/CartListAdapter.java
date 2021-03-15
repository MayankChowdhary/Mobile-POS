package com.retailstreet.mobilepos.View.CartRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.retailstreet.mobilepos.Controller.BillGenerator;
import com.retailstreet.mobilepos.Controller.ControllerCart;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.ApplicationContextProvider;
import com.retailstreet.mobilepos.View.SalesRecyclerView.CustomRecyclerViewAdapter;
import com.retailstreet.mobilepos.View.ui.Cart.CartFragmentDirections;

import java.text.DecimalFormat;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class CartListAdapter extends CustomRecyclerViewAdapter<CartListAdapter.ViewHolder> implements RefreshRecyclerView {


    static HashMap<String, String> orderList = new HashMap<>();
     View myParentLayout;
    public   TextView totalItems_view;
    public   TextView totalPrice_view;
    private static ConstraintLayout checkout_View;
    private  Button checkout_btn;
    private Activity myActivity;
    private static Context context;


    public CartListAdapter(Context context, Cursor cursor , View parentLayout , Activity activity){
        super(context,cursor);
        myParentLayout = parentLayout;
        myActivity = activity;
        this.context =context;
        //ControllerCart.printCart();
        initMap();
        totalItems_view = myParentLayout.findViewById(R.id.total_item);
        totalPrice_view = myParentLayout.findViewById(R.id.total_rupees);
        checkout_View = myParentLayout.findViewById(R.id.checkout_layout);
        checkout_btn = myParentLayout.findViewById(R.id.btn_checkout);
        /*totalPrice_view.setText(getAmountBefore()+" ₹");
        totalItems_view.setText(orderList.size()+" Item(s)");*/




        checkout_btn.setOnClickListener(v -> {

            String totalItem = String.valueOf(orderList.size());
            String amntBefore = getAmountBefore() ;
            String discount = getTotalDiscount();
            String gst = getTotalGST();
            String grand = String.valueOf(Double.parseDouble(amntBefore)-Double.parseDouble(discount)+Double.parseDouble(gst));

            CartFragmentDirections.ActionNavCartToCheckoutFragment action= CartFragmentDirections.actionNavCartToCheckoutFragment(totalItem, amntBefore, discount,gst,grand);
            Navigation.findNavController(myActivity,R.id.nav_host_fragment).navigate(action);

        });

    }

    @Override
    public void refreshIt(View v) {
        notifyItemRemoved(getmRecyclerView().getChildAdapterPosition(v));

    }

    @Override
    public void swapCursors(Cursor cursor) {
        swapCursor(  new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor());

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
        public TextView product_detail_4;
        public TextView product_detail_5;
        public ImageButton add_order;
        public ImageButton clear_order;
        public  ImageButton remove_order;
        public  TextView order_count;
        public TextView sales_qty;
        public  View root;
        CartListItem myListItem;
        public RefreshRecyclerView refreshRecyclerView;

        public ViewHolder(View view, RefreshRecyclerView refreshView) {
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
            sales_qty = view.findViewById(R.id.sales_qty_cart);
            root = view;
            this.refreshRecyclerView = refreshView;
           new Thread(() -> {
                String AmountB4 = getAmountBefore()+" ₹";
                view.post(() -> {
                            refreshRecyclerView.setCheckout(AmountB4, orderList.size() + " Item(s)");
                        }
                );
            }).start();


            add_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("Cursorclick", "onClick: "+myListItem.getPrimary());
                    TextView countorder =order_count;
                    String countText=countorder.getText().toString();
                    String primary= myListItem.getPrimary();
                    int count= Integer.parseInt(countText);

                    int qty = (int)Double.parseDouble(myListItem.getQty());

                    if(count==qty) {
                        if(count==1)
                            Toast.makeText(ApplicationContextProvider.getContext(),"Sorry only "+qty+" quantity is available!",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(ApplicationContextProvider.getContext(),"Sorry only "+qty+" quantities are available!",Toast.LENGTH_LONG).show();
                        return;
                    }

                    countText = String.valueOf(count+1);
                    countorder.setText(countText);
                    orderList.put(primary, countText);
                    putCartData(primary,myListItem.getName(),countText,myListItem.getProduct_detail_2(),myListItem.getProduct_detail_4(),myListItem.getProduct_detail_3(),myListItem.getGst(),myListItem.getSgst(),myListItem.getCgst());

                    if(checkout_View.getVisibility() == View.GONE){
                        checkout_View.setVisibility(View.VISIBLE);
                    }
                    refreshRecyclerView.setCheckout(getAmountBefore()+" ₹",orderList.size()+" Item(s)");

             /*   for (String i : orderList.keySet()) {
                    System.out.println("key: " + i + " value: " + orderList.get(i));
                    System.out.println("Size: "+orderList.size());
                }*/
                }
            });


            remove_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TextView countorder =order_count;
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

                        refreshRecyclerView.refreshIt(root);
                        orderList.remove(primary);
                        countText = String.valueOf(count-1);
                        countorder.setText(countText);
                        deletefromCart(primary);
                        Cursor cursor1 = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            refreshRecyclerView.swapCursors(cursor1);
                            if(cursor1==null){
                                refreshRecyclerView.setRecyclerVisibility(View.GONE);
                                refreshRecyclerView.emptyCartTextVisibility(View.VISIBLE);
                                checkout_View.setVisibility(View.GONE);
                            }else{
                                checkout_View.setVisibility(View.VISIBLE);
                            }


                        }, 400);


                    }else {
                        refreshRecyclerView.refreshIt(root);
                        countorder.setText("0");
                        orderList.remove(primary);
                        deletefromCart(primary);
                        Cursor cursor1 = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
                        new Handler(Looper.getMainLooper()).postDelayed(() -> {
                            refreshRecyclerView.swapCursors(cursor1);
                            if(cursor1==null){
                                refreshRecyclerView.setRecyclerVisibility(View.GONE);
                                refreshRecyclerView.emptyCartTextVisibility(View.VISIBLE);
                                checkout_View.setVisibility(View.GONE);
                            }else{
                                checkout_View.setVisibility(View.VISIBLE);
                            }


                        }, 400);
                    }
                    refreshRecyclerView.setCheckout(getAmountBefore()+" ₹",orderList.size()+" Item(s)");

               /* for (String i : orderList.keySet()) {
                    System.out.println("key: " + i + " value: " + orderList.get(i));
                     System.out.println("Size: "+orderList.size());
                }*/
                }
            });


            clear_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    refreshRecyclerView.refreshIt(root);
                    String primary= myListItem.getPrimary();
                    orderList.remove(primary);
                    deletefromCart(primary);
                    Cursor cursor1 = new ControllerCart(ApplicationContextProvider.getContext()).getCartCursor();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        refreshRecyclerView.swapCursors(cursor1);
                        if(cursor1==null){
                            refreshRecyclerView.setRecyclerVisibility(View.GONE);
                           refreshRecyclerView.emptyCartTextVisibility(View.VISIBLE);
                            checkout_View.setVisibility(View.GONE);
                        }else{
                            checkout_View.setVisibility(View.VISIBLE);
                        }

                    }, 400);

                    refreshRecyclerView.setCheckout(getAmountBefore()+" ₹",orderList.size()+" Item(s)");

                }
            });

        }

        public void setItem(Cursor cursor) {
            this.myListItem = CartListItem.fromCursor(cursor);

            String mrp ="MRP: "+myListItem.getProduct_detail_2()+" ₹";
            String sp ="Price: "+myListItem.getProduct_detail_4()+" ₹";
            double discount = Double.parseDouble(myListItem.getProduct_detail_3());
            String discountString = "Discount: "+myListItem.getProduct_detail_3()+" ₹";
            int qty = (int)Double.parseDouble(myListItem.getQty());

            if(discount==0){
                product_detail_3.setVisibility(View.GONE);
            }else {
                product_detail_3.setText(discountString);
               product_detail_3.setVisibility(View.VISIBLE);
            }
            productTitle.setText(myListItem.getName());
            product_detail_2.setText(mrp);
            product_detail_4.setText(sp);
            product_detail_5.setText(myListItem.getProduct_detail_5());
            sales_qty.setText("Avail: "+qty);


            String oc = orderList.get(myListItem.getPrimary());
            if(oc ==null) {
                order_count.setText("0");
                checkout_View.setVisibility(View.GONE);
                sales_qty.setVisibility(View.INVISIBLE);
            }
            else {
                sales_qty.setVisibility(View.VISIBLE);
                order_count.setText(orderList.get(myListItem.getPrimary()));

            }
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_view_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.setItem(cursor);

    }


    public static void putCartData(String id,String PROD_NM,String count,String MRP, String S_PRICE,String SALESDISCOUNTBYAMOUNT,String GST, String SGST, String CGST){
        try {
            String query = "INSERT INTO cart (STOCK_ID,PROD_NM,count,MRP,S_PRICE,SALESDISCOUNTBYAMOUNT,GST,SGST,CGST ) VALUES('"+id+"', '"+PROD_NM+"','"+count+"','"+MRP+"', '"+S_PRICE+"','"+SALESDISCOUNTBYAMOUNT+"','"+GST+"','"+SGST+"','"+CGST+"');";
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();

        } catch (SQLException e) {
            try {
                SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
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
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            db.delete("cart", "STOCK_ID" + "=" + key, null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static  String getAmountBefore(){
        try {
            SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select STOCK_ID, SGST,CGST, count,S_PRICE from cart";
            Cursor result = db.rawQuery( query, null );

            if(result==null)
                return "0";

            double total = 0.0; // Your default if none is found
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                String  sgst =result.getString(1);
                String cgst = result.getString(2);
                String price = result.getString(4);
                String count = result.getString(3);
                String netval =  getNetValue(sgst,cgst,price,count);
                total += Double.parseDouble(netval);
            }
            result.close();
            db.close();
            return   new DecimalFormat("#.##").format(total);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getNetValue( String cgst, String sgst,String price, String count){
        try {

            double gstD = Double.parseDouble(cgst)+Double.parseDouble(sgst);
            double total = 0.0; // Your default if none is found
                double itemprice = Double.parseDouble(price);
                itemprice = itemprice-((itemprice*gstD)/100);
                int itemcount= Integer.parseInt(count);
                total += itemprice*itemcount;
            return String.valueOf(total);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }
    public String getTotalDiscount(){
        double totalDis =0.00;

        for (String key: orderList.keySet()) {

            String dis = new BillGenerator().getDiscountValue(key);
            totalDis += Double.parseDouble(dis);
        }
        return String.valueOf(totalDis);

    }
    public String getTotalGST(){
        double totalGST =0.00;

        for (String key: orderList.keySet()) {

            double GST = Double.parseDouble( getCGST(key))+ Double.parseDouble(getSGST(key));
            totalGST += GST;
        }

        return String.valueOf(totalGST);
    }

    public  boolean isCartEmpty(){
        SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        String query = "SELECT * FROM cart";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count <= 0;
    }

    public void EmptyCart(){
        SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        db.execSQL("delete from cart");
        orderList.clear();
        getmRecyclerView().setVisibility(View.GONE);
        myParentLayout.findViewById(R.id.empty_cart_view).setVisibility(View.VISIBLE);
        checkout_View.setVisibility(View.GONE);
        db.close();
    }

    private void initMap(){
        orderList.clear();
        SQLiteDatabase db = context.openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        Cursor cursor  = db.rawQuery("SELECT * FROM cart", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(0);
                String cnt = cursor.getString(2);
                orderList.put(id,cnt);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
    }

    public String getSGST(String stockID){
        SQLiteDatabase mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String query = "select SGST,S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
        Cursor result = mydb.rawQuery( query, null );

        if(result==null)
            return "0";

        double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

            double itemSgst = Double.parseDouble(result.getString(0));
            double itemPrice = Double.parseDouble(result.getString(1));
            double sgstForone= ((itemSgst*itemPrice)/100);
            int itemcount= Integer.parseInt(result.getString(2));
            total = sgstForone*itemcount;

        }
        result.close();
        mydb.close();
        return String.valueOf(total);
    }

    public  String getCGST( String stockID){
        SQLiteDatabase  mydb  = context.openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
        String query = "select CGST,S_PRICE, count from cart WHERE STOCK_ID = "+stockID;
        Cursor result = mydb.rawQuery( query, null );

        if(result==null)
            return "0";

        double total = 0.0; // Your default if none is found
        for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

            double itemSgst = Double.parseDouble(result.getString(0));
            double itemPrice = Double.parseDouble(result.getString(1));
            double sgstForone= ((itemSgst*itemPrice)/100);
            int itemcount= Integer.parseInt(result.getString(2));
            total = sgstForone*itemcount;

        }
        result.close();
        mydb.close();
        return String.valueOf(total);

    }
}