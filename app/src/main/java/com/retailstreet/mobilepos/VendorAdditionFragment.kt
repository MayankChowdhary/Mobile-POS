package com.retailstreet.mobilepos

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class VendorAdditionFragment : Fragment() {

    companion object {
        fun newInstance() = VendorAdditionFragment()
    }

    private lateinit var viewModel: VendorAdditionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_vendor_addition, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(VendorAdditionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}