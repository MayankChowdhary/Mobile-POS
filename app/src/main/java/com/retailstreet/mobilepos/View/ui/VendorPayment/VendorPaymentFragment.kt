package com.retailstreet.mobilepos.View.ui.VendorPayment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.widget.NestedScrollView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerVendorPay
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.IDGenerator
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.Utils.Vibration
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import com.toptoche.searchablespinnerlibrary.SearchableSpinner
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

class VendorPaymentFragment : Fragment() {

    companion object {
        fun newInstance() = VendorPaymentFragment()
    }

    private lateinit var viewModel: VendorPaymentViewModel
    var vendorGuid =""
    var invoiceNumberG =""
    lateinit var paymentRadioGroup: RadioGroup
    var chequeDate:String = ""
    lateinit var chequeNumberEditText:EditText
    lateinit var  chequeAmount: EditText
    var bankGuid:String = ""
    var grand:Double = 0.0
    var amountPaid="0.0"
    var dueAmountValue="0.0"
    lateinit var dueAmountView:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vendor_payment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VendorPaymentViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val parentScrollview: NestedScrollView = view.findViewById(R.id.vd_scroll_view)
        val cashAmountLayout:LinearLayout = view.findViewById(R.id.vd_cash_amount_layout)
        var chequeLayout: View
        val grandTotalView:TextView = view.findViewById(R.id.vd_grand_balance)
        grandTotalView.setText("Grand Balance:\n0.00 ₹")
         dueAmountView = view.findViewById(R.id.vd_due_balance)
        val submitButton:Button = view.findViewById(R.id.submit_vd_payment)
        val payCash:EditText = view.findViewById(R.id.vd_cash_amount_value)


