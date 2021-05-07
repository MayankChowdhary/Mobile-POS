package com.retailstreet.mobilepos.View.ui.DayClose

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.labters.lottiealertdialoglibrary.DialogTypes
import com.retailstreet.mobilepos.Controller.ControllerShiftTrans
import com.retailstreet.mobilepos.R
import com.retailstreet.mobilepos.View.LoginActivity
import com.retailstreet.mobilepos.View.dialog.ClickListeners
import com.retailstreet.mobilepos.View.dialog.LottieAlertDialogs
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Mayank Choudhary on 07-05-2021.
 * mayankchoudhary00@gmail.com
 */

class DayCloseFragmnet : Fragment() {

    lateinit var closeCash:String

    companion object {
        fun newInstance() = DayCloseFragmnet()
    }

    private lateinit var viewModel: DayCloseFragmnetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragmnet_day_close, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DayCloseFragmnetViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val timeDate: TextView = view.findViewById(R.id.date_close_value);
        val cash2000: EditText = view.findViewById(R.id.cash_2000_value);
        val cash500: EditText = view.findViewById(R.id.cash_500_value);
        val cash200: EditText = view.findViewById(R.id.cash_200_value);
        val cash100: EditText = view.findViewById(R.id.cash_100_value);
        val cash50: EditText = view.findViewById(R.id.cash_50_value);
        val cash20: EditText = view.findViewById(R.id.cash_20_value);
        val cash10: EditText = view.findViewById(R.id.cash_10_value);
        val cash5: EditText = view.findViewById(R.id.cash_5_value);
        val cash2: EditText = view.findViewById(R.id.cash_2_value);
        val cash1: EditText = view.findViewById(R.id.cash_1_value);
        val total: TextView = view.findViewById(R.id.cash_total_value);
        val submit: Button = view.findViewById(R.id.submit_day_close);

        timeDate.text = getDateAndTime()
        var totalAmount: Long = 0;
        fun calcAmount() {
            try {
                totalAmount =0
                totalAmount += if( cash2000.text==null || cash2000.text.toString().isEmpty()) 0 else (cash2000.text.toString().toLong())*2000
                totalAmount += if( cash500.text==null || cash500.text.toString().isEmpty()) 0 else (cash500.text.toString().toLong())*500
                totalAmount += if( cash200.text==null || cash200.text.toString().isEmpty()) 0 else (cash200.text.toString().toLong())*200
                totalAmount += if( cash100.text==null || cash100.text.toString().isEmpty()) 0 else (cash100.text.toString().toLong())*100
                totalAmount += if( cash50.text==null || cash50.text.toString().isEmpty()) 0 else (cash50.text.toString().toLong())*50
                totalAmount += if( cash20.text==null || cash20.text.toString().isEmpty()) 0 else (cash20.text.toString().toLong())*20
                totalAmount += if( cash10.text==null || cash10.text.toString().isEmpty()) 0 else (cash10.text.toString().toLong())*10
                totalAmount +=if( cash5.text==null || cash5.text.toString().isEmpty()) 0 else (cash5.text.toString().toLong())*5
                totalAmount += if( cash2.text==null || cash2.text.toString().isEmpty()) 0 else (cash2.text.toString().toLong())*2
                totalAmount += if( cash1.text==null || cash1.text.toString().isEmpty()) 0 else (cash1.text.toString().toLong())*1
                total.text = totalAmount.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        cash2000.addTextChangedListener {
            calcAmount()
        }
        cash500.addTextChangedListener {
            calcAmount()
        }
        cash200.addTextChangedListener {
            calcAmount()
        }
        cash100.addTextChangedListener {
            calcAmount()
        }
        cash50.addTextChangedListener {
            calcAmount()
        }
        cash20.addTextChangedListener {
            calcAmount()
        }
        cash10.addTextChangedListener {
            calcAmount()
        }
        cash5.addTextChangedListener {
            calcAmount()
        }
        cash2.addTextChangedListener {
            calcAmount()
        }
        cash1.addTextChangedListener {
            calcAmount()
        }

      submit.setOnClickListener {

          closeCash = totalAmount.toString();

          if(closeCash.toDouble()<=0){

              Toast.makeText(context,"Invalid Amount!",Toast.LENGTH_LONG).show()
              return@setOnClickListener;
          }

           Toast.makeText(context, "Day Successfully Closed!", Toast.LENGTH_LONG).show()
            ControllerShiftTrans().closeDay(closeCash);
          val sharedPreferences: SharedPreferences? = activity?.getSharedPreferences("com.retailstreet.mobilepos", Context.MODE_PRIVATE)
          val myEdit = sharedPreferences?.edit()
          myEdit?.remove("username")
          Intent("password")
          myEdit?.apply()
          val loginIntent = Intent(activity, LoginActivity::class.java)
          startActivity(loginIntent)
          activity?.finish()
      }

        if(!isSessionOpen()){

            val alertDialog = LottieAlertDialogs.Builder(context, DialogTypes.TYPE_WARNING)
                    .setTitle("Shift Status")
                    .setDescription("Your Shift is not running!")
                    .setNegativeText("Open Day")
                    .setPositiveButtonColor(Color.parseColor("#297999"))
                    .setPositiveTextColor(Color.parseColor("#ffffff"))
                    .setPositiveText("Goto Sales")
                    .setNegativeButtonColor(Color.parseColor("#297999"))
                    .setNegativeTextColor(Color.parseColor("#ffffff"))
                    .setPositiveListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {

                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_dayclose_to_nav_sales)
                            dialog.dismiss()
                        }
                    })
                    .setNegativeListener(object : ClickListeners {
                        override fun onClick(dialog: LottieAlertDialogs) {

                            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.action_nav_dayclose_to_nav_dayopen)
                            dialog.dismiss()
                        }
                    })
                    .build()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

    }

    private fun getDateAndTime(): String {
        val formatter = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val date = Date()
        return formatter.format(date)
    }

    private fun isSessionOpen():Boolean{

        val shiftTransID:String? = ControllerShiftTrans().geShiftSession()
        Log.d("SessionCheck", "isSessionOpen:Received $shiftTransID")
        return !(shiftTransID==null || shiftTransID.isEmpty())
    }

}