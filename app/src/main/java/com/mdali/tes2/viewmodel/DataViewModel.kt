package com.mdali.tes2.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mdali.tes2.model.room.Finance
import com.mdali.tes2.model.room.FinanceDao
import com.mdali.tes2.model.room.FinanceRepository
import kotlinx.coroutines.launch
//import javax.inject.Inject

class DataViewModel (private val repository: FinanceRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allfinance: LiveData<List<Finance>> = repository.allFinance.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(finance: Finance) = viewModelScope.launch {
        repository.insert(finance)
    }

    fun delete(finance: Finance) = viewModelScope.launch {
        repository.delete(finance)
    }

    fun update(finance: Finance) = viewModelScope.launch {
        repository.update(finance)
    }


    //private val _state: MutableState<List<Finance>> = mutableStateOf(emptyList())
    //val state: State<List<Finance>> = _state

    /*init {
        getFinanceData()
    }*/

    /*private fun getFinanceData() {
        viewModelScope.launch {
            _state.value = dao.getAll()
        }
    }

    fun insertData(finance: Finance) {
        viewModelScope.launch {
            dao.insert(finance)
            getFinanceData()
        }
    }

    fun deleteData(finance: Finance) {
        viewModelScope.launch {
            dao.delete(finance)
            getFinanceData()
        }
    }*/

}

class WordViewModelFactory(private val repository: FinanceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DataViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}