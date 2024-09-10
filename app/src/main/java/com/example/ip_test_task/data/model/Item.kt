package com.example.ip_test_task.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item (
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("amount")
    val amount: Int,
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo("time")
    val time: Long,
    @ColumnInfo("tags")
    val tags: List<String>
)