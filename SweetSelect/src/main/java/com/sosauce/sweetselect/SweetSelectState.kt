package com.sosauce.sweetselect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State
import kotlin.math.max

/**
 * A [SweetSelectState] remembered across recompositions. Used to keep track of selected items and perform actions with them. It requires to be given a type parameter for [T]
 * @param maxSelectable Optional maximum amount of items that can be selected
 */
@Composable
fun <T> rememberSweetSelectState(maxSelectable: Int = Int.MAX_VALUE): SweetSelectState<T> {
    return remember { SweetSelectState(maxSelectable) }
}

/**
 * The state that contains selected items and actions related to said items.
 */
class SweetSelectState<T>(
    private val maxSelectable: Int
) {
    /**
     * Mutable set of all selected items.
     */
    private val _selectedItems = mutableStateSetOf<T>()

    /**
     * Immutable set of all selected items.
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
     */
    fun toggle(item: T) {
        if (_selectedItems.contains(item)) {
            _selectedItems.remove(item)
        } else {
            if (!isSelectionFull) {
                _selectedItems.add(item)
            }
        }
    }

    /**
     * Selects all items at once.
     * @param items Items to select.
     */
    fun toggleAll(items: Collection<T>) {
        if (isSelectionFull) return
        _selectedItems.addAll(items)
    }

    /**
     * Checks whether a given item is selected or not. Use [isSelectedAsState] in a Composable.
     * @param item Item to check for.
     */
    fun isSelected(item: T): Boolean = selectedItems.contains(item)

    /**
     * Checks whether a given item is selected or not in a Compose optimized manner
     * @param item Item to check for.
     */
    @Composable
    fun isSelectedAsState(item: T): State<Boolean> {
        return remember { derivedStateOf { selectedItems.contains(item) } }
    }

    /**
     * Clears all selected items from the list. This action is irreversible.
     */
    fun clearSelected() = _selectedItems.clear()

}
