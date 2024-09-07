package com.example.ip_test_task.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import com.example.ip_test_task.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoItem {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Flow<Item>

    @Delete
    suspend fun delete(item: Item)

    @Update
    suspend fun update(item: Item)
}