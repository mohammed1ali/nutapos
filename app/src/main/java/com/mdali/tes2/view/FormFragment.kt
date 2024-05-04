package com.mdali.tes2.view


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.mdali.tes2.FinanceApplication
import com.mdali.tes2.R
import com.mdali.tes2.databinding.FragmentFormBinding
import com.mdali.tes2.model.room.Finance
import com.mdali.tes2.viewmodel.DataViewModel
import com.mdali.tes2.viewmodel.WordViewModelFactory
//import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class FormFragment : Fragment() {

    //private lateinit var mViewModel: DataViewModel
    private var _binding: FragmentFormBinding? = null

    private val binding get() = _binding!!
    private var mSelectedDate:String = ""
    private var mFormattedTime:String = ""
    private var mMasukKe:String = ""
    private var mTerimaDari:String = ""
    private var mNominal:String = ""
    private var mKeterangan = ""
    //mViewModel

    private val financeViewModel: DataViewModel by viewModels {
        WordViewModelFactory((this.activity?.application as FinanceApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)

        //mViewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)



        //mViewModel = ViewModelProvider(this).get(DataViewModel::class.java)

        if ((activity as MainActivity).mIsDataFromListFragment == true) {
            var finance = (activity as MainActivity).checkDataForEditing()
            (activity as MainActivity).mIsDataFromListFragment = false

            setViewsForEditing(finance)


        } else {
            initDatePicker()
            initTimePicker()
            initMasukKe()
            initTerimaDari()
            //initNominal()

            initSimpan()
        }

        return binding.root
    }

    private fun initSimpan() {
        _binding?.buttonSimpan?.setOnClickListener( View.OnClickListener {
            if(mSelectedDate==null || mSelectedDate.equals("")) {
                Toast.makeText(context, "Tolong pilih Tanggal..", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (mFormattedTime==null || mFormattedTime.equals("")) {
                Toast.makeText(context, "Tolong pilih Jam..", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (mMasukKe==null || mMasukKe.equals("")) {
                Toast.makeText(context, "Tolong pilih - Masuk Ke (pilihannya: Kas, BCA, BNI)", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            mTerimaDari = _binding?.tietTerimaDari?.text.toString()
            if (mTerimaDari.equals("null") || mTerimaDari.equals("")) {
                Toast.makeText(context, "Tolong input 'Terima Dari'", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            try {
                mNominal = _binding?.tietNominal?.text.toString()
                if (mNominal.equals("null") || mNominal.equals("")) {
                    Toast.makeText(context, "Tolong input 'Nominal'", Toast.LENGTH_LONG).show()
                    return@OnClickListener
                }
            } catch (e:Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Tolong input 'Nominal'", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            mKeterangan = _binding?.tietKeterangan?.text.toString()
            if (mKeterangan.equals("null")) mKeterangan = ""


            val finance = Finance(
                date = mSelectedDate,
                time = mFormattedTime,
                masukke = mMasukKe,
                terimadari = mTerimaDari,
                nominal = mNominal,
                keterangan = mKeterangan
            )
            //val viewModel = DataViewModel(finance)
            // view model instance
            financeViewModel.insert(finance)

            resetUI()

            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        })
    }

    private fun resetUI() {
        mSelectedDate = ""
        mFormattedTime = ""
        mMasukKe = ""
        mTerimaDari = ""
        mNominal = ""
        mKeterangan = ""
        _binding?.buttonSimpan?.text = "SIMPAN"
        _binding?.tvDate?.text = "Tanggal : "
        _binding?.tvTime?.text = "Jam : "

        val items:Array<String> = resources.getStringArray(R.array.simple_items)
        (_binding?.actvMasukKe as? MaterialAutoCompleteTextView)?.setSimpleItems(items)
        (_binding?.actvMasukKe as? MaterialAutoCompleteTextView)?.setText("", false)
        //_binding?.actvMasukKe?.clearListSelection()

        _binding?.tietTerimaDari?.text?.clear()
        _binding?.tietNominal?.text?.clear()
        _binding?.tietKeterangan?.text?.clear()
    }

    private fun initNominal() {
        _binding?.tietNominal?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                _binding?.tietNominal?.removeTextChangedListener(this)

                try {
                    var originalString = s.toString()
                    val longval: Long
                    /*if (originalString.contains(",")) {
                        originalString = originalString.replace(",".toRegex(), "")
                    }*/
                    longval = originalString.toLong()
                    // Create a new Locale object for Indonesia

                    // Create a new Locale object for Indonesia
                    val myIndonesianLocale = Locale("id", "ID")
                    /*val formatter: DecimalFormat =
                        NumberFormat.getInstance(locale) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")*/
                    // Create a new NumberFormat object for the Indonesian locale
                    val numberFormat = NumberFormat.getCurrencyInstance(myIndonesianLocale)
                    numberFormat.minimumFractionDigits = 0

                    /*val formatter: DecimalFormat =
                        NumberFormat.getInstance(myIndonesianLocale) as DecimalFormat
                    formatter.applyLocalizedPattern()*/

                    val formattedNumber = numberFormat.format(longval)

                    //val formattedString: String = formatter.format(longval)
                    //formattedString.replace(",", ".")

                    //setting text after format to EditText
                    _binding?.tietNominal?.setText(formattedNumber)
                    _binding?.tietNominal?.text?.length?.let {
                        _binding?.tietNominal?.setSelection(it)
                    }
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                    _binding?.tietNominal?.addTextChangedListener(this)
                }

                _binding?.tietNominal?.addTextChangedListener(this)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun initTerimaDari() {
        //_binding?.tietTerimaDari?.
    }

    private fun initMasukKe() {
        _binding?.actvMasukKe?.setOnItemClickListener { parent, view, position, id ->
            var array = this.resources.getStringArray(R.array.simple_items)
            mMasukKe = array[position]
            //Toast.makeText(context, "mMasukKe : $mMasukKe, pos : $position, id : $id", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTimePicker() {
        _binding?.buttonPickTime?.setOnClickListener {

            // instance of MDC time picker
            val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
                // set the title for the alert dialog
                .setTitleText("SELECT YOUR TIMING")
                // set the default hour for the
                // dialog when the dialog opens
                .setHour(12)
                // set the default minute for the
                // dialog when the dialog opens
                .setMinute(10)
                // set the time format
                // according to the region
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build()

            materialTimePicker.show(this.parentFragmentManager, "TAG_TIME_PICKER")

            // on clicking the positive button of the time picker
            // dialog update the TextView accordingly
            materialTimePicker.addOnPositiveButtonClickListener {

                val pickedHour: Int = materialTimePicker.hour
                val pickedMinute: Int = materialTimePicker.minute

                // check for single digit hour hour and minute
                // and update TextView accordingly
                mFormattedTime = when {
                    pickedHour > 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour - 12}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour - 12}:${materialTimePicker.minute} pm"
                        }
                    }

                    pickedHour == 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} pm"
                        }
                    }

                    pickedHour == 0 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour + 12}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour + 12}:${materialTimePicker.minute} am"
                        }
                    }

                    else -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} am"
                        }
                    }
                }
                // then update the preview TextView
                _binding?.tvTime?.text = "Jam : $mFormattedTime"
            }
        }
    }

    private fun initDatePicker() {
        _binding?.buttonPickDate?.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Tanggal")
                    .build()
            datePicker.show(this.parentFragmentManager, "TAG_DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {

                val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
                mSelectedDate = simpleDateFormat.format(it)
                _binding?.tvDate?.text = "Tanggal : $mSelectedDate"

            }
        }
    }

    private fun setViewsForEditing(finance: Finance?) {
        mSelectedDate = finance?.date ?: ""
        mFormattedTime = finance?.time ?: ""
        mMasukKe = finance?.masukke ?: ""
        mTerimaDari = finance?.terimadari ?: ""
        mNominal = finance?.nominal ?: ""
        mKeterangan = finance?.keterangan ?: ""

        _binding?.tvDate?.text = "Tanggal : $mSelectedDate"
        _binding?.tvTime?.text = "Jam : $mFormattedTime"
        //var array = this.resources.getStringArray(R.array.simple_items)
        //var index = array.indexOf(mMasukKe)
        _binding?.actvMasukKe?.setText(mMasukKe)

        _binding?.tietTerimaDari?.setText(mTerimaDari)

        _binding?.tietNominal?.setText(mNominal)

        _binding?.tietKeterangan?.setText(mKeterangan)



        _binding?.buttonPickDate?.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Tanggal")
                    .build()
            datePicker.show(this.parentFragmentManager, "TAG_DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {

                val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
                mSelectedDate = simpleDateFormat.format(it)
                _binding?.tvDate?.text = "Tanggal : $mSelectedDate"
            }
        }


        _binding?.buttonPickTime?.setOnClickListener {

            // instance of MDC time picker
            val materialTimePicker: MaterialTimePicker = MaterialTimePicker.Builder()
                // set the title for the alert dialog
                .setTitleText("SELECT YOUR TIMING")
                // set the default hour for the
                // dialog when the dialog opens
                .setHour(12)
                // set the default minute for the
                // dialog when the dialog opens
                .setMinute(10)
                // set the time format
                // according to the region
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .build()

            materialTimePicker.show(this.parentFragmentManager, "TAG_TIME_PICKER")

            // on clicking the positive button of the time picker
            // dialog update the TextView accordingly
            materialTimePicker.addOnPositiveButtonClickListener {

                val pickedHour: Int = materialTimePicker.hour
                val pickedMinute: Int = materialTimePicker.minute

                // check for single digit hour hour and minute
                // and update TextView accordingly
                mFormattedTime = when {
                    pickedHour > 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour - 12}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour - 12}:${materialTimePicker.minute} pm"
                        }
                    }

                    pickedHour == 12 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} pm"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} pm"
                        }
                    }

                    pickedHour == 0 -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour + 12}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour + 12}:${materialTimePicker.minute} am"
                        }
                    }

                    else -> {
                        if (pickedMinute < 10) {
                            "${materialTimePicker.hour}:0${materialTimePicker.minute} am"
                        } else {
                            "${materialTimePicker.hour}:${materialTimePicker.minute} am"
                        }
                    }
                }
                // then update the preview TextView
                _binding?.tvTime?.text = "Jam : $mFormattedTime"
            }
        }

            _binding?.actvMasukKe?.setOnItemClickListener { parent, view, position, id ->
                var array = this.resources.getStringArray(R.array.simple_items)
                mMasukKe = array[position]
                //Toast.makeText(context, "mMasukKe : $mMasukKe, pos : $position, id : $id", Toast.LENGTH_SHORT).show()
            }


        _binding?.buttonSimpan?.text = "Edit"
        _binding?.buttonSimpan?.setOnClickListener( View.OnClickListener {
            if(mSelectedDate==null || mSelectedDate.equals("")) {
                Toast.makeText(context, "Tolong pilih Tanggal..", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (mFormattedTime==null || mFormattedTime.equals("")) {
                Toast.makeText(context, "Tolong pilih Jam..", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            if (mMasukKe==null || mMasukKe.equals("")) {
                Toast.makeText(context, "Tolong pilih - Masuk Ke (pilihannya: Kas, BCA, BNI)", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            mTerimaDari = _binding?.tietTerimaDari?.text.toString()
            if (mTerimaDari.equals("null") || mTerimaDari.equals("")) {
                Toast.makeText(context, "Tolong input 'Terima Dari'", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            try {
                mNominal = _binding?.tietNominal?.text.toString()
                if (mNominal.equals("null") || mNominal.equals("")) {
                    Toast.makeText(context, "Tolong input 'Nominal'", Toast.LENGTH_LONG).show()
                    return@OnClickListener
                }
            } catch (e:Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Tolong input 'Nominal'", Toast.LENGTH_LONG).show()
                return@OnClickListener
            }
            mKeterangan = _binding?.tietKeterangan?.text.toString()
            if (mKeterangan.equals("null")) mKeterangan = ""


            val fin = Finance(
                uid = finance?.uid ?: 0,
                date = mSelectedDate,
                time = mFormattedTime,
                masukke = mMasukKe,
                terimadari = mTerimaDari,
                nominal = mNominal,
                keterangan = mKeterangan
            )
            //val viewModel = DataViewModel(finance)
            // view model instance
            financeViewModel.update(fin)

            resetUI()
            findNavController().navigate(R.id.action_formFragment_to_listFragment)
        })



        }
}