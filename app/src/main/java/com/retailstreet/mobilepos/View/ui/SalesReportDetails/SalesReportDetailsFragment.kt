package com.retailstreet.mobilepos.View.ui.SalesReportDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evrencoskun.tableview.TableView
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.View.ui.SalesReportDetails.TableViewComponents.MyTableAdapter
import com.retailstreet.mobilepos.View.ui.SalesReportDetails.TableViewComponents.MyTableViewListener
import com.retailstreet.mobilepos.View.ui.SalesReportDetails.TableViewComponents.User
import java.util.*
import kotlin.concurrent.thread

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */


class SalesReportDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = SalesReportDetailsFragment()
    }
    private var mTableView: TableView? = null
    private var mTableAdapter: MyTableAdapter? = null
    private var mProgressBar: ProgressBar? = null
    private var payModeLayout:LinearLayout?=null
    lateinit var emptyReport: LinearLayout
    private var billno:String = ""
    private var masterId:String =""
    var billPayData:HashMap<String, String> = HashMap<String, String>()
    var PayLedgerData:HashMap<String, String> = HashMap<String, String>()

    private lateinit var viewModel: SalesReportDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        return inflater.inflate(R.layout.fragment_sales_report_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SalesReportDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myArgs = SalesReportDetailsFragmentArgs.fromBundle(requireArguments())
        billno = myArgs.rowid
        masterId = myArgs.masterId
        Log.d("MasterIDReceived", "onViewCreated: $masterId")
        mTableView = view.findViewById(R.id.my_TableView)
        mProgressBar = view.findViewById(R.id.progressBar)
        payModeLayout = view.findViewById(R.id.srd_pay_mode_layout)
        emptyReport = view.findViewById(R.id.empty_report_view)
        initializeTableView(mTableView)

    }

    private fun initializeTableView(tableView: TableView?) {
        // Create TableView Adapter
        mTableAdapter = MyTableAdapter(context)
        tableView?.setAdapter(mTableAdapter)
        tableView?.tableViewListener = MyTableViewListener(tableView)
        var userList:List<User>
        showProgressBar()
        thread {
            userList = getSalesDetailsData(billno)
            billPayData=getPayModeDetails(masterId)
            PayLedgerData = getPayLedgerDetails(billno)

            requireActivity().runOnUiThread(Runnable {
                // Stuff that updates the UI
                mTableAdapter!!.setUserList(userList)
                hideProgressBar()
                val lparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lparams.setMargins(50, 0, 0, 0);
                val typedValue = TypedValue()
                activity?.getTheme()?.resolveAttribute(R.attr.colorPrimary, typedValue, true)
                val color = typedValue.data
                for (key in billPayData.keys) {
                    println("Element at key $key = ${billPayData[key]}")
                    val tv = TextView(context)
                    tv.layoutParams = lparams
                    tv.setTextColor(color);
                    tv.text = getPayMode(key) + "\n" + billPayData[key] + " ₹"
                    payModeLayout!!.addView(tv)
                }

                for (key in PayLedgerData.keys) {
                    println("Element at key $key = ${PayLedgerData[key]}")
                    val tv = TextView(context)
                    tv.layoutParams = lparams
                    tv.setTextColor(color);
                    tv.text = key+ "\n" + PayLedgerData[key] + " ₹"
                    payModeLayout!!.addView(tv)
                }

                if(userList.isEmpty()){
                    emptyReport.visibility = View.VISIBLE
                    mTableView?.visibility  = View.GONE
                    payModeLayout?.visibility = View.GONE
                }else{

                    emptyReport.visibility = View.GONE
                    mTableView?.visibility  = View.VISIBLE
                    payModeLayout?.visibility = View.VISIBLE
                }
            })
        }

    }

    private fun showProgressBar() {
        mProgressBar!!.visibility = View.VISIBLE
        mTableView!!.visibility = View.INVISIBLE
    }

    fun hideProgressBar() {
        mProgressBar!!.visibility = View.INVISIBLE
        mTableView!!.visibility = View.VISIBLE
    }



    private fun getSalesDetailsData(billno: String): List<User> {
        val list: MutableList<User> = ArrayList()
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        mydb.execSQL("DROP TABLE IF EXISTS tmp_sales_report_details")
        mydb.execSQL("CREATE TABLE IF NOT EXISTS tmp_sales_report_details AS SELECT pm.PROD_NM, bd.MRP, bd.QTY, bd.TOTALVALUE,  bd.DISCOUNT_VALUE, bd.SGST, bd.CGST  FROM retail_store_prod_com pm INNER JOIN retail_str_sales_detail bd ON bd.ITEM_GUID = pm.ITEM_GUID WHERE bd.BILLNO = '$billno'")
        mydb.execSQL("UPDATE tmp_sales_report_details SET  MRP = TOTALVALUE/QTY ")
        val query = "Select * from tmp_sales_report_details"
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val user = User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6))
                list.add(user)
                cursor.moveToNext()
            }
        }
        Log.d("SalesTable", "getSalesDetails: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getPayModeDetails(masterId: String): HashMap<String, String> {
        val hashMap:HashMap<String, String> = HashMap<String, String>()
        val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val cursor = mydb.rawQuery("select MASTERPAYMODEGUID, PAYAMOUNT from billpaydetail where BILLMASTERID = '$masterId'", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                hashMap.put(cursor.getString(0), cursor.getString(1))
                Log.d("PayMoodeRetrieved", "getCreditDetails: " + cursor.getString(0))
                cursor.moveToNext()
            }
        }
        cursor.close()
        mydb.close()
        return hashMap
    }

    private fun getPayLedgerDetails(billNo: String): HashMap<String, String> {
        val hashMap:HashMap<String, String> = HashMap<String, String>()
        val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val cursor = mydb.rawQuery("select ADDITIONALPARAM6, GRANDTOTAL from customerLedger where BILLNO = '$billNo'", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val advance:String = cursor.getString(0)
                val credit:String = cursor.getString(1)

                if(advance.trim().isNotEmpty()){

                    try {
                        if(advance.toDouble()>0){
                            hashMap.put("ADVANCE", advance)
                            Log.d("PayMoodeRetrieved", "getCreditDetails: ADVANACE $advance")
                        }
                    } catch (e: Exception) {
                    }

                }

                if(credit.trim().isNotEmpty()){

                    try {
                        if(credit.toDouble()>0){
                            hashMap.put("CREDIT", credit)
                            Log.d("PayMoodeRetrieved", "getCreditDetails: CREDIT $credit")
                        }
                    } catch (e: Exception) {
                    }
                }

                cursor.moveToNext()
            }
        }
        cursor.close()
        mydb.close()
        return hashMap
    }


    private fun getPayMode(paymodeId: String): String {
        var payMode = " "
        val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val cursor = mydb.rawQuery("select PAYMODE from masterpaymode where PAYMODE_GUID = '$paymodeId'", null)
        if (cursor.moveToFirst()) {
                payMode = cursor.getString(0)
                Log.d("PayModeNameRetrieved", "getPayMode: $payMode")

        }
        cursor.close()
        mydb.close()
        return payMode
    }
}