package app.junsu.junjanote.common.ui.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode

typealias GetRawTextOfRangeCallback = (startOffset: Int, endOffset: Int) -> String

fun LazyListScope.markdownPostSheetItems(
    nodes: List<ASTNode>,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    key: ((index: Int, item: ASTNode) -> Any)? = null,
) {
    this.itemsIndexed(
        items = nodes,
        key = key,
    ) { _, node ->
        Surface(
            modifier = Modifier.postSheetItem(),
            color = Color.Transparent,
        ) {
            ASTNodeRenderer(
                node = node,
                getRawTextOfRange = getRawTextOfRange,
            )
        }
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
        MarkdownElementTypes.PARAGRAPH,
            -> {
            MarkdownParagraph(
                node,
                getRawTextOfRange,
            )
        }

        MarkdownElementTypes.ATX_1 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.headlineLarge,

                )
        }

        MarkdownElementTypes.ATX_2 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.headlineMedium,

                )
        }

        MarkdownElementTypes.ATX_3 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.headlineSmall,

                )
        }

        MarkdownElementTypes.ATX_4 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.titleLarge,

                )
        }

        MarkdownElementTypes.ATX_5 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.titleMedium,

                )
        }

        MarkdownElementTypes.ATX_6 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.titleSmall,

                )
        }

        MarkdownElementTypes.SETEXT_1 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.headlineLarge,

                )
        }

        MarkdownElementTypes.SETEXT_2 -> {
            MarkdownHeader(
                node,
                getRawTextOfRange,
                MaterialTheme.typography.headlineMedium,

                )
        }

        MarkdownElementTypes.BLOCK_QUOTE -> {
            MarkdownBlockQuote(
                node,
                getRawTextOfRange,

                )
        }

        MarkdownElementTypes.UNORDERED_LIST -> {
            MarkdownList(
                node,
                getRawTextOfRange,
                "• ",

                )
        }

        MarkdownElementTypes.ORDERED_LIST -> {
            MarkdownOrderedList(
                node,
                getRawTextOfRange,

                )
        }

        MarkdownElementTypes.CODE_BLOCK, MarkdownElementTypes.CODE_FENCE -> {
            MarkdownCodeBlock(
                node,
                getRawTextOfRange,

                )
        }

        MarkdownTokenTypes.HORIZONTAL_RULE -> {
            HorizontalRule(

            )
        }

        MarkdownElementTypes.EMPH -> {
            MarkdownText(
                node,
                getRawTextOfRange,
                SpanStyle(fontStyle = FontStyle.Italic),
            )
        }

        MarkdownElementTypes.STRONG -> {
            MarkdownText(
                node,
                getRawTextOfRange,
                SpanStyle(fontWeight = FontWeight.Bold),
            )
        }

        MarkdownElementTypes.CODE_SPAN -> {
            MarkdownText(
                node,
                getRawTextOfRange,
                SpanStyle(fontFamily = FontFamily.Monospace, background = Color.LightGray),
            )
        }

        MarkdownElementTypes.INLINE_LINK, MarkdownElementTypes.FULL_REFERENCE_LINK, MarkdownElementTypes.SHORT_REFERENCE_LINK -> {
            MarkdownLink(
                node,
                getRawTextOfRange,
            )
        }

        MarkdownElementTypes.IMAGE -> {
            MarkdownImage(
                node,
                getRawTextOfRange,
            )
        }

        MarkdownTokenTypes.TEXT -> {
            println("TEXT ENTERED ${getRawTextOfRange(node.startOffset, node.endOffset)}")
            Text(
                getRawTextOfRange(node.startOffset, node.endOffset),
            )
        }

        MarkdownTokenTypes.EOL -> {
            Spacer(Modifier.height(MaterialTheme.typography.bodyLarge.fontSize.value.dp / 2))
        }

        MarkdownTokenTypes.HARD_LINE_BREAK -> {
            Spacer(Modifier.height(MaterialTheme.typography.bodyLarge.fontSize.value.dp))
        }

        else -> {
            children.forEach { child ->
                ASTNodeRenderer(
                    child,
                    getRawTextOfRange,
                    modifier,
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
    val textBuilder = StringBuilder()

    node.children.forEach { child ->
        if (child.type == MarkdownTokenTypes.TEXT || child.type == MarkdownTokenTypes.LINK_TITLE) {
            textBuilder.append(getRawTextOfRange(child.startOffset, child.endOffset))
        } else {
            ASTNodeRenderer(child, getRawTextOfRange)
        }
    }

    if (textBuilder.isNotBlank())
        Text(
            textBuilder.toString(),
        )
}

@Composable
fun MarkdownHeader(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    val contentNode =
        node.children.firstOrNull { it.type == MarkdownTokenTypes.ATX_CONTENT || it.type == MarkdownTokenTypes.SETEXT_CONTENT }
    if (contentNode != null) {
        Text(
            getRawTextOfRange(contentNode.startOffset, contentNode.endOffset).trim(),
            style = textStyle,
            modifier = modifier.padding(vertical = 4.dp),
        )
    }
    Spacer(Modifier.height(4.dp))
}

@Composable
fun MarkdownBlockQuote(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
            .padding(start = 16.dp),
    ) {
        node.children.forEach { child ->
            ASTNodeRenderer(child, getRawTextOfRange)
        }
    }
    Spacer(Modifier.height(8.dp))
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
                    Text(bullet, style = MaterialTheme.typography.bodyLarge)
                    Column {
                        listItemNode.children.forEach { itemChild ->
                            ASTNodeRenderer(itemChild, getRawTextOfRange)
                        }
                    }
                }
            } else {
                ASTNodeRenderer(listItemNode, getRawTextOfRange)
            }
        }
    }
    Spacer(Modifier.height(8.dp))
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
                    Text("$itemNumber. ", style = MaterialTheme.typography.bodyLarge)
                    Column {
                        listItemNode.children.forEach { itemChild ->
                            ASTNodeRenderer(itemChild, getRawTextOfRange)
                        }
                    }
                }
                itemNumber++
            } else {
                ASTNodeRenderer(listItemNode, getRawTextOfRange)
            }
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
fun MarkdownCodeBlock(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    val codeContent = node.children.filter { it.type == MarkdownTokenTypes.CODE_LINE || it.type == MarkdownTokenTypes.CODE_FENCE_CONTENT }
        .joinToString("\n") { getRawTextOfRange(it.startOffset, it.endOffset) }

    Text(
        codeContent,
        fontFamily = FontFamily.Monospace,
        fontSize = 14.sp,
        color = Color.DarkGray,
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .padding(4.dp),
    )
    Spacer(Modifier.height(8.dp))
}

