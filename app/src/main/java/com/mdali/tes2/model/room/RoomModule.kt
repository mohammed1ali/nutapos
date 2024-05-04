package com.mdali.tes2.model.room

import android.content.Context
import androidx.room.Room
/*import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton*/


// Module for providing the injectable (DAO) Data access object .
//@Module
//@InstallIn(SingletonComponent::class)
object RoomModule {

    // For getting the application context we have to add Application class
    // and name of the class in manifest to get the context.
    /*@Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): FinanceDatabase {
        return Room.databaseBuilder(context, FinanceDatabase::class.java, "finance-database").build()
    }

    @Provides
    @Singleton
    fun provideUserDao(financeDatabase: FinanceDatabase): FinanceDao {
        return financeDatabase.financeDao()
    }*/

}