package com.sosauce.sweetselect

import androidx.compose.foundation.Indication
import androidx.compose.ui.Modifier
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.semantics.Role

/**
 * @param onClick Action to perform when not in selection mode
 */
fun <T> Modifier.sweetClickable(
    item: T,
    state: SweetSelectState<T>,
    onClick: () -> Unit,
    enabled: Boolean = true,
    role: Role? = null,
    onLongClickLabel: String? = null,
    onLongClick: (() -> Unit)? = null,
    hapticFeedbackEnabled: Boolean = true,
): Modifier = this.then(
    Modifier.combinedClickable(
        onClick = {
            if (state.isInSelectionMode) {
                state.toggle(item)
            } else {
                onClick()
            }
        },
        onLongClick = {
            if (!state.isInSelectionMode) {
                state.toggle(item)
                onLongClick?.invoke()
            }
        },
        enabled = enabled,
        role = role,
        onLongClickLabel = onLongClickLabel,
        hapticFeedbackEnabled = hapticFeedbackEnabled,
    )
)