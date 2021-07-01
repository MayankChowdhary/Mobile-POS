package com.retailstreet.mobilepos.View.ui.Checkout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.retailstreet.mobilepos.Controller.BillGenerator;
import com.retailstreet.mobilepos.Controller.ControllerStoreConfig;
import com.retailstreet.mobilepos.Controller.ControllerStoreParams;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.DecimalRounder;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.Utils.Vibration;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
@SuppressWarnings("unchecked")
public class CheckoutFragment extends Fragment {

    private CheckoutViewModel checkoutViewModel;
    static HashMap<String, String> orderList = new HashMap<>();
    StringWithTag spinnerSelected;
    private TextView totalItemView;
    private TextView amountBeforeView;
    private TextView totalSgstView;
    private TextView totalCgstView;
    private TextView grandTotalView;
    private Button payement;
    private  List<StringWithTag> customerNames;
    private  List<String> discOptionArray;
    private  String customerId="";
    private  String DeliveryGuidString=" ";
    private ConstraintLayout addressLayout;
    private  Button change_add;
    private EditText addDiscEditText;
    private TextView addressText;
    private TextView GSTTitle;
    private boolean isIndia;
    private String custGst="";
    private String storeGST="";
    private int discOptIndex=0;
    ControllerStoreConfig config = new ControllerStoreConfig();

    String totalItems;
    String amntBefore;
    String disc;
    String gst;
    String grand;
    String addDiscount = "0.00";
    String totalAmount = "0.00";
    String creditBalance="0.00";

