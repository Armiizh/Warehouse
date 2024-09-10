package com.example.ip_test_task.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ip_test_task.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoItem {

    @Query("SELECT * FROM item")
    fun getAllItemsFlow(): Flow<List<Item>>
    
    @Query("SELECT * FROM item")
    suspend fun getAllItems(): List<Item>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(item: List<Item>)

    @Insert
    suspend fun insertItem(item: Item)

    @Query("UPDATE item SET amount = :newAmount WHERE id = :id")
    suspend fun updateById(id: Int, newAmount: Int)

    @Query("DELETE FROM item WHERE id = :id")
    suspend fun deleteById(id: Int): Int
}