package com.example.geekstore.view.fragments

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geekstore.R
import com.example.geekstore.viewmodel.ClockViewModel

class ClockFragment : Fragment() {

    companion object {
        fun newInstance() = ClockFragment()
    }

    private lateinit var viewModel: ClockViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_clock, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(ClockViewModel::class.java)
        // TODO: Use the ViewModel
    }

}