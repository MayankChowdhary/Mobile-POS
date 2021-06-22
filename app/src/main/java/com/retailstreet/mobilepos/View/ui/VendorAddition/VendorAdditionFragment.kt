package com.retailstreet.mobilepos.View.ui.VendorAddition

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
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerVendorMaster
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.Utils.Vibration
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.*

class VendorAdditionFragment : Fragment() {

    companion object {
        fun newInstance() = VendorAdditionFragment()
    }

    private lateinit var viewModel: VendorAdditionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vendor_addition, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VendorAdditionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val vendorName: EditText = view.findViewById(R.id.va_name_value)
        val vendorAddr: EditText = view.findViewById(R.id.va_addr_value)
        val vendorLand: EditText = view.findViewById(R.id.va_land_value)
        val vendorEmail: EditText = view.findViewById(R.id.va_email_value)
        val vendorCountry: EditText = view.findViewById(R.id.va_country__value)
        val vendorPan: EditText = view.findViewById(R.id.va_pan_value)
        val vendorCity: EditText = view.findViewById(R.id.va_city_value)
        val vendorZip: EditText = view.findViewById(R.id.va_zip_value)
        val vendorMob: EditText = view.findViewById(R.id.va_mobile_value)
        val vendorGst: EditText = view.findViewById(R.id.va_gst_value)
        val submit_btn: Button = view.findViewById(R.id.submit_add_vendor)


        var vName = ""
        var vAddr = ""
        var vLand = ""
        var vEmail= ""
        var vCountry = ""
        var vPan = ""
        var vpayTerms = ""
        var vCity = ""
        var vZip = ""
        var vInventory = ""
        var vMobile=""
        var vState = ""
        var vGSt= ""
        var stateGuid = ""
        var regularVendor = "N"


        val stateTypeArray:List<StringWithTag> = getStateType()
        val stateTypeSelector: SearchableSpinner = view.findViewById(R.id.va_state_value)
        val stateTypeAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box, stateTypeArray) }!!
        stateTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateTypeSelector.adapter = stateTypeAdapter
        stateTypeSelector.setTitle("Select State")
        stateTypeSelector.setPositiveButton("OK")
        stateTypeSelector.gravity = Gravity.START

        val payTermArray:Array<String> = resources.getStringArray(R.array.paymentTerms)
        val payTermSelector: Spinner = view.findViewById(R.id.va_payterm_value)
        val payTermAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,payTermArray) }!!
        payTermAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        payTermSelector.adapter = payTermAdapter


        val inventoryArray:Array<String> = resources.getStringArray(R.array.inventory)
        val inventorySelector: Spinner = view.findViewById(R.id.va_inventory_value)
        val inventoryAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,inventoryArray) }!!
        inventoryAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        inventorySelector.adapter = inventoryAdapter

        val regVendorArray:Array<String> = resources.getStringArray(R.array.regVendor)
        val regVendorSelector: Spinner = view.findViewById(R.id.va_regular_value)
        val regVendorAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_center_box,regVendorArray) }!!
        regVendorAdapter.setDropDownViewResource(R.layout.spinner_layout_centre)
        regVendorSelector.adapter = regVendorAdapter


        stateTypeSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val stateSelected = parent.getItemAtPosition(position) as StringWithTag
                vState = stateSelected.string
                stateGuid = stateSelected.tag

                Log.d("StateTypeSelected", "onItemSelected: Tag= $vState")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        payTermSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val payTermSelected = parent.getItemAtPosition(position) as String
                vpayTerms = payTermSelected

                Log.d("StateTypeSelected", "onItemSelected: Tag= $vpayTerms")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        inventorySelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val inventorySelected = parent.getItemAtPosition(position) as String
                vInventory = if(inventorySelected == "NO"){
                    "0"
                }else{
                    "1"
                }

                Log.d("StateTypeSelected", "onItemSelected: Tag= $vInventory")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        regVendorSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val inventorySelected = parent.getItemAtPosition(position) as String
                regularVendor = if(inventorySelected == "NO"){
                    "N"
                }else{
                    "Y"
                }

                Log.d("regVendorSelector", "onItemSelected: Tag= $regularVendor")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        fun doViewsEmpty() {

             vendorName.setText("")
             vendorAddr.setText("")
             vendorLand.setText("")
             vendorEmail.setText("")
             vendorCountry.setText("")
             vendorPan.setText("")
             vendorCity.setText("")
             vendorZip.setText("")
             vendorMob.setText("")
             vendorGst.setText("")

            payTermSelector.setSelection(0)
            regVendorSelector.setSelection(0)
            stateTypeSelector.setSelection(0)
            vendorName.requestFocus()
        }



        submit_btn.setOnClickListener {

            vName = vendorName.text.toString()
            vAddr = vendorAddr.text.toString()
            vLand = vendorLand.text.toString()
            vEmail = vendorEmail.text.toString()
            vCountry = vendorCountry.text.toString()
            vPan = vendorPan.text.toString()
            vCity = vendorCity.text.toString()
            vZip = vendorZip.text.toString()
            vMobile = vendorMob.text.toString()
            vGSt = vendorGst.text.toString()

            val allStringsArray:Array<String> = arrayOf(vName,vMobile)

            if(!validateStrings(allStringsArray)){
                Vibration.vibrate(300)
                Toast.makeText(context, "Please fill up all Mandatory (*) fields first!", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }

            ControllerVendorMaster( vName, vAddr,vCity, vMobile, vEmail, vGSt, vPan, vZip, vLand,vInventory, vState, vpayTerms, regularVendor)

            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_SUCCESS)
                .setTitle("Distributor Added")
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

}