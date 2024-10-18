package app.junsu.junjanote.common.ui

import androidx.compose.ui.graphics.vector.ImageVector

interface Destination {
    val route: String
    val icon: DestinationIcon?
}

abstract class DestinationIcon

class SelectableDestinationIcon(
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) : DestinationIcon()

class SingleDestinationIcon(
    val icon: ImageVector,
)
