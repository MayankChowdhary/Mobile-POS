package com.retailstreet. mobilepos.View.ui.PurchaseInvoice

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.evrencoskun.tableview.TableView
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerPurchaseInvoice
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.Utils.Vibration
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents.MyTableAdapter
import com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents.MyTableViewListener
import com.retailstreet.mobilepos.View.ui.PurchaseInvoice.TableViewComponents.User
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

class PurchaseInvoiceFragment : Fragment() , TableViewInterface, PurchaseInvoiceEditDialog.DialogListener {

    companion object {
        fun newInstance() = PurchaseInvoiceFragment()
    }

    private lateinit var viewModel: PurchaseInvoiceViewModel
    lateinit var  invoiceDateSelector: EditText
    private var mTableView: TableView? = null
    private var mTableAdapter: MyTableAdapter? = null
    private var mProgressBar: ProgressBar? = null
    lateinit var emptyReport: LinearLayout
    lateinit var resetBtn:Button
    private  var  userList:List<User> = emptyList()
    private var MasterView:View? = null
    private var isIgstEnabled:Boolean = false
    lateinit var totalItemText:TextView
    lateinit var InvoiceNumberText:EditText
    lateinit var beforeTaxText:TextView
    lateinit var totalIgstText:TextView
    lateinit var totalGrandText:TextView
    lateinit var totalLayout:LinearLayout
    var invoiceDate:String = ""
    var GrandTotal = "0.00"
    var invoiceNumber=""
    val DIALOG_FRAGMENT:Int =1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_purchase_invoice, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PurchaseInvoiceViewModel::class.java)
        // TODO: Use the ViewModel

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val controller: NavController = Navigation.findNavController(activity!!, R.id.nav_host_fragment);

                if (userList.isNotEmpty()) {

                    val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_WARNING)
                            .setTitle("Confirm Back?")
                            .setDescription("Any Changes will be discarded!")
                            .setPositiveText("OK")
                            .setNegativeText("CANCEL")
                            .setPositiveButtonColor(Color.parseColor("#297999"))
                            .setPositiveTextColor(Color.parseColor("#ffffff"))
                            .setNegativeButtonColor(Color.parseColor("#297999"))
                            .setNegativeTextColor(Color.parseColor("#ffffff"))
                            .setPositiveListener(object : ClickListeners {
                                override fun onClick(dialog: LottieAlertDialogs) {
                                    dialog.dismiss()
                                    controller.popBackStack()
                                }

                            })
                            .setNegativeListener(object : ClickListeners {
                                override fun onClick(dialog: LottieAlertDialogs) {
                                    dialog.dismiss()
                                }

                            })
                            .build()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                } else {

                    controller.popBackStack()

                }

            }
        })
    }



    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MasterView = view;
        mTableView = view.findViewById(R.id.my_TableView)
        mProgressBar = view.findViewById(R.id.progressBar)
        emptyReport = view.findViewById(R.id.empty_report_view)
        val submit:Button = view.findViewById(R.id.pi_submit_btn)
        totalItemText = view.findViewById(R.id.pi_total_items_value)
        beforeTaxText = view.findViewById(R.id.pi_before_tax_value)
        totalIgstText = view.findViewById(R.id.pi_total_igst_value)
        totalGrandText = view.findViewById(R.id.pi_grand_total_value)
        totalLayout = view.findViewById(R.id.pi_total_layout)
        InvoiceNumberText = view.findViewById(R.id.pi_invoice_no_input)
        resetBtn = view.findViewById(R.id.pi_reset_btn)
        totalLayout.visibility = View.GONE
        val vendorNameArray:List<StringWithTag> = getVendorName()
        var vendorID = ""
        var vendorName = ""
        val vendorSearchSelector: SearchableSpinner = view.findViewById(R.id.pi_vendor_search_value)
        val custSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_small, vendorNameArray) }!!
        custSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSearchSelector.adapter = custSearchAdapter
        vendorSearchSelector.setTitle("Select Distributor")
        vendorSearchSelector.setPositiveButton("OK")
        vendorSearchSelector.gravity = Gravity.START
        val GSTEditTExt:EditText = view.findViewById(R.id.pi_gst_input)

        initTable()
        vendorSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val vendorSelected = parent.getItemAtPosition(position) as StringWithTag
                vendorID = vendorSelected.tag
                vendorName= vendorSelected.string

                GSTEditTExt.setText(getGSTNumber(vendorID))
                isIgstEnabled = isIgstEnbled(vendorID)
                Log.d("VendorSelected", "onItemSelected: Tag= $vendorID")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        val productArray: List<StringWithTag> = getProductsName();
        val productSpinner:SearchableSpinner =  view.findViewById(R.id.pi_product_selector)
        val productAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout_small, productArray) }!!
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productSpinner.adapter = productAdapter
        productSpinner.setTitle("Select Product")
        productSpinner.setPositiveButton("OK")
        productSpinner.gravity = Gravity.START

        productSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                invoiceDateSelector.requestFocus()
                if(position==0)
                    return

                if(vendorID.trim().isEmpty()){
                    Toast.makeText(context, "Please Select Vendor First!", Toast.LENGTH_LONG).show();
                   Vibration.vibrate(200)
                    return
                }

                val productSelected = parent.getItemAtPosition(position) as StringWithTag
                val itemGuid = productSelected.tag
                val prodName = productSelected.string

                insertIntoTable(itemGuid)
                reloadTableView()
                productSpinner.setSelection(0)
                Log.d("ProductSelected", "onItemSelected: Tag= $itemGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }



        invoiceDateSelector = view.findViewById<EditText>(R.id.pi_invoice_date_input)
        invoiceDateSelector.setOnTouchListener { _, event ->
            if (event != null) {
                if (event.action == MotionEvent.ACTION_UP) {

                    val c = Calendar.getInstance()
                    val year = c.get(Calendar.YEAR)
                    val month = c.get(Calendar.MONTH)
                    val day = c.get(Calendar.DAY_OF_MONTH)
                    val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->

                        invoiceDate = "$year-$monthOfYear-$dayOfMonth"
                        invoiceDateSelector.setText(invoiceDate)

                        //Toast.makeText(context,"Day"+ dayOfMonth + "MOnth: " + monthOfYear + "Year" + year,Toast.LENGTH_LONG).show()

                    }, year, month, day)

                    dpd.show()

                }


            }
            true
        }

        fun doViewsEmpty() {
            userList= emptyList();
           vendorSearchSelector.setSelection(0)
            InvoiceNumberText.setText("")
            invoiceNumber =""
            invoiceDate = ""
            invoiceDateSelector.setText("")
            GSTEditTExt.setText("")
            initTable()
            reloadTableView()

        }


        resetBtn.setOnClickListener {


            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_WARNING)
                    .setTitle("Confirm Reset?")
                    .setDescription("Any Changes will be discarded!")
                    .setPositiveText("OK")
                    .setNegativeText("CANCEL")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setNegativeButtonColor(Color.parseColor("#297999"))
                    .setNegativeTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {
                            dialog.dismiss()
                            doViewsEmpty()
                        }

                    })
                    .setNegativeListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {
                            dialog.dismiss()
                        }

                    })
                    .build()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }


        submit.setOnClickListener {

            invoiceDateSelector.requestFocus()
            invoiceNumber = InvoiceNumberText.text.toString()

            if(invoiceDate.trim().isEmpty()||GrandTotal.toDouble()<=0||invoiceNumber.trim().isEmpty()||vendorID.trim().isEmpty()){

                Toast.makeText(context, "Please Fill-up all fields first!", Toast.LENGTH_LONG).show()
                Vibration.vibrate(200)
                return@setOnClickListener
            }

            Handler(Looper.myLooper()!!).postDelayed({

                ControllerPurchaseInvoice(invoiceDate, GrandTotal, invoiceNumber, vendorID,vendorName)

            }, 300)


            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_SUCCESS)
                    .setTitle("Invoice Complete")
                    .setDescription("Purchase Successful")
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


        initializeTableView(mTableView);

       /* Handler(Looper.myLooper()!!).postDelayed({

            showEditDialog()

        }, 400)*/

    }

    private fun setTotalViews(){

        val totalItemsCount =  userList.size
        val grandTotal = getTotalCellData()
        GrandTotal = DecimalFormat("###.##").format(grandTotal)
        val beforeTaxAmount=getBeforeGSTAmount()
        if(totalItemsCount>0){
            totalLayout.visibility=View.VISIBLE
            totalItemText.text = "Item(s):\n"+totalItemsCount
            totalGrandText.text = "Grand Total:\n"+GrandTotal+" ₹"
            beforeTaxText.text = "Before Tax: \n"+DecimalFormat("###.##").format(beforeTaxAmount)+" ₹"
            if(isIgstEnabled) {
                totalIgstText.text ="IGST: \n"+ (DecimalFormat("###.##").format(grandTotal - beforeTaxAmount))+" ₹"
            }else{
                totalIgstText.text ="GST: \n"+ (DecimalFormat("###.##").format(grandTotal - beforeTaxAmount))+" ₹"
            }

        }else{
            totalLayout.visibility=View.GONE
        }

    }



    private fun getVendorName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT VENDOR", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select VENDOR_GUID, MOBILE, DSTR_NM from retail_str_dstr";
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



    private fun getProductsName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = java.util.ArrayList()
        list.add(StringWithTag("ADD PRODUCT", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select ITEM_GUID, PROD_NM from retail_store_prod_com";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                list.add(StringWithTag(name, id))
                cursor.moveToNext()
            }
        }
        Log.d("ProductDataTable", "getProductName: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun initTable(){
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            mydb.execSQL("DROP TABLE IF EXISTS tmp_purchase_invoice")
            val  DB_Query = "CREATE TABLE IF NOT EXISTS tmp_purchase_invoice (ITEM_GUID  text not null PRIMARY KEY, PROD_NM text,EXTERNALPRODUCTID text , BARCODE text, EXP_DATE text DEFAULT '00-00-00', MRP text DEFAULT '0.00', S_PRICE text DEFAULT '0.00' , P_PRICE text DEFAULT '0.00', QTY INTEGER DEFAULT 1,FQTY INTEGER DEFAULT 0,DISC text DEFAULT '0.00', UOM text, TOTAL REAL DEFAULT 0.00,TAX text DEFAULT '0.00')"
            mydb.execSQL(DB_Query)
            mydb.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun insertIntoTable(itemGuid: String){
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        var cursor:Cursor? = null
        try {
            if (itemGuid.isBlank()){
                Log.d("InsertIntoTable", "insertIntoTable: Item Guid iS Null")
                return
            }

            mydb.execSQL("INSERT INTO tmp_purchase_invoice (ITEM_GUID, PROD_NM,EXTERNALPRODUCTID, BARCODE,UOM) SELECT ITEM_GUID,PROD_NM,EXTERNALPRODUCTID, BARCODE,UOM  FROM retail_store_prod_com WHERE ITEM_GUID = '$itemGuid'")

             cursor = mydb.rawQuery("SELECT * from retail_str_stock_master WHERE ITEM_GUID = '$itemGuid'", null)

            if(cursor.moveToFirst()) {
               val cursorTemp:Cursor =  mydb.rawQuery(" SELECT sm.EXP_DATE,sm.MRP,sm.S_PRICE,sm.P_PRICE, sm.SALESDISCOUNTBYAMOUNT  FROM retail_str_stock_master sm INNER JOIN retail_store_prod_com pm ON sm.ITEM_GUID = pm.ITEM_GUID WHERE pm.ITEM_GUID = '$itemGuid'",null)

                var exp = ""
                var mrpx = ""
                var spricex = ""
                var ppricex = ""
                var sda = ""

                if(cursorTemp.moveToFirst()) {
                     exp = cursorTemp.getString(0)
                     mrpx = cursorTemp.getString(1)
                     spricex = cursorTemp.getString(2)
                     ppricex = cursorTemp.getString(3)
                     sda = cursorTemp.getString(4)
                }
                cursorTemp.close()
                mydb.execSQL("UPDATE tmp_purchase_invoice SET EXP_DATE = '$exp' ,MRP = '$mrpx' , S_PRICE = '$spricex' ,P_PRICE = '$ppricex',DISC= '$sda' WHERE ITEM_GUID = '$itemGuid'")
                mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))-  CAST(DISC AS REAL) WHERE ITEM_GUID = '$itemGuid'")


            }
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                if(cursor !=null){
                    if(!cursor.isClosed){
                        cursor.close();
                    }
                }
                var name = "Item"
                mydb.execSQL("UPDATE tmp_purchase_invoice SET QTY = QTY+1 WHERE  ITEM_GUID = '$itemGuid'")
                mydb.execSQL("UPDATE tmp_purchase_invoice SET TOTAL = (CAST(P_PRICE AS REAL)* CAST(QTY AS REAL))-  CAST(DISC AS REAL) WHERE ITEM_GUID = '$itemGuid'")
                cursor = mydb.rawQuery("SELECT PROD_NM from retail_store_prod_com WHERE ITEM_GUID = '$itemGuid'", null)
                if(cursor.moveToFirst())
                 name = cursor.getString(0)
                cursor.close()
                mydb.close()

                Toast.makeText(context, " $name Quantity Increased!", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, " Something Went Wrong", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }



    private fun getInvoiceData(): List<User> {
        val list: MutableList<User> = java.util.ArrayList()
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select * FROM tmp_purchase_invoice"
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val user = User(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(0))
                    list.add(user)
                    cursor.moveToNext()
                }
            }
            Log.d("SalesTable", "getSalesDetails: Successfully Fetched: " + cursor.count)
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }
    private fun getGSTNumber(vendorID: String):String {
        var gst:String = ""
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query:String = "select GST from retail_str_dstr where VENDOR_GUID = '$vendorID'";
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                    gst= cursor.getString(0)

            }
            Log.d("GSTDATATable", "getGSTNUM: Successfully Fetched: $gst")
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return gst
    }


    private fun removeItem(rowId: String){
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        mydb.execSQL("DELETE From tmp_purchase_invoice WHERE  ITEM_GUID = '$rowId'")
        mydb.close()
    }

    private fun initializeTableView(tableView: TableView?) {

        // Create TableView Adapter
        mTableAdapter = MyTableAdapter(context, this)
        tableView?.setAdapter(mTableAdapter)
        tableView?.tableViewListener = MyTableViewListener(tableView, this)

    }



    private fun reloadTableView(){
            userList = getInvoiceData()
            mTableAdapter!!.setUserList(userList)

            if(userList.isEmpty()){
                mTableView?.visibility= View.GONE
            }else{
                mTableView?.visibility= View.VISIBLE
            }
            setTotalViews()
        }




    override fun launchDetailsFragment(id: String?, masterID: String?) {

    }

    override fun cellToRowSelector(rowId: Int, cellId: String?, masterID: String?) {
        if(!masterID.equals("-1"))
        mTableView?.selectedRow  = rowId

        val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_WARNING)
                .setTitle("Remove Item")
                .setDescription("Do you want to remove an item?")
                .setPositiveText("Yes")
                .setPositiveButtonColor(Color.parseColor("#297999"))
                .setPositiveTextColor(Color.parseColor("#ffffff"))
                .setNegativeText("No")
                .setNegativeButtonColor(Color.parseColor("#297999"))
                .setNegativeTextColor(Color.parseColor("#ffffff"))
                .setPositiveListener(object : ClickListeners {
                    override fun onClick(dialog: LottieAlertDialogs) {
                        if (cellId != null) {
                            removeItem(cellId)
                            reloadTableView()
                            Toast.makeText(context, "One Item removed!", Toast.LENGTH_SHORT).show()
                        }
                        dialog.dismiss()
                    }
                })
                .setNegativeListener(object : ClickListeners {
                    override fun onClick(dialog: LottieAlertDialogs) {
                        dialog.dismiss()
                    }
                })
                .build()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    override fun refreshTableView() {
        reloadTableView()
    }

    override fun openDialogInterface(rowId: Int, cellId: String?, masterID: String?, nAme:String?) {
        showEditDialog(cellId,nAme)

    }

    private fun isIgstEnbled(vendorID: String): Boolean{

        var gstNumberRetalStore = ""
        var gstNumberVendor = ""

        try {
            val mydb: SQLiteDatabase = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            var cursor = mydb.rawQuery("Select GSTIN_NUMBER from retail_store", null)
            if (cursor.moveToFirst()) {
                gstNumberRetalStore = cursor.getString(0).toUpperCase(Locale.ROOT)
                Log.d("GSTNUmber Received", "for Store: " + gstNumberRetalStore)
            }
            cursor.close()
             cursor = mydb.rawQuery("Select GST from retail_str_dstr where VENDOR_GUID = '$vendorID'", null)
            if (cursor.moveToFirst()) {
                gstNumberVendor = cursor.getString(0).toUpperCase(Locale.ROOT)
                Log.d("GSTNUmber Received", "For Vendor: " + gstNumberVendor)
            }
            if(gstNumberRetalStore.trim().isNotEmpty() && gstNumberVendor.trim().isNotEmpty()){

                if(gstNumberRetalStore == gstNumberVendor){
                    Log.d("IGST Enabled", "isCgstEnbled: Invoked")
                    return true
                }
            }
            Log.d("Simple GST Enabled", "isCgstEnbled: Invoked")


        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return false
    }



    private fun getTotalCellData():Double {
        var gstTotal:Double = 0.00
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select TOTAL from tmp_purchase_invoice";
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    gstTotal += cursor.getDouble(0)
                    cursor.moveToNext()
                   // Log.d("Calculating GST", "getTotalCellData: $gstTotal")

                }
            }
            Log.d("GSTDATATable", "getGSTNUM: Successfully Fetched: $gstTotal")
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return gstTotal
    }



    private fun getBeforeGSTAmount():Double {
        var gstTotal:Double = 0.00
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select ITEM_GUID, TOTAL from tmp_purchase_invoice"
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {

                    val guid =cursor.getString(0)
                    val total = cursor.getDouble(1)

                    Log.d("CalcGST Data Retrieved", "getTotalGSTAmount: TotalBefore: $total")

                    val gstResult= getGSTORCGST(isIgstEnabled, guid)
                    val LineTax = (gstResult * total) / 100

                    mydb.execSQL("UPDATE tmp_purchase_invoice SET TAX = '$LineTax' WHERE ITEM_GUID = '$guid'")

                    Log.d("LineTaxIS", "getTotalGSTAmount: $gstResult")

                    Log.d("LineTaxIS", "getTotalGSTAmount: $LineTax")
                    gstTotal += total-LineTax
                    cursor.moveToNext()
                    // Log.d("Calculating GST", "getTotalCellData: $gstTotal")

                }
            }
            Log.d("GSTDATATable", "getGSTNUM: Successfully Fetched: $gstTotal")
            cursor.close()
            mydb.close()

            return gstTotal
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return gstTotal
    }



    private fun getGSTORCGST(isIGSTEnabled: Boolean, itemGuid: String):Double {
        Log.d("GSTRetriever", "getGSTORCGST: ITEMGUID REceived: $itemGuid")
        var gstResult:Double = 0.00
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            var query =""
            if(isIGSTEnabled) {
                query = "select IGST from retail_store_prod_com WHERE ITEM_GUID = '$itemGuid'"
                val cursor = mydb.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                        gstResult = cursor.getString(0).toDouble()
                         Log.d("Fetching IGST", "getTotalCellData: $gstResult")
                        cursor.close()
                }
            } else{
                query = "select SGST, CGST from retail_store_prod_com WHERE ITEM_GUID = '$itemGuid'"
                val cursor = mydb.rawQuery(query, null)
                if (cursor.moveToFirst()) {
                    gstResult = cursor.getString(0).toDouble() +cursor.getString(1).toDouble()
                    Log.d("Fetching GST", "getTotalCellData: $gstResult")

                    cursor.close()
                }
            }

            Log.d("GSTDATATable", "getGSTNUM: Successfully Fetched: $gstResult")
            mydb.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return gstResult
    }


    private fun showEditDialog(guid:String?, name:String?) {
        val dialogFragment: PurchaseInvoiceEditDialog = PurchaseInvoiceEditDialog()
        val bundle = Bundle()
        bundle.putBoolean("notAlertDialog",true)
        bundle.putString("GUID",guid)
        bundle.putString("NAME",name)
        dialogFragment.arguments = bundle
        dialogFragment.setTargetFragment(this, DIALOG_FRAGMENT)
        //val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
        val prev: Fragment? = requireFragmentManager().findFragmentByTag("dialog")

        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        dialogFragment.show(ft, "dialog")


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DIALOG_FRAGMENT -> if (resultCode == Activity.RESULT_OK) {
                // After Ok code.
                Toast.makeText(context,"Values Updated",Toast.LENGTH_LONG).show()

                reloadTableView()

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // After Cancel code.

            }
        }
    }

    override fun onFinishEditDialog(inputText: String?) {

    }


}