package com.mdali.tes2.model.room

import android.util.Log
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class FinanceRepository (private val financeDao: FinanceDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allFinance: Flow<List<Finance>> = financeDao.getAll()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(finance: Finance) {
        financeDao.insert(finance)
        Log.d("DB", "************************** inserting : $finance")
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(finance: Finance) {
        financeDao.delete(finance)
        Log.d("DB", "************************** deleting : $finance")
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(finance: Finance) {
        financeDao.update(finance)
        Log.d("DB", "************************** updating : $finance")
    }
}