package com.retailstreet.mobilepos.View.ui.CreditPayment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import kotlin.math.abs
/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */
class CreditPayFragment : Fragment() {

    companion object {
        fun newInstance() = CreditPayFragment()
    }

    private lateinit var viewModel: CreditPayViewModel
    private var custGuid:String = " "
    private var custBalance:String = " "
    var resumeFromBack = false
    lateinit var custSearchSelector: SearchableSpinner


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_credit_pay, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreditPayViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        if(resumeFromBack) {
            custSearchSelector.setSelection(0)
            resumeFromBack=false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val viewWrapper:LinearLayout = view.findViewById(R.id.cp_details_wrapper)
        val custNameTv: TextView = view.findViewById(R.id.cp_name_value)
        val custMobileTv: TextView = view.findViewById(R.id.cp_mobile_value)
        val custBalanceTv: TextView = view.findViewById(R.id.cp_balance_value)
        val settleBtn: Button = view.findViewById(R.id.cp_settle_submit)
        val ledgerButton: Button = view.findViewById(R.id.cp_option_leger)
        val billDetailsBtn: Button = view.findViewById(R.id.cp_option_bills)

        ledgerButton.setOnClickListener {
            val actionNavLedgerFragment = CreditPayFragmentDirections.actionNavCreditPayToCustomerLedgerFragment(custGuid)
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(actionNavLedgerFragment)

        }

        billDetailsBtn.setOnClickListener {
            val actionNavSalesReport = CreditPayFragmentDirections.actionNavCreditPayToNavSalesReport(custGuid)
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(actionNavSalesReport)
        }


        settleBtn.setOnClickListener {

            resumeFromBack = true
            val actionNavCreditPayToPayment = CreditPayFragmentDirections.actionNavCreditPayToNavPayment(custBalance, custGuid, "", true,"","")
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(actionNavCreditPayToPayment)

        }


        val custNameArray:List<StringWithTag> = getCustomerName()
         custSearchSelector = view.findViewById(R.id.cp_search_value)
        val custSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, custNameArray) }!!
        custSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        custSearchSelector.adapter = custSearchAdapter
        custSearchSelector.setTitle("Select Customer")
        custSearchSelector.setPositiveButton("OK")
        custSearchSelector.gravity = Gravity.START

        custSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val custSelected = parent.getItemAtPosition(position) as StringWithTag
                custGuid = custSelected.tag
                if(custGuid.isNotEmpty()){
                    viewWrapper.visibility =View.VISIBLE
                    val custNameMob:StringWithTag = getCustNameMob(custGuid)
                    custNameTv.text = custNameMob.string
                    custMobileTv.text = custNameMob.tag
                    custBalance = getCustBalance(custGuid)
                    custBalance = abs(custBalance.toDouble()).toString()
                    custBalanceTv.text = custBalance+" ₹"
                }else{

                    viewWrapper.visibility =View.GONE
                    custNameTv.text = ""
                    custMobileTv.text = ""
                    custBalanceTv.text = ""
                }

                Log.d("CustomerSelected", "onItemSelected: Tag= $custGuid")

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



    }


    private fun getCustomerName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT CUSTOMER", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select CUSTOMERGUID, MOBILE_NO, NAME from retail_cust";
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

    private fun getCustNameMob(custGuid: String): StringWithTag {
        var list:StringWithTag = StringWithTag("", "")
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query = "select  MOBILE_NO, NAME from retail_cust Where CUSTOMERGUID = '$custGuid'";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val mob = cursor.getString(0)
                val name = cursor.getString(1)
              list =  StringWithTag(name, mob)
                cursor.moveToNext()
            }
        }
        Log.d("CustomerOneValue", "getCustomerType: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getCustBalance(custGuid: String): String {
        var balance="0.00";
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select  GRANDTOTAL from retail_credit_cust Where CUSTOMERGUID = '$custGuid'";
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                     balance = cursor.getString(0)
                    cursor.moveToNext()
                }
            }
            if(balance.isEmpty())
                balance= "0.00"
            Log.d("CustomerOneValue", "getCustomerType: Successfully Fetched")
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
            balance = "0.00"
        }
        return balance
    }


}