package com.retailstreet.mobilepos.View.ui.Summery

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.retailstreet.mobilepos.Controller.ControllerSummery
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.View.dialog.LoadingDialog
import com.retailstreet.mobilepos.View.ui.VendorReport.TableViewComponents.User
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class SummeryFragment : Fragment() {

    companion object {
        fun newInstance() = SummeryFragment()
    }

    private lateinit var viewModel: SummeryViewModel
    private val controllerSummery:ControllerSummery = ControllerSummery()

    lateinit var spinner: Spinner
    private var startDate:String =""
    private var endDate:String = ""
    private lateinit var loadingDialog:LoadingDialog
    private var openBalance:String = "0.00"
    private lateinit var openBalanceTV:TextView
    private var billCount:String = "0.00"
    private lateinit var billCountTV:TextView
    private var billCancelled:String = "0.00"
    private lateinit var billCancelledTV:TextView
    private var salesReturnCash:String = "0.00"
    private lateinit var salesReturnCashTv:TextView
    private var salesReturnCreditNote:String = "0.00"
    private lateinit var salesReturnCreditNoteTv:TextView
    private var creditPaymentTotal:String = "0.00"
    private lateinit var creditPaymentTotalTv:TextView
    private var creditPaymentCash:String = "0.00"
    private lateinit var creditPaymentCashTv:TextView
    private var totalExpenseCash:String = "0.00"
    private lateinit var totalExpenseCashTv:TextView
    private var totalExpenseCheque:String = "0.00"
    private lateinit var totalExpenseChequeTv:TextView
    private var vendorPaymentCash:String = "0.00"
    private lateinit var vendorPaymentCashTv:TextView
    private var vendorPaymentCheque:String = "0.00"
    private lateinit var vendorPaymentChequeTv:TextView
    private var saleOfTheDay:String = "0.00"
    private lateinit var saleOfTheDayTv:TextView
    private var cashPayment:String = "0.00"
    private lateinit var cashPaymentTv:TextView
    private var cardPayment:String = "0.00"
    private lateinit var cardPaymentTv:TextView
    private var creditSale:String = "0.00"
    private lateinit var creditSaleTv:TextView
    private var creditNote:String = "0.00"
    private lateinit var creditNoteTv:TextView
    private var totalOnlinePay:String = "0.00"
    private lateinit var totalOnlinePayTv:TextView
    private var advanceCollection:String = "0.00"
    private lateinit var advanceCollectionTv:TextView
    private var advanceAdjustment:String = "0.00"
    private lateinit var advanceAdjustmentTv:TextView
    private lateinit var cashInHandTv:TextView
    private var totalCredit:String = "0.00"
    private lateinit var totalCreditTv:TextView
    private var vendorPayPending:String = "0.00"
    private lateinit var vendorPayPendingTv:TextView
    private var customerAdvance:String = "0.00"
    private lateinit var customerAdvanceTv:TextView
    private var saleOfTheMonth:String = "0.00"
    private lateinit var saleOfTheMonthTv:TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        loadingDialog = LoadingDialog()
        return inflater.inflate(R.layout.fragment_summery, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SummeryViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val item: MenuItem = menu.findItem(R.id.filterSpinner)
        item.isVisible = true
        spinner = item.actionView as Spinner
        val filterArray: Array<String> = arrayOf("TODAY'S", "DATE-RANGE")
        val filterAdapter: ArrayAdapter<String> = context?.let { ArrayAdapter(it, R.layout.spinner_item_action_bar, filterArray) }!!
        filterAdapter.setDropDownViewResource(R.layout.spinner_layout_actionbar)
        spinner.adapter = filterAdapter
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

                if(position ==1){
                val builder = MaterialDatePicker.Builder.dateRangePicker()
                //builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar_Fullscreen)
                val now = Calendar.getInstance()
                builder.setSelection(androidx.core.util.Pair(now.timeInMillis, now.timeInMillis))
                val picker = builder.build()
                picker.show(activity?.supportFragmentManager!!, picker.toString())
                picker.addOnNegativeButtonClickListener {
                    picker.dismiss()
                    spinner.setSelection(0)
                }
                picker.addOnPositiveButtonClickListener {
                    startDate = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(it.first?.let { it1 -> Date(it1) })
                    endDate = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    ).format(it.second?.let { it1 -> Date(it1) })
                    var userList: List<User>
                    showDialogs()
                    thread {
                       reloadUiValues()
                        requireActivity().runOnUiThread(Runnable {
                            // Stuff that updates the UI
                            refreshUiValues()
                            cancelDialogs()
                        })
                    }
                }

                }else{

                    startDate = ""
                    endDate = ""
                    showDialogs()
                    thread {
                        reloadUiValues()
                        requireActivity().runOnUiThread(Runnable {
                            // Stuff that updates the UI
                            refreshUiValues()
                            cancelDialogs()
                        })
                    }
                }
                Log.d("ShiftSelected", "onItemSelected: Tag= $position")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        openBalanceTV = view.findViewById(R.id.s_open_value)
        billCountTV = view.findViewById(R.id.s_total_bill_value)
        billCancelledTV = view.findViewById(R.id.s_bill_canceled_value)
        salesReturnCashTv = view.findViewById(R.id.s_sales_return_cash_value)
        salesReturnCreditNoteTv = view.findViewById(R.id.s_sales_return_credit_value)
        creditPaymentTotalTv = view.findViewById(R.id.s_credit_payment_value)
        creditPaymentCashTv = view.findViewById(R.id.s_credit_payment_cash_value)
        totalExpenseCashTv = view.findViewById(R.id.s_expanses_cash_value)
        totalExpenseChequeTv = view.findViewById(R.id.s_expanses_cheque_value)
        vendorPaymentCashTv = view.findViewById(R.id.s_vendor_pay_cash_value)
        vendorPaymentChequeTv = view.findViewById(R.id.s_vendor_pay_cheque_value)
        saleOfTheDayTv = view.findViewById(R.id.s_total_sale_day_value)
        cashPaymentTv = view.findViewById(R.id.s_cash_payment_value)
        cardPaymentTv = view.findViewById(R.id.s_card_payment_value)
        creditSaleTv = view.findViewById(R.id.s_credit_sales_value)
        creditNoteTv = view.findViewById(R.id.s_credit_note_value)
        totalOnlinePayTv = view.findViewById(R.id.s_online_payment_value)
        advanceAdjustmentTv= view.findViewById(R.id.s_today_advance_adjst_value)
        advanceCollectionTv = view.findViewById(R.id.s_today_advance_colec_value)
        cashInHandTv = view.findViewById(R.id.s_cash_in_hand_value)
        totalCreditTv = view.findViewById(R.id.s_total_credit_value)
        vendorPayPendingTv = view.findViewById(R.id.s_vendor_pay_pending_value)
        customerAdvanceTv = view.findViewById(R.id.s_advance_customer_value)
        saleOfTheMonthTv = view.findViewById(R.id.s_sale_of_month_value)
        refreshUiValues()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun reloadUiValues(){

       openBalance= controllerSummery.getOpeningBalance(startDate, endDate)
        billCount= controllerSummery.getTotalBillCount(startDate, endDate)
        billCancelled =  controllerSummery.getTotalBillCancelled(startDate, endDate)
        salesReturnCash = controllerSummery.getSalesReturnCash(startDate, endDate)
        salesReturnCreditNote =  controllerSummery.getSalesReturnCreditNote(startDate, endDate)
        creditPaymentTotal =  controllerSummery.getTotalCreditPayment(startDate, endDate)
        creditPaymentCash  =  controllerSummery.getCreditPaymentCash(startDate, endDate)
        totalExpenseCash   =  controllerSummery.getTotalDailyExpenses(startDate, endDate,"CA","UTILITY PAYMENTS")
        totalExpenseCheque =  controllerSummery.getTotalDailyExpenses(startDate, endDate,"CX","UTILITY PAYMENTS")
        vendorPaymentCash  =  controllerSummery.getTotalDailyExpenses(startDate, endDate,"CA","VENDOR PAYMENTS")
        vendorPaymentCheque =  controllerSummery.getTotalDailyExpenses(startDate, endDate,"CX","VENDOR PAYMENTS")
        saleOfTheDay =  controllerSummery.getTotalSaleOfDay(startDate, endDate)
        cardPayment =  getFormattedValue(controllerSummery.getSumOfSaleByType(startDate, endDate, "ADB4763E-1BD4-4589-A460-5F369DB42C31"))
        cashPayment =  getFormattedValue(controllerSummery.getSumOfSaleByType(startDate, endDate, "E123BBBE-A000-4617-AD49-9B5AF2275F43"))
        creditSale = getFormattedValue(controllerSummery.getDailyDebitpayment(startDate, endDate))
        creditNote = getFormattedValue(controllerSummery.getSumOfSaleByType(startDate, endDate, "03857C6B-E4C0-4E20-9318-48499911929D"))
        totalOnlinePay = controllerSummery.getTotalOnlinePayment(startDate, endDate)
        advanceCollection = getFormattedValue(controllerSummery.getDailyAdvancepaymentpaymentReceived(startDate, endDate))
        advanceAdjustment =  getFormattedValue(controllerSummery.getDailyAdvanceadjust(startDate, endDate))
        totalCredit  = controllerSummery.allCreditCustomer
        vendorPayPending = controllerSummery.allvendorpayment
        customerAdvance = controllerSummery.allCreditCustomerAdvance
        saleOfTheMonth = controllerSummery.monthlyTotal


    }

    private fun refreshUiValues(){

        openBalanceTV.text = openBalance
        billCountTV.text = billCount
        billCancelledTV.text = billCancelled
        salesReturnCashTv.text= salesReturnCash
        salesReturnCreditNoteTv.text = salesReturnCreditNote
        creditPaymentTotalTv.text = creditPaymentTotal
        creditPaymentCashTv.text = creditPaymentCash
        totalExpenseCashTv.text = totalExpenseCash
        totalExpenseChequeTv.text = totalExpenseCheque
        vendorPaymentChequeTv.text = vendorPaymentCheque
        vendorPaymentCashTv.text = vendorPaymentCash
        saleOfTheDayTv.text = saleOfTheDay
        cashPaymentTv.text = cashPayment
        cardPaymentTv.text = cardPayment
        creditSaleTv.text = creditSale
        creditNoteTv.text = creditNote
        advanceCollectionTv.text = advanceCollection
        advanceAdjustmentTv.text = advanceAdjustment
        totalCreditTv.text = totalCredit
        vendorPayPendingTv.text = vendorPayPending
        customerAdvanceTv.text = customerAdvance
        saleOfTheMonthTv.text = saleOfTheMonth
        totalOnlinePayTv.text = totalOnlinePay

        val plus: Double = cashPaymentTv.text.toString().toDouble() + openBalanceTV.text.toString().toDouble()+ creditPaymentTotalTv.text.toString().toDouble() + advanceCollectionTv.text.toString().toDouble()
        val minus: Double = totalExpenseCashTv.text.toString().toDouble() + vendorPaymentCashTv.text.toString().toDouble() + salesReturnCashTv.text.toString().toDouble()
        cashInHandTv.text = getFormattedValue(plus - minus)

    }

    private fun showDialogs(){

        loadingDialog.showDialog(activity,"Refreshing...","Please wait")
    }
    private fun cancelDialogs(){
        loadingDialog.cancelDialog()
    }

    private fun getFormattedValue(value: Double): String {
        return String.format(Locale.getDefault(), "%.2f", value)
    }
}