package com.retailstreet.mobilepos.View.ui.CustomerLedger

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.levitnudi.legacytableview.LegacyTableView
import com.levitnudi.legacytableview.LegacyTableView.BOLD
import com.levitnudi.legacytableview.LegacyTableView.OCEAN
import com.retailstreet.mobilepos.R


class CustomerLedgerFragment : Fragment() {

    companion object {
        fun newInstance() = CustomerLedgerFragment()
    }

    private lateinit var viewModel: CustomerLedgerViewModel
    private var custGuid:String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.customer_ledger_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CustomerLedgerViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val legacyTableView:LegacyTableView = view.findViewById(R.id.ledger_table_view);
        val myArgs = CustomerLedgerFragmentArgs.fromBundle(requireArguments())
        custGuid = myArgs.custGuid
        getLedgerDetails(custGuid,getFromMasterCustomer(custGuid,"NAME"))


        LegacyTableView.insertLegacyTitle("NAME", "DATE", "PAYMODE","ADVANCE AMOUNT", "CREDIT AMOUNT","DEBIT AMOUNT")
        //set table contents as string arrays
        //set table contents as string arrays

        legacyTableView.setTitle(LegacyTableView.readLegacyTitle())
        legacyTableView.setContent(LegacyTableView.readLegacyContent())

        //Add your preferred theme like this:

        //Add your preferred theme like this:
        legacyTableView.setTheme(OCEAN)

        /*
      Explore available themes
      OCEAN
      LAVICI
      GOLDALINE
      ECOKENYA
      DESKTOP
      MAASAI
      LEVICI
      ORIO
      SKELETON
      MESH*/

        //depending on the phone screen size default table scale is 100
        //you can change it using this method
        legacyTableView.setInitialScale(110);//default initialScale is zero (0)

        //if you want a smaller table, change the padding setting
        legacyTableView.setTablePadding(10)

        //to enable users to zoom in and out:

        //to enable users to zoom in and out:
        legacyTableView.setZoomEnabled(true)
        legacyTableView.setShowZoomControls(true)
        legacyTableView.setTitleFont(BOLD)
        legacyTableView.setContentTextSize(25)

        //remember to build your table as the last step
        legacyTableView.build()


    }

    private fun getLedgerDetails(custGuid: String, custName: String) {
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query = "select   ACTIONDATE, MASTERPAYMODEGUID, CREDITAMOUNT,DEBITAMOUNT,ADDITIONALPARAM6 from customerLedger Where CUSTOMERGUID = '$custGuid'";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val date = cursor.getString(0)
                val paymode:String? = getFromPayMode(cursor.getString(1))
                val creditAmount = cursor.getString(2)
                val advanceAmount = cursor.getString(4)
                val debitAmount = cursor.getString(3)
                LegacyTableView.insertLegacyContent(custName, date, paymode,advanceAmount, creditAmount,debitAmount);
                cursor.moveToNext()
            }
        }
        Log.d("LedgerViewer", "getLedger: Successfully Fetched")
        cursor.close()
        mydb.close()
    }


    private fun getFromMasterCustomer(custId: String, column: String): String{
        var result: String = ""
        try {
            val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            result = ""
            val selectQuery = "SELECT $column FROM retail_cust where CUSTOMERGUID ='$custId'"
            val cursor = mydb.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                result = cursor.getString(0)
            }
            cursor.close()
            mydb.close()
            Log.d("DataRetrieved", "getFromCustomerMaster: $custId $result")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    private fun getFromPayMode(paymodeid: String): String? {
        var result: String? = null
        try {
            val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            result = ""
            val selectQuery = "SELECT PAYMODE FROM masterpaymode where PAYMODE_GUID = '$paymodeid'"
            val cursor = mydb.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                result = cursor.getString(0)
            }
            cursor.close()
            mydb.close()
            Log.d("DataRetrieved", "getFromPaymodeMaster: $paymodeid $result")
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return result
    }
}