        var invoiceNumberArray:List<StringWithTag> = getInvoiceNumbers(vendorGuid)
        val invoiceSearchSelector: SearchableSpinner = view.findViewById(R.id.vd_invoice_number)
        var invoiceSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, invoiceNumberArray) }!!
        invoiceSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        invoiceSearchSelector.adapter = invoiceSearchAdapter
        invoiceSearchSelector.setTitle("Select Invoice Number")
        invoiceSearchSelector.setPositiveButton("OK")
        invoiceSearchSelector.gravity = Gravity.START

        invoiceSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val invoiceSelected = parent.getItemAtPosition(position) as StringWithTag
                invoiceNumberG = invoiceSelected.string

                if(invoiceSelected.tag.isNotEmpty()) {
                    grand = invoiceSelected.tag.toDouble()
                }else{
                    grand = 0.0;
                }


                if(grand>0) {
                    grandTotalView.text = "Grand Balance:\n $grand ₹"
                    dueAmountView.text = "Due Balance:\n $grand ₹"
                }else{
                    grandTotalView.text = "Grand Balance:\n0.00 ₹"
                    dueAmountView.text = "Due Balance:\n0.00 ₹"
                }

                Log.d("VendorSelected", "onItemSelected: Tag= $invoiceNumberG")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val vendorNameArray:List<StringWithTag> = getVendorName()
        val vendorSearchSelector: SearchableSpinner = view.findViewById(R.id.vd_vendor_value)
        val vendorSearchAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_layout, vendorNameArray) }!!
        vendorSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSearchSelector.adapter = vendorSearchAdapter
        vendorSearchSelector.setTitle("Select Vendor")
        vendorSearchSelector.setPositiveButton("OK")
        vendorSearchSelector.gravity = Gravity.START

        vendorSearchSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val vendorSelected = parent.getItemAtPosition(position) as StringWithTag
                vendorGuid = vendorSelected.tag
                    invoiceNumberArray = getInvoiceNumbers(vendorGuid)
                     invoiceSearchAdapter = context?.let { ArrayAdapter(it, R.layout.spinner_layout, invoiceNumberArray) }!!
                    invoiceSearchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    invoiceSearchSelector.adapter = invoiceSearchAdapter

                Log.d("VendorSelected", "onItemSelected: Tag= $vendorGuid")
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        paymentRadioGroup= view.findViewById(R.id.vd_pay_radio_group)
        paymentRadioGroup.setOnCheckedChangeListener { _, checkedId ->

            if(checkedId==R.id.vd_pay_cash_radio){

                    dueAmountValue="0.0"
                    amountPaid = "0.0"
                dueAmountView.text = "Due Balance:\n $grand ₹"

                payCash.isEnabled = true
                cashAmountLayout.visibility = View.VISIBLE


                chequeLayout = view.findViewById(R.id.scroll_view_cheque_payment)
                (view.findViewById<LinearLayout>(R.id.vd_cheque_inf_layout) as ViewGroup).removeView(chequeLayout)


                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                view.findViewById<LinearLayout>(R.id.vd_cheque_inf_layout).layoutParams = params

                val paramsScrollView = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                paramsScrollView.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                paramsScrollView.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                paramsScrollView.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
                parentScrollview.layoutParams = paramsScrollView

                val marginInPixels:Int = resources.getDimension(R.dimen._20sdp).toInt()
                val paramsBtn = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                paramsBtn.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                paramsBtn.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                paramsBtn.addRule(RelativeLayout.BELOW, R.id.vd_scroll_view)
                paramsBtn.setMargins(marginInPixels, marginInPixels, marginInPixels, marginInPixels) // left, top, right, bottom
                submitButton.layoutParams = paramsBtn

            }else{

                dueAmountValue="0.0"
                amountPaid = "0.0"
                dueAmountView.text = "Due Balance:\n $grand ₹"

                payCash.isEnabled = false
                cashAmountLayout.visibility = View.GONE
                inflateChequeDetails(view as ViewGroup)
                val valueInPixels:Int = resources.getDimension(R.dimen._250sdp).toInt()
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, valueInPixels)
                params.setMargins(0, 0, 0, 0) // left, top, right, bottom
                view.findViewById<LinearLayout>(R.id.vd_cheque_inf_layout).layoutParams = params

                val marginInPixelsBtn:Int = resources.getDimension(R.dimen._20sdp).toInt()
                val paramsBtn = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                paramsBtn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
                paramsBtn.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                paramsBtn.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                paramsBtn.setMargins(marginInPixelsBtn, 0, marginInPixelsBtn, marginInPixelsBtn) // left, top, right, bottom
                submitButton.layoutParams = paramsBtn

                val paramsScrollView = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
                paramsScrollView.addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
                paramsScrollView.addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
                paramsScrollView.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
                paramsScrollView.addRule(RelativeLayout.ABOVE, R.id.submit_vd_payment)
                parentScrollview.layoutParams = paramsScrollView

                chequeLayout = view.findViewById(R.id.scroll_view_cheque_payment)
                chequeLayout.isNestedScrollingEnabled = true

                parentScrollview.post {
                    parentScrollview.fullScroll(View.FOCUS_DOWN)
                }
            }

        }

        payCash.addTextChangedListener {

            if(grand<=0){

                Toast.makeText(context,"Select invoice number first!",Toast.LENGTH_LONG).show()
                Vibration.vibrate(300)
                return@addTextChangedListener
            }

            if(it.toString().trim().isEmpty()){
                dueAmountValue = "0.0"
                dueAmountView.text = "Due Balance:\n $grand ₹"
                return@addTextChangedListener
            }

            val received = it.toString().toDouble()
            if(received<=grand) {
                amountPaid = it.toString()
                dueAmountValue = (grand-received).toString()
                dueAmountView.text = "Due Balance:\n $dueAmountValue ₹"
            }else{
                amountPaid = "0.0"
                dueAmountValue = "0.0"
                dueAmountView.text = "Due Balance:\n $grand ₹"
                Toast.makeText(context,"Incorrect Amount!",Toast.LENGTH_LONG).show()
                Vibration.vibrate(300)
            }



        }

        fun doViewsEmpty() {

            vendorSearchSelector.setSelection(0)
            invoiceSearchSelector.setSelection(0)
            paymentRadioGroup.check(R.id.vd_pay_cash_radio)
            amountPaid = "0.0"
            payCash.setText("")
            amountPaid = "0.0"
            dueAmountValue = "0.0"
            grand = 0.0
            grandTotalView.text = "Grand Balance:\n0.00 ₹"
            dueAmountView.text = "Due Balance:\n0.00 ₹"
        }


        submitButton.setOnClickListener {

            val radioId =  paymentRadioGroup.checkedRadioButtonId
            val paymentNumber = IDGenerator.getTimeStamp()

            if(radioId==R.id.vd_pay_cash_radio){
              //  amountPaid = payCash.text.toString()
                val allStringsArray: Array<String> = arrayOf(amountPaid, vendorGuid,invoiceNumberG);
                if(!validateStrings(allStringsArray) || amountPaid.toDouble()>grand || amountPaid.toDouble()<=0){

                    Toast.makeText(context, "Please fill up all Mandatory (*) fields first!", Toast.LENGTH_LONG).show();
                    Vibration.vibrate(300)
                    return@setOnClickListener
                }

                ControllerVendorPay(paymentNumber, "", amountPaid, "CA", "", "1900-01-01 00:0:00", vendorGuid, "","VENDOR PAYMENT")
                ControllerVendorPay().updateGRNMaster(dueAmountValue,vendorGuid,invoiceNumberG)
                Log.d("RadioChecked", "onViewCreated: CashRadioInvoked")

            }else if(radioId==R.id.vd_pay_cheque_radio){

                val checkNo = chequeNumberEditText.text.toString()
                        //  amountPaid = chequeAmount.text.toString()

                val allStringsArray: Array<String> = arrayOf(amountPaid, vendorGuid,bankGuid,checkNo,chequeDate,invoiceNumberG);

                if(!validateStrings(allStringsArray) || amountPaid.toDouble()>grand || amountPaid.toDouble()<=0){

                    Toast.makeText(context, "Please fill up all Mandatory (*) fields first!", Toast.LENGTH_LONG).show();
                    Vibration.vibrate(300)
                    return@setOnClickListener
                }
                ControllerVendorPay(paymentNumber, bankGuid, amountPaid, "CX", checkNo, chequeDate, vendorGuid, "","VENDOR PAYMENT")
                ControllerVendorPay().updateGRNMaster(dueAmountValue,vendorGuid,invoiceNumberG)
                Log.d("RadioChecked", "onViewCreated: ChequeRadioInvoked")
            }

            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_SUCCESS)
                    .setTitle("Payment Done")
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

    private fun getVendorName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        try {
            list.add(StringWithTag("SELECT VENDOR", ""))
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select DISTINCT gm.VENDOR_GUID, vm.DSTR_NM from retail_str_grn_master gm INNER JOIN retail_str_dstr vm ON gm.VENDOR_GUID = vm.VENDOR_GUID WHERE  CAST(gm.GRANDAMOUNT AS REAL) > 0";
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
            val query = "select INVOICENO, GRANDAMOUNT from retail_str_grn_master  WHERE VENDOR_GUID = '$vendorGuid' AND CAST(GRANDAMOUNT AS REAL) > 0";
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



    private fun inflateChequeDetails(root: ViewGroup){

        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val myView: View= inflater.inflate(R.layout.payment_mode_cheque, null)
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        // params.addRule(RelativeLayout.BELOW, R.id.vi_scroll_view)
        myView.layoutParams = params
        root.findViewById<LinearLayout>(R.id.vd_cheque_inf_layout).addView(myView)

        initChequeDetails(root)


    }
    @SuppressLint("ClickableViewAccessibility")
    private fun initChequeDetails(root: ViewGroup) {

        val bankNameSpinner: SearchableSpinner = root.findViewById(R.id.pay_cheque_bank_value)
        chequeAmount = root.findViewById<EditText>(R.id.pay_cheque_amount_value)
        chequeNumberEditText = root.findViewById<EditText>(R.id.pay_cheque_number)


        val banknameList: List<StringWithTag> = getBankName()
        val bankNameAdapter = ArrayAdapter(requireContext(), R.layout.spinner_layout, banknameList)
        bankNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bankNameSpinner.adapter = bankNameAdapter
        bankNameSpinner.setTitle("Select Bank")
        bankNameSpinner.setPositiveButton("OK")
        bankNameSpinner.gravity = Gravity.START

        bankNameSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, views: View?, position: Int, id: Long) {
                val bankSelected = parent.getItemAtPosition(position) as StringWithTag
                bankGuid = bankSelected.tag
                Log.d("BankSelected", "onItemSelected: Tag= $bankGuid")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val dateSelector: Spinner = root.findViewById<Spinner>(R.id.pay_cheque_date_value)
        val dateItem = arrayOf("DD/MM/YYYY")
        val dateAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_bold, dateItem)
        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dateSelector.adapter = dateAdapter


        dateSelector.setOnTouchListener { v, event ->
            if (event != null) {
                if (event.action == MotionEvent.ACTION_UP) {
                    val c = Calendar.getInstance()
                    val year = c[Calendar.YEAR]
                    val month = c[Calendar.MONTH]
                    val day = c[Calendar.DAY_OF_MONTH]
                    var chequeMont: String? = null
                    var chequeYear: String? = null
                    val dpx = DatePickerDialog(requireContext(),
                            { _: DatePicker?, year1: Int, monthOfYear: Int, dayOfMonth: Int ->
                                chequeMont = if (month < 10) {
                                    "0" + (monthOfYear + 1)
                                } else {
                                    (monthOfYear + 1).toString()
                                }
                                chequeYear = year1.toString()
                                chequeDate = "$dayOfMonth/$chequeMont/$chequeYear"
                                dateItem[0] = chequeDate
                                val chequeAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_bold, dateItem)
                                chequeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                dateSelector.adapter = chequeAdapter

                                chequeDate = "$chequeYear-$chequeMont-$dayOfMonth 00:00:00"

                                Log.d("TimeSelected", "initChequeDetails: " + chequeDate)

                            }, year, month, day)
                    dpx.show()
                }
            }
            true
        }


        chequeAmount.addTextChangedListener {

            if (grand <= 0) {

                Toast.makeText(context, "Select invoice number first!", Toast.LENGTH_LONG).show()
                Vibration.vibrate(300)
                return@addTextChangedListener
            }

            if (it.toString().trim().isEmpty()) {
                dueAmountValue = "0.0"
                dueAmountView.text = "Due Balance:\n $grand ₹"
                return@addTextChangedListener
            }

            val received = it.toString().toDouble()
            if (received <= grand) {
                amountPaid = it.toString()
                dueAmountValue = (grand - received).toString()
                dueAmountView.text = "Due Balance:\n $dueAmountValue ₹"
            } else {
                amountPaid = "0.0"
                dueAmountValue = "0.0"
                dueAmountView.text = "Due Balance:\n $grand ₹"
                Toast.makeText(context, "Incorrect Amount!", Toast.LENGTH_LONG).show()
                Vibration.vibrate(300)
            }

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

    private fun getBankName(): List<StringWithTag> {
        val list: MutableList<StringWithTag> = ArrayList()
        try {
            list.add(StringWithTag("No Bank Selected", ""))
            val mydb = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val query = "select BANK_GUID, BANK_NAME from bank_details"
            val cursor = mydb.rawQuery(query, null)
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast) {
                    val id = cursor.getString(0)
                    val name = cursor.getString(1)
                    list.add(StringWithTag(name, id))
                    cursor.moveToNext()
                }
            }
            Log.d("BankNamesTable", "getBankType: Successfully Fetched")
            cursor.close()
            mydb.close()
        } catch (e: Exception) {
        }
        return list
    }



}