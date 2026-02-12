package com.sosauce.sweetselect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlin.math.max

/**
 * A [SweetSelectState] remembered across recompositions. Used to keep track of selected items and perform actions with them. It requires to be given a type parameter for [T]
 * @param maxSelectable Optional maximum amount of items that can be selected
 */
@Composable
fun <T> rememberSweetSelectState(
    initialSelectedItems: Collection<T> = emptySet(),
    maxSelectable: Long = Long.MAX_VALUE
): SweetSelectState<T> {
    return rememberSaveable(saver = SweetSelectState.Saver(maxSelectable)) { SweetSelectState(initialSelectedItems, maxSelectable) }
}

/**
 * The state that contains selected items and actions related to said items
 */
class SweetSelectState<T>(
    private val initialSelectedItems: Collection<T>,
    private val maxSelectable: Long
) {
    /**
     * Mutable set of all selected items
     */
    private val _selectedItems = mutableStateSetOf<T>().apply {
        addAll(initialSelectedItems)
    }

    /**
     * Immutable set of all selected items
     */
    val selectedItems: Set<T> = _selectedItems

    /**
     * Whether the state is in selection mode or not. Can be useful, for example, to display radio buttons on items
     */
    val isInSelectionMode: Boolean
        get() = selectedItems.isNotEmpty()

    /**
     * Whether the maximum allowed of selected items has been reached
     */
    val isSelectionFull: Boolean
        get() = selectedItems.size >= maxSelectable

    /**
     * Selects or deselects an item based on it's state.
     * @param item Item to select or deselect.
     * @return Whether the item had been toggled
     */
    fun toggle(item: T): Boolean {
        return when {
            _selectedItems.contains(item) -> _selectedItems.remove(item)
            !isSelectionFull -> _selectedItems.add(item)
            else -> false
        }
    }

    /**
     * Selects all items at once
     * @param items Items to select.
     * @return Whether all items have been added or not
     */
    fun toggleAll(items: Collection<T>): Boolean {

        return if (selectedItems.containsAll(items)) {
            _selectedItems.removeAll(items)
        } else {
            val remainingSlots = (maxSelectable - selectedItems.size).toInt()
            val toAdd = items.filter { it !in selectedItems }.take(remainingSlots)
            _selectedItems.addAll(toAdd)
        }
    }

    /**
     * Checks whether a given item is selected or not. Use [isSelectedAsState] in a Composable
     * @param item Item to check for
     */
    fun isSelected(item: T): Boolean = selectedItems.contains(item)

    /**
     * Checks whether a given item is selected or not in a Compose optimized manner
     * @param item Item to check for
     */
    @Composable
    fun isSelectedAsState(item: T): State<Boolean> {
        return remember { derivedStateOf { selectedItems.contains(item) } }
    }

    /**
     * Clears all selected items from the list. This action is irreversible.
     */
    fun clearSelected() = _selectedItems.clear()

    companion object {
        fun <T> Saver(maxSelectable: Long): Saver<SweetSelectState<T>, *> = Saver(
            save = { it.selectedItems.toList() }, // Transform Set to List for serializing
            restore = { SweetSelectState(initialSelectedItems = it, maxSelectable = maxSelectable) }
        )
    }

}
