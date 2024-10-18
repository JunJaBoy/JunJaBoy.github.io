package app.junsu.junjanote

import androidx.compose.runtime.Composable
import app.junsu.junjanote.common.ui.theme.NoteTheme
import app.junsu.junjanote.common.ui.Destination
import app.junsu.junjanote.common.ui.SelectableDestinationIcon
import app.junsu.junjanote.ui.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    NoteTheme {
        MainScreen()
    }
}

enum class NoteDestinations(
    override val route: String,
    override val icon: SelectableDestinationIcon,
) : Destination
