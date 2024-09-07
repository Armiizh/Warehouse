package com.example.ip_test_task.presentation.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ip_test_task.R
import com.example.ip_test_task.data.model.Item
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    var search by remember { mutableStateOf("") }
    val items = listOf(
        Item(1, "iPhone 13", 1633046400000, listOf("Телефон", "Новый", "Распродажа"), 15),
        Item(2, "Samsung Galaxy S21", 1633132800000, listOf("Телефон", "Хит"), 30),
        Item(
            3,
            "PlayStation 5",
            1633219200000,
            listOf("Игровая приставка", "Акция", "Распродажа"),
            7
        ),
        Item(4, "LG OLED TV", 1633305600000, listOf("Телевизор", "Эксклюзив", "Ограниченный"), 22),
        Item(5, "Apple Watch Series 7", 1633392000000, listOf("Часы", "Новый", "Рекомендуем"), 0),
        Item(6, "Xiaomi Mi 11", 1633478400000, listOf("Телефон", "Скидка", "Распродажа"), 5),
        Item(7, "MacBook Air M1", 1633564800000, listOf("Ноутбук", "Тренд"), 12),
        Item(
            8,
            "Amazon Kindle Paperwhite",
            1633651200000,
            listOf("Электронная книга", "Последний шанс", "Ограниченный"), 18
        ),
        Item(9, "Fitbit Charge 5", 1633737600000, emptyList(), 27),
        Item(10, "GoPro Hero 10", 1633824000000, listOf("Камера", "Эксклюзив"), 25)
    )
    val filteredItems = remember(search) {
        items.filter { item ->
            item.name.contains(search, ignoreCase = true)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.Product_list),
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.topAppBar)
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .padding(top = 16.dp, bottom = 4.dp),
                    value = search,
                    onValueChange = {
                        search = it
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "SearchIcon"
                        )
                    },
                    trailingIcon = {
                        if (search.isNotEmpty()) {
                            IconButton(onClick = { search = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    label = { Text(text = stringResource(R.string.Search_products)) },
                    shape = RoundedCornerShape(6.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(id = R.color.focusedBorder),
                        unfocusedBorderColor = Color.Black
                    )
                )
                LazyColumn {
                    items(filteredItems) { item ->
                        ItemRow(item = item)
                    }
                }
            }
        },
        containerColor = colorResource(id = R.color.scaffoldConteiner)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemRow(item: Item) {
    val formatedDate = item.time.toFormattedDate()
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    modifier = Modifier
                        .clickable { }
                        .weight(1f),
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = colorResource(id = R.color.edit)
                )
                Icon(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .clickable { }
                        .weight(1f),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = colorResource(id = R.color.delete)
                )
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                content = {
                    item.tags.forEach { tag ->
                        CustomChip(text = tag)
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.In_stock),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = "${item.amount}",
                        color = Color.Black
                    )
                }
                Column(
                    modifier = Modifier.weight(0.5f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.Date_added),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )
                    Text(
                        text = formatedDate,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

fun Long.toFormattedDate(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

/** Я так понял, что тут не используется,
 * AssistChip, из-за слишком большого
 * вертикального отступа между строками,
 * поэтому сделал свой chip, который повторяет то,
 * что на предостваленном Вами скриншоте.
 **/
@Composable
fun CustomChip(
    text: String,
    onClick: () -> Unit = {},
    borderColor: Color = Color.Gray,
    contentColor: Color = Color.Black
) {
    Text(
        text = text,
        color = contentColor,
        modifier = Modifier
            .border(width = 1.dp, brush = SolidColor(borderColor), shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 2.dp)
            .clip(RoundedCornerShape(4.dp))
            .clickable(onClick = onClick)
    )
}