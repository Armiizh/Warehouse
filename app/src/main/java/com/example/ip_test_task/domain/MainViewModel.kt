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
        addInitialData()
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
    private fun addInitialData() {
        viewModelScope.launch {
            if (appDatabase.daoItem().getAllItems().isEmpty()) {
                val items = listOf(
                    Item(1, "iPhone 13", 1633046400000, listOf("Телефон", "Новый", "Распродажа"), 15),
                    Item(2, "Samsung Galaxy S21", 1633132800000, listOf("Телефон", "Хит"), 30),
                    Item(3, "PlayStation 5", 1633219200000, listOf("Игровая приставка", "Акция", "Распродажа"), 7),
                    Item(4, "LG OLED TV", 1633305600000, listOf("Телевизор", "Эксклюзив", "Ограниченный"), 22),
                    Item(5, "Apple Watch Series 7", 1633392000000, listOf("Часы", "Новый", "Рекомендуем"), 0),
                    Item(6, "Xiaomi Mi 11", 1633478400000, listOf("Телефон", "Скидка", "Распродажа"), 5),
                    Item(7, "MacBook Air M1", 1633564800000, listOf("Ноутбук", "Тренд"), 12),
                    Item(8, "Amazon Kindle Paperwhite", 1633651200000, listOf("Электронная книга", "Последний шанс", "Ограниченный"), 18),
                    Item(9, "Fitbit Charge 5", 1633737600000, emptyList(), 27),
                    Item(10, "GoPro Hero 10", 1633824000000, listOf("Камера", "Эксклюзив"), 25)
                )
                appDatabase.daoItem().insertAllItems(items)
            }
        }
    }
}