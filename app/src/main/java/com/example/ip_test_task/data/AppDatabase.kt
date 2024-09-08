package com.example.ip_test_task.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.ip_test_task.data.dao.DaoItem
import com.example.ip_test_task.data.model.Item

@Database(entities = [Item::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun daoItem(): DaoItem
}