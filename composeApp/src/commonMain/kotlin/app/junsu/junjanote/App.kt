package app.junsu.junjanote

import androidx.compose.runtime.Composable
import app.junsu.junjanote.common.ui.theme.NoteTheme
import app.junsu.junjanote.common.ui.Destination
import app.junsu.junjanote.common.ui.SelectableDestinationIcon
import app.junsu.junjanote.common.ui.image.getAsyncImageLoader
import app.junsu.junjanote.ui.MainScreen
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    NoteTheme {
        setSingletonImageLoaderFactory { context ->
            getAsyncImageLoader(context)
        }

        MainScreen()
    }
}

enum class NoteDestinations(
    override val route: String,
    override val icon: SelectableDestinationIcon,
) : Destination
