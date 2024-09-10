package com.example.ip_test_task.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ip_test_task.data.AppDatabase
import com.example.ip_test_task.data.model.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appDatabase: AppDatabase
): ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        viewModelScope.launch {
            appDatabase.daoItem().getAllItemsFlow().collect { items ->
                _items.value = items
            }
        }
    }

    fun refreshItems() {
        viewModelScope.launch {
            _items.value = appDatabase.daoItem().getAllItems()
        }
    }

    fun updateItemAmountById(id: Int, newAmount: Int) {
        viewModelScope.launch {
            appDatabase.daoItem().updateById(id, newAmount)
        }
    }
    suspend fun deleteItem(id: Int) {
        viewModelScope.launch {
            appDatabase.daoItem().deleteById(id)
        }
    }
}