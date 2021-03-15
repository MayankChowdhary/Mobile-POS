package com.retailstreet.mobilepos.View.ui.Payment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.retailstreet.mobilepos.Controller.BillGenerator;
import com.retailstreet.mobilepos.R;
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs;
import com.retailstreet.mobilepos.View.dialog.ClickListeners;

import org.jetbrains.annotations.NotNull;

public class PaymentFragment extends Fragment implements View.OnClickListener {

    private PaymentViewModel mViewModel;
    private String amountToPay;
    private String customerId;
    private EditText received_amnt;
    private String deliveryGuid;
    private String masterPayModeGuid="E123BBBE-A000-4617-AD49-9B5AF2275F43";


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

        PaymentFragmentArgs myArgs =PaymentFragmentArgs.fromBundle(requireArguments());
        amountToPay=myArgs.getAmountToPay();
        customerId=myArgs.getCustomerID();
        deliveryGuid = myArgs.getDelTypeGuid();

        TextView amount_to_pay = view.findViewById(R.id.pay_amount_value);
        amount_to_pay.setText(amountToPay+" ₹");
        received_amnt = view.findViewById(R.id.received_amount_value);

        TextView changeAmount = view.findViewById(R.id.expected_change_value);
        changeAmount.setText("0.00 ₹");
        final String[] changeValue = {"0.00"};

        final Boolean[] isPay = {false};


        Button cash50 = view.findViewById(R.id.cash_fifty);
        Button cash100 = view.findViewById(R.id.cash_hundred);
        Button cash200 = view.findViewById(R.id.cash_200);
        Button cash500 = view.findViewById(R.id.cash_500);
        Button cash2000 = view.findViewById(R.id.cash_2000);

        cash50.setOnClickListener(this);
        cash100.setOnClickListener(this);
        cash200.setOnClickListener(this);
        cash500.setOnClickListener(this);
        cash2000.setOnClickListener(this);

        received_amnt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                String amountReceived="0";
                if(s.length() != 0)
                    amountReceived = String.valueOf(s);
                    double amountR = Double.parseDouble(amountReceived);
                    double amountP = Double.parseDouble(amountToPay);
                    if(amountR>amountP) {
                        isPay[0] =true;
                        String balance = String.valueOf(amountR - amountP);
                        changeAmount.setText("-"+balance+" ₹");
                        changeValue[0] = balance;
                    }else if(amountR==amountP){
                        isPay[0] =true;
                        changeValue[0] = "0.00";
                        changeAmount.setText("0.00 ₹");

                    }else {
                        isPay[0] =false;
                        changeValue[0] = "0.00";
                        changeAmount.setText("0.00 ₹");
                    }
            }
        });

        Button payButton = view.findViewById(R.id.pay_buton);
        payButton.setOnClickListener(v -> {

            if(!isPay[0]){

                Toast.makeText(getContext(),"Insufficient Amount!",Toast.LENGTH_LONG).show();
                return;
            }
            String receivedCash = received_amnt.getText().toString();
            if(receivedCash.isEmpty())
            {
                Toast.makeText(getContext(),"Please Enter Amount!",Toast.LENGTH_SHORT).show();
                return;
            }
            payButton.setEnabled(false);
            String balanceCash = changeValue[0];

         LottieAlertDialogs alertDialog= new LottieAlertDialogs.Builder(getContext(), DialogTypes.TYPE_SUCCESS)
                            .setTitle("Purchase Completed")
                            .setDescription("Thank You!")
                            .setPositiveText("Back")
                            .setPositiveButtonColor(Color.parseColor("#297999"))
                            .setPositiveTextColor(Color.parseColor("#ffffff"))
                             .setPositiveListener(new ClickListeners() {
                                @Override
                                public void onClick(@NotNull LottieAlertDialogs lottieAlertDialog) {
                                    Navigation.findNavController(requireActivity(),R.id.nav_host_fragment).navigate(R.id.nav_sales);
                                    lottieAlertDialog.dismiss();
                                }
                            })
                    .build();
                     alertDialog.setCancelable(false);
                     alertDialog.show();

            new BillGenerator(customerId,receivedCash,balanceCash,deliveryGuid,masterPayModeGuid);
            EmptyCart();

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
        SQLiteDatabase db = getContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null);
        db.execSQL("delete from cart");
        db.close();
    }


}