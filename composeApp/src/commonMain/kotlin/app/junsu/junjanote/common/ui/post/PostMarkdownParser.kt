package app.junsu.junjanote.common.ui.post

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.intellij.markdown.ast.ASTNode

typealias GetRawTextOfRangeCallback = (startOffset: Int, endOffset: Int) -> String

@Composable
fun LazyListScope.markdownPostSheetItems(
    nodes: List<ASTNode>,
    getRawTextOfRange: GetRawTextOfRangeCallback,
    key: ((index: Int, item: ASTNode) -> Any)? = null,
) {
    this.itemsIndexed(
        items = nodes,
        key = key,
    ) { index, item ->
        item.buildMarkdownPostSheetItem(
            node = item,
            getRawTextOfRange = getRawTextOfRange,
        )
    }
}

@Composable
tailrec fun ASTNode.buildMarkdownPostSheetItem(
    node: ASTNode,
    getRawTextOfRange: GetRawTextOfRangeCallback,
) {
    if (node.children.isEmpty())
        return

}

@Composable
fun String.buildMarkdownTagType(
    modifier: Modifier = Modifier,
) {
    val typeLowercased = this.lowercase()


}

enum class MarkdownTagType(
    val value: String,
) {
    EOL(
        value = "eol",
    ),
    TEXT(
        value = "text",
    ),
    PARAGRAPH(
        value = "paragraph",
    ),

    ATX_HEADER_1(
        value = "atx_header_1",
    ),
    ATX_HEADER_2(
        value = "atx_header_2",
    ),
    ATX_HEADER_3(
        value = "atx_header_3",
    ),
    ATX_HEADER_4(
        value = "atx_header_4",
    ),
    ATX_HEADER_5(
        value = "atx_header_5",
    ),
    ATX_HEADER_6(
        value = "atx_header_6",
    ),

    BOLD(
        value = "bold",
    ),
    ITALIC(
        value = "italic",
    ),
    STRIKETHROUGH(
        value = "strikethrough",
    ),

    UNORDERED_LIST_ITEM(
        value = "unordered_list_item",
    ),
    ORDERED_LIST_ITEM(
        value = "ordered_list_item",
    ),

    INLINE_CODE(
        value = "inline_code",
    ),
    CODE_BLOCK(
        value = "code_block",
    ),

    INLINE_LINK(
        value = "inline_link",
    ),
    IMAGE(
        value = "image",
    ),

    BLOCKQUOTE(
        value = "blockquote",
    ),
    HORIZONTAL_RULE(
        value = "horizontal_rule",
    ),
}
