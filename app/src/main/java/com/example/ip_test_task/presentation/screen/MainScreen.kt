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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.example.ip_test_task.R
import com.example.ip_test_task.data.model.Item
import com.example.ip_test_task.domain.MainViewModel
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {

    val items by viewModel.items.collectAsState(initial = emptyList())
    var search by remember { mutableStateOf("") }

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
                    items(items.filter { item ->
                        item.name.contains(search, ignoreCase = true)
                    }) { item ->
                        ItemRow(item, viewModel)
                    }
                }
            }
        },
        containerColor = colorResource(id = R.color.scaffoldConteiner)
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ItemRow(item: Item, viewModel: MainViewModel) {
    val formatedDate = item.time.toFormattedDate()
    var editShowDialog by remember { mutableStateOf(false) }
    var deleteShowDialog by remember { mutableStateOf(false) }
    var refreshList by remember { mutableStateOf(false) }
    LaunchedEffect(!editShowDialog || !deleteShowDialog) {
        if (refreshList) {
            viewModel.refreshItems()
            refreshList = false
        }
    }
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
        content = {
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
                            .clickable { editShowDialog = true }
                            .weight(1f),
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = colorResource(id = R.color.edit)
                    )
                    Icon(
                        modifier = Modifier
                            .padding(start = 12.dp)
                            .clickable { deleteShowDialog = true }
                            .weight(1f),
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = colorResource(id = R.color.delete)
                    )
                }
                if (item.tags.isNotEmpty()) {
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
                }

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
            if (editShowDialog) {
                var newAmount by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { editShowDialog = false },
                    title = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "EditAlert",
                                tint = colorResource(id = R.color.AlertIcon)
                            )
                            Text(
                                modifier = Modifier.padding(top = 12.dp),
                                text = stringResource(R.string.Product_quantity)
                            )
                        }
                    },
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { /*TODO*/ }
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(id = R.drawable.ic_reduce_quantity),
                                    contentDescription = "reduce_btn",
                                    tint = colorResource(id = R.color.editBtn)
                                )
                            }
                            Text(
                                modifier = Modifier.padding(horizontal = 22.dp),
                                text = "${item.amount}",
                                fontSize = 20.sp
                            )
                            IconButton(
                                onClick = { /*TODO*/ }
                            ) {
                                Icon(
                                    modifier = Modifier.size(40.dp),
                                    painter = painterResource(id = R.drawable.ic_increase_quantity),
                                    contentDescription = "increase_btn",
                                    tint = colorResource(id = R.color.editBtn)
                                )
                            }
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { editShowDialog = false }
                        ) {
                            Text(
                                text = stringResource(R.string.Cancel),
                                color = colorResource(id = R.color.editBtn),
                                fontSize = 14.sp
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                refreshList = true
                                editShowDialog = false
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.Confirm),
                                color = colorResource(id = R.color.editBtn),
                                fontSize = 14.sp
                            )
                        }
                    }
                )
            }
            if (deleteShowDialog) {
                AlertDialog(
                    onDismissRequest = { deleteShowDialog = false },
                    title = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "DeleteAlert",
                                tint = colorResource(id = R.color.AlertIcon)
                            )
                            Text(
                                modifier = Modifier.padding(top = 12.dp),
                                text = stringResource(R.string.Delete_product)
                            )
                        }
                    },
                    text = {
                        Text(text = stringResource(R.string.Confirm_delete))
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { deleteShowDialog = false }
                        ) {
                            Text(
                                text = stringResource(R.string.No),
                                color = colorResource(id = R.color.editBtn),
                                fontSize = 14.sp
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.viewModelScope.launch {
                                    viewModel.deleteItem(item.id!!)
                                }
                                refreshList = true
                                deleteShowDialog = false }
                        ) {
                            Text(
                                text = stringResource(R.string.Yes),
                                color = colorResource(id = R.color.editBtn),
                                fontSize = 14.sp
                            )
                        }
                    }
                )
            }
        }
    )
}


fun Long.toFormattedDate(): String {
    val date = Date(this)
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    return dateFormat.format(date)
}

/** Я так понял, что тут не используется
 * AssistChip, из-за слишком большого
 * вертикального отступа между строками,
 * поэтому сделал свой chip, который повторяет то,
 * что на предостваленном Вами скриншоте.
 **/

@Composable
fun CustomChip(
    text: String,
    onClick: () -> Unit = { },
    borderColor: Color = Color.Black,
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