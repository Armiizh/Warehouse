package com.example.ip_test_task.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ip_test_task.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoItem {

    @Query("SELECT * FROM items")
    fun getAllItemsFlow(): Flow<List<Item>>
    
    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items: List<Item>)

    @Insert
    suspend fun insertItem(item: Item)

    @Query("UPDATE items SET amount = :newAmount WHERE id = :id")
    suspend fun updateById(id: Int, newAmount: Int)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}