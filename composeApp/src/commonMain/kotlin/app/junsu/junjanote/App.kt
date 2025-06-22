package app.junsu.junjanote

import androidx.compose.runtime.Composable
import app.junsu.junjanote.common.ui.Destination
import app.junsu.junjanote.common.ui.DestinationIcon
import app.junsu.junjanote.common.ui.image.getAsyncImageLoader
import app.junsu.junjanote.common.ui.theme.NoteTheme
import app.junsu.junjanote.ui.Post
import app.junsu.junjanote.ui.PostScreen
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    NoteTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

        PostScreen(
            post = Post(
                markdownRawUrl = "https://gist.githubusercontent.com/mufid/4062574/raw/300fb2535bcb1c6766cd990777a3b929abb42572/markdown-syntax.md",
            ),
        )
    }
}

enum class NoteDestinations(
    override val route: String,
    override val icon: DestinationIcon? = null,
) : Destination {
    MAIN(
        route = "/main",
    ),
    POST(
        route = "/post",
    ),
}
