package com.retailstreet.mobilepos.View.ui.Products

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.OnTouchListener
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerProductMaster
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.Utils.Vibration
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import com.tsongkha.spinnerdatepicker.DatePicker
import com.tsongkha.spinnerdatepicker.DatePickerDialog
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder
import java.text.SimpleDateFormat
import java.util.*


class ProductsFragment : Fragment() , DatePickerDialog.OnDateSetListener {

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel
    private var categoryGuid:String = " "
    private  var hsnId:String = " "
    private  var sgst:String =""
    private  var cgst:String =""
    private  var igst:String =""
    lateinit var  expirySelector: Spinner
    var expiryDate:String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        // TODO: Use the ViewModel
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expiryDate = getSaleDateAndTime()

        val productNameEdittext:EditText = view.findViewById(R.id.p_name__value)
        val brandNameEdittext: EditText = view.findViewById(R.id.p_brand__value)
        val ext_prod_id_editext: EditText = view.findViewById(R.id.p_ext_id__value)
        val batch_num_edittext: EditText = view.findViewById(R.id.p_batch__value)
        val barcode_edittext: EditText = view.findViewById(R.id.p_barcode__value)
        val sprice_edittext: EditText = view.findViewById(R.id.p_s_price_value)
        val pprice_edittext: EditText = view.findViewById(R.id.p_p_price_value)
        val special_price_edittext: EditText = view.findViewById(R.id.p_special_price_value)
        val internet_price_edittext: EditText = view.findViewById(R.id.p_internet_price_value)
        val mrp_edittext: EditText = view.findViewById(R.id.p_mrp__value)
        val whole_price_edittext: EditText = view.findViewById(R.id.p_whole_price_value)
        val stock_qty_edittext: EditText = view.findViewById(R.id.p_stock_qty_value)
        val cess1_edittext: EditText = view.findViewById(R.id.p_cess1_value)
        val cess2_edittext: EditText = view.findViewById(R.id.p_cess2_value)
        val min_qty_edittext: EditText = view.findViewById(R.id.p_min_qty_value)
        val max_qty_edittext: EditText = view.findViewById(R.id.p_max_qty_value)
        val submitButton: Button = view.findViewById(R.id.submit_add_product)

        var productName: String
        var brandName: String
        var extProdId: String
        var batchNum: String
        var barCode: String
        var sPrice: String
        var pPrice: String
        var specialPrice: String
        var internetPrice: String
        var mrp: String
        var wholePrice: String
        var stockQuantity: String
        var cess1: String
        var cess2: String
        var minQty: String
        var maxQty: String
        var categoryname = " "
        var subCatName = " "
        var subCatGuid = " "
        var vendorName = " "
        var vendorGuid = " "
        var hsnName = " "
        var uomName =" "
        var uomGuid = " "
        var isProductReturnable:String = "TRUE"
        var isLooseItem:String = "TRUE"


        val sgstName: EditText = view.findViewById(R.id.p_sgst_value);
        val cgstName: EditText = view.findViewById(R.id.p_cgst_value);
        val igstName: EditText = view.findViewById(R.id.p_igst_value);
        val categorySpinner: SearchableSpinner = view.findViewById(R.id.p_cat_value);
        val subCategorySpinner: SearchableSpinner = view.findViewById(R.id.p_sub_cat_value);
        val vendorSpinner: SearchableSpinner = view.findViewById(R.id.p_vendor_value);
        val hsnSpinner: SearchableSpinner = view.findViewById(R.id.p_hsn_value);
        val uomSpinner: SearchableSpinner = view.findViewById(R.id.p_uom_value);
        val productReturnableChk:CheckBox = view.findViewById(R.id.product_returnable_checkbox)
        val looseItemChk:CheckBox = view.findViewById(R.id.product_looseitem_checkbox)
        val categoryArray: List<StringWithTag> = getCategoryName();
        var subCategoryArray: List<StringWithTag>
        val vendorArray: List<StringWithTag> = getVendorName();
        val hsnArray: List<StringWithTag> = getHSN();
        val uomArray: List<StringWithTag> = getUOM();
        val editTextArray: Array<EditText> = arrayOf(productNameEdittext, brandNameEdittext, sprice_edittext, pprice_edittext, special_price_edittext, mrp_edittext, whole_price_edittext, internet_price_edittext, stock_qty_edittext, cess1_edittext, cess2_edittext, min_qty_edittext, max_qty_edittext)
        val allStringsArray: Array<String> = arrayOf(categoryGuid, subCatGuid, vendorGuid, hsnId, uomGuid);


        val internetPriceTitle: TextView = view.findViewById(R.id.p_internet_price_title)
        internetPriceTitle.isSelected = true
        val wholePriceTitle: TextView = view.findViewById(R.id.p_whole_price_title)
        wholePriceTitle.isSelected = true
        val specialPriceTitle: TextView = view.findViewById(R.id.p_special_price_title)
        specialPriceTitle.isSelected = true
        val stockQuantityTitle: TextView = view.findViewById(R.id.p_stock_qty_title)
        stockQuantityTitle.isSelected = true
        val extProdIdTitle: TextView = view.findViewById(R.id.p_ext_id_title)
        extProdIdTitle.isSelected = true


