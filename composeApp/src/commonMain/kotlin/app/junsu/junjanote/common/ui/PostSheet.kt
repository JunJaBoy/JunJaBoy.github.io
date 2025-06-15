package app.junsu.junjanote.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import app.junsu.junjanote.common.ui.corner.SmoothCornerShape

fun LazyListScope.postSheetHeaderItem(
    key: Any? = null,
    contentType: Any? = null,
) {
    this.item(
        key = key,
        contentType = contentType,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().height(32.0.dp).clip(
                shape = SmoothCornerShape(
                    topLeft = 32.0.dp, topRight = 32.0.dp,
                ),
            ),
            content = {},
            color = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

fun LazyListScope.postSheetFooterItem(
    key: Any? = null,
    contentType: Any? = null,
) {
    this.item(
        key = key,
        contentType = contentType,
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().height(32.0.dp).clip(
                shape = SmoothCornerShape(
                    bottomLeft = 32.0.dp, bottomRight = 32.0.dp,
                ),
            ),
            content = {},
            color = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}


@Composable
fun Modifier.postSheetItem(): Modifier = this.background(
    color = MaterialTheme.colorScheme.surfaceVariant,
).fillMaxWidth().padding(
    horizontal = 32.0.dp,
)
