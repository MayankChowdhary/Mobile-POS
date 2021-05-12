package com.retailstreet.mobilepos.View.ui.VendorReturn

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
import com.retailstreet.mobilepos.Controller.ControllerVendorReturn
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.Utils.Vibration
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.retailstreet.mobilepos.View.ui.VendorReturn.VendorReturnRecyclerView.VendorReturnListAdapter
import com.retailstreet.mobilepos.View.ui.VendorReturn.VendorReturnRecyclerView.VendorReturnListAdapter2
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.*

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */



class VendorReturnFragment : Fragment() {

    companion object {
        fun newInstance() = VendorReturnFragment()
    }

    private lateinit var viewModel: VendorReturnViewModel
    lateinit var returnSubmitLayout: ConstraintLayout
    private lateinit var returnSubmitBtn: Button
    private lateinit var resetBtn: ImageButton
    lateinit var mainFrame:ViewGroup
    private lateinit var vendorReturnRadioGroup: RadioGroup
    lateinit var vendorReturnRecyclerView: RecyclerView
    lateinit var vendorRecyclerAdapter: VendorReturnListAdapter
    var rejectTypeGuid = " "
    var   rejectTypeName = " "
    private var GrnNumer:String = " "
    private var InvoiceNum:String = " "
    private var vendorGuid:String = " "
    lateinit var vendorSearchSelector: SearchableSpinner
    lateinit var vendorRecyclerAdapter2:VendorReturnListAdapter2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vendor_return, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VendorReturnViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returnSubmitLayout = view.findViewById(R.id.return_submit_layout)
        returnSubmitBtn = view.findViewById(R.id.btn_return_submit)
        resetBtn = view.findViewById(R.id.vendor_return_reset)
        mainFrame = view.findViewById(R.id.vendor_return_main_frame) as ViewGroup
        vendorReturnRadioGroup= view.findViewById(R.id.vendor_return_radio_group)

        vendorReturnRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            if(checkedId==R.id.vendor_return_billno_radio){

                inflateWithGRNLayout(mainFrame)
            }else{
                inflateNoGrnLayout(mainFrame)
            }
            returnSubmitLayout.visibility = View.GONE

        }
       inflateWithGRNLayout(mainFrame)

        var checkedId:Int
        resetBtn.setOnClickListener {

            checkedId = vendorReturnRadioGroup.checkedRadioButtonId
            if(checkedId==R.id.vendor_return_billno_radio){

                inflateWithGRNLayout(mainFrame)
            }else{
                inflateNoGrnLayout(mainFrame)

            }
             //salesReturnRecyclerView.adapter = null;
            returnSubmitLayout.visibility = View.GONE
            Toast.makeText(context, "Reset Done!", Toast.LENGTH_LONG).show()
            Vibration.vibrate(300)
        }

        returnSubmitBtn.setOnClickListener {
            checkedId = vendorReturnRadioGroup.checkedRadioButtonId

            if(vendorGuid.isEmpty()){
                Toast.makeText(context, "Please Select Vendor First!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(rejectTypeGuid.isEmpty()){
                Toast.makeText(context, "Please Select Return Reason First!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(checkedId==R.id.vendor_return_billno_radio){

                if(InvoiceNum.isEmpty()){
                    Toast.makeText(context, "Please Select an invoice Number First!", Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                ControllerVendorReturn(vendorGuid,InvoiceNum,rejectTypeName)
                inflateWithGRNLayout(mainFrame)
            }else{

                ControllerVendorReturn(vendorGuid,rejectTypeName)
                inflateNoGrnLayout(mainFrame)
            }

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
    }


    private fun inflateWithGRNLayout(root: ViewGroup){

        root.removeAllViews()
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.vendor_return_with_grn, root)
        initWithGrnLayout(root)

    }

    private fun initWithGrnLayout(root: ViewGroup){

        vendorReturnRecyclerView = root.findViewById(R.id.vendor_return_recycler_view)

        val rejectReasonArray:List<StringWithTag> = getReturnReasons()
        val rejectReasonSelector: Spinner = root.findViewById(R.id.vendor_reason_spinner)
        val rejectReasonAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, rejectReasonArray) }!!
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


        var invoiceNumberArray:List<StringWithTag> = getInvoiceNumbers(vendorGuid)
        val invoiceSearchSelector: SearchableSpinner = root.findViewById(R.id.vr_invoice_number)
        var invoiceSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, invoiceNumberArray) }!!
        invoiceSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        invoiceSearchSelector.adapter = invoiceSearchAdapter
        invoiceSearchSelector.setTitle("Select Invoice Number")
        invoiceSearchSelector.setPositiveButton("OK")
        invoiceSearchSelector.gravity = Gravity.START

        invoiceSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val invoiceSelected = parent.getItemAtPosition(position) as StringWithTag
                InvoiceNum = invoiceSelected.string
                GrnNumer = invoiceSelected.tag

               /* if(GrnNumer.trim().isEmpty())
                    return*/

                try {

                    val grnReturnCursor: Cursor = ControllerVendorReturn().getVendorReturnCursor(GrnNumer)
                    val vendorRecyclerAdapter = VendorReturnListAdapter(context, grnReturnCursor, view, activity)
                    vendorReturnRecyclerView.layoutManager= LinearLayoutManager(context)
                    vendorReturnRecyclerView.adapter = vendorRecyclerAdapter
                    if(grnReturnCursor.count>0) {
                        returnSubmitLayout.visibility = View.VISIBLE

                    }else {
                        returnSubmitLayout.visibility = View.GONE
                        vendorSearchSelector.isEnabled = true
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    if(GrnNumer.isNotEmpty()){
                        Toast.makeText(context,"GRN Or Returnable Item Not Found!",Toast.LENGTH_LONG).show();
                    }
                    vendorReturnRecyclerView.adapter = null
                    returnSubmitLayout.visibility = View.GONE
                }

                Log.d("GRNSelected", "onItemSelected: Tag= $GrnNumer")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        val vendorNameArray:List<StringWithTag> = getVendorName()
        vendorSearchSelector = root.findViewById(R.id.sr_vendor_search_value)
        val vendorSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, vendorNameArray) }!!
        vendorSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSearchSelector.adapter = vendorSearchAdapter
        vendorSearchSelector.setTitle("Select Vendor")
        vendorSearchSelector.setPositiveButton("OK")
        vendorSearchSelector.gravity = Gravity.START
        vendorSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val custSelected = parent.getItemAtPosition(position) as StringWithTag
                vendorGuid = custSelected.tag
                invoiceNumberArray = getInvoiceNumbers(vendorGuid)
                invoiceSearchAdapter = context?.let { ArrayAdapter(it, R.layout.spinner_layout, invoiceNumberArray) }!!
                invoiceSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                invoiceSearchSelector.adapter = invoiceSearchAdapter
                Log.d("VendorSelected", "onItemSelected: Tag= $vendorGuid")

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }


    private fun inflateNoGrnLayout(root: ViewGroup){
        root.removeAllViews()
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.vendor_return_without_grn, root)
        initNoBillLayout(root)

    }

    private  fun initNoBillLayout(root: ViewGroup) {
        vendorReturnRecyclerView = root.findViewById(R.id.vendor_return_recycler_view)
        val productArray: List<StringWithTag> = getProductsName();
        val productSpinner: SearchableSpinner = root.findViewById(R.id.vendor_return_product_selector)
        val productAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, productArray) }!!
        productAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        productSpinner.adapter = productAdapter
        productSpinner.setTitle("Select Product")
        productSpinner.setPositiveButton("OK")
        productSpinner.gravity = Gravity.START
        initTable()


        val rejectReasonArray:List<StringWithTag> = getReturnReasons()
        val rejectReasonSelector: Spinner = root.findViewById(R.id.vendor_reason_spinner)
        val rejectReasonAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, rejectReasonArray) }!!
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

        val vendorNameArray:List<StringWithTag> = getVendorName()
        vendorSearchSelector = root.findViewById(R.id.sr_vendor_search_value)
        val vendorSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, vendorNameArray) }!!
        vendorSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSearchSelector.adapter = vendorSearchAdapter
        vendorSearchSelector.setTitle("Select Vendor")
        vendorSearchSelector.setPositiveButton("OK")
        vendorSearchSelector.gravity = Gravity.START
        vendorSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val custSelected = parent.getItemAtPosition(position) as StringWithTag
                vendorGuid = custSelected.tag
                Log.d("VendorSelected", "onItemSelected: Tag= $vendorGuid")

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        productSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                if(position==0)
                    return

                val productSelected = parent.getItemAtPosition(position) as StringWithTag
                val stockGuid = productSelected.tag
                val prodName = productSelected.string


                insertIntoTable(stockGuid)
                swapNoBillRecycler()
                productSpinner.setSelection(0)
                Log.d("ProductSelected", "onItemSelected: Tag= $stockGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        try {
            vendorRecyclerAdapter2 = VendorReturnListAdapter2(context, null, view, activity)
            vendorReturnRecyclerView.layoutManager= LinearLayoutManager(context)
            vendorReturnRecyclerView.adapter = vendorRecyclerAdapter2

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
    private fun swapNoBillRecycler(){
        try {
            val vendorReturnCursor: Cursor? = ControllerVendorReturn().refreshNoGrnCursor()
            vendorRecyclerAdapter2.swapCursors(vendorReturnCursor)
            if (vendorReturnCursor != null && vendorReturnCursor.count>0) {
                returnSubmitLayout.visibility = View.VISIBLE
            }else{
                returnSubmitLayout.visibility = View.GONE
            }

        } catch (e: Exception) {
            e.printStackTrace()
            returnSubmitLayout.visibility = View.GONE
        }

    }


        private fun getReturnReasons(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT RETURN REASON", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query = "select REASONGUID, REASON_FOR_REJECTION from retail_store_vend_reject"
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(0)
                val name = cursor.getString(1)
                list.add(StringWithTag(name, id))
                cursor.moveToNext()
            }
        }
        Log.d("VendorRejectTable", "getVendorRejectType: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getVendorName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        try {
            list.add(StringWithTag("SELECT VENDOR", ""))
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select DISTINCT gm.VENDOR_GUID, vm.DSTR_NM from retail_str_grn_master gm INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID = vm.VENDOR_GUID"
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val id = cursor.getString(0)
                    val name = cursor.getString(1)
                    list.add(StringWithTag(name, id))
                    cursor.moveToNext()
                }
            }
            Log.d("VendorNamesTable", "getVendorType: Successfully Fetched")
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
        }
        return list
    }

    private fun getInvoiceNumbers(vendorGuid:String): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        try {
            list.add(StringWithTag("SELECT INVOICE NO.", ""))
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select INVOICENO, GRN_GUID from retail_str_grn_master  WHERE VENDOR_GUID = '$vendorGuid'"
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val id = cursor.getString(0)
                    val name = cursor.getString(1)
                    list.add(StringWithTag(id, name))
                    cursor.moveToNext()
                }
            }
            Log.d("InvoiceNumber", "getInvoiceNumber: Successfully Fetched")
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
        }
        return list
    }

    private fun initTable(){
        try {
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            mydb.execSQL("DROP TABLE IF EXISTS tmp_vr_no_grn")
            val  DB_Query = "CREATE TABLE IF NOT EXISTS tmp_vr_no_grn (STOCK_ID  text not null PRIMARY KEY, PROD_NM text, BARCODE text, QTY INTEGER DEFAULT 1, P_PRICE text not null, UOM text, TOTAL REAL DEFAULT 0.00, EXP_DATE text)"
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
            mydb.execSQL("INSERT INTO tmp_vr_no_grn (STOCK_ID, PROD_NM,BARCODE,P_PRICE,UOM,EXP_DATE) SELECT STOCK_ID, PROD_NM,BARCODE,P_PRICE,UOM,EXP_DATE FROM retail_str_stock_master WHERE STOCK_ID = '$stockid'")
            mydb.execSQL("UPDATE tmp_vr_no_grn SET TOTAL = CAST(P_PRICE AS REAL)* CAST(QTY AS REAL) WHERE STOCK_ID = '$stockid'")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Item already added!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getProductsName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("ADD PRODUCT", ""))
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
}