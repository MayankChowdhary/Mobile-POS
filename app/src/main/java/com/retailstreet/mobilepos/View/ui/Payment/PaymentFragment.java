package com.retailstreet.mobilepos.View.ui.Payment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.Controller.BillGenerator;
import com.retailstreet.mobilepos.Controller.ControllerCreditPay;
import com.retailstreet.mobilepos.Controller.ControllerStoreConfig;
import com.retailstreet.mobilepos.Controller.ControllerStoreParams;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.Utils.StringWithTag;
import com.retailstreet.mobilepos.Utils.Vibration;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;
import com.retailstreet.mobilepos.View.dialog.MonthYearPickerDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

public class PaymentFragment extends Fragment implements View.OnClickListener {

    private PaymentViewModel mViewModel;
    BillPreviewDialog dialogFragment;
    private String billNumber = new BillGenerator().getBillNumber();
    private String amountToPay;
    private String customerId;
    private EditText received_amnt;
    private String deliveryGuid;
    private TextView pendingAmountTextView;
    private LinearLayout creditPayLayout;
    private LinearLayout advanceLayout;
    private boolean isCreditPay = false;
    private Double pendingAmount;
     double paidByCash = 0.00;
    double paidByCard = 0.00;
    double paidByCheque = 0.00;
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
    String chequeNumber="";
    String bankName = "";
    String checkBankName = "";
    String bankGuid = "";
    String checkBankGuid = "";
    String expDate = " ";
    String chequeDate = " ";
    String receivedcash="0.00";
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
    String advance_amount = "0.00";
    Double paidByAdvance = 0.00;
    String newAdvance = "0.00";

    List<String> cardValidateStrings;
    List<String> chequeValidateStrings;
    EditText cardNumber1 ;
    EditText cardNumber2 ;
    EditText cardNumber3 ;
    EditText cardNumber4 ;
    EditText chequeNumberEditText ;
    EditText custNameEdittext;
    String expMont;
    String expYear ;
    String card1 ;
    String card2 ;
    String card3 ;
    String card4 ;

    String chequeMont;
    String chequeYear ;

    int DIALOG_FRAGMENT =1;
    boolean CARD_MACHINE = true;
    boolean IS_CREDIT_ALLOWED = true;
    boolean IS_MULTI_CURR = false;

    ControllerStoreConfig config = new  ControllerStoreConfig();
    ControllerStoreParams params = new ControllerStoreParams();


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

        CARD_MACHINE = config.getCardMachine();
        IS_CREDIT_ALLOWED = params.getCreditSale();
        IS_MULTI_CURR = config.getMultiCurrency();

        createBillPreviewTable();

        if(!customerId.trim().isEmpty()){

            getCreditDetails(customerId);
            advance_amount = getAdvanceDetails(customerId);
        }


        if(!isCreditPay && Double.parseDouble(advance_amount)>0.00){
            double advance = Math.abs(Double.parseDouble(advance_amount));
            if(advance<Double.parseDouble(amountToPay)){
                amountToPay= String.valueOf(Double.parseDouble(amountToPay) - advance);
                paidByAdvance = advance;
                newAdvance = "0.00";
            }else if(advance>=Double.parseDouble(amountToPay)){
                amountToPay = "0.00";
                paidByAdvance = Double.parseDouble(amountToPay);
                newAdvance = String.valueOf( advance - Double.parseDouble(amountToPay));
            }

        }

        Log.d("Credit Balance Received", "onViewCreated: "+creditBalance);
        Log.d("Additional Disc", "onViewCreated: "+addDiscount);
        Log.d("CustGuid Received", "onViewCreated: "+customerId);

        getPayModeID();
       pendingAmountTextView = view.findViewById(R.id.pending_amount_value);
        pendingAmountTextView.setText(amountToPay);
        pendingAmount = Double.parseDouble(amountToPay);