    public static boolean isGstIncluded = true;
    ControllerStoreParams params = new ControllerStoreParams();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_checkout, container, false);
        setHasOptionsMenu(true); // Add this!
        isGstIncluded = params.getIsGSTInclude();
        initMap();
        spinnerSelected = new StringWithTag("","");
        totalItemView = root.findViewById(R.id.item_value);
        amountBeforeView= root.findViewById(R.id.amount_before_value);
        totalSgstView = root.findViewById(R.id.sgst_value);
        totalCgstView= root.findViewById(R.id.cgst_value);
        grandTotalView = root.findViewById(R.id.grand_value);
        payement = root.findViewById(R.id.payment_btn);
        addressLayout= root.findViewById(R.id.address_layout);
        change_add = root.findViewById(R.id.change_add);
        GSTTitle = root.findViewById(R.id.cgst_title);
        addressLayout.setVisibility(View.GONE);
        addDiscEditText = root.findViewById(R.id.add_discount_value);
        addressText = root.findViewById(R.id.addr_text);
        isIndia = config.getIsIndia();
        storeGST = getFromRetailStore("GSTIN_NUMBER");



        LinearLayout gstLayout = root.findViewById(R.id.item_layout_4);
        LinearLayout amntB4Tax = root.findViewById(R.id.item_layout_2);

        if(!isIndia){
            gstLayout.setVisibility(View.GONE);
            amntB4Tax.setVisibility(View.GONE);
        }



        //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*final TextView textView = root.findViewById(R.id.text_slideshow);
        checkoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/


        payement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String  discTemp =addDiscEditText.getText().toString().trim();

               if(!discTemp.isEmpty()){

                   if(Double.parseDouble(discTemp) != Double.parseDouble(addDiscount)){

                       Toast.makeText(getContext(),"Incorrect Addition Discount",Toast.LENGTH_LONG).show();
                       Vibration.Companion.vibrate(300);
                       return;
                   }
               }

                CheckoutFragmentDirections.ActionNavCheckoutToPayment actionNavCheckoutToPayment= CheckoutFragmentDirections.actionNavCheckoutToPayment(totalAmount,customerId,DeliveryGuidString,false,creditBalance,addDiscount);
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(actionNavCheckoutToPayment);

            }
        });

        change_add.setOnClickListener(v -> Toast.makeText(getContext(),"Not Yet Implemented !",Toast.LENGTH_SHORT).show());

        SearchableSpinner spinner = root.findViewById(R.id.customer_selector);
        Spinner deliveryOptions = root.findViewById(R.id.deliveryOptSpinner);
        Spinner discOptions = root.findViewById(R.id.co_disc_selector);

        discOptionArray = Arrays.asList( "BY AMOUNT", "BY PERCENTAGE");
        ArrayAdapter<String> discAdapter = new ArrayAdapter<> (getActivity(), android.R.layout.simple_spinner_item, discOptionArray);
        discAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        discOptions.setAdapter(discAdapter);

        discOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                discOptIndex = position;
                addDiscEditText.setText("");
                Log.d("DiscountSelected", "onItemSelected: Pos = "+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        customerNames = getSpinnerItems();
        ArrayAdapter<StringWithTag> adap = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_layout, customerNames);
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adap);
        spinner.setTitle("Select Customer");
        spinner.setPositiveButton("OK");
        spinner.setGravity(Gravity.START);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                spinnerSelected = (StringWithTag) parent.getItemAtPosition(pos);

                customerId = spinnerSelected.tag;
                creditBalance = DecimalRounder.roundDecimal(2,getCustBalance(customerId));
                custGst = getCustGst(customerId,storeGST);
               creditBalance = String.valueOf( Math.abs(Double.parseDouble(creditBalance)));
               addressText.setText(getCustAddress(getCustID(customerId)));

                if(isNotIGST(custGst,storeGST)){
                    GSTTitle.setText("GST:");
                }else {
                    GSTTitle.setText("IGST:");
                }
                Log.d("SpinnerSelected", "onItemSelected: Tag= "+customerId);

                deliveryOptions.setEnabled(!customerId.trim().isEmpty());


            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        // Create the instance of ArrayAdapter
        // having the list of courses
        List<StringWithTag> deliveryOptionsArray = getDeliveryOptions();
        ArrayAdapter<StringWithTag> deladapt = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_layout, deliveryOptionsArray);
        deladapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        deliveryOptions.setAdapter(deladapt);

        deliveryOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               StringWithTag DeliverySelected = (StringWithTag) parent.getItemAtPosition(position);
                DeliveryGuidString= DeliverySelected.tag;
                if(position!=0) {
                    addressLayout.setVisibility(View.VISIBLE);
                }
                else {
                    addressLayout.setVisibility(View.GONE);
                }

                Log.d("DeliverySelected", "onItemSelected: Tag= "+DeliveryGuidString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addDiscEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                totalAmount = grand;
                addDiscount = "0.00";
                grandTotalView.setText(totalAmount);
                if(s.toString().trim().isEmpty()){
                    return;
                }
                try {
                    if( Double.parseDouble(s.toString())<Double.parseDouble(grand)) {
                        addDiscount = getDiscValue(s.toString());
                        totalAmount = DecimalRounder.roundDecimal(2,Double.parseDouble(totalAmount)-Double.parseDouble(addDiscount));
                    }else {
                        totalAmount = grand;
                        addDiscount = "0.00";
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                    grandTotalView.setText(totalAmount);
                } catch (NumberFormatException e) {
                    totalAmount = grand;
                    addDiscount = "0.00";
                    grandTotalView.setText(totalAmount);
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         totalItems = String.valueOf(orderList.size());
         disc = getTotalDiscount();
         gst = getTotalGST();

        if(isGstIncluded){
            amntBefore = getAmountBefore();
            grand = DecimalRounder.grandRoundOff(Double.parseDouble(getGrandTotal()) -  Double.parseDouble(disc));
        }else {
            amntBefore = getAmountBeforeNoGST();
            grand = DecimalRounder.grandRoundOff((Double.parseDouble(getGrandTotal()) -  Double.parseDouble(disc) )+ Double.parseDouble(gst));
        }



        //grand = DecimalRounder.grandRoundOff(Double.parseDouble(amntBefore) - Double.parseDouble(disc) + Double.parseDouble(gst));

        totalAmount = grand;

        try {
            totalItems = new DecimalFormat("#0.00").format(Double.parseDouble(totalItems));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        totalItemView.setText(totalItems);
        amountBeforeView.setText(amntBefore);
        totalSgstView.setText(disc);
        totalCgstView.setText(gst);
        grandTotalView.setText(grand);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        MenuItem mAddCust = menu.findItem(R.id.addCust);
        mAddCust.setVisible(true);
        mAddCust.getIcon().setTint(Color.parseColor("#ffffff"));

        MenuItem mUpdateCust = menu.findItem(R.id.updateCust);
        mUpdateCust.setVisible(true);
        mUpdateCust.getIcon().setTint(Color.parseColor("#ffffff"));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addCust) {
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.action_nav_checkout_to_nav_customer);
            return true;
        }else if(item.getItemId() == R.id.updateCust){
            CheckoutFragmentDirections.ActionNavCheckoutToNavCustomerUpdate actionCustomerUpdate =CheckoutFragmentDirections.actionNavCheckoutToNavCustomerUpdate(customerId);
            Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(actionCustomerUpdate);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }


    }

    private List<StringWithTag> getSpinnerItems(){
        List<StringWithTag> list = new ArrayList<StringWithTag>();
        list.add(new StringWithTag("No Customer Selected", " "));

        SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select CUSTOMERGUID, MOBILE_NO, NAME from retail_cust",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(0);
                String name = cursor.getString(2) +" - "+ cursor.getString(1);
                list.add(new StringWithTag(name, id));
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
        return  list;
    }


    private List<StringWithTag> getDeliveryOptions(){
        List<StringWithTag> list = new ArrayList<>();
        //list.add(new StringWithTag("No Delivery Type", " "));

        SQLiteDatabase mydb = getActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select DELIVERYTYPE_GUID, DELIVERYTYPE from masterdeliverytype",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(1);
                String name = cursor.getString(0);
                list.add(new StringWithTag(id, name));
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
        return  list;

    }

    private String getCustBalance(String custid) {

        String balance="0.00";
        try {
            SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select  GRANDTOTAL from retail_credit_cust Where CUSTOMERGUID = '"+custid+"'";
            Cursor cursor = mydb.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                    balance = cursor.getString(0);
            }
            if(balance.isEmpty())
                balance= "0.00";
            Log.d("CustomerOneValue", "getCustomerType: Successfully Fetched"+balance);
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            balance = "0.00";
            e.printStackTrace();
        }
        return balance;
    }

    private String getCustGst(String custid,String storeGst) {

        String gstnum="";
        try {
            SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select  GSTNO from retail_cust Where CUSTOMERGUID = '"+custid+"'";
            Cursor cursor = mydb.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                gstnum = cursor.getString(0);
            }
            if(gstnum!=null && gstnum.trim().isEmpty())
                gstnum= storeGst;

            Log.d("CustomerOneValue", "getCustomerGst: Successfully Fetched"+gstnum);
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            gstnum= storeGst;
            e.printStackTrace();
        }
        return gstnum;
    }

    private String getCustID(String custid) {

        String mycustid ="";
        try {
            SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select  CUST_ID from retail_cust Where CUSTOMERGUID = '"+custid+"'";
            Cursor cursor = mydb.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                mycustid = cursor.getString(0);
            }
            if(mycustid.isEmpty())
                mycustid = "";
            Log.d("CustomerOneValue", "getCustomerType: Successfully Fetched"+ mycustid);
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            mycustid = "0.00";
            e.printStackTrace();
        }
        return mycustid;
    }

    private String getCustAddress(String custid) {

        String address ="";
        try {
            SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select  ADDRESSLINE1,ADDRESSLINE2,STREET_AREA,CITY,PINCODE from retail_cust_address Where MASTERCUSTOMERID = '"+custid+"'";
            Cursor cursor = mydb.rawQuery(query, null);
            if (cursor.moveToLast()) {
                address = cursor.getString(0);
                address = address+", "+cursor.getString(1);
                address = address+", "+cursor.getString(2);
                address = address+", "+cursor.getString(3);
                address = address+", "+cursor.getString(4);

            }
            if(address.trim().isEmpty())
                address = "NO ADDRESS FOUND";
            Log.d("CustomerAddress", "getCustomerAddress: Successfully Fetched"+ address);
            cursor.close();
            mydb.close();
        } catch (Exception e) {
            address = "NO ADDRESS FOUND";
            e.printStackTrace();
        }
        return address;
    }

    private void initMap(){
        orderList.clear();
        SQLiteDatabase db = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
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

    public String getTotalDiscount(){
        double totalDis =0.00;

        for (String key: orderList.keySet()) {

            String dis = new BillGenerator().getDiscountValue(key);
            totalDis += Double.parseDouble(dis);
        }
        return new DecimalFormat("#0.00").format(totalDis);

    }
    public String getTotalGST(){
        double totalGST =0.00;

        for (String key: orderList.keySet()) {

            double GST = Double.parseDouble( getCGST(key))+ Double.parseDouble(getSGST(key));
            totalGST += GST;
        }

        return new DecimalFormat("#0.00").format(totalGST);
    }

    public String getSGST(String stockID){
        double total = 0; // Your default if none is found
        try {
            SQLiteDatabase mydb  = requireContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String query = "select SGST,S_PRICE, count from cart WHERE STOCK_ID = '"+stockID+"'";
            Cursor result = mydb.rawQuery( query, null );

            if(result==null)
                return "0";

            total = 0.0;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                double itemSgst = Double.parseDouble(result.getString(0));
                double itemPrice = Double.parseDouble(result.getString(1));
                double sgstForone= ((itemSgst*itemPrice)/100);
                int itemcount= Integer.parseInt(result.getString(2));
                total = sgstForone*itemcount;

            }
            result.close();
            mydb.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(total);
    }

    public  String getCGST( String stockID){
        double total = 0; // Your default if none is found
        try {
            SQLiteDatabase  mydb  = requireContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            String query = "select CGST,S_PRICE, count from cart WHERE STOCK_ID = '"+stockID+"'";
            Cursor result = mydb.rawQuery( query, null );

            if(result==null)
                return "0";

            total = 0.0;
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                double itemSgst = Double.parseDouble(result.getString(0));
                double itemPrice = Double.parseDouble(result.getString(1));
                double sgstForone= ((itemSgst*itemPrice)/100);
                int itemcount= Integer.parseInt(result.getString(2));
                total = sgstForone*itemcount;

            }
            result.close();
            mydb.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return String.valueOf(total);

    }

    public   String getAmountBefore(){
        try {
            SQLiteDatabase db = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
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
            return   new DecimalFormat("#0.00").format(total);

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


    public   String getAmountBeforeNoGST(){
        try {
            SQLiteDatabase db =requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select count,S_PRICE from cart";
            Cursor result = db.rawQuery( query, null );

            if(result==null)
                return "0";

            double total = 0.0; // Your default if none is found
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                String price = result.getString(1);
                String count = result.getString(0);
                total += (Double.parseDouble(price)*Double.parseDouble(count));
            }
            result.close();
            db.close();
            return   new DecimalFormat("#0.00").format(total);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    public   String getGrandTotal(){
        try {
            SQLiteDatabase db =requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
            String query = "select count,S_PRICE from cart";
            Cursor result = db.rawQuery( query, null );

            if(result==null)
                return "0";

            double total = 0.0; // Your default if none is found
            for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {

                String price = result.getString(1);
                String count = result.getString(0);
                total += (Double.parseDouble(price)*Double.parseDouble(count));
            }
            result.close();
            db.close();
            return   new DecimalFormat("#0.00").format(total);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getFromRetailStore( String column){
        String result= null;
        try {
            SQLiteDatabase  mydb  = requireContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            result = "";
            String selectQuery = "SELECT "+column+" FROM retail_store ";
            Cursor cursor = mydb.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {

                result= cursor.getString(0);
            }
            cursor.close();
            mydb.close();
            Log.d("DataRetrieved", "getFromRetailStore: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    private boolean isNotIGST(String custGst, String storeGST){

        try {
            String custGst2 = getFirstTwo(custGst.trim());
            String storeGst2 = getFirstTwo(storeGST.trim());

            return custGst2.equalsIgnoreCase(storeGst2);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    public String getFirstTwo(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }


    public String getDiscValue(String raw){
        try {
            Double value = Double.parseDouble(raw.trim());
            Double totals = Double.parseDouble(grand);
                if(discOptIndex==0){
                    Log.d("Disc1", "getDiscValue: "+raw);
                    return raw;
                }else if(discOptIndex==1) {
                    Double result = (totals*value)/100;
                    Log.d("Disc1", "getDiscValue: "+result);
                    return  DecimalRounder.roundDecimal(2,result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        return raw;
    }
}
