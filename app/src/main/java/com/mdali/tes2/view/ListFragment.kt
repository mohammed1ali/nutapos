package com.mdali.tes2.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.mdali.tes2.FinanceApplication
import com.mdali.tes2.R
import com.mdali.tes2.databinding.FragmentListBinding
import com.mdali.tes2.model.room.Finance
import com.mdali.tes2.view.lists.WordListAdapter
import com.mdali.tes2.viewmodel.DataViewModel
import com.mdali.tes2.viewmodel.WordViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class ListFragment : Fragment(), WordListAdapter.ClickInterface {

    private lateinit var mAdapter: WordListAdapter
    private var _binding: FragmentListBinding? = null

    private val binding get() = _binding!!

    private val financeViewModel: DataViewModel by viewModels {
        WordViewModelFactory((this.activity?.application as FinanceApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        val recyclerView = _binding?.recyclerview
        mAdapter = WordListAdapter(this)
        recyclerView?.adapter = mAdapter
        recyclerView?.layoutManager = LinearLayoutManager(context)

        // Add an observer on the LiveData returned by .
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.

/*        val dateToday = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(Date())

        val liveFinanceData = financeViewModel.allfinance
        val listFinance = liveFinanceData.value

        var tempTodayList = mutableListOf<Finance>()
        listFinance?.forEach {
            if (it.date.equals(dateToday))  {
                tempTodayList.add(it)
            }
        }
        if (tempTodayList.size > 0) {
            mAdapter.submitList(tempTodayList)
        }*/

        financeViewModel.allfinance.observe(viewLifecycleOwner) { finance ->

            finance.let { mAdapter.submitList(it)
            }
        }


        _binding?.buttonPickDaterange?.setOnClickListener {
            datePickerDialog()
        }


        return binding.root
    }

    private fun datePickerDialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        val builder =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")

        builder.setTitleText("Select a date range")

        // Building the date picker dialog
        val datePicker = builder.build()
        datePicker.addOnPositiveButtonClickListener { selection ->
            // Retrieving the selected start and end dates
            val start_Date = selection.first
            val end_Date = selection.second

            // Formatting the selected dates as strings
            val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
            val startDateString = sdf.format(Date(start_Date))
            val endDateString = sdf.format(Date(end_Date))

            // Creating the date range string
            val selectedDateRange = "$startDateString - $endDateString"

            // Displaying the selected date range in the TextView
            _binding?.buttonPickDaterange?.text = selectedDateRange

            /*val startDate = LocalDate.parse(startDateString)
            val endDate = LocalDate.parse(endDateString)
            var tempRangeList = mutableListOf<Finance>()
            financeViewModel.allfinance.value?.forEach{
                var elementDate = LocalDate.parse(it.date)
                if(elementDate.equals(startDate) || elementDate.equals(endDate) || ( elementDate.isAfter(startDate) && elementDate.isBefore(endDate) )) {
                    tempRangeList.add(it)
                }
            }
            mAdapter.submitList(tempRangeList)*/
            //(_binding?.recyclerview?.adapter as ListAdapter).submitList(tempRangeList)

        }

        // Showing the date picker dialog
        datePicker.show(parentFragmentManager, "DATE_PICKER")
    }

    public fun deleteOneItem(finance: Finance) {
        financeViewModel.delete(finance)
        Log.d("FRAG_LIST", "************** deleting $finance")
    }

    override fun frag2btnClicked(finance: Finance) {
        deleteOneItem(finance)
    }

    fun editOneItem(finance: Finance) {
        financeViewModel.update(finance)
    }

    override fun fragEditbtnClicked(finance: Finance) {
        //editOneItem(finance)
        findNavController().navigate(R.id.action_listFragment_to_formFragment)
        (this.context as MainActivity).dataForFormFragment(finance)
    }

}