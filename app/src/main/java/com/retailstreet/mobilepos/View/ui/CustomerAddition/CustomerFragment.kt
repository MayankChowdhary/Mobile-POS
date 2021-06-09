package com.retailstreet.mobilepos.View.ui.CustomerAddition

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerCustomerMaster
import com.retailstreet.mobilepos.Controller.ControllerStoreConfig
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.*

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

class CustomerFragment : Fragment() {

    companion object {
        fun newInstance() = CustomerFragment()
    }

    private lateinit var viewModel: CustomerViewModel
    private var isIndia:Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_customer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomerViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isIndia = ControllerStoreConfig().isIndia
        val custNameEdtText: EditText = view.findViewById(R.id.c_name__value)
        val custMobileEdtText: EditText = view.findViewById(R.id.c_mobile_value)
        val custEmailEdtText: EditText = view.findViewById(R.id.c_email_value)
        val custAgeEdtText: EditText = view.findViewById(R.id.c_age_value)
        val custGSTEdtText: EditText = view.findViewById(R.id.c_gst_value)
        val custPanEdtText: EditText = view.findViewById(R.id.c_pan_value)
        val custBalanceEdittext: EditText = view.findViewById(R.id.c_balance_value)
        val custAdvanceEdtText: EditText = view.findViewById(R.id.c_advance_value)
        val custCreditLimitEdtText: EditText = view.findViewById(R.id.c_credit_limit_value)
        val custAdd1EdtText: EditText = view.findViewById(R.id.c_add1_value)
        val custAdd2EdtText: EditText = view.findViewById(R.id.c_add2_value)
        val streetEdtText: EditText = view.findViewById(R.id.c_street_value)
        val pincodeEdtText: EditText = view.findViewById(R.id.c_pin_value)
        val cityEdtText: EditText = view.findViewById(R.id.c_city_value)
        val submit_btn: Button = view.findViewById(R.id.submit_add_customer)
        val addressSwitch: SwitchMaterial = view.findViewById(R.id.ca_address_switch)
        val addressLayout:LinearLayout = view.findViewById(R.id.address_master_layout)
        val gstLayout:LinearLayout = view.findViewById(R.id.c_gst_layout)
        val panLayout:LinearLayout = view.findViewById(R.id.c_pan_layout)

        if(!isIndia){

            gstLayout.visibility = View.GONE
            panLayout.visibility = View.GONE

        }

        enableDisableView(addressLayout,false)

        var custName = " "
        var custMobile= " "
        var custEmail= " "
        var custAge= " "
        var custGender= " "
        var custGST= " "
        var custPAN= " "
        var custBalance= " "
        var custAdvance= " "
        var custCreditLimit= " "
        var custType= " "
        var custCategory= " "
        var custCreditType= " "
        var custAddType= " "
        var custAdd1= " "
        var custAdd2= " "
        var custStreet= " "
        var custPincode= " "
        var custState= " "
        var custStateGuid= " "
        var custTypeGuid= " "
        var custCatId = " "
        var custCity = " "

    addressSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

