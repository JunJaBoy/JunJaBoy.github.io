package app.junsu.junjanote.common.ui.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.findChildOfType

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
    val childNodes = node.children

    Surface(
        modifier = modifier.postSheetItem(),
        color = Color.Transparent,
    ) {
        when (node.type) {
            MarkdownElementTypes.IMAGE -> {
                val imageUrlNode =
                    node.findChildOfType(MarkdownElementTypes.INLINE_LINK)?.findChildOfType(MarkdownElementTypes.LINK_DESTINATION)
                        ?: node.findChildOfType(MarkdownTokenTypes.TEXT)

                val altTextNode =
                    node.findChildOfType(MarkdownElementTypes.LINK_TEXT) ?: node.findChildOfType(MarkdownElementTypes.LINK_LABEL)

                val imageUrl = imageUrlNode?.let {
                    val rawUrl = getRawTextOfRange(it.startOffset, it.endOffset)
                    rawUrl.removePrefix("<").removeSuffix(">").trim()
                } ?: ""

                val altText = altTextNode?.let {
                    val rawAlt = getRawTextOfRange(it.startOffset, it.endOffset)
                    rawAlt.removePrefix("[").removeSuffix("]").trim()
                } ?: ""

                if (imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = altText,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        onError = {
                            it.result.throwable.printStackTrace()
                        },
                    )
                } else {
                    Text(
                        text = "Invalid Image: Alt='$altText'",
                        color = MaterialTheme.colorScheme.error,
                    )
                }
            }

            MarkdownTokenTypes.EOL -> {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {}
            }

            MarkdownElementTypes.ATX_1,
            MarkdownTokenTypes.SETEXT_1,
                -> CompositionLocalProvider(
                value = LocalTextStyle provides MaterialTheme.typography.displayMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                ),
            ) {
                val headerAnnotatedText = buildAnnotatedString {
                    childNodes.forEach { child ->
                        appendStyledText(child, getRawTextOfRange)
                    }
                }
                Column(
                    modifier = Modifier.padding(
                        top = 32.0.dp,
                        bottom = 16.0.dp,
                    ),
                ) {
                    Text(
                        text = headerAnnotatedText,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            MarkdownElementTypes.ATX_2,
            MarkdownTokenTypes.SETEXT_2,
                -> CompositionLocalProvider(
                value = LocalTextStyle provides MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
            ) {
                val headerAnnotatedText = buildAnnotatedString {
                    childNodes.forEach { child ->
                        appendStyledText(child, getRawTextOfRange)
                    }
                }
                Column(
                    modifier = Modifier.padding(
                        top = 32.0.dp,
                        bottom = 16.0.dp,
                    ),
                ) {
                    Text(
                        text = headerAnnotatedText,
                    )
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            MarkdownElementTypes.ATX_3,
                -> CompositionLocalProvider(
                value = LocalTextStyle provides MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
            ) {
                val headerAnnotatedText = buildAnnotatedString {
                    node.children.forEach { child ->
                        appendStyledText(child, getRawTextOfRange)
                    }
                }
                Text(
                    text = headerAnnotatedText,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 8.dp,
                    ),
                )
            }

            MarkdownElementTypes.ATX_4,
                -> CompositionLocalProvider(
                value = LocalTextStyle provides MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
            ) {
                val headerAnnotatedText = buildAnnotatedString {
                    node.children.forEach { child ->
                        appendStyledText(child, getRawTextOfRange)
                    }
                }
                Text(
                    text = headerAnnotatedText,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 8.dp,
                    ),
                )
            }

            MarkdownElementTypes.ATX_5,
                -> CompositionLocalProvider(
                value = LocalTextStyle provides MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
            ) {
                val headerAnnotatedText = buildAnnotatedString {
                    node.children.forEach { child ->
                        appendStyledText(child, getRawTextOfRange)
                    }
                }
                Text(
                    text = headerAnnotatedText,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 8.dp,
                    ),
                )
            }

            MarkdownElementTypes.ATX_6,
                -> CompositionLocalProvider(
                value = LocalTextStyle provides MaterialTheme.typography.titleSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.38f,
                    ),
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                ),
            ) {
                val headerAnnotatedText = buildAnnotatedString {
                    childNodes.forEach { child ->
                        appendStyledText(child, getRawTextOfRange)
                    }
                }
                Text(
                    text = headerAnnotatedText,
                    modifier = Modifier.padding(
                        top = 16.dp,
                        bottom = 8.dp,
                    ),
                )
            }

            MarkdownElementTypes.PARAGRAPH -> {
                CompositionLocalProvider(
                    value = LocalTextStyle provides MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                ) {
                    val annotatedString = buildAnnotatedString {
                        appendStyledText(node, getRawTextOfRange)
                    }
                    Text(
                        text = annotatedString,
                        modifier = Modifier.padding(vertical = 4.dp),
                    )
                }
            }

            MarkdownTokenTypes.TEXT,
            MarkdownTokenTypes.COLON,
            MarkdownElementTypes.EMPH,
            MarkdownElementTypes.STRONG,
            MarkdownElementTypes.CODE_SPAN,
            MarkdownElementTypes.INLINE_LINK,
            MarkdownElementTypes.FULL_REFERENCE_LINK,
            MarkdownElementTypes.SHORT_REFERENCE_LINK,
            MarkdownTokenTypes.AUTOLINK,
            MarkdownTokenTypes.EMAIL_AUTOLINK,
            MarkdownTokenTypes.HARD_LINE_BREAK,
            MarkdownTokenTypes.WHITE_SPACE,
            MarkdownElementTypes.LINK_LABEL,
            MarkdownElementTypes.LINK_DESTINATION,
            MarkdownElementTypes.LINK_TEXT,
            MarkdownElementTypes.LINK_TITLE,
            MarkdownTokenTypes.LBRACKET,
            MarkdownTokenTypes.RBRACKET,
            MarkdownTokenTypes.LPAREN,
            MarkdownTokenTypes.RPAREN,
            MarkdownTokenTypes.LT,
            MarkdownTokenTypes.GT,
            MarkdownTokenTypes.EXCLAMATION_MARK,
            MarkdownTokenTypes.SINGLE_QUOTE,
            MarkdownTokenTypes.DOUBLE_QUOTE,
            MarkdownTokenTypes.BACKTICK,
            MarkdownTokenTypes.SETEXT_CONTENT,
                -> {
                if (childNodes.isNotEmpty()) {
                    for (child in childNodes) {
                        ASTNodeRenderer(
                            node = child,
                            getRawTextOfRange = getRawTextOfRange,
                        )
                    }
                }
            }

            MarkdownElementTypes.CODE_BLOCK, MarkdownElementTypes.CODE_FENCE -> {
                Column(
                    modifier = Modifier
                        .padding(vertical = 8.dp).fillMaxWidth().background(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.08f,
                            ),
                            shape = MaterialTheme.shapes.small,
                        )
                        .then(
                            Modifier.padding(8.dp),
                        ),
                ) {
                    CompositionLocalProvider(
                        value = LocalTextStyle provides MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    ) {
                        val codeContent = StringBuilder()
                        childNodes.forEach { child ->
                            if (child.type == MarkdownTokenTypes.CODE_LINE || child.type == MarkdownTokenTypes.CODE_FENCE_CONTENT) {
                                val line = getRawTextOfRange(child.startOffset, child.endOffset)
                                codeContent.append(line)
                                if (line.isNotEmpty() && !line.endsWith("\n")) {
                                    codeContent.append("\n")
                                }
                            }
                        }
                        Text(text = codeContent.toString())
                    }
                }
            }

            MarkdownElementTypes.BLOCK_QUOTE -> {
                Column(
                    modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp).then(
                        Modifier.padding(start = 8.dp),
                    ),
                ) {
                    CompositionLocalProvider(
                        value = LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                            fontStyle = FontStyle.Italic,
                        ),
                    ) {
                        childNodes.forEach { child ->
                            if (child.type != MarkdownTokenTypes.BLOCK_QUOTE && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                                ASTNodeRenderer(node = child, getRawTextOfRange = getRawTextOfRange)
                            }
                        }
                    }
                }
            }

            MarkdownElementTypes.UNORDERED_LIST, MarkdownElementTypes.ORDERED_LIST -> {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    childNodes.forEach { child ->
                        if (child.type == MarkdownElementTypes.LIST_ITEM) {
                            ASTNodeRenderer(node = child, getRawTextOfRange = getRawTextOfRange)
                        }
                    }
                }
            }

            MarkdownElementTypes.LIST_ITEM -> {
                val isOrdered = node.parent?.type == MarkdownElementTypes.ORDERED_LIST
                val listItemMarker =
                    node.findChildOfType(MarkdownTokenTypes.LIST_NUMBER) ?: node.findChildOfType(MarkdownTokenTypes.LIST_BULLET)

                val bulletOrNumber =
                    listItemMarker?.let { getRawTextOfRange(it.startOffset, it.endOffset).trim() } ?: if (isOrdered) "1." else "•"

                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(
                        text = "$bulletOrNumber ",
                        modifier = Modifier.padding(end = 4.dp),
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        childNodes.forEach { child ->
                            if (child.type != MarkdownTokenTypes.LIST_BULLET && child.type != MarkdownTokenTypes.LIST_NUMBER && child.type != MarkdownTokenTypes.WHITE_SPACE && child.type != MarkdownTokenTypes.EOL) {
                                ASTNodeRenderer(node = child, getRawTextOfRange = getRawTextOfRange)
                            }
                        }
                    }
                }
            }

            MarkdownTokenTypes.HORIZONTAL_RULE -> HorizontalDivider(
                modifier = Modifier.padding(
                    vertical = 32.0.dp,
                ),
            )

            MarkdownElementTypes.HTML_BLOCK,
            MarkdownTokenTypes.HTML_TAG,
                -> {
                val rawHtml = getRawTextOfRange(node.startOffset, node.endOffset)
                Text(
                    text = rawHtml,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 4.dp),
                )
            }

            else -> {
                if (childNodes.isEmpty()) {
                } else {
                    for (child in childNodes) {
                        ASTNodeRenderer(
                            node = child,
                            getRawTextOfRange = getRawTextOfRange,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun AnnotatedString.Builder.appendStyledText(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
) {
    if (node.children.isEmpty() && (node.type == MarkdownTokenTypes.TEXT || node.type == MarkdownTokenTypes.CODE_LINE || node.type == MarkdownTokenTypes.SETEXT_CONTENT)) {
        append(getRawTextOfRange(node.startOffset, node.endOffset))
        return
    }

    node.children.forEach { child ->
        when (child.type) {
            MarkdownTokenTypes.TEXT,
            MarkdownTokenTypes.COLON,
            MarkdownTokenTypes.SETEXT_CONTENT,
                -> {
                append(getRawTextOfRange(child.startOffset, child.endOffset))
            }

            MarkdownElementTypes.EMPH -> {
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    appendStyledText(child, getRawTextOfRange)
                }
            }

            MarkdownElementTypes.STRONG -> {
                withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                    appendStyledText(child, getRawTextOfRange)
                }
            }

            MarkdownElementTypes.CODE_SPAN -> {
                withStyle(SpanStyle(fontFamily = FontFamily.Monospace, color = MaterialTheme.colorScheme.onSurfaceVariant)) {
                    pushStyle(
                        SpanStyle(
                            background = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.08f,
                            ),
                        ),
                    )
                    child.children.forEach { codeChild ->
                        if (codeChild.type == MarkdownTokenTypes.TEXT || codeChild.type == MarkdownTokenTypes.CODE_LINE) {
                            append(getRawTextOfRange(codeChild.startOffset, codeChild.endOffset))
                        } else {
                            appendStyledText(codeChild, getRawTextOfRange)
                        }
                    }
                    pop()
                }
            }

            MarkdownElementTypes.INLINE_LINK, MarkdownElementTypes.FULL_REFERENCE_LINK, MarkdownElementTypes.SHORT_REFERENCE_LINK -> {
                val linkTextNode = child.findChildOfType(MarkdownElementTypes.LINK_TEXT)

                val linkText = linkTextNode?.let {
                    buildAnnotatedString { appendStyledText(it, getRawTextOfRange) }.text
                } ?: getRawTextOfRange(child.startOffset, child.endOffset).trim()

                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                    append(linkText)
                }
            }

            MarkdownTokenTypes.AUTOLINK, MarkdownTokenTypes.EMAIL_AUTOLINK -> {
                val content = getRawTextOfRange(child.startOffset, child.endOffset)
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)) {
                    append(content)
                }
            }

            MarkdownTokenTypes.HARD_LINE_BREAK -> {
                append("\n")
            }

            MarkdownTokenTypes.WHITE_SPACE -> {
                append(" ")
            }

            MarkdownTokenTypes.ATX_HEADER,
            MarkdownTokenTypes.SETEXT_1,
            MarkdownTokenTypes.SETEXT_2,
            MarkdownTokenTypes.LBRACKET,
            MarkdownTokenTypes.RBRACKET,
            MarkdownTokenTypes.LPAREN,
            MarkdownTokenTypes.RPAREN,
            MarkdownTokenTypes.LT,
            MarkdownTokenTypes.GT,
            MarkdownTokenTypes.EXCLAMATION_MARK,
            MarkdownTokenTypes.SINGLE_QUOTE,
            MarkdownTokenTypes.DOUBLE_QUOTE,
            MarkdownTokenTypes.BACKTICK,
            MarkdownElementTypes.LINK_LABEL,
            MarkdownElementTypes.LINK_DESTINATION,
            MarkdownElementTypes.LINK_TITLE,
            MarkdownElementTypes.LINK_TEXT,
                -> {
                if (child.children.isNotEmpty()) {
                    appendStyledText(child, getRawTextOfRange)
                }
            }

            else -> {
                if (child.children.isNotEmpty()) {
                    appendStyledText(child, getRawTextOfRange)
                }
            }
        }
    }
}
