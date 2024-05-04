package com.mdali.tes2.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * data class representing the table and its column for the room database.
 */
@Entity(tableName = "finance")
data class Finance (
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "masukke") val masukke: String,
    @ColumnInfo(name = "terimadari") val terimadari: String,
    @ColumnInfo(name = "nominal") val nominal: String,
    @ColumnInfo(name = "keterangan") val keterangan: String,

    /*@ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "phone") val phone: String,*/
    )