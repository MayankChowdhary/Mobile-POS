package com.retailstreet.mobilepos.View.ui.Payment;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.Controller.BillGenerator;
import com.retailstreet.mobilepos.Controller.ControllerCreditPay;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.Utils.Vibration;
import com.retailstreet.mobilepos.View.PaymentPagerAdapter.PaymentPagerAdapter;
import com.retailstreet.mobilepos.View.dialog.ClickListeners;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;
import com.retailstreet.mobilepos.View.dialog.MonthYearPickerDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class PaymentFragment extends Fragment implements View.OnClickListener {

    private PaymentViewModel mViewModel;
    private String amountToPay;
    private String customerId;
    private EditText received_amnt;
    private String deliveryGuid;
    private TextView pendingAmountTextView;
    private LinearLayout creditPayLayout;
    private boolean isCreditPay = false;
    private Double pendingAmount;
     double paidByCash = 0.00;
    double paidByCard = 0.00;
    double paidByOnline = 0.00;
    double paidByPaytm = 0.00;
    String paytmTransNo ="";
    double paidByGpay = 0.00;
    String gpayTransNo ="";
    double paidByWhatsapp = 0.00;
    String whatsappTransNo ="";
    double paidByAmazon = 0.00;
    String amazonTransNo ="";
    double paidByBhim = 0.00;
    String bhimTransNo ="";
    String custName = "";
    String cardNumberX="";
    String bankName = "";
    String bankGuid = "";
    String expDate = " ";
    String receivedcash="";
    HashMap<String , String[]> payModeData= new HashMap<String, String[]>();
    HashMap<String ,String> payModeId= new HashMap<String, String>();
    String cardType = "OT";

    String creditBalance="0.00";
    String addDiscount="0.00";
    double paidByCredit = 0.00;
    String isCreditEnabled;
    String creditLimit ;
    String redeemNumber="";
    String redeemMasterAmount="0.00";



    public static PaymentFragment newInstance() {
        return new PaymentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_payment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        PaymentFragmentArgs myArgs =PaymentFragmentArgs.fromBundle(requireArguments());
        amountToPay=myArgs.getAmountToPay();
        customerId=myArgs.getCustomerID();
        deliveryGuid = myArgs.getDelTypeGuid();
        isCreditPay = myArgs.getIsCreditPay();
        creditBalance = myArgs.getCreditBalance();
        addDiscount = myArgs.getAddDiscount();

        if(!customerId.trim().isEmpty()){

            getCreditDetails(customerId);
        }

        Log.d("Credit Balance Received", "onViewCreated: "+creditBalance);
        Log.d("Additional Disc", "onViewCreated: "+addDiscount);
        Log.d("CustGuid Received", "onViewCreated: "+customerId);

        getPayModeID();
       pendingAmountTextView = view.findViewById(R.id.pending_amount_value);
        pendingAmountTextView.setText(amountToPay+" ₹");
        pendingAmount = Double.parseDouble(amountToPay);


        ViewPager payModeViewPager = view.findViewById(R.id.pay_mode_viewpager);
        PaymentPagerAdapter paymentPagerAdapter = new PaymentPagerAdapter();
        paymentPagerAdapter.insertViewId(R.id.pager_cash);
        paymentPagerAdapter.insertViewId(R.id.pager_card);
        paymentPagerAdapter.insertViewId(R.id.pager_online);
        paymentPagerAdapter.insertViewId(R.id.pager_redeem);
        // attach adapter to viewpager
        payModeViewPager.setAdapter(paymentPagerAdapter);
        TabLayout payModeTab = view.findViewById(R.id.pay_mode_tab);
        payModeTab.setupWithViewPager(payModeViewPager);
        payModeViewPager.setOffscreenPageLimit(4);
        initCashLayout(payModeViewPager.getRootView());
        initCardLayout(payModeViewPager.getRootView());
        initOnlinePayLayout(payModeViewPager.getRootView());
        initRedeemLayout(payModeViewPager.getRootView());

        Button payButton = view.findViewById(R.id.pay_buton);
        payButton.setOnClickListener(v -> {

            if(pendingAmount>0 && !isCreditPay){

                Toast.makeText(getContext(),"Insufficient Amount",Toast.LENGTH_LONG).show();
                Vibration.Companion.vibrate(500);
                return;
            }

            String message = "";

            if(isCreditPay){

                message = "Settlement Completed";
            }else {

                message = "Purchase Completed";
            }

            payButton.setClickable(false);
            String balanceCash = String.valueOf(pendingAmount);

         LottieAlertDialogs alertDialog= new LottieAlertDialogs.Builder(getContext(), DialogTypes.TYPE_SUCCESS)
                            .setTitle(message)
                            .setDescription("Thank You!")
                            .setPositiveText("Back")
                            .setPositiveButtonColor(Color.parseColor("#297999"))
                            .setPositiveTextColor(Color.parseColor("#ffffff"))
                             .setPositiveListener(new ClickListeners() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialogs lottieAlertDialog) {
                                    if(!isCreditPay)
                                        {
                                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_sales);
                                        }else {

                                        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();

                                    }
                                    lottieAlertDialog.dismiss();
                                }
                            })
                    .build();
                     alertDialog.setCancelable(false);
                     alertDialog.show();


            if(isCreditPay){

                new ControllerCreditPay(customerId,payModeData);
            }else {
                new BillGenerator(customerId, receivedcash, balanceCash, deliveryGuid, payModeData,addDiscount,redeemNumber);
                EmptyCart();
            }



        });
    }

    @Override
    public void onClick(View v) {
        Button quick_add = (Button) v;
        String add_amount =  quick_add.getText().toString();
        String received_amt = received_amnt.getText().toString();
        if(received_amt.isEmpty()){
            received_amt="0";
        }
        String final_amnt = String.valueOf( Double.parseDouble(add_amount)+Double.parseDouble(received_amt));
        received_amnt.setText(final_amnt);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PaymentViewModel.class);
        // TODO: Use the ViewModel
    }

    public void EmptyCart(){
        SQLiteDatabase db = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        db.execSQL("delete from cart");
        db.close();
    }

    private void initCashLayout(View viewPager){
        received_amnt = viewPager.findViewById(R.id.received_amount_value);
        TextView creditBalanceTV = viewPager.findViewById(R.id.pay_cash_credit_value);
        creditBalanceTV.setText(creditBalance+" ₹");
        EditText creditPayEditText = viewPager.findViewById(R.id.pay_credit_amount_value);


        Button cash50 = viewPager.findViewById(R.id.cash_fifty);
        Button cash100 = viewPager.findViewById(R.id.cash_hundred);
        Button cash200 = viewPager.findViewById(R.id.cash_200);
        Button cash500 = viewPager.findViewById(R.id.cash_500);
        Button cash2000 = viewPager.findViewById(R.id.cash_2000);

        cash50.setOnClickListener(this);
        cash100.setOnClickListener(this);
        cash200.setOnClickListener(this);
        cash500.setOnClickListener(this);
        cash2000.setOnClickListener(this);

        creditPayLayout = viewPager.findViewById(R.id.pay_credit_main_layout);
        if(isCreditPay || customerId.trim().isEmpty() || Integer.parseInt(isCreditEnabled)==0){
            creditPayLayout.setVisibility(View.GONE);
        }else {
            creditPayLayout.setVisibility(View.VISIBLE);
        }


        creditPayEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                paidByCredit = 0;
                setPendingAmount();
                payModeData.remove("RX");

                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if(Double.parseDouble(creditLimit)<= 0.00){
                        Toast.makeText(getContext(), "Not Allowed! Credit Limit is 0", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;

                    }

                    if (pendingAmount <= 0) {
                        Toast.makeText(getContext(), "No Amount is Pending!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }

                    String amntReceived = s.toString();
                    if (amntReceived.trim().isEmpty()){
                        amntReceived = "0.00";
                    }


                    Log.d("Printing Double", "afterTextChanged: "+amntReceived);
                    double received = Double.parseDouble(amntReceived);

                    if(Double.parseDouble(creditLimit)<received){

                        Toast.makeText(getContext(), "Incorrect Amount, Current Credit Limit is:  "+creditLimit, Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }


                    if (received <= pendingAmount && received > 0) {
                        paidByCredit = received;
                        setPendingAmount();
                        payModeData.put("RX", new String[]{payModeId.get("RX"), "", String.valueOf(paidByCredit), "", "", ""});
                    } else {
                        paidByCredit = 0;
                        setPendingAmount();
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });

        received_amnt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                paidByCash = 0;
                setPendingAmount();
                payModeData.remove("CA");
                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if(pendingAmount<=0) {
                        Toast.makeText(getContext(), "No Amount is Pending!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);

                    }
                    receivedcash = s.toString();
                    if (receivedcash.trim().isEmpty()) {
                        receivedcash = "0.00";
                    }
                    double received = Double.parseDouble(receivedcash);

                    if (received == 0) {
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }else {
                        paidByCash = received;
                        setPendingAmount();
                        double payCash=0.00;
                        if(!isCreditPay) {
                            payCash = received <= pendingAmount ? received : received - pendingAmount;
                        }else {
                            if(received<=pendingAmount){
                                payCash = received;
                            }else if(received>pendingAmount) {
                                payCash = pendingAmount;
                            }
                        }
                        payModeData.put("CA",new String[]{payModeId.get("CA"),"",String.valueOf(payCash),"","",""});

                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {



            }
        });
    }

    private void initCardLayout(View viewPager){

        SearchableSpinner bankNameSpinner = viewPager.findViewById(R.id.pay_card_bank_value);
        EditText cardAmount = viewPager.findViewById(R.id.pay_card_amount_value);
        EditText custNameEdittext = viewPager.findViewById(R.id.pay_card_name__value);
        EditText cardNumber1 = viewPager.findViewById(R.id.pay_card_number_one);
        EditText cardNumber2 = viewPager.findViewById(R.id.pay_card_number_two);
        EditText cardNumber3 = viewPager.findViewById(R.id.pay_card_number_three);
        EditText cardNumber4 = viewPager.findViewById(R.id.pay_card_number_four);


         List<StringWithTag> banknameList = getBankListData();
        ArrayAdapter<StringWithTag> bankNameAdapter = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_layout, banknameList);
        bankNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankNameSpinner.setAdapter(bankNameAdapter);
        bankNameSpinner.setTitle("Select Bank");
        bankNameSpinner.setPositiveButton("OK");
        bankNameSpinner.setGravity(Gravity.START);


        Spinner expirySelector = viewPager.findViewById(R.id.pay_card_expiry_value);
        String[] expiryItem = new String[] {"MM/YYYY"};
        ArrayAdapter<String> expiryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_bold, expiryItem);
        expiryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expirySelector.setAdapter(expiryAdapter);
        AtomicReference<String> expMont =  new AtomicReference<>();
        AtomicReference<String> expYear = new AtomicReference<>();

        expirySelector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    if(event.getAction() ==MotionEvent.ACTION_UP) {
                        MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                        pickerDialog.setListener((datePicker, year, month, i2) -> {
                            //Toast.makeText(getContext(), year + "-" + month, Toast.LENGTH_SHORT).show();

                            if(month<10) {
                                expMont.set("0" + month);
                            }
                            else {
                                expMont.set(String.valueOf(month));
                            }

                            expYear.set(String.valueOf(year));
                            expiryItem[0] =expDate = expMont + "/" + expYear;
                            ArrayAdapter<String> expiryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_bold, expiryItem);
                            expiryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            expirySelector.setAdapter(expiryAdapter);
                        });
                        pickerDialog.show(requireActivity().getSupportFragmentManager(), "MonthYearPickerDialog");

                    }
                }

                return true;
            }
        });

        RadioGroup payCardRadioGroup = (RadioGroup) viewPager.findViewById(R.id.pay_card_radiogroup);

        payCardRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId== R.id.pay_card_radio_debit) {

                cardType = "OT";

            }else if(checkedId== R.id.pay_card_radio_credit){

                cardType = "CR";
            }
        });

        cardAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                paidByCard = 0;
                setPendingAmount();
                payModeData.remove("OT");
                payModeData.remove("CR");

                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if(pendingAmount<=0){
                        Toast.makeText(getContext(),"No Amount is Pending!",Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }
                    String card1 =  cardNumber1.getText().toString();
                    String card2 =  cardNumber2.getText().toString();
                    String card3 =  cardNumber3.getText().toString();
                    String card4 =  cardNumber4.getText().toString();
                    custName = custNameEdittext.getText().toString();
                    cardNumberX = card1+card2+card3+card4;

                    String cardReceived = s.toString();
                    if(cardReceived.trim().isEmpty()) {
                        cardReceived = "0.00";
                    }

                    double received =  Double.parseDouble(cardReceived);

                    List<String> allStrings = new ArrayList<>(Arrays.asList(card1,card2,card3,card4,custName, expMont.get(), expYear.get(),bankGuid));

                    if (!validateStrings(allStrings)) {

                        Toast.makeText(getContext(),"Please Fill up all fields first!",Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }

                    if(received<=pendingAmount && received >0) {
                        paidByCard = received;
                        setPendingAmount();

                        expDate = expMont.get()+expYear.get();
                        if(cardType.equals("OT")) {
                            payModeData.put(cardType, new String[]{payModeId.get("OT"), "", String.valueOf(paidByCard), cardNumberX, expDate, custName});
                        }else if(cardType.equals("CR")) {
                            payModeData.put(cardType, new String[]{payModeId.get("CR"), "", String.valueOf(paidByCard), cardNumberX, expDate, custName});

                        }

                    }else {
                        paidByCard = 0;
                        setPendingAmount();
                        Toast.makeText(getContext(),"Incorrect Amount!",Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });


        cardNumber1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(s.length()==4)     //size as per your requirement
                {
                    cardNumber2.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });

        cardNumber2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(s.length()==4)     //size as per your requirement
                {

                    cardNumber3.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });
        cardNumber3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start,int before, int count)
            {
                // TODO Auto-generated method stub
                if(s.length()==4)     //size as per your requirement
                {
                    cardNumber4.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

        });


        bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                StringWithTag spinnerSelected = (StringWithTag) parent.getItemAtPosition(pos);
                bankGuid = spinnerSelected.tag;
                bankName= spinnerSelected.string;
                Log.d("SpinnerSelected", "onItemSelected: Tag= "+bankGuid);

            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    private  void initOnlinePayLayout(View viewPger) {

        EditText paytmAmount = viewPger.findViewById(R.id.pay_paytm_value);
        EditText paytmTrans = viewPger.findViewById(R.id.pay_paytm_trans);
        EditText gpayAmount = viewPger.findViewById(R.id.pay_gpay_value);
        EditText gpayTrans = viewPger.findViewById(R.id.pay_gpay_trans);
        EditText whatsappAmount = viewPger.findViewById(R.id.pay_whatsapp_value);
        EditText whatsappTrans = viewPger.findViewById(R.id.pay_whatsapp_trans);
        EditText amazonAmount = viewPger.findViewById(R.id.pay_amazon_value);
        EditText amazonTrans = viewPger.findViewById(R.id.pay_amazon_trans);
        EditText bhimAmount = viewPger.findViewById(R.id.pay_bhim_value);
        EditText bhimTrans = viewPger.findViewById(R.id.pay_bhim_trans);


        paytmAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                paidByPaytm = 0;
                setPendingAmount();
                payModeData.remove("PP");

                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if (pendingAmount <= 0) {
                        Toast.makeText(getContext(), "No Amount is Pending!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }

                    paytmTransNo = paytmTrans.getText().toString();

                    String amntReceived = s.toString();
                    if (amntReceived.trim().isEmpty()){
                        amntReceived = "0.00";
                    }


                    Log.d("Printing Double", "afterTextChanged: "+amntReceived);
                    double received = Double.parseDouble(amntReceived);


                    if (received <= pendingAmount && received > 0) {
                        paidByPaytm = received;
                        setPendingAmount();
                        payModeData.put("PP", new String[]{payModeId.get("PP"), paytmTransNo, String.valueOf(paidByPaytm), "", "", ""});
                    } else {
                        paidByPaytm = 0;
                        setPendingAmount();
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });


        gpayAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                paidByGpay = 0;
                setPendingAmount();
                payModeData.remove("GP");

                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if (pendingAmount <= 0) {
                        Toast.makeText(getContext(), "No Amount is Pending!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }

                    gpayTransNo = gpayTrans.getText().toString();

                    String amntReceived = s.toString();
                    if (amntReceived.trim().isEmpty()) {
                        amntReceived = "0.00";
                    }
                    double received = Double.parseDouble(amntReceived);


                    if (received <= pendingAmount && received > 0) {
                        paidByGpay = received;
                        setPendingAmount();
                        payModeData.put("GP", new String[]{payModeId.get("GP"), gpayTransNo, String.valueOf(paidByGpay), "", "", ""});

                    } else {
                        paidByGpay = 0;
                        setPendingAmount();
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });

        whatsappAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                paidByWhatsapp = 0;
                setPendingAmount();
                payModeData.remove("WP");

                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if (pendingAmount <= 0) {
                        Toast.makeText(getContext(), "No Amount is Pending!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }

                    whatsappTransNo = whatsappTrans.getText().toString();

                    String amntReceived = s.toString();
                    if (amntReceived.trim().isEmpty()) {
                        amntReceived = "0.00";
                    }

                    double received = Double.parseDouble(amntReceived);


                    if (received <= pendingAmount && received > 0) {
                        paidByWhatsapp = received;
                        setPendingAmount();
                        payModeData.put("WP", new String[]{payModeId.get("WP"), whatsappTransNo, String.valueOf(paidByWhatsapp), "", "", ""});


                    } else {
                        paidByWhatsapp = 0;
                        setPendingAmount();
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });

        amazonAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                paidByAmazon = 0;
                setPendingAmount();
                payModeData.remove("ZP");
                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if (pendingAmount <= 0) {
                        Toast.makeText(getContext(), "No Amount is Pending!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }

                    amazonTransNo = amazonTrans.getText().toString();

                    String amntReceived = s.toString();
                    if (amntReceived.trim().isEmpty()) {
                        amntReceived = "0.00";
                    }

                    double received = Double.parseDouble(amntReceived);

                    if (received <= pendingAmount && received > 0) {
                        paidByAmazon = received;
                        setPendingAmount();
                        payModeData.put("ZP", new String[]{payModeId.get("ZP"), amazonTransNo, String.valueOf(paidByAmazon), "", "", ""});

                    } else {
                        paidByAmazon = 0;
                        setPendingAmount();
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });

        bhimAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                paidByBhim = 0;
                setPendingAmount();
                payModeData.remove("BP");
                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if (pendingAmount <= 0) {
                        Toast.makeText(getContext(), "No Amount is Pending!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }

                    bhimTransNo = bhimTrans.getText().toString();

                    String amntReceived = s.toString();
                    if (amntReceived.trim().isEmpty()) {
                        amntReceived = "0.00";
                    }

                    double received = Double.parseDouble(amntReceived);

                    if (received <= pendingAmount && received > 0) {
                        paidByBhim = received;
                        setPendingAmount();
                        payModeData.put("BP", new String[]{payModeId.get("BP"), bhimTransNo, String.valueOf(paidByBhim), "", "", ""});

                    } else {
                        paidByBhim = 0;
                        setPendingAmount();
                        Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    e.printStackTrace();
                }
            }
        });
    }

    private  void initRedeemLayout(View viewPger) {

        TextView redeemAmount = viewPger.findViewById(R.id.pay_redeem_amount_value);
        AutoCompleteTextView redeemNoteValue = viewPger.findViewById(R.id.pay_redeem_note_value);

        List<StringWithTag> creditNotelist = getCreditNoteArray();

        ArrayAdapter<StringWithTag> redeemAdapter = new ArrayAdapter<>(getContext(), android.R.layout.select_dialog_item, creditNotelist);
        //Getting the instance of AutoCompleteTextView
        redeemNoteValue.setThreshold(1);//will start working from first character
        redeemNoteValue.setAdapter(redeemAdapter);//setting the adapter data into the AutoCompleteTextView

        redeemNoteValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
                Object item = parent.getItemAtPosition(position);
                StringWithTag myItem = (StringWithTag) item;
                String amount = myItem.tag;
                redeemAmount.setText(amount+" ₹");
                Log.d("NoteSelected", "onItemClick: "+redeemNumber);
                double amountInNum = Math.abs(Double.parseDouble(amount));
                if(amountInNum>pendingAmount){
                    Toast.makeText(getContext(), "Incorrect Amount!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                }else {
                    redeemNumber = myItem.string;
                    redeemMasterAmount = amount;
                    setPendingAmount();
                }

            }
        });

        redeemNoteValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                redeemAmount.setText("0.00 ₹");
                redeemMasterAmount = "0.00";
                redeemNumber = "";
                setPendingAmount();
            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }


    private List<StringWithTag> getBankListData(){
        List<StringWithTag> list = new ArrayList<StringWithTag>();
        list.add(new StringWithTag("No Bank Selected", " "));

        SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select BANK_GUID, BANK_NAME from bank_details",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                list.add(new StringWithTag(name, id));
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
        return  list;

    }

    private void getPayModeID(){
      payModeId.clear();
        SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select PAYMODE_GUID, LEGEND from masterpaymode",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String id = cursor.getString(1);
                String name = cursor.getString(0);
                payModeId.put(id,name);
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();


    }
    private boolean validateStrings(List<String> fields) {
        for (String str : fields) {
            if (str ==null || str.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private void doEmptyEditTexts(List<EditText> fields) {
        for (EditText editText : fields) {
            editText.setText("");
        }

    }

    private void setPendingAmount(){

        List<Double> allAmount = new ArrayList<>(Arrays.asList(paidByCash, paidByCard, paidByOnline,paidByPaytm,paidByGpay,paidByWhatsapp,paidByAmazon,paidByBhim,paidByCredit));
        pendingAmount = Double.parseDouble(amountToPay);
        for(Double paid: allAmount){

            pendingAmount = pendingAmount - paid;
            Log.d("PendingAmount Set", "setPendingAmount: "+pendingAmount);
        }
        if(redeemMasterAmount!=null && !redeemMasterAmount.trim().isEmpty()){
            if(Double.parseDouble(redeemMasterAmount)>0){

                pendingAmount = pendingAmount - Double.parseDouble(redeemMasterAmount);
            }
        }
        pendingAmountTextView.setText((new DecimalFormat("#.##").format(pendingAmount))+" ₹");

    }

    private void getCreditDetails(String custId){
        SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select CREDITLIMIT, CREDIT_CUST from retail_cust where CUSTOMERGUID = '"+custId+"'",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                creditLimit = cursor.getString(0);
                isCreditEnabled = cursor.getString(1);

                Log.d("CreditLimitRetrieved", "getCreditDetails: "+creditLimit);
                Log.d("isCreditEnabled", "getCreditDetails: "+isCreditEnabled);

                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
    }

    /*private String getTotalPayment(){
        double totalSum=0.00;
        for (Map.Entry<String,String[]> entry : payModeData.entrySet()) {
            String[] data = entry.getValue();
           String  PAYAMOUNT = data[2];
           totalSum += Double.parseDouble(PAYAMOUNT);
        }

        return String.valueOf(totalSum);
    }*/



    private List<StringWithTag> getCreditNoteArray(){
        List<StringWithTag> list = new ArrayList<>();
        //list.add(new StringWithTag("No Delivery Type", " "));

        SQLiteDatabase mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select CREDITNOTENUMBER, AMOUNTREFUNDED from customerReturnMaster Where REPLACEMENTBILLNO = ?",new String[] {""});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String number = cursor.getString(0);
                String amount = cursor.getString(1);
                list.add(new StringWithTag(number, amount));
                cursor.moveToNext();
            }
        }
        cursor.close();
        mydb.close();
        return  list;

    }

}