@Composable
fun MarkdownText(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    spanStyle: SpanStyle,
    modifier: Modifier = Modifier,
) {
    val annotatedString = AnnotatedString.Builder().apply {
        node.children.forEach { child ->
            when (child.type) {
                MarkdownTokenTypes.TEXT -> append(getRawTextOfRange(child.startOffset, child.endOffset))
                MarkdownElementTypes.EMPH -> {
                    pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
                    append(getRawTextOfRange(child.startOffset, child.endOffset))
                    pop()
                }

                MarkdownElementTypes.STRONG -> {
                    pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                    append(getRawTextOfRange(child.startOffset, child.endOffset))
                    pop()
                }

                MarkdownElementTypes.CODE_SPAN -> {
                    pushStyle(SpanStyle(fontFamily = FontFamily.Monospace, background = Color.LightGray))
                    append(getRawTextOfRange(child.startOffset, child.endOffset))
                    pop()
                }

                else -> append(getRawTextOfRange(child.startOffset, child.endOffset))
            }
        }
    }.toAnnotatedString()

    Text(
        AnnotatedString.Builder().apply {
            pushStyle(spanStyle)
            append(annotatedString)
            pop()
        }.toAnnotatedString(),
        modifier = modifier,
    )
}

@Composable
fun MarkdownLink(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    val linkTextNode = node.children.firstOrNull { it.type == MarkdownElementTypes.LINK_TEXT }
    val linkDestinationNode = node.children.firstOrNull { it.type == MarkdownElementTypes.LINK_DESTINATION }

    val linkText = linkTextNode?.let { getRawTextOfRange(it.startOffset, it.endOffset) } ?: "Link"
    val linkUrl = linkDestinationNode?.let { getRawTextOfRange(it.startOffset, it.endOffset) } ?: ""

    ClickableText(
        AnnotatedString(
            linkText,
            SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline),
        ),
        onClick = { offset ->
            println("Clicked link: $linkUrl")
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
    val linkTextNode = node.children.firstOrNull { it.type == MarkdownElementTypes.LINK_TEXT }
    val linkDestinationNode = node.children.firstOrNull { it.type == MarkdownElementTypes.LINK_DESTINATION }

    val altText = linkTextNode?.let { getRawTextOfRange(it.startOffset, it.endOffset) } ?: "Image"
    val imageUrl = linkDestinationNode?.let { getRawTextOfRange(it.startOffset, it.endOffset) } ?: ""

    Column(
        modifier = modifier,
    ) {
        Text("Image: $altText", style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic))
        Text("URL: $imageUrl", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
fun HorizontalRule(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier
            .height(1.dp)
            .background(Color.LightGray)
            .padding(vertical = 8.dp),
    )
}
