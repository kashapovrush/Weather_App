package com.kashapovrush.database_impl.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourite_cities")
data class CityDb(
    @PrimaryKey val id: Int,
    val name: String,
    val country: String
)
