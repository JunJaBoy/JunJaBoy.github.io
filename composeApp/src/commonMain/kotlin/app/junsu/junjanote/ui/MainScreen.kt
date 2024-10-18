package app.junsu.junjanote.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import app.junsu.junjanote.common.ui.Destination
import app.junsu.junjanote.common.ui.SelectableDestinationIcon
import junjanote.composeapp.generated.resources.Res
import junjanote.composeapp.generated.resources.aboutMe_route_name
import junjanote.composeapp.generated.resources.home_route_name
import junjanote.composeapp.generated.resources.posts_route_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
) {
    val (selectedRoute, onSelectRoute) = remember {
        mutableStateOf(MainDestinations.HOME)
    }

    Scaffold(
        modifier = modifier,
    ) { paddingValues ->
        Row(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
        ) {
            MainNavigationRail(
                selectedRoute = selectedRoute,
                onSelectRoute = onSelectRoute,
            )
            LazyColumn {

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainNavigationRail(
    selectedRoute: MainDestinations,
    onSelectRoute: (MainDestinations) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(
        header = {
            FloatingActionButton(
                onClick = {

                },
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    // TODO
                    contentDescription = null,
                )
            }
        },
    ) {
        MainDestinations.entries.forEach { destination ->
            val selected = destination == selectedRoute
            NavigationRailItem(
                selected = selected,
                onClick = { onSelectRoute(destination) },
                icon = {
                    Icon(
                        imageVector = destination.icon.run {
                            if (selected) {
                                selectedIcon
                            } else {
                                unselectedIcon
                            }
                        },
                        contentDescription = destination.text,
                    )
                },
            )
        }
        NavigationRailItem(
            selected = false,
            onClick = {

            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    // TODO
                    contentDescription = null,
                )
            },
        )
    }
}


enum class MainDestinations(
    override val route: String,
    override val icon: SelectableDestinationIcon,
) : Destination {
    HOME(
        route = "home",
        icon = SelectableDestinationIcon(
            unselectedIcon = Icons.Outlined.Home,
            selectedIcon = Icons.Filled.Home,
        ),
    ),
    POSTS(
        route = "posts",
        icon = SelectableDestinationIcon(
            unselectedIcon = Icons.AutoMirrored.Outlined.List,
            selectedIcon = Icons.AutoMirrored.Filled.List,
        ),
    ),
    ABOUT_ME(
        route = "about-me",
        icon = SelectableDestinationIcon(
            unselectedIcon = Icons.Outlined.Info,
            selectedIcon = Icons.Filled.Info,
        ),
    ),
    ;
}

private val MainDestinations.text: String
    @Composable
    get() = stringResource(
        resource = when (this) {
            MainDestinations.HOME -> Res.string.home_route_name
            MainDestinations.POSTS -> Res.string.posts_route_name
            MainDestinations.ABOUT_ME -> Res.string.aboutMe_route_name
        },
    )
