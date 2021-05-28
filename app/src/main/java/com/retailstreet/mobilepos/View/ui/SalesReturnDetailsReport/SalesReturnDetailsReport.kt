package com.retailstreet.mobilepos.View.ui.SalesReturnDetailsReport

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.evrencoskun.tableview.TableView
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.View.ui.SalesReturnDetailsReport.TableViewComponents.MyTableAdapter
import com.retailstreet.mobilepos.View.ui.SalesReturnDetailsReport.TableViewComponents.MyTableViewListener
import com.retailstreet.mobilepos.View.ui.SalesReturnDetailsReport.TableViewComponents.User

import java.util.*
import kotlin.concurrent.thread

class SalesReturnDetailsReport : Fragment() {

    companion object {
        fun newInstance() = SalesReturnDetailsReport()
    }

    private lateinit var viewModel: SalesReturnDetailsViewModel
    private var mTableView: TableView? = null
    private var mTableAdapter: MyTableAdapter? = null
    private var mProgressBar: ProgressBar? = null
    lateinit var emptyReport: LinearLayout
    private var masterId:String =""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sales_return_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SalesReturnDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myArgs = SalesReturnDetailsReportArgs.fromBundle(requireArguments())
        masterId = myArgs.masterID
        Log.d("MasterIDReceived", "onViewCreated: $masterId")
        mTableView = view.findViewById(R.id.my_TableView)
        mProgressBar = view.findViewById(R.id.progressBar)
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
            userList = getSalesDetailsData(masterId)

            requireActivity().runOnUiThread(Runnable {
                // Stuff that updates the UI
                mTableAdapter!!.setUserList(userList)
                hideProgressBar()

                if(userList.isEmpty()){
                    emptyReport.visibility = View.VISIBLE
                    mTableView?.visibility  = View.GONE
                }else{

                    emptyReport.visibility = View.GONE
                    mTableView?.visibility  = View.VISIBLE
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

    private fun getSalesDetailsData(masterID: String): List<User> {
        val list: MutableList<User> = ArrayList()
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query = "Select sm.PROD_NM, sm.BARCODE, sm.S_PRICE,rd.RETURNQUANTITY  from customerReturnDetail rd INNER JOIN retail_str_stock_master sm ON rd.MASTER_PRODUCT_ITEM_ID = sm.ITEM_GUID where rd.CUSTOMER_RETURNS_MASTER_ID ='$masterID'"
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val user = User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3))
                list.add(user)
                Log.d("VendorItemAdded", "getSalesDetailsData: "+cursor.getString(0))
                cursor.moveToNext()
            }
        }
        Log.d("VendorTable", "getVendorDetails: Successfully Fetched - Size: "+list.size)
        cursor.close()
        mydb.close()
        return list
    }

}