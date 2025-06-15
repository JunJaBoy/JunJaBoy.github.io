package app.junsu.junjanote.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.junsu.junjanote.common.ui.corner.SmoothCornerShape

fun LazyListScope.postSheetHeaderItem(
    key: Any? = null,
    contentType: Any? = null,
    padding: PaddingValues = PaddingValues(all = 8.0.dp),
    content: @Composable () -> Unit,
) {
    this.item(
        key = key,
        contentType = contentType,
    ) {
        val cornerRadius = 32.0.dp
        Surface(
            modifier = Modifier.fillMaxWidth().background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = SmoothCornerShape(
                    topLeft = cornerRadius,
                    topRight = cornerRadius,
                ),
            ).padding(padding),
            color = Color.Transparent,
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth().clip(
                    shape = SmoothCornerShape(
                        all = cornerRadius - padding.calculateTopPadding(),
                    ),
                ),
                color = Color.Transparent,
                content = content,
            )
        }
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
                    bottomLeft = 32.0.dp,
                    bottomRight = 32.0.dp,
                    smoothnessAsPercent = 0,
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
