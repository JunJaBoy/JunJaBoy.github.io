package app.junsu.junjanote.common.ui.post

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.intellij.markdown.IElementType
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
fun ASTNodeRenderer(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
) {
    val childNodes = node.children

    when (node.type) {
        MarkdownElementTypes.IMAGE -> {
            val imageUrlNode = node.children.find { child ->
                child.type == MarkdownElementTypes.INLINE_LINK
            }!!.children.find { child ->
                child.type == MarkdownElementTypes.LINK_DESTINATION
            }!!

            val imageUrl = StringBuilder()
            imageUrl.append(
                getRawTextOfRange(imageUrlNode.startOffset, imageUrlNode.endOffset),
            )
            AsyncImage(
                model = imageUrl.toString(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.postSheetItem(),
                onError = {
                    it.result.throwable.printStackTrace()
                },
            )
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
            val headerText = StringBuilder()
            node.children.forEach { child ->
                if (child.type != MarkdownTokenTypes.ATX_HEADER && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                    headerText.append(getRawTextOfRange(child.startOffset, child.endOffset))
                }
            }
            Column(
                modifier = Modifier.postSheetItem().padding(
                    top = 32.0.dp,
                    bottom = 16.0.dp,
                ),
            ) {
                Text(
                    text = headerText.toString(),
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
            val headerText = StringBuilder()
            node.children.forEach { child ->
                if (child.type != MarkdownTokenTypes.ATX_HEADER && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                    headerText.append(
                        getRawTextOfRange(
                            child.startOffset,
                            child.endOffset,
                        ),
                    )
                }
            }
            Column(
                modifier = Modifier.postSheetItem().padding(
                    top = 32.0.dp,
                    bottom = 16.0.dp,
                ),
            ) {
                Text(
                    text = headerText.toString(),
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
            val headerText = StringBuilder()
            node.children.forEach { child ->
                if (child.type != MarkdownTokenTypes.ATX_HEADER && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                    headerText.append(
                        getRawTextOfRange(
                            child.startOffset,
                            child.endOffset,
                        ),
                    )
                }
            }
            Text(
                text = headerText.toString(),
                modifier = Modifier.postSheetItem().padding(vertical = 16.0.dp),
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
            val headerText = StringBuilder()
            node.children.forEach { child ->
                if (child.type != MarkdownTokenTypes.ATX_HEADER && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                    headerText.append(
                        getRawTextOfRange(
                            child.startOffset,
                            child.endOffset,
                        ),
                    )
                }
            }
            Text(
                text = headerText.toString(),
                modifier = Modifier.postSheetItem().padding(vertical = 16.0.dp),
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
            val headerText = StringBuilder()
            node.children.forEach { child ->
                if (child.type != MarkdownTokenTypes.ATX_HEADER && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                    headerText.append(
                        getRawTextOfRange(
                            child.startOffset,
                            child.endOffset,
                        ),
                    )
                }
            }
            Text(
                text = headerText.toString(),
                modifier = Modifier.postSheetItem().padding(vertical = 16.0.dp),
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
            val headerText = StringBuilder()
            node.children.forEach { child ->
                if (child.type != MarkdownTokenTypes.ATX_HEADER && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                    headerText.append(
                        getRawTextOfRange(
                            child.startOffset,
                            child.endOffset,
                        ),
                    )
                }
            }
            Text(
                text = headerText.toString(),
                modifier = Modifier.postSheetItem().padding(vertical = 16.0.dp),
            )
        }


        MarkdownElementTypes.PARAGRAPH, MarkdownTokenTypes.TEXT -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        ) {
            val text = StringBuilder()
            node.children.forEach { child ->
                if (child.type != MarkdownTokenTypes.ATX_HEADER && child.type != MarkdownTokenTypes.WHITE_SPACE) {
                    text.append(
                        getRawTextOfRange(
                            child.startOffset,
                            child.endOffset,
                        ),
                    )
                }
            }
            Text(
                text = text.toString(),
                modifier = Modifier.postSheetItem(),
            )
        }


        MarkdownElementTypes.LIST_ITEM -> {
            val paragraphText = StringBuilder()
            node.children.forEach { child ->
                paragraphText.append(
                    getRawTextOfRange(
                        child.startOffset,
                        child.endOffset,
                    ),
                )
            }
            MarkdownTokenTypes.LIST_BULLET.buildMarkdownTagType(
                text = paragraphText.toString(),
            )
        }

        else -> if (childNodes.isEmpty()) {
            node.type.buildMarkdownTagType(
                text = getRawTextOfRange(
                    node.startOffset,
                    node.endOffset,
                ),
            )
        } else {
//            for (child in childNodes) {
//                ASTNodeRenderer(
//                    node = child,
//                    getRawTextOfRange = getRawTextOfRange,
//                )
//            }
        }
    }
}

@Composable
fun IElementType.buildMarkdownTagType(
    modifier: Modifier = Modifier,
    text: String,
) {
    when (this) {
        MarkdownTokenTypes.TEXT,
        MarkdownTokenTypes.SINGLE_QUOTE,
        MarkdownTokenTypes.DOUBLE_QUOTE,
        MarkdownTokenTypes.COLON,

        MarkdownTokenTypes.EOL,
        MarkdownTokenTypes.WHITE_SPACE,
            -> Spacer(modifier = Modifier)

//        MarkdownTokenTypes.CODE_LINE -> "A line of code (often inside a code block)"
//        MarkdownTokenTypes.BLOCK_QUOTE -> "Blockquote character (e.g., '>')"
//        MarkdownTokenTypes.HTML_BLOCK_CONTENT -> "Content inside an HTML block"
//        MarkdownTokenTypes.LPAREN -> "Left parenthesis character ( ( )"
//        MarkdownTokenTypes.RPAREN -> "Right parenthesis character ( ) )"
//        MarkdownTokenTypes.LBRACKET -> Text("NONONONO~~")
//        MarkdownTokenTypes.RBRACKET -> "Right square bracket character ( ] )"
//        MarkdownTokenTypes.LT -> "Less than character ( < )"
//        MarkdownTokenTypes.GT -> "Greater than character ( > )"
//        MarkdownTokenTypes.EXCLAMATION_MARK -> "Exclamation mark character ( ! )"
//        MarkdownTokenTypes.HARD_LINE_BREAK -> "Explicit hard line break (two spaces + EOL)"
        MarkdownTokenTypes.HARD_LINE_BREAK -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        ) {
            Text(
                text = "\nj",
                modifier = modifier.postSheetItem(),
            )
        }
//        MarkdownTokenTypes.LINK_ID -> "Identifier for a reference link"
//        MarkdownTokenTypes.ATX_HEADER -> "==="
//        MarkdownTokenTypes.ATX_CONTENT -> "HIHI"


        MarkdownTokenTypes.SETEXT_CONTENT -> Text("HIHI " + text)
//        MarkdownTokenTypes.EMPH -> "Emphasis delimiter ( * or _ )"
//        MarkdownTokenTypes.BACKTICK -> "Backtick character ( ` )"
//        MarkdownTokenTypes.ESCAPED_BACKTICKS -> "Escaped backticks (e.g., for inline code with backticks)"
        MarkdownTokenTypes.LIST_BULLET -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        ) {
            Row(
                modifier = modifier.postSheetItem(),
                horizontalArrangement = Arrangement.spacedBy(4.0.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = text.replaceFirst("-", "• "),
                    modifier = Modifier.weight(1f),
                )
            }
        }

        MarkdownTokenTypes.URL -> AsyncImage(
            model = text,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(512.dp).clip(shape = RoundedCornerShape(16.dp)),
            onError = { it.result.throwable.printStackTrace() },
        )

        MarkdownTokenTypes.HORIZONTAL_RULE -> HorizontalDivider(
            modifier = Modifier.postSheetItem().padding(
                vertical = 32.0.dp,
            ),
        )
//        MarkdownTokenTypes.LIST_NUMBER -> "Ordered list number (e.g., '1.')"
//        MarkdownTokenTypes.FENCE_LANG -> "Language specifier for a code fence"
//        MarkdownTokenTypes.CODE_FENCE_START -> "Start delimiter of a code fence ( ` ` ` or ~ ~ ~ )"
//        MarkdownTokenTypes.CODE_FENCE_CONTENT -> "Content lines within a code fence"
//        MarkdownTokenTypes.CODE_FENCE_END -> "End delimiter of a code fence"
//        MarkdownTokenTypes.LINK_TITLE -> "Title attribute of a link"
//        MarkdownTokenTypes.AUTOLINK -> "An automatically recognized link (e.g., <http://example.com>)"
//        MarkdownTokenTypes.EMAIL_AUTOLINK -> "An automatically recognized email link (e.g., <mailto:a@b.com>)"
//        MarkdownTokenTypes.HTML_TAG -> "Raw HTML tag"
//        MarkdownTokenTypes.BAD_CHARACTER -> "An unrecognized or invalid character"
        else -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurface,
            ),
        ) {
            TextButton(
                onClick = {},
                modifier = Modifier.postSheetItem(),
                contentPadding = PaddingValues(),
            ) {
                Text(
                    "TYPE ${this@buildMarkdownTagType.name} NOT HANDLED.",
                )
            }
        }
    }
}
