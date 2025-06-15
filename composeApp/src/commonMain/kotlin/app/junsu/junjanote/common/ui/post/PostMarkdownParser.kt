package app.junsu.junjanote.common.ui.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.intellij.markdown.IElementType
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
    ) { index, node ->
        node.buildMarkdownPostSheetItem(
            getRawTextOfRange = getRawTextOfRange,
        )
    }
}

@Composable
tailrec fun ASTNode.buildMarkdownPostSheetItem(
    getRawTextOfRange: GetRawTextOfRangeCallback,
) {
    val childNodes = this.children
    if (childNodes.isEmpty()) {
        return this.type.buildMarkdownTagType(
            text = getRawTextOfRange(this.startOffset, this.endOffset),
        )
    }

    for (child in childNodes) {
        return child.buildMarkdownPostSheetItem(
            getRawTextOfRange = getRawTextOfRange,
        )
    }
}

@Composable
fun IElementType.buildMarkdownTagType(
    modifier: Modifier = Modifier,
    text: String,
) {
    when (this) {
        MarkdownTokenTypes.TEXT -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography
                .bodyLarge,
        ) {
            Text(
                text = text,
                modifier = modifier.postSheetItem(),
                style = TextStyle(
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }

//        MarkdownTokenTypes.CODE_LINE -> "A line of code (often inside a code block)"
//        MarkdownTokenTypes.BLOCK_QUOTE -> "Blockquote character (e.g., '>')"
//        MarkdownTokenTypes.HTML_BLOCK_CONTENT -> "Content inside an HTML block"
//        MarkdownTokenTypes.SINGLE_QUOTE -> "A single quote character (' )"
//        MarkdownTokenTypes.DOUBLE_QUOTE -> "A double quote character (\" )"
//        MarkdownTokenTypes.LPAREN -> "Left parenthesis character ( ( )"
//        MarkdownTokenTypes.RPAREN -> "Right parenthesis character ( ) )"
//        MarkdownTokenTypes.LBRACKET -> "Left square bracket character ( [ )"
//        MarkdownTokenTypes.RBRACKET -> "Right square bracket character ( ] )"
//        MarkdownTokenTypes.LT -> "Less than character ( < )"
//        MarkdownTokenTypes.GT -> "Greater than character ( > )"
//        MarkdownTokenTypes.COLON -> "Colon character ( : )"
//        MarkdownTokenTypes.EXCLAMATION_MARK -> "Exclamation mark character ( ! )"
//        MarkdownTokenTypes.HARD_LINE_BREAK -> "Explicit hard line break (two spaces + EOL)"
//        MarkdownTokenTypes.EOL -> "End of line character (newline)"
//        MarkdownTokenTypes.LINK_ID -> "Identifier for a reference link"
        MarkdownTokenTypes.ATX_HEADER -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography
                .titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
        ) {
            Text(
                text = text,
                modifier = modifier.postSheetItem(),
                style = TextStyle(
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
            )
        }

        MarkdownTokenTypes.ATX_CONTENT -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography
                .titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = text,
                    modifier = Modifier.postSheetItem(),
                    style = TextStyle(
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
                HorizontalDivider(
                    modifier = Modifier.postSheetItem(),
                )
            }
        }
//        MarkdownTokenTypes.SETEXT_1 -> "Setext Level 1 heading underline ( = )"
//        MarkdownTokenTypes.SETEXT_2 -> "Setext Level 2 heading underline ( - )"
//        MarkdownTokenTypes.SETEXT_CONTENT -> "Content of a Setext header"
//        MarkdownTokenTypes.EMPH -> "Emphasis delimiter ( * or _ )"
//        MarkdownTokenTypes.BACKTICK -> "Backtick character ( ` )"
//        MarkdownTokenTypes.ESCAPED_BACKTICKS -> "Escaped backticks (e.g., for inline code with backticks)"
//        MarkdownTokenTypes.LIST_BULLET -> "List item bullet ( - , * , or + )"
//        MarkdownTokenTypes.URL -> "URL string"
//        MarkdownTokenTypes.HORIZONTAL_RULE -> "Horizontal rule characters ( --- , *** , ___ )"
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
//        MarkdownTokenTypes.WHITE_SPACE -> "Whitespace character(s)"
        else -> CompositionLocalProvider(
            value = LocalTextStyle provides MaterialTheme.typography
                .bodyMedium,
        ) {
            TextButton(
                onClick = {},
            ) {
                Text(
                    "TYPE ${this@buildMarkdownTagType.name} NOT HANDLED.",
                    modifier = Modifier.postSheetItem(),
                )
            }
        }
    }
}
