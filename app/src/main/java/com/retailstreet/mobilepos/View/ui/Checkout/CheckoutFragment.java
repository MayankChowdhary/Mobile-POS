package com.retailstreet.mobilepos.View.ui.Checkout;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.Utils.Vibration;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
@SuppressWarnings("unchecked")
public class CheckoutFragment extends Fragment {

    private CheckoutViewModel checkoutViewModel;
     StringWithTag spinnerSelected;
     private TextView totalItemView;
    private TextView amountBeforeView;
    private TextView totalSgstView;
    private TextView totalCgstView;
    private TextView grandTotalView;
    private Button payement;
    private  List<StringWithTag> customerNames;
    private  String customerId=" ";
    private  String DeliveryGuidString=" ";
    private  List<StringWithTag> deliveryOptionsArray;
    private ConstraintLayout addressLayout;
    private  Button change_add;
    private EditText addDiscEditText;
    private TextView addressText;

    String totalItems;
    String amntBefore;
    String disc;
    String gst;
    String grand;
    String addDiscount = "0.00";
    String totalAmount = "0.00";
    String creditBalance="0.00";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        checkoutViewModel = new ViewModelProvider(this).get(CheckoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_checkout, container, false);
        spinnerSelected = new StringWithTag("","");
        totalItemView = root.findViewById(R.id.item_value);
        amountBeforeView= root.findViewById(R.id.amount_before_value);
        totalSgstView = root.findViewById(R.id.sgst_value);
        totalCgstView= root.findViewById(R.id.cgst_value);
        grandTotalView = root.findViewById(R.id.grand_value);
        payement = root.findViewById(R.id.payment_btn);
        addressLayout= root.findViewById(R.id.address_layout);
        change_add = root.findViewById(R.id.change_add);
        addressLayout.setVisibility(View.GONE);
        addDiscEditText = root.findViewById(R.id.add_discount_value);
        addressText = root.findViewById(R.id.addr_text);





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
                CheckoutFragmentDirections.ActionNavCheckoutToPayment actionNavCheckoutToPayment= CheckoutFragmentDirections.actionNavCheckoutToPayment(totalAmount,customerId,DeliveryGuidString,false,creditBalance,addDiscount);
                Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(actionNavCheckoutToPayment);

            }
        });

        change_add.setOnClickListener(v -> Toast.makeText(getContext(),"Not Yet Implemented !",Toast.LENGTH_SHORT).show());

        SearchableSpinner spinner = root.findViewById(R.id.customer_selector);
        Spinner deliveryOptions = root.findViewById(R.id.deliveryOptSpinner);

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
                creditBalance = getCustBalance(customerId);
               creditBalance = String.valueOf( Math.abs(Double.parseDouble(creditBalance)));
               addressText.setText(getCustAddress(getCustID(customerId)));
                Log.d("SpinnerSelected", "onItemSelected: Tag= "+customerId);

                deliveryOptions.setEnabled(!customerId.trim().isEmpty());


            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        // Create the instance of ArrayAdapter
        // having the list of courses
        deliveryOptionsArray = getDeliveryOptions();
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
                grandTotalView.setText(totalAmount+" ₹");
                if(s.toString().trim().isEmpty()){
                    return;
                }
                try {
                    if( Double.parseDouble(s.toString())<Double.parseDouble(grand)) {
                        addDiscount = s.toString();
                        totalAmount = String.valueOf(Double.parseDouble(totalAmount)-Double.parseDouble(s.toString()));
                    }else {
                        totalAmount = grand;
                        addDiscount = "0.00";
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                    grandTotalView.setText(totalAmount+" ₹");
                } catch (NumberFormatException e) {
                    totalAmount = grand;
                    addDiscount = "0.00";
                    grandTotalView.setText(totalAmount+" ₹");
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

        CheckoutFragmentArgs myArgs =CheckoutFragmentArgs.fromBundle(requireArguments());
         totalItems=myArgs.getTotalItems();
         amntBefore=myArgs.getAmountBefore()+" ₹";
         disc=myArgs.getDiscount()+" ₹";
         gst=myArgs.getGst()+" ₹";
         grand=myArgs.getGrandTotal();
         totalAmount = grand;

        totalItemView.setText(totalItems);
        amountBeforeView.setText(amntBefore);
        totalSgstView.setText(disc);
        totalCgstView.setText(gst);
        grandTotalView.setText(grand+" ₹");

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

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

}
