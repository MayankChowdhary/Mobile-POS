package com.retailstreet.mobilepos.View.ui.VendorInfo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.toptoche.searchablespinnerlibrary.SearchableSpinner

class VendorInfoFragment : Fragment() {

    companion object {
        fun newInstance() = VendorInfoFragment()
    }

    private lateinit var viewModel: VendorInfoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vendor_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VendorInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vendorNameArray:List<StringWithTag> = getVendorName()
        var vendorID = ""
        val vendorSearchSelector: SearchableSpinner = view.findViewById(R.id.vu_search_value)
        val custSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, vendorNameArray) }!!
        custSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSearchSelector.adapter = custSearchAdapter
        vendorSearchSelector.setTitle("Select Distributor")
        vendorSearchSelector.setPositiveButton("OK")
        vendorSearchSelector.gravity = Gravity.START

        val distNameEdtText: EditText = view.findViewById(R.id.vu_name__value)
        val distContactNameEdtText: EditText = view.findViewById(R.id.vu_contact_nm__value)
        val addEdtText: EditText = view.findViewById(R.id.vu_address__value)
        val distLandlineEdtText: EditText = view.findViewById(R.id.vu_landline__value)
        val distEmailEdtText: EditText = view.findViewById(R.id.vu_email_value)
        val distCityEdtText: EditText = view.findViewById(R.id.vu_city_value)
        val distStateEdtText: EditText = view.findViewById(R.id.vu_state_value)
        val distZipEdtText: EditText = view.findViewById(R.id.vu_zip__value)
        val distCountryEdtText: EditText = view.findViewById(R.id.vu_country__value)
        val distMobileEdtText: EditText = view.findViewById(R.id.vu_mobile_value)
        val distInventoryEdtText: EditText = view.findViewById(R.id.vu_inventory_value)
        val distGstEditText: EditText = view.findViewById(R.id.vu_gst_value)
        val distPanEditText: EditText = view.findViewById(R.id.vu_pan_value)
        val distActiveEditText: EditText = view.findViewById(R.id.vu_active_value)

        var vendorPosition:Int = 0

        fun doViewsEmpty() {
            distNameEdtText.setText("")
            distContactNameEdtText.setText("")
            addEdtText.setText("")
            distLandlineEdtText.setText("")
            distEmailEdtText.setText("")
            distCityEdtText.setText("")
            distStateEdtText.setText("")
            distZipEdtText.setText("")
            distCountryEdtText.setText("")
            distMobileEdtText.setText("")
            distInventoryEdtText.setText("")
            distGstEditText.setText("")
            distPanEditText.setText("")
            distActiveEditText.setText("")


        }
        val backBtn = view.findViewById<Button>(R.id.submit_update_vendor)
        backBtn.setOnClickListener {

            activity?.let { it1 -> Navigation.findNavController(it1, R.id.nav_host_fragment).popBackStack() }

        }
        val searchLayout = view.findViewById<View>(R.id.vu_search_layout)
        vendorSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val custSelected = parent.getItemAtPosition(position) as StringWithTag
                vendorID = custSelected.tag
                Log.d("VendorSelected", "onItemSelected: Tag= $vendorID")

                if (vendorID.isBlank()){
                    enableDisableView(view, false, searchLayout)
                    doViewsEmpty()
                    backBtn.isEnabled = true
                }
                else{
                    vendorPosition = position
                    enableDisableView(view, true, searchLayout)
                    backBtn.isEnabled = true
                    val vendorData =  getFromMasterVendor(vendorID)
                    distNameEdtText.setText(vendorData[0])
                    distContactNameEdtText.setText(vendorData[1])
                    addEdtText.setText(vendorData[2])
                    distLandlineEdtText.setText(vendorData[3])
                    distEmailEdtText.setText(vendorData[4])
                    distCityEdtText.setText(vendorData[5])
                    distStateEdtText.setText(vendorData[6])
                    distZipEdtText.setText(vendorData[7])
                    distCountryEdtText.setText(vendorData[8])
                    distMobileEdtText.setText(vendorData[9])
                    distInventoryEdtText.setText(vendorData[10])
                    distGstEditText.setText(vendorData[11])
                    distPanEditText.setText(vendorData[12])
                    distActiveEditText.setText(vendorData[13])
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun enableDisableView(view: View, enabled: Boolean, viewIgnore: View) {
        view.isEnabled = false
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {

                if(viewIgnore == group.getChildAt(idx))
                    continue

                enableDisableView(group.getChildAt(idx), enabled, viewIgnore)
            }
        }
    }
    private fun getVendorName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT DISTRIBUTOR", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select DSTR_ID, MOBILE, DSTR_NM from retail_str_dstr";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(0)
                val name = cursor.getString(2) +" - "+ cursor.getString(1)
                list.add(StringWithTag(name, id))
                cursor.moveToNext()
            }
        }
        Log.d("VendorNamesTable", "getVendorType: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }




    private fun getFromMasterVendor(distId: String): Array<String> {
        val viewData: Array<String> = arrayOf(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
        try {

            val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val selectQuery = "SELECT DSTR_NM, DSTR_CNTCT_NM, ADD_1, TELE, EMAIL, CITY,VENDORSTATE,ZIP,MASTERCOUNTRYID,MOBILE,DSTR_INV,GST,PAN,VENDOR_STATUS FROM retail_str_dstr where DSTR_ID ='$distId'"
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
                viewData[8]= (if(cursor.getString(8).isBlank()) " " else cursor.getString(8))
                viewData[9]= (if(cursor.getString(9).isBlank()) " " else cursor.getString(9))
                viewData[10]= (if(cursor.getString(10).isBlank()) " " else cursor.getString(10))
                viewData[11]= (if(cursor.getString(11).isBlank()) " " else cursor.getString(11))
                viewData[12]= (if(cursor.getString(12).isBlank()) " " else cursor.getString(12))
                viewData[13]= (if(cursor.getString(13).isBlank()) " " else cursor.getString(13))

            }
            cursor.close()
            mydb.close()
            Log.d("DataRetrieved", "getFromCustomerMaster:")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return viewData
    }
}