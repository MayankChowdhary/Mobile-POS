package com.retailstreet.mobilepos.View.ui.VendorReport

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.evrencoskun.tableview.TableView
import com.google.android.material.datepicker.MaterialDatePicker
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.View.ui.VendorReport.TableViewComponents.MyTableAdapter
import com.retailstreet.mobilepos.View.ui.VendorReport.TableViewComponents.MyTableViewListener
import com.retailstreet.mobilepos.View.ui.VendorReport.TableViewComponents.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class VendorReportFragment : Fragment() , TableViewInterface {

    companion object {
        fun newInstance() = VendorReportFragment()
    }

    private lateinit var viewModel: VendorReportViewModel
    private var mTableView: TableView? = null
    private var mTableAdapter: MyTableAdapter? = null
    private var mProgressBar: ProgressBar? = null
    lateinit var emptyReport: LinearLayout
    private var filterType:Int = 0
    private var startDate:String =""
    private var endDate:String = ""
    lateinit var spinner: Spinner
    private var isResume: Boolean = false
    private var vendorGuid:String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true) // Add this!
        return inflater.inflate(R.layout.fragment_vendor_report, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VendorReportViewModel::class.java)
        // TODO: Use the ViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTableView = view.findViewById(R.id.my_TableView)
        mProgressBar = view.findViewById(R.id.progressBar)
        emptyReport = view.findViewById(R.id.empty_report_view)

       /* val myArgs = SalesReportFragmentArgs.fromBundle(requireArguments())
        custGuid = myArgs.custGuid*/
        if(vendorGuid.isNotEmpty() && filterType!=2){
            filterType=1
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        val item: MenuItem = menu.findItem(R.id.filterSpinner)
        item.isVisible = true
        spinner = item.actionView as Spinner
        val filterArray: Array<String> = arrayOf("TODAY'S", "SHOW ALL", "DATE-RANGE")
        val filterAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_item_action_bar, filterArray) }!!
        filterAdapter.setDropDownViewResource(R.layout.spinner_layout_actionbar)
        spinner.adapter = filterAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                if(isResume && filterType==2){
                    isResume = false
                    showProgressBar()
                    var userList: List<User>
                    thread {
                        userList = getSalesRangeData(startDate, endDate)
                        requireActivity().runOnUiThread(Runnable {
                            // Stuff that updates the UI
                            mTableAdapter!!.setUserList(userList)
                            setTableWidth()
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

                }else{
                    changeFilter(position)
                }
                Log.d("ShiftSelected", "onItemSelected: Tag= $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        initializeTableView(mTableView)
    }

    private fun initializeTableView(tableView: TableView?) {
        // Create TableView Adapter
        mTableAdapter = MyTableAdapter(context)
        tableView?.setAdapter(mTableAdapter)
        tableView?.tableViewListener = MyTableViewListener(tableView, this)
        spinner.setSelection(filterType)
    }

    private fun showProgressBar() {
        mProgressBar!!.visibility = View.VISIBLE
        mTableView!!.visibility = View.INVISIBLE
    }

    fun hideProgressBar() {
        mProgressBar!!.visibility = View.INVISIBLE
        mTableView!!.visibility = View.VISIBLE
    }

    private fun getSalesData(): List<User> {
        val millis = System.currentTimeMillis()
        val date = java.sql.Date(millis)
        println(date)

        val list: MutableList<User> = ArrayList()
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        var query =""
        if(vendorGuid.isNotEmpty()){
            query =
                "select um.USER_NAME, vm.DSTR_NM,gm.INVOICENO,gm.INVOICEDATE,gm.GRANDAMOUNT,gm.GRNDate, gm.GRN_GUID from retail_str_grn_master gm LEFT OUTER JOIN group_user_master um ON gm.USER_GUID=um.USER_GUID INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID=vm.VENDOR_GUID Where GRNDate LIKE '$date%' AND vm.VENDOR_GUID = '$vendorGuid'"
            Log.d("SalesReportSingle", "getSalesData: CustID "+vendorGuid)
        }else {
            query =
                "select um.USER_NAME, vm.DSTR_NM,gm.INVOICENO,gm.INVOICEDATE,gm.GRANDAMOUNT,gm.GRNDate, gm.GRN_GUID from retail_str_grn_master gm LEFT OUTER JOIN group_user_master um ON gm.USER_GUID=um.USER_GUID INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID=vm.VENDOR_GUID Where GRNDate LIKE '$date%'"
            Log.d("SalesReportMulti", "getSalesData: CustID "+vendorGuid)
        }
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
               val user = User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6))
                list.add(user)
                cursor.moveToNext()
            }
        }
        Log.d("SalesTable", "getSalesDetails: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }
    private fun getSalesRangeData(startDate: String, endDate: String): List<User> {
        val list: MutableList<User> = ArrayList()
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            var query=""
            if(vendorGuid.isNotEmpty()) {
                query = "select um.USER_NAME, vm.DSTR_NM,gm.INVOICENO,gm.INVOICEDATE,gm.GRANDAMOUNT,gm.GRNDate, gm.GRN_GUID,gm.GRN_GUID from retail_str_grn_master gm LEFT OUTER JOIN group_user_master um ON gm.USER_GUID=um.USER_GUID INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID=vm.VENDOR_GUID Where gm.GRNDate  BETWEEN '$startDate' AND  '$endDate' AND gm.VENDOR_GUID = '$vendorGuid'";
                Log.d("SalesReportSingle", "getSalesData: CustID "+vendorGuid)
            }else{
                query = "select um.USER_NAME, vm.DSTR_NM,gm.INVOICENO,gm.INVOICEDATE,gm.GRANDAMOUNT,gm.GRNDate, gm.GRN_GUID,gm.GRN_GUID from retail_str_grn_master gm LEFT OUTER JOIN group_user_master um ON gm.USER_GUID=um.USER_GUID INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID=vm.VENDOR_GUID Where gm.GRNDate  BETWEEN  '$startDate' AND  '$endDate'";
                Log.d("SalesReportMulti", "getSalesData: CustID "+vendorGuid)
            }
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val user = User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6))
                    print(user.toString())
                    list.add(user)
                    cursor.moveToNext()
                }
            }
            Log.d("SalesTable", "getSalesDetails: Successfully Fetched: $startDate : $endDate")
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
        }
        return list
    }

    private fun getSalesAllData(): List<User> {
        val list: MutableList<User> = ArrayList()
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String
        if(vendorGuid.isNotEmpty()) {
            query = "select um.USER_NAME, vm.DSTR_NM,gm.INVOICENO,gm.INVOICEDATE,gm.GRANDAMOUNT,gm.GRNDate, gm.GRN_GUID from retail_str_grn_master gm LEFT OUTER JOIN group_user_master um ON gm.USER_GUID=um.USER_GUID INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID=vm.VENDOR_GUID Where gm.VENDOR_GUID = '$vendorGuid'"
            Log.d("SalesReportSingle", "getSalesData: CustID "+vendorGuid)
        }else{
            query = "select um.USER_NAME, vm.DSTR_NM,gm.INVOICENO,gm.INVOICEDATE,gm.GRANDAMOUNT,gm.GRNDate,gm.GRN_GUID from retail_str_grn_master gm LEFT OUTER JOIN group_user_master um ON gm.USER_GUID=um.USER_GUID INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID=vm.VENDOR_GUID"
            Log.d("SalesReportMulti", "getSalesData: CustID "+vendorGuid)
        }
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val user = User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5),cursor.getString(6))
                list.add(user)
                cursor.moveToNext()
            }
        }
        Log.d("SalesTable", "getSalesDetails: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }


    override fun launchDetailsFragment(id: String, masterId:String) {
        isResume = true
        Log.d("LauncherInvoked", "launchDetailsFragment: ")
      /*  val actionNavSalesDetailsFragment = SalesReportFragmentDirections.actionNavSalesReportToNavSalesReportDetails(id,masterId)
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(actionNavSalesDetailsFragment)*/

    }

    override fun cellToRowSelector(rowId: Int, cellId:String,masterID:String) {

        try {
            mTableView?.selectedRow  = rowId
            isResume = true
            Log.d("LauncherInvoked", "launchDetailsFragment: ")
            val actionVendorDetailsFragment = VendorReportFragmentDirections.actionNavVendorReportsToNavVendorReportDetails(masterID)
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(actionVendorDetailsFragment)
        } catch (e: Exception) {

        }
    }

    private fun changeFilter(option: Int) {
        val tempIndex:Int=filterType
        when (option) {
            0 -> {
                showProgressBar()
                var userList: List<User>
                thread {
                    userList = getSalesData()
                    requireActivity().runOnUiThread(Runnable {
                        // Stuff that updates the UI
                        mTableAdapter!!.setUserList(userList)
                        setTableWidth()
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
            1 -> {
                showProgressBar()
                var userList: List<User>
                thread {
                    userList = getSalesAllData()
                    requireActivity().runOnUiThread(Runnable {
                        // Stuff that updates the UI
                        mTableAdapter!!.setUserList(userList)
                        setTableWidth()
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

            2 -> {
                val builder = MaterialDatePicker.Builder.dateRangePicker()
                //builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen)
                val now = Calendar.getInstance()
                builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
                val picker = builder.build()
                picker.show(activity?.supportFragmentManager!!, picker.toString())
                picker.addOnNegativeButtonClickListener {
                    picker.dismiss()
                    spinner.setSelection(tempIndex)
                }
                picker.addOnPositiveButtonClickListener {
                    mTableAdapter?.notifyDataSetChanged()
                    startDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.first?.let { it1 -> Date(it1) })
                    endDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(it.second?.let { it1 -> Date(it1) })
                    showProgressBar()
                    var userList: List<User>
                    thread {
                        userList = getSalesRangeData(startDate, endDate)
                        requireActivity().runOnUiThread(Runnable {
                            // Stuff that updates the UI
                            mTableAdapter!!.setUserList(userList)
                            setTableWidth()
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

                    Toast.makeText(context, " Range Selected from : $startDate To $endDate", Toast.LENGTH_LONG).show()

                }


            }
            else -> { // Note the block
                filterType=0
                print("Default Case Selected")
            }

        }
        filterType=option
        //mTableAdapter?.notifyDataSetChanged()

    }

    private  fun setTableWidth(){
        val dimen1:Int = resources.getDimension(R.dimen._160sdp).toInt()
        val dimen2:Int = resources.getDimension(R.dimen._100sdp).toInt()
        val dimen3:Int = resources.getDimension(R.dimen._120sdp).toInt()
        val dimen4:Int = resources.getDimension(R.dimen._60sdp).toInt()
        val dimen5:Int = resources.getDimension(R.dimen._50sdp).toInt()
        val dimen6:Int = resources.getDimension(R.dimen._40sdp).toInt()

        mTableView!!.setColumnWidth(0,dimen2)
        mTableView!!.setColumnWidth(1,dimen3)
        mTableView!!.setColumnWidth(2,dimen2)
        mTableView!!.setColumnWidth(3,dimen3)
        mTableView!!.setColumnWidth(4,dimen4)
        mTableView!!.setColumnWidth(5,dimen3)
    }

}