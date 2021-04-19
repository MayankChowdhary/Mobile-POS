package com.retailstreet.mobilepos.View.ui.CustomerUpdate

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.database.SQLException
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerCreditPay
import com.retailstreet.mobilepos.Controller.ControllerCustomerMaster
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.Utils.WorkManagerSync
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner


class CustomerUpdateFragment : Fragment() {

    companion object {
        fun newInstance() = CustomerUpdateFragment()
    }

    private lateinit var viewModel: CustomerUpdateViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_customer_update, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomerUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val custNameEdtText: EditText = view.findViewById(R.id.cu_name__value)
        val custMobileEdtText: EditText = view.findViewById(R.id.cu_mobile_value)
        val custEmailEdtText: EditText = view.findViewById(R.id.cu_email_value)
        val custPanEdtText: EditText = view.findViewById(R.id.cu_pan_value)
        val custGstEdtText: EditText = view.findViewById(R.id.cu_gst_value)
        val custAdvanceTextView: TextView = view.findViewById(R.id.cu_advance_title)
        val custCreditLimitEditText: EditText = view.findViewById(R.id.cu_credit_limit_value)
        val submitCustUpdate:Button = view.findViewById(R.id.submit_update_customer)
        val updateCashButton:Button = view.findViewById(R.id.cu_add_cash_btn)
        var custPosition:Int = 0

        var custName = " "
        var custMobile= " "
        var custEmail= " "
        var custGST= " "
        var custPAN= " "
        var custType= " "
        var custCreditType= " "
        var custTypeGuid= " "
        var custId = " "
        var custGuid = " "
        var custAdvance = " "
        var custCreditLImit = " "
        var custAdvanceOld = "0.00"




