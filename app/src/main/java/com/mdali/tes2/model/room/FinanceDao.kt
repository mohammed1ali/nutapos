package com.mdali.tes2.model.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import com.mdali.tes2.model.room.Finance

@Dao
interface FinanceDao {
    @Query("SELECT * FROM finance")
    fun getAll(): Flow<List<Finance>>
    //suspend fun getAll(): Flow<List<Finance>>

    // Add or update the existing user data
    @Upsert
    suspend fun insert(finance: Finance)

    @Delete
    suspend fun delete(finance: Finance)

    @Query("DELETE FROM finance")
    suspend fun deleteAll()

    @Update
    suspend fun update(finance: Finance)

}