package app.junsu.junjanote.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.junsu.junjanote.common.ui.corner.SmoothCornerShape

fun LazyListScope.postSheetHeaderItem(
    key: Any? = null,
    contentType: Any? = null,
    padding: PaddingValues = PaddingValues(all = 8.0.dp),
    thumbnail: @Composable () -> Unit,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    description: @Composable () -> Unit,
) {
    this.item(
        key = key,
        contentType = contentType,
    ) {
        val cornerRadius = 32.0.dp
        Column(
            modifier = Modifier.fillMaxWidth().background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = SmoothCornerShape(
                    topLeft = cornerRadius,
                    topRight = cornerRadius,
                ),
            ).padding(padding),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
            ),
        ) {
            val edgeRadius = cornerRadius - padding.calculateTopPadding()
            val contentRadius = edgeRadius / 2
            Surface(
                modifier = Modifier.fillMaxWidth().clip(
                    shape = SmoothCornerShape(
                        topRight = edgeRadius,
                        topLeft = edgeRadius,
                        bottomRight = contentRadius,
                        bottomLeft = contentRadius,
                    ),
                ),
                color = Color.Transparent,
                content = thumbnail,
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                ),
            ) {
                val titleRadius = contentRadius / 2
                Surface(
                    modifier = Modifier.fillMaxWidth().clip(
                        shape = SmoothCornerShape(
                            topRight = contentRadius,
                            topLeft = contentRadius,
                            bottomRight = titleRadius,
                            bottomLeft = titleRadius,
                        ),
                    ).background(
                        color = MaterialTheme.colorScheme.surface,
                    ).padding(
                        all = 16.dp,
                    ),
                    color = Color.Transparent,
                    content = {
                        CompositionLocalProvider(
                            value = LocalTextStyle provides MaterialTheme.typography.displayLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                            content = title,
                        )
                    },
                )
                if (subtitle != null) Surface(
                    modifier = Modifier.fillMaxWidth().clip(
                        shape = SmoothCornerShape(all = titleRadius),
                    ).background(
                        color = MaterialTheme.colorScheme.surface,
                    ).padding(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
                    color = Color.Transparent,
                    content = {
                        CompositionLocalProvider(
                            value = LocalTextStyle provides MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface,
                            ),
                            content = subtitle,
                        )
                    },
                )
                Surface(
                    modifier = Modifier.fillMaxWidth().clip(
                        shape = SmoothCornerShape(
                            topRight = titleRadius,
                            topLeft = titleRadius,
                            bottomRight = contentRadius,
                            bottomLeft = contentRadius,
                        ),
                    ).background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                    ).padding(
                        horizontal = 16.dp,
                        vertical = 4.dp,
                    ),
                    color = Color.Transparent,
                    content = {
                        CompositionLocalProvider(
                            value = LocalTextStyle provides MaterialTheme.typography.labelLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                            content = description,
                        )
                    },
                )
            }
        }
    }
}

fun LazyListScope.postSheetFooterItem(
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
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = SmoothCornerShape(
                    bottomRight = cornerRadius,
                    bottomLeft = cornerRadius,
                ),
            ).padding(padding),
            color = Color.Transparent,
        ) {
            val edgeRadius = cornerRadius - padding.calculateTopPadding()
            Surface(
                modifier = Modifier.fillMaxWidth().clip(
                    shape = SmoothCornerShape(all = edgeRadius),
                ).background(
                    color = MaterialTheme.colorScheme.surface,
                ),
                color = Color.Transparent,
                content = {
                    CompositionLocalProvider(
                        value = LocalTextStyle provides MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                        content = content,
                    )
                },
            )
        }
    }
}


@Composable
fun Modifier.postSheetItem(): Modifier = this.background(
    color = MaterialTheme.colorScheme.surfaceContainer,
).fillMaxWidth().padding(
    horizontal = 32.0.dp,
)
