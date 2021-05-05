package com.retailstreet.mobilepos.View.ui.StockUpdate

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerStockMaster
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class StockUpdateFragment : Fragment() , DatePickerDialog.OnDateSetListener {

    companion object {
        fun newInstance() = StockUpdateFragment()
    }

    private lateinit var viewModel: StockUpdateViewModel
    private var stockId = ""
    lateinit var expirySelector:Spinner
    private var expiryDate =""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stock_update, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StockUpdateViewModel::class.java)
        // TODO: Use the ViewModel
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var stockPosition=0
        val productNameEdtText: EditText = view.findViewById(R.id.su_name__value)
        val extProdIdEditText: EditText = view.findViewById(R.id.su_extid__value)
        val barcodeEdtText: EditText = view.findViewById(R.id.su_barcode_value)
        val mrpEdtText: EditText = view.findViewById(R.id.su_mrp_value)
        val spriceEdtText: EditText = view.findViewById(R.id.su_sprice_value)
        val ppriceEdtText: EditText = view.findViewById(R.id.su_pprice_value)
        val quantityEdtText: EditText = view.findViewById(R.id.su_qty_value)
        val totalEdtText: EditText = view.findViewById(R.id.su_total_value)
        val cgstEdtText: EditText = view.findViewById(R.id.su_cgst_value)
        val sgstEdtText: EditText = view.findViewById(R.id.su_sgst_value)
        val submitStockUpdate: Button = view.findViewById(R.id.submit_update_stock)

        var productName = " "
        var extProdId = " "
        var barcode = " "
        var mrp = " "
        var sprice = " "
        var pprice = " "
        var quantity = " "
        var total = " "
        var cgst = " "
        var sgst = " "
        var vendorGuid =" "
        var vendorName = " "


        val stockNameArray:List<StringWithTag> = getProductName()
        val stockSearchSelector: SearchableSpinner = view.findViewById(R.id.su_search_value)
        val stockSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, stockNameArray) }!!
        stockSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stockSearchSelector.adapter = stockSearchAdapter
        stockSearchSelector.setTitle("Select Product")
        stockSearchSelector.setPositiveButton("OK")
        stockSearchSelector.gravity = Gravity.START


        val vendorNameArray:List<StringWithTag> = getVendorName()
        val vendorSearchSelector: SearchableSpinner = view.findViewById(R.id.su_vendor_value)
        val vendorSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, vendorNameArray) }!!
        vendorSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSearchSelector.adapter = vendorSearchAdapter
        vendorSearchSelector.setTitle("Select Vendor")
        vendorSearchSelector.setPositiveButton("OK")
        vendorSearchSelector.gravity = Gravity.START

        fun doViewsEmpty() {

             productNameEdtText.setText("")
             extProdIdEditText.setText("")
             barcodeEdtText.setText("")
             mrpEdtText.setText("")
             spriceEdtText.setText("")
             ppriceEdtText.setText("")
             quantityEdtText.setText("")
             totalEdtText.setText("")
             cgstEdtText.setText("")
             sgstEdtText.setText("")

            val expiryItem = arrayOf("DD/MM/YYYY")
            val expiryAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout_centre, expiryItem)
            expirySelector.adapter = expiryAdapter
            vendorSearchSelector.setSelection(0)


        }
        vendorSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val custTypeSelected = parent.getItemAtPosition(position) as StringWithTag
                vendorGuid = custTypeSelected.tag
                vendorName = custTypeSelected.string

                Log.d("StateTypeSelected", "onItemSelected: Tag= $vendorName")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val search = view.findViewById<View>(R.id.su_search_layout)
        stockSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val stockSelected = parent.getItemAtPosition(position) as StringWithTag
                stockId = stockSelected.tag
                Log.d("StockSelected", "onItemSelected: Tag= $stockId")

                if (stockId.isBlank()){
                    enableDisableView(view, false, search)
                    doViewsEmpty()

                }
                else{
                    stockPosition = position
                    enableDisableView(view, true, search)
                    val StockData =  getFromStockMaster(stockId)

                    productNameEdtText.setText(StockData[1])
                    extProdIdEditText.setText(StockData[2])
                    barcodeEdtText.setText(StockData[3])
                    mrpEdtText.setText(StockData[5])
                    spriceEdtText.setText(StockData[6])
                    ppriceEdtText.setText(StockData[7])
                    quantityEdtText.setText(StockData[8])
                    cgstEdtText.setText(StockData[9])
                    sgstEdtText.setText(StockData[10])

                    try {
                        Log.d("dateReceived", "onItemSelected: " + StockData[4])
                        val dateSplit: List<String> = StockData[4].split(" ")
                        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()) // 12 hour format
                        val d1 = format.parse(dateSplit[0]) as Date
                        val dateFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                        val strDate: String = dateFormat.format(d1)
                        val dateFormat2: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        val strDate2: String = dateFormat2.format(d1)
                        val expiryItem = arrayOf(strDate)
                        Log.d("FomattedDate", "onItemSelected: $expiryItem")
                        val expiryAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout_centre, expiryItem)
                        expirySelector.adapter = expiryAdapter
                        expiryDate = "$strDate2 00:00:00"
                        Log.d("ExpiryDateSelected", "onDateSet: " + expiryDate)
                    } catch (e: Exception) {
                        Log.e("Exception is ", e.toString())
                        val expiryItem = arrayOf("DD/MM/YYYY")
                        val expiryAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout_centre, expiryItem)
                        expirySelector.adapter = expiryAdapter
                    }

                    vendorSearchSelector.setSelection(getIndex(vendorSearchSelector, StockData[0]))

                    try {
                        totalEdtText.setText((StockData[6].toDouble() * StockData[8].toDouble()).toString())

                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    totalEdtText.isEnabled=false
                }

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }




        expirySelector = view.findViewById<Spinner>(R.id.su_expiry_value)
        val expiryItem = arrayOf("DD/MM/YYYY")
        val expiryAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout_centre, expiryItem)
        expiryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        expirySelector.adapter = expiryAdapter

        expirySelector.setOnTouchListener { v, event ->
            if (event != null) {
                if (event.action == MotionEvent.ACTION_UP) {

                    val date = Date()
                    val calendar = Calendar.getInstance()
                    calendar.time = date


                    SpinnerDatePickerDialogBuilder()
                            .context(activity)
                            .callback(this)
                            .spinnerTheme(R.style.NumberPickerStyle)
                            .showTitle(true)
                            .showDaySpinner(true)
                            .defaultDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                            .maxDate(2099, 0, 1)
                            .minDate(calendar.get(Calendar.YEAR), 0, 1)
                            .build()
                            .show()
                }


            }
            true
        }

        spriceEdtText.addTextChangedListener {

            try {
                totalEdtText.setText((it.toString().toDouble() * quantityEdtText.text.toString().toDouble()).toString())

            } catch (e: Exception) {
                totalEdtText.setText("0.00")
                e.printStackTrace()
            }

        }

        quantityEdtText.addTextChangedListener {
            try {
                totalEdtText.setText((it.toString().toDouble() * spriceEdtText.text.toString().toDouble()).toString())

            } catch (e: Exception) {
                totalEdtText.setText("0.00")
                e.printStackTrace()
            }

        }

        submitStockUpdate.setOnClickListener{

             productName = productNameEdtText.text.toString()
             extProdId = extProdIdEditText.text.toString()
             barcode = barcodeEdtText.text.toString()
             mrp = mrpEdtText.text.toString()
             sprice = spriceEdtText.text.toString()
             pprice = ppriceEdtText.text.toString()
             quantity = quantityEdtText.text.toString()
             total = totalEdtText.text.toString()
             cgst = cgstEdtText.text.toString()
             sgst = sgstEdtText.text.toString()

            val allStringsArray: Array<String> = arrayOf(productName,barcode,expiryDate,mrp,vendorGuid,pprice, sprice,quantity,cgst,sgst);

            if(!validateStrings(allStringsArray)){

                Toast.makeText(context, "Please fill up all Mandatory (*) fields first!", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }
            ControllerStockMaster(context).updateStockMaster(stockId,vendorGuid,vendorName,productName,extProdId,barcode,expiryDate,mrp,sprice,pprice,quantity,cgst,sgst)
            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_SUCCESS)
                    .setTitle("Stock Updated")
                    .setDescription("Successful!")
                    .setPositiveText("OK")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {
                            dialog.dismiss()
                            stockSearchSelector.setSelection(0)

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

    private fun enableDisableView(view: View, enabled: Boolean, viewIgnore: View) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            val group = view
            for (idx in 0 until group.childCount) {

                if(viewIgnore == group.getChildAt(idx))
                    continue

                enableDisableView(group.getChildAt(idx), enabled, viewIgnore)
            }
        }
    }
    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if ((spinner.getItemAtPosition(i) as StringWithTag).tag.equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }


    private fun getProductName(): List<StringWithTag> {
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
        Log.d("ProductNamesTable", "getStock: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
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
        Log.d("VendorNameTable", "getVendorName: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val expiryItem = arrayOf("DD/MM/YYYY")
        expiryItem[0] = dayOfMonth.toString()+"/"+(monthOfYear+1)+"/"+year.toString()
        val expiryAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout_centre, expiryItem)
        expiryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        expirySelector.adapter = expiryAdapter
        expiryDate = "$year-$monthOfYear-$dayOfMonth 00:00:00"
        Log.d("ExpiryDateSelected", "onDateSet: " + expiryDate)

    }


    private fun getFromStockMaster(stockId: String): Array<String> {
        val viewData: Array<String> = arrayOf(" ", " ", " ", " ", " ", " ", " ", " ", " ", " ", " ")
        try {

            val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val selectQuery = "SELECT VENDOR_GUID, PROD_NM, EXTERNALPRODUCTID, BARCODE, EXP_DATE, MRP,S_PRICE,P_PRICE,QTY,CGST,SGST FROM retail_str_stock_master where STOCK_ID ='$stockId'"
            val cursor = mydb.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {

                viewData[0]= if(cursor.getString(0).isBlank()) " " else cursor.getString(0)
                viewData[1]= (if(cursor.getString(1).isBlank()) " " else cursor.getString(1))
                viewData[2]= (if(cursor.getString(2).isBlank()) " " else cursor.getString(2))
                viewData[3]= (if(cursor.getString(3).isBlank()) " " else cursor.getString(3))
                viewData[4]= (if(cursor.getString(4).isBlank()) " " else cursor.getString(4))
                viewData[5]= (if(cursor.getString(5).isBlank()) "0.00" else cursor.getString(5))
                viewData[6]= (if(cursor.getString(6).isBlank()) "0.00" else cursor.getString(6))
                viewData[7]= (if(cursor.getString(7).isBlank()) "0.00" else cursor.getString(7))
                viewData[8]= (if(cursor.getString(8).isBlank()) "0.00" else cursor.getString(8))
                viewData[9]= (if(cursor.getString(9).isBlank()) "0.00" else cursor.getString(9))
                viewData[10]= (if(cursor.getString(10).isBlank()) "0.00" else cursor.getString(10))

            }
            cursor.close()
            mydb.close()
            Log.d("DataRetrieved", "getFromStockMaster:")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return viewData
    }

}