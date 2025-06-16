package app.junsu.junjanote.common.ui.layout

import androidx.compose.foundation.layout.widthIn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun Modifier.safeMaxWidth(): Modifier = this.then(
    Modifier.widthIn(
        max = 1200.dp,
    ),
)