        sprice_edittext.addTextChangedListener {

            special_price_edittext.setText(it.toString())
            internet_price_edittext.setText(it.toString())
            whole_price_edittext.setText(it.toString())

        }


        val categoryAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, categoryArray) }!!
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter
        categorySpinner.setTitle("Select Category")
        categorySpinner.setPositiveButton("OK")
        categorySpinner.gravity = Gravity.START

        subCategorySpinner.setTitle("Select Sub-Category")
        subCategorySpinner.setPositiveButton("OK")
        subCategorySpinner.gravity = Gravity.START
        subCategorySpinner.isActivated=false

        val vendorAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, vendorArray) }!!
        vendorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSpinner.adapter = vendorAdapter
        vendorSpinner.setTitle("Select Vendor Name")
        vendorSpinner.setPositiveButton("OK")
        vendorSpinner.gravity = Gravity.START

        val hsnAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, hsnArray) }!!
        hsnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hsnSpinner.adapter = hsnAdapter
        hsnSpinner.setTitle("Select HSN")
        hsnSpinner.setPositiveButton("OK")
        hsnSpinner.gravity = Gravity.START

        val uomAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, uomArray) }!!
        uomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        uomSpinner.adapter = uomAdapter
        uomSpinner.setTitle("Select UOM")
        uomSpinner.setPositiveButton("OK")
        uomSpinner.gravity = Gravity.START


         expirySelector = view.findViewById<Spinner>(R.id.product_expiry_value)
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



        class SpinnerTouchListener : OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent?): Boolean {
                if (event != null) {
                    if(event.action ==MotionEvent.ACTION_UP) {
                        Toast.makeText(context, "Select Category First!", Toast.LENGTH_LONG).show()
                        Vibration.vibrate(200);


                    }
                }
                return true
            }
        }

        val subCatTouchListener = SpinnerTouchListener()
        subCategorySpinner.setOnTouchListener(subCatTouchListener)

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val categorySelected = parent.getItemAtPosition(position) as StringWithTag
                categoryGuid = categorySelected.tag
                categoryname = categorySelected.string
                subCategoryArray = getSubCategoryName(categoryGuid)
                val subCategoryAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, subCategoryArray) }!!
                subCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                subCategorySpinner.adapter = subCategoryAdapter
                subCategorySpinner.isActivated = categoryGuid.isNotEmpty()
               if(categoryGuid.isNotEmpty()) {
                   subCategorySpinner.setOnTouchListener(null)
                   subCategorySpinner.performClick()
               }else{
                   subCategorySpinner.setOnTouchListener(subCatTouchListener)
               }
                Log.d("CategorySelected", "onItemSelected: Tag= $categoryGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        subCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val subCatSelected = parent.getItemAtPosition(position) as StringWithTag
                subCatGuid = subCatSelected.tag
                subCatName = subCatSelected.string

                Log.d("SubCategorySelected", "onItemSelected: Tag= $subCatGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        vendorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val vendorSelected = parent.getItemAtPosition(position) as StringWithTag
                vendorGuid = vendorSelected.tag
                vendorName = vendorSelected.string

                Log.d("SubCategorySelected", "onItemSelected: Tag= $subCatGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        uomSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val uomSelected = parent.getItemAtPosition(position) as StringWithTag
                uomGuid = uomSelected.tag
                uomName = uomSelected.string

                Log.d("SubCategorySelected", "onItemSelected: Tag= $subCatGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        hsnSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val hsnSelected = parent.getItemAtPosition(position) as StringWithTag
                hsnId = hsnSelected.tag
                hsnName = hsnSelected.string
                getAllGst(hsnId)
                sgstName.setText(sgst)
                cgstName.setText(cgst)
                igstName.setText(igst)
                Log.d("HSNSelected", "onItemSelected: Tag= $hsnId")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        fun doViewsEmpty(){
            productNameEdittext.setText("")
            brandNameEdittext.setText("")
            categorySpinner.setSelection(0)
            subCategorySpinner.setSelection(0)
            ext_prod_id_editext.setText("")
            batch_num_edittext.setText("")
            barcode_edittext.setText("")
            mrp_edittext.setText("")
            sprice_edittext.setText("")
            pprice_edittext.setText("")
            special_price_edittext.setText("")
            whole_price_edittext.setText("")
            internet_price_edittext.setText("")
            vendorSpinner.setSelection(0)
            hsnSpinner.setSelection(0)
            stock_qty_edittext.setText("")
            igstName.setText("")
            sgstName.setText("")
            cgstName.setText("")
            cess1_edittext.setText("")
            cess2_edittext.setText("")
            uomSpinner.setSelection(0)
            min_qty_edittext.setText("")
            max_qty_edittext.setText("")

        }

        submitButton.setOnClickListener {


            if (!validateFields(editTextArray) && !validateStrings(allStringsArray)) {
                Toast.makeText(context, "Please fill up all Mandatory fields first!", Toast.LENGTH_LONG).show();
                return@setOnClickListener
            }

            Log.d("SubmitButton", "onViewCreated: All fields are validated.! ")

            productName = productNameEdittext.text.toString()
            brandName = brandNameEdittext.text.toString()
            extProdId = ext_prod_id_editext.text.toString()
            batchNum = batch_num_edittext.text.toString()
            barCode = barcode_edittext.text.toString()
            mrp = mrp_edittext.text.toString()
            sPrice = sprice_edittext.text.toString()
            pPrice = pprice_edittext.text.toString()
            specialPrice = special_price_edittext.text.toString()
            wholePrice = whole_price_edittext.text.toString()
            internetPrice = internet_price_edittext.text.toString()
            stockQuantity = stock_qty_edittext.text.toString()
            cess1 = cess1_edittext.text.toString()
            cess2 = cess2_edittext.text.toString()
            minQty= min_qty_edittext.text.toString()
            maxQty= max_qty_edittext.text.toString()

            isProductReturnable = if(productReturnableChk.isChecked){
                "TRUE"
            }else{
                "FALSE"
            }

            isLooseItem = if(looseItemChk.isChecked){
                "TRUE"
            }else{
                "FALSE"
            }


            ControllerProductMaster(stockQuantity, batchNum, internetPrice, sPrice, mrp, minQty, maxQty, wholePrice, specialPrice, expiryDate, vendorName, vendorGuid, productName, brandName, pPrice, categoryname, subCatName, barCode, categoryGuid, cess1, cess2, cgst, extProdId, hsnName, igst, sgst, subCatGuid, uomName, uomGuid, isProductReturnable, isLooseItem)
            //Toast.makeText(context, "Product Successfully Added", Toast.LENGTH_LONG).show();
            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_SUCCESS)
                    .setTitle("Product Added")
                    .setDescription("Successful!")
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
    }


    private fun getCategoryName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT CATEGORY", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val cursor = mydb.rawQuery("select CATEGORY_GUID, CATEGORY from master_category", null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(1)
                val name = cursor.getString(0)
                list.add(StringWithTag(id, name))
                cursor.moveToNext()
            }
        }
        Log.d("CategoryDataTable", "getCategoryName: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getSubCategoryName(catGuid: String): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT SUB-CATEGORY", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select SUB_CATEGORYGUID, SUBCATEGORY_DESCRIPTION from master_subcategory where CATEGORY_GUID ='$catGuid'";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(1)
                val name = cursor.getString(0)
                list.add(StringWithTag(id, name))
                cursor.moveToNext()
            }
        }
        Log.d("SubCategoryDataTable", "getSubCategoryName: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getVendorName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT VENDOR", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select VENDOR_GUID, DSTR_NM from retail_str_dstr";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(1)
                val name = cursor.getString(0)
                list.add(StringWithTag(id, name))
                cursor.moveToNext()
            }
        }
        Log.d("VendorDataTable", "getVendorName: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getHSN(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT HSN", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select HSN_ID, HSN from hsn_master";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(1)
                val name = cursor.getString(0)
                list.add(StringWithTag(id, name))
                cursor.moveToNext()
            }
        }
        Log.d("HSNDataTable", "getHSN: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getUOM(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        list.add(StringWithTag("SELECT UOM", ""))
        val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
        val query:String = "select UOM_GUID, UoM from master_uom";
        val cursor = mydb.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast) {
                val id = cursor.getString(1)
                val name = cursor.getString(0)
                list.add(StringWithTag(id, name))
                cursor.moveToNext()
            }
        }
        Log.d("UOMDataTable", "getUOM: Successfully Fetched")
        cursor.close()
        mydb.close()
        return list
    }

    private fun getAllGst(hsnId: String) {
        try {
            sgst=""
            cgst=""
            igst =""
            val mydb = requireContext().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val selectQuery = "SELECT SGST,CGST,IGST FROM hsn_master where HSN_ID ='$hsnId'"
            val cursor = mydb.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                sgst = cursor.getString(0)
                cgst = cursor.getString(1)
                igst = cursor.getString(2)
            }
            cursor.close()
            mydb.close()
            Log.d("GSTDataRetrieved", "getGstMaster: SGST= $sgst")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun validateFields(fields: Array<EditText>): Boolean {
        for (i in fields.indices) {
            val currentField = fields[i]
            if (currentField.text.toString().trim().isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun validateStrings(fields: Array<String>): Boolean {
        for (i in fields.indices) {
            val currentField = fields[i]
            if (currentField.trim().isEmpty()) {
                return false
            }
        }
        return true
    }

    private fun getSaleDateAndTime(): String{
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return formatter.format(date)
    }

    override fun onDateSet(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val expiryItem = arrayOf("DD/MM/YYYY")
        expiryItem[0] = dayOfMonth.toString()+"/"+(monthOfYear+1)+"/"+year.toString()
        val expiryAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout_centre, expiryItem)
        expiryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        expirySelector.adapter = expiryAdapter

        expiryDate = year.toString()+"-"+monthOfYear.toString()+"-"+dayOfMonth.toString()+" 00:00:00"
        Log.d("ExpiryDateSelected", "onDateSet: "+expiryDate)

    }
}