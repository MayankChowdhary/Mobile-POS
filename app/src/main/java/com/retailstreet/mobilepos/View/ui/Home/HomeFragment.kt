package com.retailstreet.mobilepos.View.ui.Home

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.retailstreet.mobilepos.Controller.ControllerShiftTrans
import com.retailstreet.mobilepos.R
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val myArray:Array<String?> = getStoreName()

        val handlerData: String? = null
        var doubleBackToExitPressedOnce = false
        val queryLatencyHandler = Handler(Looper.getMainLooper())

        val storeTitle:TextView = view.findViewById(R.id.home_store_title)
        val emailTitle:TextView = view.findViewById(R.id.home_email_title)
        val shiftStatus:TextView = view.findViewById(R.id.home_shift_value)
        val username:TextView = view.findViewById(R.id.home_username_value)
        val startTime:TextView = view.findViewById(R.id.home_time_value)
        storeTitle.text = myArray[0]
        emailTitle.text = myArray[1]
        username.text = myArray[2]
        if(isSessionOpen()){
            shiftStatus.text = "Running"

            try {
                val format = SimpleDateFormat("hh:mm:ss", Locale.getDefault()) // 12 hour format
                val d1 = format.parse(ControllerShiftTrans().shiftTime) as Date
                val ppstime = Time(d1.time)
                startTime.text = ppstime.toString()
            } catch (e: java.lang.Exception) {
                Log.e("Exception is ", e.toString())
                startTime.text = ControllerShiftTrans().shiftTime
            }


        }else{
            shiftStatus.text = "Not Running"
          startTime.text = "00:00:00"
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    requireActivity().finish()
                } else {
                    Toast.makeText(context, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
                }

                doubleBackToExitPressedOnce = true

                Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)

                // in here you can do logic when backPress is clicked
            }
        })

    }


    fun getStoreName():Array<String?>{
        var myArray:Array<String?> = arrayOf("", "", "")
        val sh: SharedPreferences = requireContext().getSharedPreferences("com.retailstreet.mobilepos", Context.MODE_PRIVATE)
        val user_id = sh.getString("username", "")


        try {
            val mydb: SQLiteDatabase = requireActivity().openOrCreateDatabase("MasterDB", Context.MODE_PRIVATE, null)
            val cursor = mydb.rawQuery("Select STR_NM, E_MAIL from retail_store", null)
            if (cursor.moveToFirst()) {
                val title: String = cursor.getString(0)
                val subtitle: String = cursor.getString(1)
                myArray[0] = title.toUpperCase(Locale.ROOT)
                myArray[1] = subtitle.toLowerCase(Locale.ROOT)
                myArray[2] = user_id
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return myArray
    }

    private fun isSessionOpen():Boolean{

        val shiftTransID:String? = ControllerShiftTrans().geShiftSession()
        Log.d("SessionCheck", "isSessionOpen:Received $shiftTransID")
        return !(shiftTransID==null || shiftTransID.isEmpty())
    }
}