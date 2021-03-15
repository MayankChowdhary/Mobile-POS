package com.retailstreet.mobilepos.View.ui.DayOpen

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerShiftTrans
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.Utils.StringWithTag
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import java.text.SimpleDateFormat
import java.util.*

class DayOpenFragment : Fragment() {

    companion object {
        fun newInstance() = DayOpenFragment()
    }

    private lateinit var viewModel: DayOpenViewModel
    private var shif_array: Array<StringWithTag> = emptyArray()
    private lateinit var shift_tag: String
    private lateinit var username: String
    private lateinit var dateTime: String
    private lateinit var startCash: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_day_open, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = getUsername();
        shif_array= ControllerShiftTrans().shiftOptions.toTypedArray();
        dateTime = getDateAndTime()

        val posDate: TextView = view.findViewById(R.id.pos_date_value)
        posDate.text = dateTime

        val shiftSelector: Spinner = view.findViewById<Spinner>(R.id.shift_spinner)
        val shiftAdapter: ArrayAdapter<StringWithTag> = context?.let { ArrayAdapter(it, R.layout.spinner_item_centre, shif_array) }!!
        shiftAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        shiftSelector.adapter = shiftAdapter

        val cashEditText : EditText = view.findViewById(R.id.cash_value)

        shiftSelector.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val shiftSelected = parent.getItemAtPosition(position) as StringWithTag
                shift_tag = shiftSelected.tag

                Log.d("ShiftSelected", "onItemSelected: Tag= $shift_tag")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        val openButton: Button = view.findViewById(R.id.submit_dayopen);
        openButton.setOnClickListener{
            startCash = cashEditText.text.toString()
            ControllerShiftTrans(dateTime, username, shift_tag, startCash)
            activity?.let { it1 -> Navigation.findNavController(it1, R.id.nav_host_fragment).navigate(R.id.nav_sales) }
            Toast.makeText(activity, "Shift Successfully Started!", Toast.LENGTH_LONG).show();

        }

        if(isSessionOpen()) {

            val progressDialog: LottieAlertDialogs = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_LOADING)
                    .setTitle("Shift Status")
                    .setDescription("Your Shift is Running...")
                    .setPositiveText("Goto Sales")
                    .setNegativeText("Day Close")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setNegativeButtonColor(Color.parseColor("#297999"))
                    .setNegativeTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {

                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_dayopen_to_nav_sales)
                            dialog.dismiss()
                        }
                    })
                    .setNegativeListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {
                            ControllerShiftTrans().closeSessions()
                            Toast.makeText(context, "Force Closed Shift!!", Toast.LENGTH_LONG).show()
                            dialog.dismiss()

                        }
                    })
                    .build()
            progressDialog.setCancelable(false)
            progressDialog.show()

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DayOpenViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun getDateAndTime(): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val date = Date()
        return formatter.format(date)
    }

    private fun getUsername():String{

        val sh = requireContext().getSharedPreferences("com.retailstreet.mobilepos", Context.MODE_PRIVATE)
        return sh.getString("username", "")!!

    }
    private fun isSessionOpen():Boolean{

        val shiftTransID:String? = ControllerShiftTrans().geShiftSession()
        Log.d("SessionCheck", "isSessionOpen:Received $shiftTransID")
        return !(shiftTransID==null || shiftTransID.isEmpty())
    }



}