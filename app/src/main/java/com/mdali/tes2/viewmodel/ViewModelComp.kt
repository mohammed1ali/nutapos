package com.mdali.tes2.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mdali.tes2.model.room.Finance
import com.mdali.tes2.model.room.FinanceDao
//import com.raikwaramit.roomdatabasemodule.room.User
//import com.raikwaramit.roomdatabasemodule.room.UserDao
//import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
//import javax.inject.Inject

//@HiltViewModel
class ViewModelComp { //:ViewModel() { // @Inject constructor(private val dao: FinanceDao) : ViewModel() {

    private val _state: MutableState<List<Finance>> = mutableStateOf(emptyList())
    val state: State<List<Finance>> = _state

/*    init {
        getFinanceData()
    }

    private fun getFinanceData() {
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

    /*fun editData(finance: Finance) {
        viewModelScope.launch {
            dao.edit(finance)
            getFinanceData()
        }
    }*/

}