        ViewPager payModeViewPager = view.findViewById(R.id.pay_mode_viewpager);
        PaymentPagerAdapter paymentPagerAdapter = new PaymentPagerAdapter();
        paymentPagerAdapter.insertViewId(R.id.pager_cash);
        paymentPagerAdapter.insertViewId(R.id.pager_card);
        paymentPagerAdapter.insertViewId(R.id.pager_online);
        paymentPagerAdapter.insertViewId(R.id.pager_cheque);
        paymentPagerAdapter.insertViewId(R.id.pager_redeem);
        // attach adapter to viewpager
        payModeViewPager.setAdapter(paymentPagerAdapter);
        TabLayout payModeTab = view.findViewById(R.id.pay_mode_tab);
        payModeTab.setupWithViewPager(payModeViewPager);
        payModeViewPager.setOffscreenPageLimit(5);
        initCashLayout(payModeViewPager.getRootView());
        initCardLayout(payModeViewPager.getRootView());
        initOnlinePayLayout(payModeViewPager.getRootView());
        initRedeemLayout(payModeViewPager.getRootView());
        initChequeLayout(payModeViewPager.getRootView());

        Button payButton = view.findViewById(R.id.pay_buton);
        payButton.setOnClickListener(v -> {

            if(pendingAmount>0 && !isCreditPay){

                Toast.makeText(getContext(),"Insufficient Amount",Toast.LENGTH_LONG).show();
                Vibration.Companion.vibrate(500);
                return;
            }
            if(isCreditPay){

                if(Double.parseDouble(getTotalPayment())<=0){
                    Toast.makeText(getContext(),"No amount added!",Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(500);
                    return;
                }

            }


            if(paidByCard>0 ) {
                Log.d("PaidByCard is true", "onViewCreated: "+paidByCard);
                 card1 =  cardNumber1.getText().toString();
                 card2 =  cardNumber2.getText().toString();
                 card3 =  cardNumber3.getText().toString();
                 card4 =  cardNumber4.getText().toString();
                custName = custNameEdittext.getText().toString();
                cardNumberX = card1+card2+card3+card4;
                if(CARD_MACHINE) {
                    cardValidateStrings = new ArrayList<>(Arrays.asList(card1, card2, card3, card4, custName, bankGuid));
                }else {
                    cardValidateStrings = new ArrayList<>(Arrays.asList(card1, card2, card3, card4, custName));

                }
                if (!validateStrings(cardValidateStrings)) {

                    Toast.makeText(getContext(), "Please Fill up card details first!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    return;
                }
            }

            if(paidByCheque>0 ) {
                Log.d("PaidByCheque is true", "onViewCreated: "+paidByCheque);
                chequeNumber =  chequeNumberEditText.getText().toString();
                chequeValidateStrings = new ArrayList<>(Arrays.asList(chequeNumber,chequeMont, chequeYear,checkBankGuid));
                if (!validateStrings(chequeValidateStrings)) {

                    Toast.makeText(getContext(), "Please Fill up Cheque details first!", Toast.LENGTH_LONG).show();
                    Vibration.Companion.vibrate(300);
                    return;
                }
            }

            payButton.setClickable(false);
            String balanceCash = String.valueOf(pendingAmount);

            if(isCreditPay){

                new ControllerCreditPay(customerId,payModeData);
            }else {
                new BillGenerator(customerId, receivedcash, balanceCash, deliveryGuid, payModeData,addDiscount,redeemNumber,paidByAdvance,billNumber);
                EmptyCart();
            }

            if(!isCreditPay && Double.parseDouble(advance_amount)>0.00) {
                updateCustomerAdvance(newAdvance,customerId);
            }

            if(isCreditPay){
                LottieAlertDialogs alertDialog= new LottieAlertDialogs.Builder(getContext(), DialogTypes.TYPE_SUCCESS)
                        .setTitle( "Settlement Completed")
                        .setDescription("Thank You!")
                        .setPositiveText("Back")
                        .setPositiveButtonColor(Color.parseColor("#297999"))
                        .setPositiveTextColor(Color.parseColor("#ffffff"))
                        .setPositiveListener(lottieAlertDialog -> {
                                Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();
                                 lottieAlertDialog.dismiss();
                        })
                        .build();
                alertDialog.setCancelable(false);
                alertDialog.show();

            }else {

                Log.d("Billing_DONE", "onViewCreated: Showing Dialog");
                showEditDialog(billNumber,customerId);
            }

        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DIALOG_FRAGMENT) {
            if (resultCode == Activity.RESULT_OK) {
                if(dialogFragment!=null ){
                    dialogFragment.dismiss();
                }
                if (!isCreditPay) {
                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.nav_sales);
                } else {

                    Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).popBackStack();

                }


            }
        }
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
        creditBalanceTV.setText(creditBalance);
        EditText creditPayEditText = viewPager.findViewById(R.id.pay_credit_amount_value);
        advanceLayout = viewPager.findViewById(R.id.advance_amount_layout);
        TextView advanceAmountTV = viewPager.findViewById(R.id.advance_amount_value);
        advanceAmountTV.setText(advance_amount);
        LinearLayout multiCurr = viewPager.findViewById(R.id.pay_multi_curr_layout);
        TextView usdValue = viewPager.findViewById(R.id.amount_usd_pay);
        usdValue.setText(getConvertedCurrency(pendingAmount,0));
        TextView euroValue = viewPager.findViewById(R.id.amount_euro_pay);
        euroValue.setText(getConvertedCurrency(pendingAmount,1));
        TextView poundValue = viewPager.findViewById(R.id.amount_pound_pay);
        poundValue.setText(getConvertedCurrency(pendingAmount,2));

        if(IS_MULTI_CURR){
            multiCurr.setVisibility(View.VISIBLE);
        }

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
        if(isCreditPay || customerId.trim().isEmpty() || Integer.parseInt(isCreditEnabled)==0 || !IS_CREDIT_ALLOWED){
            creditPayLayout.setVisibility(View.GONE);

        }else {
            creditPayLayout.setVisibility(View.VISIBLE);
        }

        if(isCreditPay || customerId.trim().isEmpty() || !IS_CREDIT_ALLOWED){
            advanceLayout.setVisibility(View.GONE);

        }else {
            advanceLayout.setVisibility(View.VISIBLE);

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
                payModeData.remove("CA");
                if(paidByAdvance>0.00){
                    payModeData.put("CA",new String[]{payModeId.get("CA"),"",String.valueOf(paidByAdvance),"","",""});
                }

                setPendingAmount();
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
                        if(paidByAdvance>0.00){
                            payModeData.put("CA",new String[]{payModeId.get("CA"),"",String.valueOf(payCash+paidByAdvance),"","",""});

                        }else {
                            payModeData.put("CA", new String[]{payModeId.get("CA"), "", String.valueOf(payCash), "", "", ""});
                        }
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


        if(IS_MULTI_CURR)
        received_amnt.setText(pendingAmount.toString());

    }

    private void initCardLayout(View viewPager){

        SearchableSpinner bankNameSpinner = viewPager.findViewById(R.id.pay_card_bank_value);
        EditText cardAmount = viewPager.findViewById(R.id.pay_card_amount_value);
         custNameEdittext = viewPager.findViewById(R.id.pay_card_name__value);
         cardNumber1 = viewPager.findViewById(R.id.pay_card_number_one);
         cardNumber2 = viewPager.findViewById(R.id.pay_card_number_two);
         cardNumber3 = viewPager.findViewById(R.id.pay_card_number_three);
         cardNumber4 = viewPager.findViewById(R.id.pay_card_number_four);


         TextView bankTitle = viewPager.findViewById(R.id.pay_card_bank_title);
         List<StringWithTag> banknameList = getBankListData();
        ArrayAdapter<StringWithTag> bankNameAdapter = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_layout, banknameList);
        bankNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankNameSpinner.setAdapter(bankNameAdapter);
        bankNameSpinner.setTitle("Select Bank");
        bankNameSpinner.setPositiveButton("OK");
        bankNameSpinner.setGravity(Gravity.START);


        TextView expriryTitle = viewPager.findViewById(R.id.pay_card_expiry_title);
        Spinner expirySelector = viewPager.findViewById(R.id.pay_card_expiry_value);
        String[] expiryItem = new String[] {"MM/YYYY"};
        ArrayAdapter<String> expiryAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_bold, expiryItem);
        expiryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expirySelector.setAdapter(expiryAdapter);