        if(isChecked){

            enableDisableView(addressLayout,true)
        }else{
            enableDisableView(addressLayout,false)
        }

    }

        val custCatTitle: TextView = view.findViewById(R.id.c_cat_type_title)
        custCatTitle.isSelected = true

        val creditCustTitle: TextView = view.findViewById(R.id.c_credit_type_title)
        creditCustTitle.isSelected = true

        val addTypeTitle: TextView = view.findViewById(R.id.c_add_type_title)
        addTypeTitle.isSelected = true

        val streetAreaTitle: TextView = view.findViewById(R.id.c_street_title)
        streetAreaTitle.isSelected = true

        val genderArray:Array<String> = arrayOf("MALE","FEMALE","OTHERS")
        val genderSelector: Spinner = view.findViewById(R.id.c_gender_value)
        val genderAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,genderArray) }!!
        genderAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        genderSelector.adapter = genderAdapter

        val custCatArray:Array<String> = arrayOf("BRONZE","SILVER","GOLD")
        val custCatSelector: Spinner = view.findViewById(R.id.c_cat_type_value)
        val custCatAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,custCatArray) }!!
        custCatAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        custCatSelector.adapter = custCatAdapter

        val creditCustArray:Array<String> = arrayOf("NO","YES")
        val creditCustSelector: Spinner = view.findViewById(R.id.c_credit_type_value)
        val creditCustAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,creditCustArray) }!!
        creditCustAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        creditCustSelector.adapter = creditCustAdapter

        val addTypeArray:Array<String> = arrayOf("HOME","OFFICE","OTHERS")
        val addTypeSelector: Spinner = view.findViewById(R.id.c_add_type_value)
        val addTypeAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,addTypeArray) }!!
        addTypeAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        addTypeSelector.adapter = addTypeAdapter

        val custTypeArray:List<StringWithTag> = getCustomerType()
        val custTypeSelector: Spinner = view.findViewById(R.id.c_cust_type_value)
        val custTypeAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,custTypeArray) }!!
        custTypeAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        custTypeSelector.adapter = custTypeAdapter
        custTypeSelector.setSelection(3)


        val stateTypeArray:List<StringWithTag> = getStateType()
        val stateTypeSelector: SearchableSpinner = view.findViewById(R.id.c_state_value)
        val stateTypeAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box, stateTypeArray) }!!
        stateTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateTypeSelector.adapter = stateTypeAdapter
        stateTypeSelector.setTitle("Select State")
        stateTypeSelector.setPositiveButton("OK")
        stateTypeSelector.gravity = Gravity.START


        genderSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                custGender = parent.getItemAtPosition(position) as String
                Log.d("GenderSelected", "onItemSelected: Tag= $custGender")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        custCatSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                custCategory = parent.getItemAtPosition(position) as String
                custCatId = position.toString()
                Log.d("CategorySelected", "onItemSelected: Tag= $custCategory")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        creditCustSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                custCreditType = parent.getItemAtPosition(position) as String
                custCreditType = if(custCreditType == "NO"){
                    "0"
                }else{
                    "1"
                }
                Log.d("CreditTypeSelected", "onItemSelected: Tag= $custCreditType")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        addTypeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                custAddType = parent.getItemAtPosition(position) as String
                Log.d("AddressTypeSelected", "onItemSelected: Tag= $custAddType")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        custTypeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val custTypeSelected = parent.getItemAtPosition(position) as StringWithTag
                custTypeGuid = custTypeSelected.tag
                custType = custTypeSelected.string

                Log.d("CustTypeSelected", "onItemSelected: Tag= $custType")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        stateTypeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val stateSelected = parent.getItemAtPosition(position) as StringWithTag
                custStateGuid = stateSelected.tag
                custState = stateSelected.string

                Log.d("StateTypeSelected", "onItemSelected: Tag= $custState")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        fun doViewsEmpty() {
            custNameEdtText.setText("")
            custMobileEdtText.setText("")
            custEmailEdtText.setText("")
            custAgeEdtText.setText("")
            custGSTEdtText.setText("")
            custPanEdtText.setText("")
            custBalanceEdittext.setText("0")
            custAdvanceEdtText.setText("0")
            custCreditLimitEdtText.setText("0")
            custAdd1EdtText.setText("")
            custAdd2EdtText.setText("")
            streetEdtText.setText("")
            pincodeEdtText.setText("")
            cityEdtText.setText("")

            genderSelector.setSelection(0)
            custTypeSelector.setSelection(3)
            custCatSelector.setSelection(0)
            creditCustSelector.setSelection(0)
            addTypeSelector.setSelection(0)
            stateTypeSelector.setSelection(0)

        }

       submit_btn.setOnClickListener {

           custName = custNameEdtText.text.toString()
           custMobile = custMobileEdtText.text.toString()
           custEmail = custEmailEdtText.text.toString()
           custAge = custAgeEdtText.text.toString()
           custGST = custGSTEdtText.text.toString()
           custPAN = custPanEdtText.text.toString()
           custBalance = if(custBalanceEdittext.text.toString().isBlank()) "0.00" else custBalanceEdittext.text.toString()
           custAdvance = if(custAdvanceEdtText.text.toString().isBlank()) "0.00" else custAdvanceEdtText.text.toString()
           custCreditLimit = if(custCreditLimitEdtText.text.toString().isBlank()) "0.00" else custCreditLimitEdtText.text.toString()
          // custCreditLimit = (custCreditLimit.toDouble() + (custAdvance.toDouble() - custBalance.toDouble())).toString()
           custAdd1 = custAdd1EdtText.text.toString()
           custAdd2 = custAdd2EdtText.text.toString()
           custStreet = streetEdtText.text.toString()
           custPincode = pincodeEdtText.text.toString()
           custCity = cityEdtText.text.toString()

           var allStringsArray:Array<String>? = null
           allStringsArray = if(addressSwitch.isChecked){

               arrayOf(custName,custMobile,custAdd1,custAdd2,custStreet,custPincode,custState,custCity);

           }else{

               arrayOf(custName,custMobile);

           }


           if(!validateStrings(allStringsArray)){

               Toast.makeText(context, "Please fill up all Mandatory (*) fields first!", Toast.LENGTH_LONG).show();
               return@setOnClickListener
           }

            ControllerCustomerMaster(custStateGuid,custCity,custPincode,custStreet,custAdd1,custAdd2,custAddType, custCreditLimit,  custBalance,  custAdvance,  custCatId,  custTypeGuid,  custPAN,  custGST,  custCreditType,  custMobile,  custGender,  custType,  custCategory,  custName ,  custEmail,  custAge,addressSwitch.isChecked)
           val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_SUCCESS)
                   .setTitle("Customer Added")
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


    private fun getCustomerType(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
       // list.add(StringWithTag("NO CUST TYPE", ""))
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

    private fun getStateType(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("NO STATE", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select STATE_GUID, STATE from masterState";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(1)
                val name = cursor.getString(0)
                list.add(StringWithTag(id, name))
                cursor.moveToNext()
            }
        }
        Log.d("masterStateTable", "getmasterState: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
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

    private fun enableDisableView(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {
                enableDisableView(group.getChildAt(idx), enabled)
            }
        }
    }


}