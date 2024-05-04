package com.mdali.tes2

import android.app.Application
import com.mdali.tes2.model.room.FinanceDatabase
import com.mdali.tes2.model.room.FinanceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

//import dagger.hilt.android.HiltAndroidApp

/**
 * For providing the application context anywhere.
 */
class FinanceApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { FinanceDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { FinanceRepository(database.financeDao()) }
}