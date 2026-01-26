package com.sosauce.sweetselect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember

/**
 * A [SweetSelectState] remembered across recompositions. Used to keep track of selected items and perform actions with them. It requires to be given a type parameter for [T]
 */
@Composable
fun <T> rememberSweetSelectState(): SweetSelectState<T> {
    return remember { SweetSelectState() }
}

/**
 * The state that contains selected items and actions related to said items.
 */
class SweetSelectState<T> {
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
     * Selects or deselects an item based on it's state.
     * @param item Item to select or deselect.
     */
    fun toggle(item: T) {
        if (!_selectedItems.add(item)) {
            _selectedItems.remove(item)
        }
    }

    /**
     * Selects all items at once.
     * @param items Items to select.
     */
    fun toggleAll(items: Collection<T>) = _selectedItems.addAll(items)

    /**
     * Checks whether a given item is selected or not. Should be used with [androidx.compose.runtime.derivedStateOf] for optimal Compose performances
     * @param item Item to check for.
     */
    fun isSelected(item: T): Boolean = selectedItems.contains(item)

    /**
     * Clears all selected items from the list. This action is irreversible.
     */
    fun clearSelected() = _selectedItems.clear()

}
