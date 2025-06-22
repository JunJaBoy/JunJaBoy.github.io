package app.junsu.junjanote.common.ui.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
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
        ASTNodeRenderer(
            node = node,
            getRawTextOfRange = getRawTextOfRange,
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
        // --- Block Elements ---
        MarkdownElementTypes.PARAGRAPH -> {
            MarkdownParagraph(node = node, getRawTextOfRange = getRawTextOfRange, modifier = modifier)
        }

        MarkdownElementTypes.ATX_1 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.headlineLarge, modifier)
        }

        MarkdownElementTypes.ATX_2 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.headlineMedium, modifier)
        }

        MarkdownElementTypes.ATX_3 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.headlineSmall, modifier)
        }

        MarkdownElementTypes.ATX_4 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.titleLarge, modifier)
        }

        MarkdownElementTypes.ATX_5 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.titleMedium, modifier)
        }

        MarkdownElementTypes.ATX_6 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.titleSmall, modifier)
        }

        MarkdownElementTypes.SETEXT_1 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.headlineLarge, modifier)
        }

        MarkdownElementTypes.SETEXT_2 -> {
            MarkdownHeader(node, getRawTextOfRange, MaterialTheme.typography.headlineMedium, modifier)
        }

        MarkdownElementTypes.BLOCK_QUOTE -> {
            MarkdownBlockQuote(node, getRawTextOfRange, modifier)
        }

        MarkdownElementTypes.UNORDERED_LIST -> {
            MarkdownList(node, getRawTextOfRange, bullet = "• ", modifier)
        }

        MarkdownElementTypes.ORDERED_LIST -> {
            MarkdownOrderedList(node, getRawTextOfRange, modifier)
        }

        MarkdownElementTypes.CODE_BLOCK, MarkdownElementTypes.CODE_FENCE -> {
            MarkdownCodeBlock(node, getRawTextOfRange, modifier)
        }

        MarkdownTokenTypes.HORIZONTAL_RULE -> {
            HorizontalRule(modifier)
        }

        // --- Inline Elements ---
        MarkdownElementTypes.EMPH -> {
            // Render children with emphasis style
            MarkdownText(node, getRawTextOfRange, SpanStyle(fontStyle = FontStyle.Italic))
        }

        MarkdownElementTypes.STRONG -> {
            // Render children with strong style
            MarkdownText(node, getRawTextOfRange, SpanStyle(fontWeight = FontWeight.Bold))
        }

        MarkdownElementTypes.CODE_SPAN -> {
            MarkdownText(node, getRawTextOfRange, SpanStyle(fontFamily = FontFamily.Monospace, background = Color.LightGray))
        }

        MarkdownElementTypes.INLINE_LINK, MarkdownElementTypes.FULL_REFERENCE_LINK, MarkdownElementTypes.SHORT_REFERENCE_LINK -> {
            MarkdownLink(node, getRawTextOfRange)
        }

        MarkdownElementTypes.IMAGE -> {
            MarkdownImage(node, getRawTextOfRange)
        }

        MarkdownTokenTypes.TEXT -> {
            Text(text = getRawTextOfRange(node.startOffset, node.endOffset), modifier = modifier)
        }

        MarkdownTokenTypes.EOL -> {
            // Newline, often handled by block elements or just ignored for inline
            Spacer(Modifier.height(MaterialTheme.typography.bodyLarge.fontSize.value.dp / 2))
        }
        // Handle other token types if needed (e.g., MarkdownTokenTypes.HARD_LINE_BREAK)
        MarkdownTokenTypes.HARD_LINE_BREAK -> {
            Spacer(Modifier.height(MaterialTheme.typography.bodyLarge.fontSize.value.dp))
        }

        // Fallback for unhandled types or tokens that are part of other elements
        else -> {
            children.forEach { child ->
                ASTNodeRenderer(node = child, getRawTextOfRange = getRawTextOfRange, modifier = modifier)
            }
        }
    }
}

// --- Composable Helpers for specific Markdown Elements ---

@Composable
fun MarkdownParagraph(node: ASTNode, getRawTextOfRange: GetRawTextOfRangeCallback, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth()) {
        node.children.forEach { child ->
            ASTNodeRenderer(node = child, getRawTextOfRange = getRawTextOfRange)
        }
    }
    Spacer(modifier = Modifier.height(8.dp)) // Add some spacing after paragraphs
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
            text = getRawTextOfRange(contentNode.startOffset, contentNode.endOffset).trim(),
            style = textStyle,
            modifier = modifier.padding(vertical = 4.dp),
        )
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun MarkdownBlockQuote(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp)
            .fillMaxWidth(),
    ) {
        node.children.forEach { child ->
            ASTNodeRenderer(node = child, getRawTextOfRange = getRawTextOfRange)
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun MarkdownList(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    bullet: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        node.children.forEach { listItemNode ->
            if (listItemNode.type == MarkdownElementTypes.LIST_ITEM) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = bullet, style = MaterialTheme.typography.bodyLarge)
                    Column(modifier = Modifier.weight(1f)) {
                        listItemNode.children.forEach { itemChild ->
                            ASTNodeRenderer(node = itemChild, getRawTextOfRange = getRawTextOfRange)
                        }
                    }
                }
            } else {
                ASTNodeRenderer(node = listItemNode, getRawTextOfRange = getRawTextOfRange)
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun MarkdownOrderedList(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    modifier: Modifier = Modifier,
) {
    var itemNumber = 1
    Column(modifier = modifier.fillMaxWidth()) {
        node.children.forEach { listItemNode ->
            if (listItemNode.type == MarkdownElementTypes.LIST_ITEM) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "$itemNumber. ", style = MaterialTheme.typography.bodyLarge)
                    Column(modifier = Modifier.weight(1f)) {
                        listItemNode.children.forEach { itemChild ->
                            ASTNodeRenderer(node = itemChild, getRawTextOfRange = getRawTextOfRange)
                        }
                    }
                }
                itemNumber++
            } else {
                ASTNodeRenderer(node = listItemNode, getRawTextOfRange = getRawTextOfRange)
            }
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
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
        text = codeContent,
        fontFamily = FontFamily.Monospace,
        fontSize = 14.sp,
        color = Color.DarkGray,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(Color.LightGray.copy(alpha = 0.3f))
            .padding(4.dp),
    )
    Spacer(modifier = Modifier.height(8.dp))
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
                // Recursively apply styles for nested elements if needed
                else -> append(getRawTextOfRange(child.startOffset, child.endOffset)) // Fallback for other inline elements
            }
        }
    }.toAnnotatedString()

    Text(
        text = AnnotatedString.Builder().apply {
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

    // In a real application, you'd use a more robust way to handle clicks,
    // e.g., launching a browser intent.
    ClickableText(
        text = AnnotatedString(
            linkText,
            spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline),
        ),
        onClick = { offset ->
            // Handle link click, e.g., open URL
            println("Clicked link: $linkUrl")

            // You can use Android's UriHandler or a custom callback here
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

    // In a real application, you would use an image loading library like Coil or Glide
    // For now, we'll just display the alt text and URL
    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = "Image: $altText", style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic))
        Text(text = "URL: $imageUrl", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun HorizontalRule(
    modifier: Modifier = Modifier,
) {
    Spacer(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray)
            .padding(vertical = 8.dp),
    )
}
