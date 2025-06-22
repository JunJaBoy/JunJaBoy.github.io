package app.junsu.junjanote.common.ui.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.junsu.junjanote.common.ui.corner.SmoothCornerShape
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode

typealias GetRawTextOfRangeCallback = (startOffset: Int, endOffset: Int) -> String

fun LazyListScope.markdownPostSheetItems(
    nodes: List<ASTNode>,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    key: ((index: Int, item: ASTNode) -> Any)? = null,
) {
    this.item {
        Spacer(
            modifier = Modifier.postSheetItem().height(
                height = 32.0.dp,
            ),
        )
    }
    this.itemsIndexed(
        items = nodes,
        key = key,
    ) { _, node ->
        ASTNodeRenderer(
            node = node,
            getRawTextOfRange = getRawTextOfRange,
        )
    }
    this.item {
        Spacer(
            modifier = Modifier.postSheetItem().height(
                height = 32.0.dp,
            ),
        )
    }
}

@Composable
private fun ASTNodeRenderer(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    val children = node.children

    when (node.type) {
        MarkdownElementTypes.PARAGRAPH -> {
            MarkdownParagraph(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 16.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.ATX_1, MarkdownElementTypes.SETEXT_1 -> {
            val cornerRadius = 16.0.dp
            Column(
                modifier = Modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 8.0.dp,
                    ),
                ).fillMaxWidth().background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = SmoothCornerShape(
                        all = cornerRadius,
                    ),
                ).padding(all = 16.0.dp),
            ) {
                MarkdownHeader(
                    node = node,
                    getRawTextOfRange = getRawTextOfRange,
                    textStyle = MaterialTheme.typography.headlineLarge.copy(
                        fontFamily = FontFamily.Serif,
                    ),
                    modifier = modifier,
                )
            }
        }

        MarkdownElementTypes.ATX_2, MarkdownElementTypes.SETEXT_2 -> {
            MarkdownHeader(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                textStyle = MaterialTheme.typography.headlineMedium,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 16.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.ATX_3 -> {
            MarkdownHeader(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                textStyle = MaterialTheme.typography.headlineSmall,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.ATX_4 -> {
            MarkdownHeader(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                textStyle = MaterialTheme.typography.titleLarge,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.ATX_5 -> {
            MarkdownHeader(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.ATX_6 -> {
            MarkdownHeader(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.BLOCK_QUOTE -> {
            MarkdownBlockQuote(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.UNORDERED_LIST -> {
            MarkdownList(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                bullet = "• ",
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.ORDERED_LIST -> {
            MarkdownOrderedList(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownElementTypes.CODE_BLOCK, MarkdownElementTypes.CODE_FENCE -> {
            MarkdownCodeBlock(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownTokenTypes.HORIZONTAL_RULE -> {
            HorizontalRule(
                modifier = modifier.postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownTokenTypes.EOL -> {
            Spacer(
                modifier = Modifier.height(
                    height = MaterialTheme.typography.bodyLarge.fontSize.value.dp / 2,
                ).postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        MarkdownTokenTypes.HARD_LINE_BREAK -> {
            Spacer(
                modifier = Modifier.height(
                    height = MaterialTheme.typography.bodyLarge.fontSize.value.dp,
                ).postSheetItem(
                    padding = PaddingValues(
                        horizontal = 32.0.dp,
                    ),
                ),
            )
        }

        else -> {
            children.forEach { child ->
                ASTNodeRenderer(
                    node = child,
                    getRawTextOfRange = getRawTextOfRange,
                    modifier = modifier,
                )
            }
        }
    }
}

@Composable
fun MarkdownParagraph(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    val annotatedString = buildAnnotatedStringWithInlineStyles(
        node = node,
        getRawTextOfRange = getRawTextOfRange,
    )

    Text(
        text = annotatedString,
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
fun MarkdownHeader(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    val contentNode = node.children.firstOrNull {
        it.type == MarkdownTokenTypes.ATX_CONTENT || it.type == MarkdownTokenTypes.SETEXT_CONTENT
    }
    if (contentNode != null) {
        val annotatedString = buildAnnotatedStringWithInlineStyles(
            node = contentNode,
            getRawTextOfRange = getRawTextOfRange,
        )
        Text(
            text = annotatedString,
            modifier = modifier,
            style = textStyle,
        )
    }
}

@Composable
fun MarkdownBlockQuote(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
        ),
    ) {
        node.children.forEach { child ->
            ASTNodeRenderer(
                node = child,
                getRawTextOfRange = getRawTextOfRange,
            )
        }
    }
    Spacer(
        modifier = Modifier.height(
            height = 8.dp,
        ),
    )
}

@Composable
fun MarkdownList(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    bullet: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        node.children.forEach { listItemNode ->
            if (listItemNode.type == MarkdownElementTypes.LIST_ITEM) {
                Row {
                    Text(
                        text = bullet,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Column {
                        listItemNode.children.forEach { itemChild ->
                            ASTNodeRenderer(
                                node = itemChild,
                                getRawTextOfRange = getRawTextOfRange,
                            )
                        }
                    }
                }
            } else {
                ASTNodeRenderer(
                    node = listItemNode,
                    getRawTextOfRange = getRawTextOfRange,
                )
            }
        }
    }
    Spacer(
        modifier = Modifier.height(
            height = 8.dp,
        ),
    )
}

@Composable
fun MarkdownOrderedList(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    var itemNumber = 1
    Column(
        modifier = modifier,
    ) {
        node.children.forEach { listItemNode ->
            if (listItemNode.type == MarkdownElementTypes.LIST_ITEM) {
                Row {
                    Text(
                        text = "$itemNumber. ",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    Column {
                        listItemNode.children.forEach { itemChild ->
                            ASTNodeRenderer(
                                node = itemChild,
                                getRawTextOfRange = getRawTextOfRange,
                            )
                        }
                    }
                }
                itemNumber++
            } else {
                ASTNodeRenderer(
                    node = listItemNode,
                    getRawTextOfRange = getRawTextOfRange,
                )
            }
        }
    }
    Spacer(
        modifier = Modifier.height(
            height = 8.dp,
        ),
    )
}

@Composable
fun MarkdownCodeBlock(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    val codeContent = node.children.filter {
        it.type == MarkdownTokenTypes.CODE_LINE || it.type == MarkdownTokenTypes.CODE_FENCE_CONTENT
    }.joinToString(
        separator = "\n",
    ) {
        getRawTextOfRange(
            it.startOffset,
            it.endOffset,
        )
    }

    Text(
        text = codeContent,
        fontFamily = FontFamily.Monospace,
        fontSize = 14.sp,
        color = Color.DarkGray,
        modifier = modifier.padding(
            horizontal = 8.dp,
            vertical = 4.dp,
        ).background(
            color = Color.LightGray.copy(
                alpha = 0.3f,
            ),
        ).padding(
            all = 4.dp,
        ),
    )
    Spacer(
        modifier = Modifier.height(
            height = 8.dp,
        ),
    )
}

@Composable
fun MarkdownLink(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    val linkTextNode = node.children.firstOrNull {
        it.type == MarkdownElementTypes.LINK_TEXT
    }
    val linkDestinationNode = node.children.firstOrNull {
        it.type == MarkdownElementTypes.LINK_DESTINATION
    }

    val linkText = linkTextNode?.let {
        getRawTextOfRange(
            it.startOffset,
            it.endOffset,
        )
    } ?: "Link"
    val linkUrl = linkDestinationNode?.let {
        getRawTextOfRange(
            it.startOffset,
            it.endOffset,
        )
    } ?: ""

    ClickableText(
        text = AnnotatedString(
            text = linkText,
            spanStyle = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
            ),
        ),
        onClick = {
            println(
                "Clicked link: $linkUrl",
            )
        },
        modifier = modifier,
    )
}

@Composable
fun MarkdownImage(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    val linkTextNode = node.children.firstOrNull {
        it.type == MarkdownElementTypes.LINK_TEXT
    }
    val linkDestinationNode = node.children.firstOrNull {
        it.type == MarkdownElementTypes.LINK_DESTINATION
    }

    val altText = linkTextNode?.let {
        getRawTextOfRange(
            it.startOffset,
            it.endOffset,
        )
    } ?: "Image"
    val imageUrl = linkDestinationNode?.let {
        getRawTextOfRange(
            it.startOffset,
            it.endOffset,
        )
    } ?: ""

    Column(
        modifier = modifier,
    ) {
        Text(
            text = "Image: $altText",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontStyle = FontStyle.Italic,
            ),
        )
        Text(
            text = "URL: $imageUrl",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
        )
    }
    Spacer(
        modifier = Modifier.height(
            height = 8.dp,
        ),
    )
}

@Composable
fun HorizontalRule(
    modifier: Modifier = Modifier,
) {
    HorizontalDivider(
        modifier = modifier.padding(
            vertical = 8.dp,
        ),
    )
}

@Composable
private fun buildAnnotatedStringWithInlineStyles(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
): AnnotatedString {
    return AnnotatedString.Builder().apply {
        node.children.forEach { child ->
            when (child.type) {
                MarkdownTokenTypes.WHITE_SPACE -> {
                    append(
                        " ",
                    )
                }

                MarkdownElementTypes.EMPH -> {
                    pushStyle(
                        SpanStyle(
                            fontStyle = FontStyle.Italic,
                        ),
                    )
                    child.children.forEach { inner ->
                        if (inner.type == MarkdownTokenTypes.TEXT) {
                            append(
                                getRawTextOfRange(
                                    inner.startOffset,
                                    inner.endOffset,
                                ),
                            )
                        }
                    }
                    pop()
                }

                MarkdownElementTypes.STRONG -> {
                    pushStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                    child.children.forEach { inner ->
                        if (inner.type == MarkdownTokenTypes.TEXT) {
                            append(
                                getRawTextOfRange(
                                    inner.startOffset,
                                    inner.endOffset,
                                ),
                            )
                        }
                    }

                    pop()
                }

                MarkdownElementTypes.CODE_SPAN -> {
                    pushStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            background = Color.LightGray,
                        ),
                    )
                    child.children.forEach { inner ->
                        if (inner.type == MarkdownTokenTypes.TEXT) {
                            append(
                                getRawTextOfRange(
                                    inner.startOffset,
                                    inner.endOffset,
                                ),
                            )
                        }
                    }

                    pop()
                }

                MarkdownElementTypes.INLINE_LINK,
                MarkdownElementTypes.FULL_REFERENCE_LINK,
                MarkdownElementTypes.SHORT_REFERENCE_LINK,
                    -> {
                    val linkTextNode = child.children.firstOrNull {
                        it.type == MarkdownElementTypes.LINK_TEXT
                    }
                    val linkText = linkTextNode?.let {
                        getRawTextOfRange(
                            it.startOffset,
                            it.endOffset,
                        )
                    } ?: "Link"
                    val linkUrlNode = child.children.firstOrNull {
                        it.type == MarkdownElementTypes.LINK_DESTINATION
                    }
                    val linkUrl = linkUrlNode?.let {
                        getRawTextOfRange(
                            it.startOffset,
                            it.endOffset,
                        )
                    } ?: ""

                    pushStringAnnotation(
                        tag = "URL",
                        annotation = linkUrl,
                    )
                    pushStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration = TextDecoration.Underline,
                        ),
                    )
                    append(
                        text = linkText,
                    )
                    pop()
                    pop()
                }

                MarkdownElementTypes.IMAGE -> {
                    val altTextNode = child.children.firstOrNull {
                        it.type == MarkdownElementTypes.LINK_TEXT
                    }
                    val altText = altTextNode?.let {
                        getRawTextOfRange(
                            it.startOffset,
                            it.endOffset,
                        )
                    } ?: "[Image]"
                    append(
                        text = altText,
                    )
                }

                MarkdownTokenTypes.EOL -> {
                    append(
                        text = "\n",
                    )
                }

                MarkdownTokenTypes.HARD_LINE_BREAK -> {
                    append(
                        text = "\n",
                    )
                }

                else -> {
                    append(
                        getRawTextOfRange(
                            child.startOffset,
                            child.endOffset,
                        ),
                    )
                }
            }
        }
    }.toAnnotatedString()
}