        expirySelector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    if(event.getAction() ==MotionEvent.ACTION_UP) {
                        MonthYearPickerDialog pickerDialog = new MonthYearPickerDialog();
                        pickerDialog.setListener((datePicker, year, month, i2) -> {
                            //Toast.makeText(getContext(), year + "-" + month, Toast.LENGTH_SHORT).show();

                            if(month<10) {
                                expMont="0" + month;
                            }
                            else {
                                expMont=String.valueOf(month);
                            }

                            expYear=String.valueOf(year);
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

        if(!CARD_MACHINE){
            cardNumber1.setEnabled(false);
            cardNumber2.setEnabled(false);
            cardNumber3.setEnabled(false);
            cardNumber1.setText("XXXX");
            cardNumber2.setText("XXXX");
            cardNumber3.setText("XXXX");
            expirySelector.setVisibility(View.GONE);
            bankNameSpinner.setVisibility(View.GONE);
            bankTitle.setVisibility(View.GONE);
            expriryTitle.setVisibility(View.GONE);
        }

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


                    String cardReceived = s.toString();
                    if(cardReceived.trim().isEmpty()) {
                        cardReceived = "0.00";
                    }

                    double received =  Double.parseDouble(cardReceived);


                    /*if (!validateStrings(cardValidateStrings)) {

                        Toast.makeText(getContext(),"Please Fill up all fields first!",Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }*/

                    if(received<=pendingAmount && received >0) {
                        paidByCard = received;
                        setPendingAmount();
                        card1 =  cardNumber1.getText().toString();
                        card2 =  cardNumber2.getText().toString();
                        card3 =  cardNumber3.getText().toString();
                        card4 =  cardNumber4.getText().toString();
                        cardNumberX = card1+card2+card3+card4;
                        custName = custNameEdittext.getText().toString();

                        if(cardType.equals("OT")) {
                            payModeData.put(cardType, new String[]{payModeId.get("OT"), "", String.valueOf(paidByCard), cardNumberX, bankName, custName});
                        }else if(cardType.equals("CR")) {
                            payModeData.put(cardType, new String[]{payModeId.get("CR"), "", String.valueOf(paidByCard), cardNumberX, bankName, custName});
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




    private void initChequeLayout(View viewPager){

        SearchableSpinner bankNameSpinner = viewPager.findViewById(R.id.pay_cheque_bank_value);
        EditText chequeAmount = viewPager.findViewById(R.id.pay_cheque_amount_value);
        chequeNumberEditText = viewPager.findViewById(R.id.pay_cheque_number);

        List<StringWithTag> banknameList = getBankListData();
        ArrayAdapter<StringWithTag> bankNameAdapter = new ArrayAdapter<StringWithTag> (getActivity(), R.layout.spinner_layout, banknameList);
        bankNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankNameSpinner.setAdapter(bankNameAdapter);
        bankNameSpinner.setTitle("Select Bank");
        bankNameSpinner.setPositiveButton("OK");
        bankNameSpinner.setGravity(Gravity.START);

        Spinner dateSelector = viewPager.findViewById(R.id.pay_cheque_date_value);
        String[] dateItem = new String[] {"DD/MM/YYYY"};
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_bold, dateItem);
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSelector.setAdapter(dateAdapter);


        dateSelector.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event != null) {
                    if(event.getAction() ==MotionEvent.ACTION_UP) {

                        Calendar c = Calendar.getInstance();
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dpx = new DatePickerDialog(requireContext(),
                                (view, year1, monthOfYear, dayOfMonth) -> {

                                    if(month<10) {
                                        chequeMont="0" + (monthOfYear+1);
                                    }
                                    else {
                                        chequeMont=String.valueOf(monthOfYear+1);
                                    }
                                    chequeYear=String.valueOf(year1);
                                    dateItem[0] =chequeDate = dayOfMonth+"/"+chequeMont + "/" + chequeYear;
                                    ArrayAdapter<String> chequeAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item_bold, dateItem);
                                    chequeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    dateSelector.setAdapter(chequeAdapter);
                                }, year, month, day);
                        dpx.show();
                    }
                }

                return true;
            }
        });


        chequeAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                paidByCheque = 0;
                setPendingAmount();
                payModeData.remove("CX");

                if(s.toString().trim().isEmpty()){
                    return;
                }

                try {
                    if(pendingAmount<=0){
                        Toast.makeText(getContext(),"No Amount is Pending!",Toast.LENGTH_LONG).show();
                        Vibration.Companion.vibrate(300);
                        return;
                    }


                    String chequeReceived = s.toString();
                    if(chequeReceived.trim().isEmpty()) {
                        chequeReceived = "0.00";
                    }

                    double received =  Double.parseDouble(chequeReceived);


                    if(received<=pendingAmount && received >0) {
                        paidByCheque = received;
                        setPendingAmount();

                        chequeNumber = chequeNumberEditText.getText().toString();
                            payModeData.put("CX", new String[]{payModeId.get("CX"), "", String.valueOf(paidByCheque), chequeNumber, chequeDate, checkBankName});
                    }else {
                        paidByCheque = 0;
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



        bankNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                StringWithTag spinnerSelected = (StringWithTag) parent.getItemAtPosition(pos);
                checkBankGuid = spinnerSelected.tag;
                checkBankName= spinnerSelected.string;
                Log.d("SpinnerSelected", "onItemSelected: Tag= "+checkBankGuid);

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
                redeemAmount.setText(amount);
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


                redeemAmount.setText("0.00");
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
            Log.d("ValidatinCard", "validateStrings: "+str);
            if (str ==null || str.trim().isEmpty()) {
                Log.d("FoundEmpty", "validateStrings: "+str);
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

        List<Double> allAmount = new ArrayList<>(Arrays.asList(paidByCash, paidByCard, paidByOnline,paidByPaytm,paidByGpay,paidByWhatsapp,paidByAmazon,paidByBhim,paidByCredit,paidByCheque));
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
        pendingAmountTextView.setText((new DecimalFormat("#0.00").format(pendingAmount)));

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

    private String getTotalPayment(){
        double totalSum=0.00;
        for (Map.Entry<String,String[]> entry : payModeData.entrySet()) {
            String[] data = entry.getValue();
           String  PAYAMOUNT = data[2];
           totalSum += Double.parseDouble(PAYAMOUNT);
        }

        return String.valueOf(totalSum);
    }



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

    private String getAdvanceDetails(String custId){
        String advance="0.00";
        SQLiteDatabase mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE,null);
        Cursor cursor = mydb.rawQuery("select ADVANCE_AMOUNT from retail_cust where CUSTOMERGUID = '"+custId+"'",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                advance = cursor.getString(0);
                Log.d("AdvanceRetrieved", "getAdvanceAmount: "+advance_amount);
                cursor.moveToNext();
            }
        }

        cursor.close();
        mydb.close();
        return advance;
    }

    public void updateCustomerAdvance(String newAdvance, String custGuid){
        try {

            String query = "Update retail_cust Set ADVANCE_AMOUNT = '"+newAdvance+"'  where CUSTOMERGUID = '"+custGuid+"'";
            SQLiteDatabase db  = requireContext().openOrCreateDatabase("MasterDB", MODE_PRIVATE, null);
            db.execSQL(query);
            db.close();
            Log.d("UpdateAdvance", "Update New Advance: Successful"+newAdvance);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void showEditDialog(String billNum, String custId) {
         dialogFragment= new  BillPreviewDialog();
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog",true);
        bundle.putBoolean("fullScreen",false);
        bundle.putString("BILLNum", billNum);
        bundle.putString("CUSTID", custId);
        dialogFragment.setArguments(bundle);
        dialogFragment.setTargetFragment(this, DIALOG_FRAGMENT);
        //val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        FragmentTransaction ft = requireFragmentManager().beginTransaction();
        Fragment prev = requireFragmentManager().findFragmentByTag("dialog");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        dialogFragment.show(ft, "dialog");
    }


    private void createBillPreviewTable(){
        SQLiteDatabase mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        mydb.execSQL("DROP TABLE IF EXISTS tmp_bill_preview");
        mydb.execSQL("CREATE TABLE IF NOT EXISTS tmp_bill_preview (PROD_NM text, MRP text, SPRICE  text, QTY  text, TOTAL text)");
        mydb.close();
    }

    private  String getConvertedCurrency( double amount, int index){
        double result=0.00;

        switch (index){
            case 0:
                 // calculating usd
                result = amount/73.25;
                break;

            case 1:
                // calculating euro
                result = amount/88.74;
                break;

            case 2:
                // calculating pound
                result = amount/103.26;
                break;
        }

        Log.d("Converted Curr", "getConvertedCurrency: "+result);
        return  new DecimalFormat("#0.00").format( result);

    }
}