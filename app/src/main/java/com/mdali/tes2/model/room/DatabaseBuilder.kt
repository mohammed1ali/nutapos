package com.mdali.tes2.model.room

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: FinanceDatabase? = null
    fun getInstance(context: Context): FinanceDatabase {
        if (INSTANCE == null) {
            synchronized(FinanceDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }
    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            FinanceDatabase::class.java,
            "finance-database"
        ).build()
}