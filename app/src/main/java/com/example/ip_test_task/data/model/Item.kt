package com.example.ip_test_task.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item (
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int?,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("time")
    val time: Long,
    @ColumnInfo("tags")
    val tags: List<String>,
    @ColumnInfo("amount")
    val amount: Int
)