@file:OptIn(ExperimentalUuidApi::class, ExperimentalMaterial3ExpressiveApi::class)

package com.sosauce.sweetselect

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MaterialExpressiveTheme(
                colorScheme = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) dynamicDarkColorScheme(LocalContext.current) else darkColorScheme()
            ) {
                val sweetState = rememberSweetSelectState<Item>()
                val items = remember { List(100) { Item() } }

                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                if (items.size == sweetState.selectedItems.size) {
                                    sweetState.clearSelected()
                                } else {
                                    sweetState.toggleAll(items)
                                }
                            }
                        ) {
                            val icon = if (items.size == sweetState.selectedItems.size) {
                                R.drawable.close
                            } else R.drawable.add

                            Icon(
                                painter = painterResource(icon),
                                contentDescription = null
                            )
                        }
                    }
                ) { pv ->
                    LazyColumn(contentPadding = pv) {
                        items(
                            items = items,
                            key = { it.number }
                        ) { item ->

                            val isSelected by remember {
                                derivedStateOf { sweetState.isSelected(item) }
                            }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                                    .height(70.dp)
                                    .combinedClickable(
                                        onClick = {
                                            if (sweetState.isInSelectionMode) {
                                                sweetState.toggle(item)
                                            } else {
                                                // do something
                                            }
                                        },
                                        onLongClick = { sweetState.toggle(item) }
                                    )
                            ) {
                                Text(item.number.toString())
                                Spacer(Modifier.weight(1f))
                                if (isSelected) {
                                    Icon(
                                        painter = painterResource(R.drawable.round_check_24),
                                        contentDescription = null
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }

    }
}

data class Item(
    val number: Int = Random.nextInt()
)