        val custNameArray:List<StringWithTag> = getCustomerName()
        val custSearchSelector: SearchableSpinner = view.findViewById(R.id.cu_search_value)
        val custSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, custNameArray) }!!
        custSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        custSearchSelector.adapter = custSearchAdapter
        custSearchSelector.setTitle("Select Customer")
        custSearchSelector.setPositiveButton("OK")
        custSearchSelector.gravity = Gravity.START


        val custTypeArray:List<StringWithTag> = getCustomerType()
        val custTypeSelector: Spinner = view.findViewById(R.id.cu_cust_type_value)
        val custTypeAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box, custTypeArray) }!!
        custTypeAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        custTypeSelector.adapter = custTypeAdapter

        val creditCustTitle: TextView = view.findViewById(R.id.cu_credit_type_title)
        creditCustTitle.isSelected = true
        val creditCustArray:Array<String> = arrayOf("0", "1")
        val creditCustSelector: Spinner = view.findViewById(R.id.cu_credit_type_value)
        val creditCustAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box, creditCustArray) }!!
        creditCustAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        creditCustSelector.adapter = creditCustAdapter
        fun doViewsEmpty() {
            custNameEdtText.setText("")
            custMobileEdtText.setText("")
            custEmailEdtText.setText("")
            custGstEdtText.setText("")
            custPanEdtText.setText("")
            custCreditLimitEditText.setText("")
            custAdvanceTextView.setText("Advance:\n0.00 ₹")
            custTypeSelector.setSelection(0)
            creditCustSelector.setSelection(0)
            custSearchSelector.setSelection(0)

        }


        val search = view.findViewById<View>(R.id.cu_search_layout)
        custSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val custSelected = parent.getItemAtPosition(position) as StringWithTag
                 custId = custSelected.tag
                Log.d("GenderSelected", "onItemSelected: Tag= $custId")

                if (custId.isBlank()){
                    enableDisableView(view, false, search)
                    doViewsEmpty()
                    }
                else{
                    custPosition = position
                    enableDisableView(view, true, search)
                    custMobileEdtText.isEnabled = false
                    val customerData =  getFromMasterCustomer(custId)
                    custMobileEdtText.setText(customerData[0])
                    custNameEdtText.setText(customerData[1])
                    custEmailEdtText.setText(customerData[2])
                    custPanEdtText.setText(customerData[3])
                    custGstEdtText.setText(customerData[4])
                    custTypeSelector.setSelection(getIndex(custTypeSelector, customerData[5]))
                    val spinnerPosition: Int = creditCustAdapter.getPosition(customerData[6])
                    creditCustSelector.setSelection(spinnerPosition)
                    custGuid = customerData[7]
                    custAdvanceOld = customerData[8]
                    custAdvanceTextView.setText("Advance:\n" + customerData[8] + " ₹")
                    custCreditLimitEditText.setText(customerData[9])
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        updateCashButton.setOnClickListener {

            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle("Enter Advance Amount")
            val input = EditText(activity)
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            builder.setView(input)
            builder.setPositiveButton("UPDATE") { dialog, which ->

                val advanceText: String = input.text.toString()
                Log.d("AdvanceInput", "onViewCreated: Received$advanceText")

                if (custGuid.trim().isNotEmpty()) {
                    custAdvance = if (advanceText.isEmpty()) {

                        (custAdvanceOld.toDouble() + 0.00).toString()
                    } else {
                        (custAdvanceOld.toDouble() + advanceText.toDouble()).toString()
                    }

                    updateAdvanceCash(custGuid, custAdvance)
                    ControllerCreditPay().updateCustAdditionLedger(custGuid, advanceText)
                    custSearchSelector.setSelection(0)
                    try {
                        WorkManagerSync(5)
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                    Handler(Looper.myLooper()!!).postDelayed({
                        custSearchSelector.setSelection(custPosition)
                    }, 100)

                    Log.d("AdvanceUpdated", "onViewCreated: Updated$custAdvance")
                } else {

                    Toast.makeText(context, "Select Customer First!", Toast.LENGTH_LONG).show();
                }
            }
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            builder.show()

        }


        custTypeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val custTypeSelected = parent.getItemAtPosition(position) as StringWithTag
                custTypeGuid = custTypeSelected.tag
                custType = custTypeSelected.string

                Log.d("StateTypeSelected", "onItemSelected: Tag= $custType")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        creditCustSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                custCreditType = parent.getItemAtPosition(position) as String
                Log.d("CreditTypeSelected", "onItemSelected: Tag= $custCreditType")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        submitCustUpdate.setOnClickListener{

            custName = custNameEdtText.text.toString()
            custMobile = custMobileEdtText.text.toString()
            custEmail = custEmailEdtText.text.toString()
            custGST = custGstEdtText.text.toString()
            custPAN = custPanEdtText.text.toString()
            custCreditLImit = custCreditLimitEditText.text.toString()




            val allStringsArray: Array<String> = arrayOf(custName, custMobile);

            if(!validateStrings(allStringsArray)){

                Toast.makeText(context, "Please fill up all Mandatory (*) fields first!", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }

            ControllerCustomerMaster(custId, custMobile, custName, custEmail, custPAN, custGST, custType, custTypeGuid, custGuid, custCreditLImit, custCreditType)
            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_SUCCESS)
                    .setTitle("Customer Updated")
                    .setDescription("Successful!")
                    .setPositiveText("OK")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {
                            dialog.dismiss()
                            doViewsEmpty()
                        }

                    })
                    .build()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
    }


    private fun getCustomerName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT CUSTOMER", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select CUST_ID, MOBILE_NO, NAME from retail_cust";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(0)
                val name = cursor.getString(2) +" - "+ cursor.getString(1)
                list.add(StringWithTag(name, id))
                cursor.moveToNext()
            }
        }
        Log.d("CustomerNamesTable", "getCustomerType: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getCustomerType(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("NO CUST TYPE", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select MASTER_CUSTOMERTYPEID, CUSTOMERTYPE from master_customer_type";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(1)
                val name = cursor.getString(0)
                list.add(StringWithTag(id, name))
                cursor.moveToNext()
            }
        }
        Log.d("CustomerTypeTable", "getCustomerType: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }


    private fun enableDisableView(view: View, enabled: Boolean, viewIgnore: View) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {

                if(viewIgnore == group.getChildAt(idx))
                    continue

                enableDisableView(group.getChildAt(idx), enabled, viewIgnore)
            }
        }
    }

    private fun getFromMasterCustomer(custId: String): Array<String> {
        val viewData: Array<String> = arrayOf(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
        try {

            val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val selectQuery = "SELECT MOBILE_NO, NAME, E_MAIL, PANNO, GSTNO, CUSTOMERTYPE,CREDIT_CUST,CUSTOMERGUID,ADVANCE_AMOUNT,CREDITLIMIT FROM retail_cust where CUST_ID ='$custId'"
            val cursor = mydb.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {

                viewData[0]= if(cursor.getString(0).isBlank()) " " else cursor.getString(0)
               viewData[1]= (if(cursor.getString(1).isBlank()) " " else cursor.getString(1))
               viewData[2]= (if(cursor.getString(2).isBlank()) " " else cursor.getString(2))
               viewData[3]= (if(cursor.getString(3).isBlank()) " " else cursor.getString(3))
               viewData[4]= (if(cursor.getString(4).isBlank()) " " else cursor.getString(4))
               viewData[5]= (if(cursor.getString(5).isBlank()) " " else cursor.getString(5))
               viewData[6]= (if(cursor.getString(6).isBlank()) " " else cursor.getString(6))
                viewData[7]= (if(cursor.getString(7).isBlank()) " " else cursor.getString(7))
                viewData[8]= (if(cursor.getString(8).isBlank()) "0.00" else cursor.getString(8))
                viewData[9]= (if(cursor.getString(9).isBlank()) "0.00" else cursor.getString(9))

            }
            cursor.close()
            mydb.close()
            Log.d("DataRetrieved", "getFromCustomerMaster:")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return viewData
    }



    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if ((spinner.getItemAtPosition(i) as StringWithTag).string.equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    private fun validateStrings(fields: Array<String>): Boolean {
        for (i in fields.indices) {
            val currentField = fields[i]
            Log.d("Validating String", "validateStrings: $currentField")
            if (currentField.trim().isEmpty()) {
                Log.d("Validating String", "validateStrings: Failed! ")
                return false
            }
        }
        return true
    }

    private fun updateAdvanceCash(custGuid: String, newAdvance: String) {
        try {
            val query = "Update retail_cust Set ADVANCE_AMOUNT = '$newAdvance', ISSYNCED = '0'  where CUSTOMERGUID = '$custGuid'"
            val db = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            db.execSQL(query)
            db.close()
            Log.d("UpdateAdvanceAmount", "UpdateQuantity: Successful$custGuid")
            Toast.makeText(context, "Advance Cash Updated!", Toast.LENGTH_LONG).show();

        } catch (e: SQLException) {
            e.printStackTrace()
            Toast.makeText(context, "Advance Update Failed!", Toast.LENGTH_LONG).show();

        }
    }
}