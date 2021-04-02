package com.retailstreet.mobilepos.View.ui.SalesRefund

import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerCustomerReturn
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.IDGenerator
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.Utils.Vibration
import com.retailstreet.mobilepos.View.SalesReturnRecyclerView.SalesReturnListAdapter
import com.retailstreet.mobilepos.View.SalesReturnRecyclerView.SalesReturnListAdapter2
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.text.DecimalFormat
import java.util.*


class SalesRefundFragment : Fragment() {

    companion object {
        fun newInstance() = SalesRefundFragment()
    }

    private lateinit var viewModel: SalesRefundViewModel
    lateinit var returnSubmitLayout: ConstraintLayout
    lateinit var returnSubmitBtn: Button
    lateinit var resetBtn: ImageButton
    lateinit var mainFrame:ViewGroup
    lateinit var salesReturnRadioGroup:RadioGroup
    lateinit var billNoEditText:EditText
    lateinit var submitBillNo:Button
    lateinit var salesReturnRecyclerView:RecyclerView
    lateinit var salesRecyclerAdapter2:SalesReturnListAdapter2
     private var totalAmount:String? = "0.00"
    private var billNumer:String = " "
    var rejectTypeGuid = " "
    var   rejectTypeName = " "
    var creditNoteNumber=""
    lateinit var cashCheckBox:CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sales_refund, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SalesRefundViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        returnSubmitLayout = view.findViewById(R.id.return_submit_layout) ;
        returnSubmitBtn = view.findViewById(R.id.btn_return_submit);
        resetBtn = view.findViewById(R.id.sales_return_reset);
        mainFrame = view.findViewById(R.id.sales_return_main_frame) as ViewGroup;
        salesReturnRadioGroup= view.findViewById(R.id.sales_return_radio_group)
        cashCheckBox = view.findViewById(R.id.sales_return_cash_checkbox)


        salesReturnRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            if(checkedId==R.id.sales_return_billno_radio){

                inflateBillLayout(mainFrame)
            }else{
                inflateNoBillLayout(mainFrame)
            }
            returnSubmitLayout.visibility = View.GONE

        }
        inflateBillLayout(mainFrame)

        var checkedId:Int
        returnSubmitBtn.setOnClickListener {

             checkedId = salesReturnRadioGroup.checkedRadioButtonId
            if(!cashCheckBox.isChecked){
                creditNoteNumber= IDGenerator.getTimeStamp()
            }


            if(checkedId==R.id.sales_return_billno_radio){
                totalAmount = getAmountTotal1()
                Log.d("Proceeding With Bill", "onViewCreated: Total " + totalAmount)
                ControllerCustomerReturn(billNumer, rejectTypeGuid, " ", rejectTypeName, totalAmount, creditNoteNumber)
                inflateBillLayout(mainFrame)
            }else{
                totalAmount = getAmountTotal()
                Log.d("Proceeding With NoBill", "onViewCreated: Total " + totalAmount)
                ControllerCustomerReturn(rejectTypeGuid, " ", rejectTypeName, totalAmount, creditNoteNumber)
                inflateNoBillLayout(mainFrame)
            }
            salesReturnRecyclerView.adapter = null;
            returnSubmitLayout.visibility = View.GONE

            val alertDialog = LottieAlertDialogs.Builder(activity, DialogTypes.TYPE_SUCCESS)
                    .setTitle("Return Completed")
                    .setDescription("Thank You!")
                    .setPositiveText("Back")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {
                            dialog.dismiss()

                        }
                    })
                    .build()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        resetBtn.setOnClickListener {

            checkedId = salesReturnRadioGroup.checkedRadioButtonId
            if(checkedId==R.id.sales_return_billno_radio){

                inflateBillLayout(mainFrame)
            }else{
                inflateNoBillLayout(mainFrame)
            }
            salesReturnRecyclerView.adapter = null;
            returnSubmitLayout.visibility = View.GONE
            Toast.makeText(context, "Reset Done!", Toast.LENGTH_LONG).show();
            Vibration.vibrate(300)
        }

    }

    private fun inflateBillLayout(root: ViewGroup){

        root.removeAllViews()
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.sales_return_with_billno, root)
        initBillLayout(root)

    }

    private fun initBillLayout(root: ViewGroup){

        billNoEditText = root.findViewById(R.id.sales_return_bill_input)
        submitBillNo = root.findViewById(R.id.sales_return_bill_submit)
        salesReturnRecyclerView = root.findViewById(R.id.sales_return_recycler_view)

        val rejectReasonArray:List<StringWithTag> = getReturnReasons()
        val rejectReasonSelector: Spinner = root.findViewById(R.id.sales_reason_spinner)
        val rejectReasonAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, rejectReasonArray) }!!
        rejectReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rejectReasonSelector.adapter = rejectReasonAdapter
        rejectReasonSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val rejectTypeSelected = parent.getItemAtPosition(position) as StringWithTag
                 rejectTypeGuid = rejectTypeSelected.tag
                 rejectTypeName = rejectTypeSelected.string

                Log.d("RejectTypeSelected", "onItemSelected: Tag= $rejectTypeGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        submitBillNo.setOnClickListener {

            try {
                billNumer = billNoEditText.text.toString().trim()
                val saleReturnCursor: Cursor = ControllerCustomerReturn().getSalesReturnCursor(billNumer)
                val salesRecyclerAdapter = SalesReturnListAdapter(context, saleReturnCursor, view, activity)
                salesReturnRecyclerView.layoutManager= LinearLayoutManager(context)
                salesReturnRecyclerView.adapter = salesRecyclerAdapter
                if(saleReturnCursor.count>0)
                    returnSubmitLayout.visibility = View.VISIBLE
                else
                    returnSubmitLayout.visibility = View.GONE

            } catch (e: Exception) {
                e.printStackTrace()
                salesReturnRecyclerView.adapter = null;
                returnSubmitLayout.visibility = View.GONE
            }


        }
    }

    private fun inflateNoBillLayout(root: ViewGroup){

        root.removeAllViews()
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.sales_return_without_billno, root)
        initNoBillLayout(root)

    }

    private  fun initNoBillLayout(root: ViewGroup){
        salesReturnRecyclerView = root.findViewById(R.id.sales_return_recycler_view)
        val productArray: List<StringWithTag> = getProductsName();
       val productSpinner:SearchableSpinner =  root.findViewById(R.id.sales_return_product_selector)
        val productAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, productArray) }!!
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productSpinner.adapter = productAdapter
        productSpinner.setTitle("Select Category")
        productSpinner.setPositiveButton("OK")
        productSpinner.gravity = Gravity.START
        initTable()

        val rejectReasonArray:List<StringWithTag> = getReturnReasons()
        val rejectReasonSelector: Spinner = root.findViewById(R.id.sales_reason_spinner)
        val rejectReasonAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, rejectReasonArray) }!!
        rejectReasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rejectReasonSelector.adapter = rejectReasonAdapter
        rejectReasonSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val rejectTypeSelected = parent.getItemAtPosition(position) as StringWithTag
                rejectTypeGuid = rejectTypeSelected.tag
                rejectTypeName = rejectTypeSelected.string

                Log.d("RejectTypeSelected", "onItemSelected: Tag= $rejectTypeGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        productSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val productSelected = parent.getItemAtPosition(position) as StringWithTag
                val stockGuid = productSelected.tag
                val prodName = productSelected.string

                insertIntoTable(stockGuid)
                swapNoBillRecycler()

                Log.d("ProductSelected", "onItemSelected: Tag= $stockGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        try {
             salesRecyclerAdapter2 = SalesReturnListAdapter2(context, null, view, activity)
            salesReturnRecyclerView.layoutManager= LinearLayoutManager(context)
            salesReturnRecyclerView.adapter = salesRecyclerAdapter2

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun swapNoBillRecycler(){
        try {
            val saleReturnCursor: Cursor? = ControllerCustomerReturn().refreshNoBillCursor()
            salesRecyclerAdapter2.swapCursors(saleReturnCursor)
            if (saleReturnCursor != null && saleReturnCursor.count>0) {
                returnSubmitLayout.visibility = View.VISIBLE
            }else{
                    returnSubmitLayout.visibility = View.GONE
            }

        } catch (e: Exception) {
            e.printStackTrace()
            returnSubmitLayout.visibility = View.GONE
        }

    }

    private fun getProductsName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT PRODUCT", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select STOCK_ID, PROD_NM from retail_str_stock_master";
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
            mydb.execSQL("DROP TABLE IF EXISTS tmp_sr_no_bill")
            val  DB_Query = "CREATE TABLE IF NOT EXISTS tmp_sr_no_bill (STOCK_ID  text not null PRIMARY KEY, PROD_NM text, BARCODE text, QTY INTEGER DEFAULT 1, S_PRICE text not null, UOM text, TOTAL REAL DEFAULT 0.00)"
            mydb.execSQL(DB_Query)
            mydb.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun insertIntoTable(stockid: String){
        try {
            if (stockid.isBlank())
                return

            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            mydb.execSQL("INSERT INTO tmp_sr_no_bill (STOCK_ID, PROD_NM,BARCODE,S_PRICE,UOM) SELECT STOCK_ID, PROD_NM,BARCODE,S_PRICE,UOM FROM retail_str_stock_master WHERE STOCK_ID = '$stockid'")
            mydb.execSQL("UPDATE tmp_sr_no_bill SET TOTAL = CAST(S_PRICE AS REAL)* CAST(QTY AS REAL) WHERE STOCK_ID = '$stockid'")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Item already added!", Toast.LENGTH_LONG).show()
        }
    }


    fun getAmountTotal(): String? {
        return try {
            val db = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select TOTAL from tmp_sr_no_bill"
            val result = db.rawQuery(query, null)
            var total = 0.00
            result.moveToFirst()
            while (!result.isAfterLast) {
                val resultString = result.getString(0)
                total += resultString.toDouble()
                result.moveToNext()
            }
            result.close()
            db.close()
            Log.d("TotalAmountRefunded", "getAmountTotal: " + total)
            DecimalFormat("#.##").format(total)
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            ""
        }
    }

    private fun getReturnReasons(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT REJECT REASON", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query = "select REASONGUID, REASON_FOR_REJECTION from retail_store_cust_reject";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                list.add(StringWithTag(name, id))
                cursor.moveToNext()
            }
        }
        Log.d("CustRejectTable", "getCustomerRejectType: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    fun getAmountTotal1(): String? {
        return try {
            val db = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select QTY,MRP from tmp_sales_return"
            val result = db.rawQuery(query, null) ?: return "0"
            var total = 0.0 // Your default if none is found
            result.moveToFirst()
            while (!result.isAfterLast) {
                val count = result.getString(0)
                val price = result.getString(1)
                total += price.toDouble() * count.toDouble()
                result.moveToNext()
            }
            result.close()
            db.close()
            DecimalFormat("#.##").format(total)
        } catch (e: java.lang.NumberFormatException) {
            e.printStackTrace()
            ""
        }
    }
}