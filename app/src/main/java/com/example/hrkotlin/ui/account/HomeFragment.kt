package com.example.hrkotlin.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import com.example.hrkotlin.R
import com.example.hrkotlin.base.BaseFragment
import com.example.hrkotlin.network.adapter.SalaryAdapter
import com.example.hrkotlin.network.adapter.SalaryListenner
import com.example.hrkotlin.ui.home.HomeActivity
import com.example.hrkotlin.ui.home.HomeActivityPresenter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeActivityPresenter, HomeActivity>() {
    var adapter: SalaryAdapter? = null
     var rc : RecyclerView? = null
        var spinnerYear: Spinner?= null;

    override val layoutId: Int
        get() = R.layout.fragment_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this, view)
        rc = view.findViewById(R.id.recyclerTest)
        spinnerYear = view.findViewById(R.id.spinnerYear)
        var adapterSpinner = ArrayAdapter.createFromResource(requireActivity(), R.array.numbers_Year, R.layout.my_selected_item)
        adapterSpinner.setDropDownViewResource(R.layout.my_dropdown_item)
        spinnerYear?.setAdapter(adapterSpinner)
        val list = listOf("Tháng 1", "Tháng 2", "Tháng 3")
        adapter = SalaryAdapter(list)
        adapter?.setListenner(object : SalaryListenner {
            override fun onSalarySelected() {
            }
        })
        rc?.layoutManager = LinearLayoutManager(requireContext())
        rc?.adapter = adapter
        return view
    }



    override fun onPrepareLayout() {
        val list = listOf("Tháng 1", "Tháng 2", "Tháng 3")
        adapter = SalaryAdapter(list)
        rc?.layoutManager = LinearLayoutManager(requireContext())
        rc?.adapter = adapter
    }

    override fun onCreatePresenter(): HomeActivityPresenter? {
        return null
    }
}
