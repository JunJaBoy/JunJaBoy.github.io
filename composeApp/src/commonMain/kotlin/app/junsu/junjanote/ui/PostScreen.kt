package app.junsu.junjanote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.junsu.junjanote.common.ui.corner.SmoothCornerShape
import app.junsu.junjanote.common.ui.layout.safeMaxWidth
import app.junsu.junjanote.common.ui.post.markdownPostSheetItems
import app.junsu.junjanote.common.ui.post.postSheetFooterItem
import app.junsu.junjanote.common.ui.post.postSheetHeaderItem
import coil3.compose.AsyncImage
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.launch
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.parser.MarkdownParser

data class Post(
    val markdownRawUrl: String,
)

@Composable
fun PostScreen(
    post: Post,
    modifier: Modifier = Modifier,
) {
    val client = remember { HttpClient() }
    var text by remember { mutableStateOf<String?>(null) }
    var md by remember { mutableStateOf<ASTNode?>(null) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            text = client.get(post.markdownRawUrl).bodyAsText()
            val flavour = GFMFlavourDescriptor()
            val parsedTree = MarkdownParser(flavour).buildMarkdownTreeFromString(text!!)
            md = parsedTree
        }
    }

    if (md == null || text == null) return CircularProgressIndicator()

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            SelectionContainer {
                LazyColumn(
                    modifier = Modifier.safeMaxWidth(),
                    contentPadding = PaddingValues(
                        vertical = 32.0.dp,
                    ),
                ) {
                    postSheetHeaderItem(
                        thumbnail = {
                            AsyncImage(
                                model = "https://upload.wikimedia.org/wikipedia/commons/6/61/San_Francisco_from_the_Marin_Headlands_in_August_2022.jpg",
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxWidth().height(
                                    512.dp,
                                ),
                                onError = {
                                    it.result.throwable.printStackTrace()
                                },
                            )
                        },
                        title = {
                            Text("This is Text Title")
                        },
                        subtitle = {
                            Text("Subtitle looks good")
                        },
                        description = {
                            Text("And this is caption")
                        },
                    )
                    markdownPostSheetItems(
                        nodes = md!!.children,
                        getRawTextOfRange = { startOffset, endOffset ->
                            text!!.substring(startOffset until endOffset).trim()
                        },
                        key = { _, item ->
                            item.hashCode()
                        },
                    )
                    postSheetFooterItem {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(all = 8.0.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 8.dp,
                                    alignment = Alignment.End,
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                TextButton(
                                    onClick = {},
                                    shape = SmoothCornerShape(
                                        all = 16.0.dp,
                                    ),
                                    modifier = Modifier.wrapContentHeight().widthIn(min = 200.0.dp),
                                    contentPadding = PaddingValues(all = 16.0.dp),
                                ) {
                                    Icon(
                                        Icons.AutoMirrored.Default.ArrowLeft,
                                        contentDescription = null,
                                    )
                                    Text("Previous Post")
                                }
                                TextButton(
                                    onClick = {},
                                    shape = SmoothCornerShape(
                                        all = 16.0.dp,
                                    ),
                                    modifier = Modifier.wrapContentHeight().widthIn(min = 200.0.dp),
                                    contentPadding = PaddingValues(all = 16.0.dp),
                                ) {
                                    Text("Next Post")
                                    Icon(
                                        Icons.AutoMirrored.Default.ArrowRight,
                                        contentDescription = null,
                                    )
                                }

                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(
                                    space = 8.dp,
                                    alignment = Alignment.End,
                                ),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                IconButton(
                                    onClick = {},
                                ) {
                                    Icon(
                                        Icons.Default.Share,
                                        contentDescription = null,
                                    )
                                }
                                IconButton(
                                    onClick = {},
                                ) {
                                    Icon(
                                        Icons.Default.Favorite,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
