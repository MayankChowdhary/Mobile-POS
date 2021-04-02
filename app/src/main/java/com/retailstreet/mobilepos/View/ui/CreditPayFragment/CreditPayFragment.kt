package com.retailstreet.mobilepos.View.ui.CreditPayFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.retailstreet.mobilepos.R

class CreditPayFragment : Fragment() {

    companion object {
        fun newInstance() = CreditPayFragment()
    }

    private lateinit var viewModel: CreditPayViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_credit_pay, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreditPayViewModel::class.java)
        // TODO: Use the ViewModel
    }

}