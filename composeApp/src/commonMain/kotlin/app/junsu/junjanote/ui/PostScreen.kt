package app.junsu.junjanote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import app.junsu.junjanote.common.ui.layout.safeMaxWidth
import app.junsu.junjanote.common.ui.postSheetFooterItem
import app.junsu.junjanote.common.ui.postSheetHeaderItem
import app.junsu.junjanote.common.ui.postSheetItem
import coil3.compose.AsyncImage

@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
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
                items(count = 25) { index ->
                    Text(
                        text = "HIHI TEXT $index",
                        modifier = Modifier.postSheetItem(),
                    )
                    Spacer(
                        modifier = Modifier.postSheetItem(),
                    )
                }
                postSheetFooterItem {
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
